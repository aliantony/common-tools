package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetInstallTemplateDao;
import com.antiy.asset.entity.AssetInstallTemplate;
import com.antiy.asset.entity.PatchInfo;
import com.antiy.asset.intergration.ActivityClient;
import com.antiy.asset.service.IAssetHardSoftLibService;
import com.antiy.asset.service.IAssetInstallTemplateService;
import com.antiy.asset.util.BeanConvert;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.vo.enums.AssetEventEnum;
import com.antiy.asset.vo.enums.AssetInstallTemplateStatusEnum;
import com.antiy.asset.vo.enums.BaselineTemplateStatusEnum;
import com.antiy.asset.vo.query.ActivityWaitingQuery;
import com.antiy.asset.vo.query.AssetInstallTemplateQuery;
import com.antiy.asset.vo.query.PrimaryKeyQuery;
import com.antiy.asset.vo.request.*;
import com.antiy.asset.vo.response.*;
import com.antiy.biz.util.RedisKeyUtil;
import com.antiy.biz.util.RedisUtil;
import com.antiy.common.base.*;
import com.antiy.common.encoder.AesEncoder;
import com.antiy.common.enums.BusinessModuleEnum;
import com.antiy.common.enums.BusinessPhaseEnum;
import com.antiy.common.enums.ModuleEnum;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.utils.*;
import org.apache.commons.compress.utils.Lists;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p> 装机模板 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-09-16
 */
@Service
public class AssetInstallTemplateServiceImpl extends BaseServiceImpl<AssetInstallTemplate>
        implements IAssetInstallTemplateService {

    private Logger logger = LogUtils.get(this.getClass());

    @Resource
    private AssetInstallTemplateDao assetInstallTemplateDao;
    @Resource
    private BaseConverter<AssetInstallTemplateRequest, AssetInstallTemplate> requestConverter;
    @Resource
    private BaseConverter<AssetInstallTemplate, AssetInstallTemplateResponse> responseConverter;
    @Resource
    private BaseConverter<SysUser, AssetSysUserResponse> userConverter;
    @Resource
    public IAssetHardSoftLibService iAssetHardSoftLibService;
    @Resource
    private ActivityClient activityClient;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private AesEncoder aesEncoder;

    private static final String PROCESS_KEY_INSTALL = "installTemplate";
    private static final String AUTHORITY_EXCEPTION_PROMPT = "该项操作暂无权限，请联系管理员";

    @Override
    public List<AssetInstallTemplateOsResponse> queryTemplateOs() {
        return assetInstallTemplateDao.queryTemplateOs();
    }

    @Override
    public List<AssetInstallTemplateStatusResponse> queryTemplateStatus() {
        return assetInstallTemplateDao.queryTemplateStatus().stream().map(v -> new AssetInstallTemplateStatusResponse(v, AssetInstallTemplateStatusEnum.getEnumByCode(v).getStatus())).collect(Collectors.toList());
    }

    @Override
    public Integer queryNumberCode(String numberCode) {
        if (numberCode == null || numberCode.isEmpty()) {
            return 0;
        }
        return assetInstallTemplateDao.queryNumberCode(numberCode);
    }

    @Override
    public List<AssetInstallTemplateOsResponse> queryOs(String osBusinessId) {
        return assetInstallTemplateDao.queryOs(osBusinessId);
    }

    @Override
    public String saveAssetInstallTemplate(AssetInstallTemplateRequest request) throws Exception {
        AssetInstallTemplate assetInstallTemplate = requestConverter.convert(request, AssetInstallTemplate.class);
        assetInstallTemplateDao.insert(assetInstallTemplate);
        return assetInstallTemplate.getStringId();
    }

    @Override
    @Transactional
    public synchronized ActionResponse updateAssetInstallTemplate(AssetInstallTemplateRequest request) throws Exception {

        boolean isUpdateStatusOnly = request.getIsUpdateStatus() != null && request.getIsUpdateStatus() == 0;
        Integer templateId = request.getId();
        AssetInstallTemplate template = assetInstallTemplateDao.getById(templateId.toString());
        int currentStatus = template.getCurrentStatus();
        int requestStatus = request.getUpdateStatus() == null ? 0 : request.getUpdateStatus();
        int enableCode = AssetInstallTemplateStatusEnum.ENABLE.getCode();
        int forbiddenCode = AssetInstallTemplateStatusEnum.FOBIDDEN.getCode();
        int rejectCode = AssetInstallTemplateStatusEnum.REJECT.getCode();
        if (currentStatus != enableCode && currentStatus != forbiddenCode && currentStatus != rejectCode) {
            throw new RequestParamValidateException("非法操作");
        }
        request.setGmtModified(System.currentTimeMillis());
        request.setModifiedUser(LoginUserUtil.getLoginUser().getStringId());
        request.setGmtCreate(request.getGmtModified());
        request.setCreateUser(request.getModifiedUser());
        AssetInstallTemplate assetInstallTemplate = requestConverter.convert(request, AssetInstallTemplate.class);
        //设置启用/禁用
        if (isUpdateStatusOnly) {
            if ((requestStatus != enableCode && requestStatus != forbiddenCode) ||
                    (requestStatus == enableCode && currentStatus != forbiddenCode) ||
                    (requestStatus == forbiddenCode && currentStatus != enableCode)) {
                throw new BusinessException("状态非法修改");
            }
            if (requestStatus == enableCode) {
                LogUtils.recordOperLog(new BusinessData(AssetEventEnum.TEMPLATE_ENABLE.getName(),
                        templateId, template.getNumberCode(), assetInstallTemplate.toString(), BusinessModuleEnum.ASSET_INSTALL_TEMPLATE, BusinessPhaseEnum.NONE));
                LogUtils.info(logger, "启用装机模板:{}", assetInstallTemplate.toString());
            } else {
                LogUtils.recordOperLog(new BusinessData(AssetEventEnum.TEMPLATE_FORBIDDEN.getName(),
                        templateId, template.getNumberCode(), assetInstallTemplate.toString(), BusinessModuleEnum.ASSET_INSTALL_TEMPLATE, BusinessPhaseEnum.NONE));
                LogUtils.info(logger, "禁用装机模板:{}", assetInstallTemplate.toString());
            }
            if (!assetInstallTemplateDao.updateStatus(assetInstallTemplate).equals(0)) {

                return ActionResponse.success("更新状态成功");
            }
            return ActionResponse.fail(RespBasicCode.BUSSINESS_EXCETION, "更新状态失败");
        }

        //编辑模板
        ParamterExceptionUtils.isBlank(request.getName(), "模板名称必填");
        ParamterExceptionUtils.isBlank(request.getNumberCode(), "模板编号必填");
        ParamterExceptionUtils.isNull(request.getOperationSystem(), "操作系统必填");
        if (request.getSoftBussinessIds().isEmpty() && request.getPatchIds().isEmpty()) {
            throw new RequestParamValidateException("请至少选择一个软件或者一个补丁");
        }
        setTemplateInfo(request, assetInstallTemplate);
        assetInstallTemplateDao.update(assetInstallTemplate);

        //以前关联的软件和现在关联的软件求差集
        Set<String> preSoftBusinessRelation = iAssetHardSoftLibService.querySoftsRelations(templateId.toString()).stream().map(AssetHardSoftLibResponse::getBusinessId).collect(Collectors.toSet());
        //当前关联软件业务id集
        Set<String> curSoftBusinessRelation = request.getSoftBussinessIds();
        Set<String> delSofts = new HashSet<>(preSoftBusinessRelation);
        delSofts.removeAll(curSoftBusinessRelation);
        curSoftBusinessRelation.removeAll(preSoftBusinessRelation);
        if (!delSofts.isEmpty()) {
            request.setSoftBussinessIds(delSofts);
            assetInstallTemplateDao.deleteBatchSoft(request);
        }
        if (!curSoftBusinessRelation.isEmpty()) {
            request.setSoftBussinessIds(curSoftBusinessRelation);
            assetInstallTemplateDao.insertBatchSoft(request);
        }
        PrimaryKeyQuery query = new PrimaryKeyQuery();
        query.setPid(templateId.toString());
        Set<String> prePatchIdRelation = assetInstallTemplateDao.queryPatchIds(query);
        Set<String> curPatchIds = request.getPatchIds();
        Set<String> delPatchs = new HashSet<>(prePatchIdRelation);
        delPatchs.removeAll(curPatchIds);
        curPatchIds.removeAll(prePatchIdRelation);
        if (!delPatchs.isEmpty()) {
            request.setPatchIds(delPatchs);
            assetInstallTemplateDao.deleteBatchPatch(request);
        }
        if (!curPatchIds.isEmpty()) {
            request.setPatchIds(curPatchIds);
            assetInstallTemplateDao.insertBatchPatch(request);
        }
        this.insertCheckTemplateInfo(request);
        //将模板更新为待审核
        assetInstallTemplate.setCurrentStatus(AssetInstallTemplateStatusEnum.NOTAUDIT.getCode());
        assetInstallTemplate.setOperationSystemName(this.queryOs(request.getOperationSystem().toString()).get(0).getOsName());
        if (!assetInstallTemplateDao.updateStatus(assetInstallTemplate).equals(0)) {
            LogUtils.recordOperLog(new BusinessData(AssetEventEnum.TEMPLATE_MODIFY.getName(),
                    templateId, assetInstallTemplate.getNumberCode(), assetInstallTemplate.toString(), BusinessModuleEnum.ASSET_INSTALL_TEMPLATE, BusinessPhaseEnum.NONE));
            LogUtils.info(logger, "编辑装机模板:{}", request.toString());
            if (currentStatus == AssetInstallTemplateStatusEnum.REJECT.getCode()) {
                ParamterExceptionUtils.isBlank(request.getTaskId(), "非流程任务发起人不能编辑");
                ActionResponse actionResponse = activityClient.completeTask(setActivityHandleRequest(request.getFormData(), request.getTaskId()));
                if (null == actionResponse
                        || !RespBasicCode.SUCCESS.getResultCode().equals(actionResponse.getHead().getCode())) {
                    LogUtils.info(logger, "结束拒绝状态的模板工作流任务失败: {}", "模板编辑");
                    throw new BusinessException("调用流程引擎出错");
                }
                return actionResponse;
            }
            return sendTask(request, assetInstallTemplate);
        }
        return ActionResponse.fail(RespBasicCode.BUSSINESS_EXCETION, "编辑失败");
    }


    @Override
    public PageResult<AssetInstallTemplateResponse> queryPageAssetInstallTemplate(AssetInstallTemplateQuery query) throws Exception {
        String baselineId = query.getBaselineId();
        Integer count = null;
        Integer type = null;
        int index = 0;
        if (baselineId == null) {
            count = this.findCount(query);
        } else if ((type = assetInstallTemplateDao.queryBaselineTemplateType(query)).equals(BaselineTemplateStatusEnum.BLACK_ITEM.getCode())) {
            count = assetInstallTemplateDao.CountFilterBlackItemTemplate(query);
            index = 1;
        } else if (type.equals(BaselineTemplateStatusEnum.WHITE_ITEM.getCode())) {
            count = assetInstallTemplateDao.CountWhiteItemTemplate(query);
            index = 2;
        } else {
            count = this.findCount(query);
        }
        if (count == null || count == 0) {
            return new PageResult<>(query.getPageSize(), 0, query.getCurrentPage(), new ArrayList<AssetInstallTemplateResponse>());
        }
        List<AssetInstallTemplateResponse> responses = null;
        if (index == 0) {
            responses = assetInstallTemplateDao.queryTemplateInfo(query);
            List<WaitingTaskReponse> waitingTaskReponseList = queryTemplateTasksByLoginId();
            if (waitingTaskReponseList != null && !waitingTaskReponseList.isEmpty()) {
                responses.forEach(v -> v.setWaitingTask(queryTemplateTaskById(v.getStringId(), waitingTaskReponseList)));
            }
            return new PageResult<>(query.getPageSize(), count, query.getCurrentPage(),
                    responses);
        } else if (index == 1) {
            responses = assetInstallTemplateDao.FilterBlackItemTemplate(query);
        } else {
            responses = assetInstallTemplateDao.findWhiteItemTemplate(query);
        }
        return new PageResult<>(query.getPageSize(), count, query.getCurrentPage(),
                responses);
    }

    @Override
    public AssetInstallTemplateResponse queryAssetInstallTemplateById(QueryCondition queryCondition) throws Exception {

        ParamterExceptionUtils.isBlank(queryCondition.getPrimaryKey(), "主键Id不能为空");
        AssetInstallTemplate template = assetInstallTemplateDao.getById(queryCondition.getPrimaryKey());
        AssetInstallTemplateResponse assetInstallTemplateResponse = responseConverter.convert(
                template, AssetInstallTemplateResponse.class);
        List<AssetSysUserResponse> list = new ArrayList<>();
        String executor = template.getExecutor();
        if (executor.equalsIgnoreCase("all")) {
            assetInstallTemplateResponse.setExecutor(list);
        } else {
            list.add(getUserNameByUserId(Integer.valueOf(executor)));
            assetInstallTemplateResponse.setExecutor(list);
        }
        return assetInstallTemplateResponse;
    }

    @Override
    @Transactional
    public synchronized String deleteAssetInstallTemplateById(BatchQueryRequest request) {
        ParamterExceptionUtils.isEmpty(request.getIds(), "主键Id不能为空");
        ParamterExceptionUtils.isEmpty(request.getProcessInstanceIds(), "流程实例id不能为空");
        ParamterExceptionUtils.isTrue(request.getIds().size() == request.getProcessInstanceIds().size(), "请确保参数正确");
        int count = assetInstallTemplateDao.batchDeleteTemplate(request.getIds(), System.currentTimeMillis(),
                LoginUserUtil.getLoginUser().getName());
        List<String> processInstanceIds = request.getProcessInstanceIds().stream().filter(v -> !v.isEmpty()).collect(Collectors.toList());
        if (count != processInstanceIds.size()) {
            throw new RequestParamValidateException("请确保拒绝模板存在代办任务");
        }
        ActionResponse response = activityClient.deleteProcessInstance(processInstanceIds);
        if (null == response || !response.getHead().getCode().equals(RespBasicCode.SUCCESS.getResultCode())) {
            LogUtils.info(logger, "根据流程实例id集结束工作流出错: {}", processInstanceIds);
            throw new BusinessException("调用流程引擎出错");
        }
        if (count < request.getIds().size()) {
            return "已经删除状态为拒绝的模板";
        }
        StringBuilder ids = new StringBuilder();
        request.getIds().forEach(v -> {
            ids.append(v);
            ids.append(",");
        });
        ids.deleteCharAt(ids.length() - 1);
        LogUtils.recordOperLog(new BusinessData(AssetEventEnum.TEMPLATE_DELETE.getName(),
                ids.toString(), null, request.toString(), BusinessModuleEnum.ASSET_INSTALL_TEMPLATE, BusinessPhaseEnum.NONE));
        LogUtils.info(logger, "删除装机模板:{}", request.toString());
        return "删除成功";
    }

    @Override
    public AssetTemplateRelationResponse queryTemplateByAssetId(QueryCondition queryCondition) throws Exception {
        AssetTemplateRelationResponse templateRelationResponse = assetInstallTemplateDao
                .queryTemplateById(DataTypeUtils.stringToInteger(queryCondition.getPrimaryKey()));
        return templateRelationResponse;
    }

    @Override
    public PageResult<AssetHardSoftLibResponse> querySoftPage(PrimaryKeyQuery query) {
        Integer count = assetInstallTemplateDao.querySoftCount(query);
        if (count <= 0) {
            return new PageResult<>(query.getPageSize(), 0, query.getCurrentPage(), Lists.newArrayList());
        }
        List<AssetHardSoftLibResponse> assetHardSoftLibResponseList = BeanConvert
                .convert(assetInstallTemplateDao.querySoftList(query), AssetHardSoftLibResponse.class);
        return new PageResult<>(query.getPageSize(), count, query.getCurrentPage(), assetHardSoftLibResponseList);
    }

    @Override
    public PageResult<PatchInfoResponse> queryPatchPage(PrimaryKeyQuery query) {
        Integer count = assetInstallTemplateDao.queryPatchCount(query);
        if (count <= 0) {
            return new PageResult<>(query.getPageSize(), 0, query.getCurrentPage(), Lists.newArrayList());
        }
        List<PatchInfoResponse> patchInfoResponseList = BeanConvert
                .convert(assetInstallTemplateDao.queryPatchList(query), PatchInfoResponse.class);
        return new PageResult<>(query.getPageSize(), count, query.getCurrentPage(), patchInfoResponseList);
    }


    @Override
    public List<PatchInfoResponse> queryPatchs(String templateId) {

        List<PatchInfo> list = assetInstallTemplateDao.queryPatchRelations(templateId);
        if (list.isEmpty()) {
            return new ArrayList<>();
        }
        return BeanConvert
                .convert(list, PatchInfoResponse.class);
    }

    @Override
    @Transactional
    public synchronized ActionResponse submitTemplateInfo(AssetInstallTemplateRequest request) throws Exception {

        if (request.getSoftBussinessIds().isEmpty() && request.getPatchIds().isEmpty()) {
            throw new RequestParamValidateException("请至少选择一个软件或者一个补丁");
        }
        int result = queryNumberCode(request.getNumberCode());
        if (result > 0) {
            throw new RequestParamValidateException("模板编号已经存在");
        }
        if (request.getStringId() == null) {
            String unknowId = "0";
            request.setStringId(unknowId);
        }
        request.setCreateUser(LoginUserUtil.getLoginUser().getId().toString());
        request.setGmtCreate(System.currentTimeMillis());
        AssetInstallTemplate template = requestConverter.convert(request, AssetInstallTemplate.class);
        template.setCategoryModel(1);
        setTemplateInfo(request, template);
        assetInstallTemplateDao.insert(template);
        request.setStringId(template.getStringId());
        if (!request.getPatchIds().isEmpty()) {
            assetInstallTemplateDao.insertBatchPatch(request);
        }
        if (!request.getSoftBussinessIds().isEmpty()) {
            assetInstallTemplateDao.insertBatchSoft(request);
        }
        this.insertCheckTemplateInfo(request);
        LogUtils.recordOperLog(new BusinessData(AssetEventEnum.TEMPLATE_CREATION.getName(),
                template.getId(), request.getNumberCode(), template.toString(), BusinessModuleEnum.ASSET_INSTALL_TEMPLATE, BusinessPhaseEnum.NONE));
        LogUtils.info(logger, "创建装机模板:{}", template.toString());
        return sendTask(request, template);
    }

    private synchronized void setTemplateInfo(AssetInstallTemplateRequest request, AssetInstallTemplate template) {
        template.setCurrentStatus(AssetInstallTemplateStatusEnum.NOTAUDIT.getCode());
        template.setOperationSystemName(this.queryOs(request.getOperationSystem().toString()).get(0).getOsName());
        List<String> executors = request.getNextExecutor().stream().collect(Collectors.toList());
        if (executors.size() > 1) {
            template.setExecutor("all");
        } else if (executors.size() == 1) {
            template.setExecutor(aesEncoder.decode(executors.get(0), LoginUserUtil.getLoginUser().getUsername()));
        } else {
            throw new RequestParamValidateException("请选择下一步执行人");
        }
    }

    @Override
    @Transactional
    public synchronized String checkTemplate(AssetInstallTemplateCheckRequest request) throws Exception {

        Integer loginUserId = LoginUserUtil.getLoginUser().getId();
        ActivityWaitingQuery query = new ActivityWaitingQuery();
        query.setUser(loginUserId.toString());
        query.setProcessDefinitionKey(PROCESS_KEY_INSTALL);
        WaitingTaskReponse waitingTaskReponse = queryTemplateTaskById(request.getStringId(), queryTemplateTasksByLoginId());
        if (waitingTaskReponse == null) {
            throw new BusinessException(AUTHORITY_EXCEPTION_PROMPT);
        }

        Integer currentStatus = request.getResult() == 0 ? AssetInstallTemplateStatusEnum.REJECT.getCode() : AssetInstallTemplateStatusEnum.ENABLE.getCode();
        //更新模板检查表
        Integer checkResult = request.getResult();
        request.setResult(checkResult.equals(0) ? 2 : 3);
        request.setCreateUser(loginUserId.toString());
        request.setGmtCreate(System.currentTimeMillis());
        request.setUserId(loginUserId);
        Integer checkInfoResult = assetInstallTemplateDao.insertTemplateCheckInfo(request);
        if (checkInfoResult == null || checkInfoResult.equals(0)) {
            return "审核结果提交失败";
        }
        //更新模板表状态
        AssetInstallTemplate template = new AssetInstallTemplate();
        template.setId(request.getId());
        template.setCurrentStatus(currentStatus);
        template.setModifiedUser(loginUserId.toString());
        template.setGmtModified(request.getGmtCreate());
        Integer result = assetInstallTemplateDao.update(template);
        Map<String, String> map = new HashMap<>();
        map.put("checkResult", String.valueOf(checkResult));
        ActivityHandleRequest handleRequest = setActivityHandleRequest(map, waitingTaskReponse.getTaskId());
        QueryCondition queryCondition = new QueryCondition();
        queryCondition.setPrimaryKey(String.valueOf(template.getId()));
        AssetInstallTemplateResponse response = queryAssetInstallTemplateById(queryCondition);

        if (result != null && result == 1) {
            ActionResponse actionResponse = activityClient.completeTask(handleRequest);
            if (null == actionResponse
                    || !RespBasicCode.SUCCESS.getResultCode().equals(actionResponse.getHead().getCode())) {
                LogUtils.info(logger, "结束工作流任务失败: {}", "模板审核");
                throw new BusinessException("调用流程引擎出错");
            }
            LogUtils.recordOperLog(new BusinessData(AssetEventEnum.TEMPLATE_CHECK.getName(),
                    template.getId(), response.getNumberCode(), response.toString(), BusinessModuleEnum.ASSET_INSTALL_TEMPLATE, BusinessPhaseEnum.NONE));
            LogUtils.info(logger, "审核装机模板:{}", response.toString());
            return "审核结果提交成功";
        }
        return "审核结果提交失败";
    }

    @Override
    public Integer deleteBatchPatch(AssetInstallTemplateRequest request) {
        request.setGmtModified(System.currentTimeMillis());
        request.setGmtCreate(LoginUserUtil.getLoginUser().getId());
        return CollectionUtils.isEmpty(request.getPatchIds()) ? 0 : assetInstallTemplateDao.deleteBatchPatchByPatchCode(request);
    }

    private synchronized ActionResponse sendTask(AssetInstallTemplateRequest request, AssetInstallTemplate template) {
        ManualStartActivityRequest activityRequest = new ManualStartActivityRequest();
        activityRequest.setAssignee(LoginUserUtil.getLoginUser().getStringId());
        activityRequest.setBusinessId(template.getStringId());
        activityRequest.setFormData(request.getFormData());
        activityRequest.setProcessDefinitionKey(PROCESS_KEY_INSTALL);
        ActionResponse actionResponse = activityClient.manualStartProcess(activityRequest);
        // 如果流程引擎为空,直接返回错误信息
        if (null == actionResponse
                || !RespBasicCode.SUCCESS.getResultCode().equals(actionResponse.getHead().getCode())) {
            LogUtils.info(logger, "启动工作流失败: {}", PROCESS_KEY_INSTALL);
            throw new BusinessException("调用流程引擎出错");
        }
        return actionResponse;
    }

    private Integer insertCheckTemplateInfo(AssetInstallTemplateRequest request) {
        AssetInstallTemplateCheckRequest checkRequest = new AssetInstallTemplateCheckRequest();
        checkRequest.setStringId(request.getStringId());
        // 1提交审核
        checkRequest.setResult(1);
        checkRequest.setUserId(Integer.valueOf(request.getCreateUser()));
        checkRequest.setGmtCreate(request.getGmtCreate());
        checkRequest.setCreateUser(request.getCreateUser());
        return assetInstallTemplateDao.insertTemplateCheckInfo(checkRequest);
    }

    private AssetSysUserResponse getUserNameByUserId(Integer id) {
        // 获取redis key
        String key = RedisKeyUtil.getKeyWhenGetObject(ModuleEnum.SYSTEM.getType(), SysUser.class, id);
        SysUser user = null;
        try {
            user = redisUtil.getObject(key, SysUser.class);
        } catch (Exception e) {
            LogUtils.error(logger, "redis获取信息失败，用户信息：" + id + "获取失败");
        }
        AssetSysUserResponse sysUser = null;
        if (user == null) {
            sysUser = new AssetSysUserResponse();
            sysUser.setName("");
            sysUser.setUsername("");
            sysUser.setStringId(aesEncoder.encode(id.toString(), LoginUserUtil.getLoginUser().getUsername()));

        } else {
            sysUser = userConverter.convert(user, AssetSysUserResponse.class);
        }
        return sysUser;
    }

    private List<WaitingTaskReponse> queryTemplateTasksByLoginId() {
        ActivityWaitingQuery taskQuery = new ActivityWaitingQuery();
        taskQuery.setUser(LoginUserUtil.getLoginUser().getStringId());
        taskQuery.setProcessDefinitionKey(PROCESS_KEY_INSTALL);
        ActionResponse<List<WaitingTaskReponse>> actionResponse = activityClient.queryAllWaitingTask(taskQuery);
        // 如果流程引擎为空,直接返回错误信息
        if (null == actionResponse
                || !RespBasicCode.SUCCESS.getResultCode().equals(actionResponse.getHead().getCode())) {
            LogUtils.info(logger, "获取当前用户的所有代办任务失败" + " {}", PROCESS_KEY_INSTALL);
            throw new BusinessException("调用流程引擎出错");
        }
        return actionResponse.getBody();
    }

    private WaitingTaskReponse queryTemplateTaskById(String id, List<WaitingTaskReponse> waitingTaskReponseList) {
        List<WaitingTaskReponse> filter = waitingTaskReponseList.stream().filter(v -> v.getBusinessId().equals(id)).collect(Collectors.toList());
        if (filter.size() > 1) {
            throw new BusinessException("同一代办任务存在多条，脏数据未处理");
        }
        return filter.isEmpty() ? null : filter.get(0);

    }

    private ActivityHandleRequest setActivityHandleRequest(Map formData, String taskId) {
        ActivityHandleRequest handleRequest = new ActivityHandleRequest();
        handleRequest.setTaskId(taskId);
        handleRequest.setFormData(formData);
        return handleRequest;
    }

}
