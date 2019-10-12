package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetInstallTemplateDao;
import com.antiy.asset.entity.AssetInstallTemplate;
import com.antiy.asset.entity.PatchInfo;
import com.antiy.asset.intergration.ActivityClient;
import com.antiy.asset.service.IAssetHardSoftLibService;
import com.antiy.asset.service.IAssetInstallTemplateService;
import com.antiy.asset.util.BeanConvert;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.vo.enums.AssetInstallTemplateStatusEnum;
import com.antiy.asset.vo.query.ActivityWaitingQuery;
import com.antiy.asset.vo.query.AssetInstallTemplateQuery;
import com.antiy.asset.vo.query.PrimaryKeyQuery;
import com.antiy.asset.vo.request.*;
import com.antiy.asset.vo.request.SysArea;
import com.antiy.asset.vo.response.*;
import com.antiy.biz.entity.SysMessageRequest;
import com.antiy.biz.message.SysMessageSender;
import com.antiy.common.base.*;
import com.antiy.common.encoder.AesEncoder;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.utils.*;
import org.apache.commons.compress.utils.Lists;
import org.apache.kafka.common.protocol.types.Field;
import org.apache.zookeeper.OpResult;
import org.aspectj.bridge.MessageUtil;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.MessageUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public IAssetHardSoftLibService iAssetHardSoftLibService;
    @Resource
    private ActivityClient activityClient;
//    @Resource
//    private SysMessageSender sysMessageSender;
    @Resource
    private AesEncoder aesEncoder;
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
    public ActionResponse updateAssetInstallTemplate(AssetInstallTemplateRequest request) throws Exception {
        boolean isUpdateStatusOnly = request.getIsUpdateStatus() == 0;
        Integer templateId = request.getId();
        int currentStatus = assetInstallTemplateDao.getById(templateId.toString()).getCurrentStatus();
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
            if (!assetInstallTemplateDao.updateStatus(assetInstallTemplate).equals(0)) {
                return ActionResponse.success("更新状态成功");
            }
            return ActionResponse.fail(RespBasicCode.BUSSINESS_EXCETION, "更新状态失败");
        }
        //编辑模板
        //todo 用户角色权限
        // Set<SysRole> roles=  LoginUserUtil.getLoginUser().getSysRoles();//.forEach(v->System.out.println(v.getName()));
        assetInstallTemplate.setCurrentStatus(AssetInstallTemplateStatusEnum.NOTAUDIT.getCode());
        assetInstallTemplate.setOperationSystemName(this.queryOs(request.getOperationSystem().toString()).get(0).getOsName());
        assetInstallTemplateDao.update(assetInstallTemplate);

        //以前关联的软件和现在关联的软件求差集
        Set<String> preSoftBusinessRelation = iAssetHardSoftLibService.querySoftsRelations(templateId.toString()).stream().map(v -> v.getBusinessId()).collect(Collectors.toSet());
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
            return sendTask(request, assetInstallTemplate);
        }
        return ActionResponse.fail(RespBasicCode.BUSSINESS_EXCETION, "编辑失败");
    }


    @Override
    public List<AssetInstallTemplateResponse> queryListAssetInstallTemplate(AssetInstallTemplateQuery query) throws Exception {
        List<AssetInstallTemplate> assetInstallTemplateList = assetInstallTemplateDao.findQuery(query);
        return responseConverter.convert(assetInstallTemplateList, AssetInstallTemplateResponse.class);
    }

    @Override
    public PageResult<AssetInstallTemplateResponse> queryPageAssetInstallTemplate(AssetInstallTemplateQuery query) throws Exception {
        String baselineId = query.getBaselineId();
        PageResult<AssetInstallTemplateResponse> responsePageResult = null;

        Integer count = baselineId == null ? this.findCount(query) : assetInstallTemplateDao.findFilteredCount(query);
        if (count == 0 || count == null) {

            return new PageResult<AssetInstallTemplateResponse>(query.getPageSize(), 0, query.getCurrentPage(), new ArrayList<AssetInstallTemplateResponse>());
        }
        Integer type = assetInstallTemplateDao.queryBaselineTemplateType(query);
        if (baselineId == null || (baselineId != null && (type == null || type != 2))) {
            responsePageResult = new PageResult<AssetInstallTemplateResponse>(query.getPageSize(), count, query.getCurrentPage(),
                    assetInstallTemplateDao.queryTemplateInfo(query));
        }
        //根据配置模板id过滤包含黑名单软件的装机模板
        else {
            responsePageResult = new PageResult<AssetInstallTemplateResponse>(query.getPageSize(), count, query.getCurrentPage(),
                    assetInstallTemplateDao.queryFilteredTemplate(query));
        }
        return responsePageResult;
    }

    @Override
    public AssetInstallTemplateResponse queryAssetInstallTemplateById(QueryCondition queryCondition) throws Exception {
        ParamterExceptionUtils.isBlank(queryCondition.getPrimaryKey(), "主键Id不能为空");
        AssetInstallTemplateResponse assetInstallTemplateResponse = responseConverter.convert(
                assetInstallTemplateDao.getById(queryCondition.getPrimaryKey()), AssetInstallTemplateResponse.class);
        return assetInstallTemplateResponse;
    }

    @Override
    public String deleteAssetInstallTemplateById(BatchQueryRequest request) throws Exception {
        ParamterExceptionUtils.isEmpty(request.getIds(), "主键Id不能为空");
        int count = assetInstallTemplateDao.batchDeleteTemplate(request.getIds(), System.currentTimeMillis(),
                LoginUserUtil.getLoginUser().getName());
        if (count < request.getIds().size()) {
            return "状态为拒绝的模板才能被删除";
        }
        return "删除成功，请刷新界面";
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
    public ActionResponse submitTemplateInfo(AssetInstallTemplateRequest request) throws Exception {
        if (request.getStringId() == null) {
            String unknowId = "0";
            request.setStringId(unknowId);
        }
        request.setCreateUser(LoginUserUtil.getLoginUser().getId().toString());
        request.setGmtCreate(System.currentTimeMillis());
        AssetInstallTemplate template = requestConverter.convert(request, AssetInstallTemplate.class);
        template.setCategoryModel(1);
        template.setCurrentStatus(AssetInstallTemplateStatusEnum.NOTAUDIT.getCode());
        template.setOperationSystemName(this.queryOs(request.getOperationSystem().toString()).get(0).getOsName());
        assetInstallTemplateDao.insert(template);
        request.setStringId(template.getStringId());
        if (request.getPatchIds() != null && !request.getPatchIds().isEmpty()) {
            assetInstallTemplateDao.insertBatchPatch(request);
        }
        if (request.getSoftBussinessIds() != null && !request.getSoftBussinessIds().isEmpty()) {
            assetInstallTemplateDao.insertBatchSoft(request);
        }
        this.insertCheckTemplateInfo(request);
        return sendTask(request, template);
    }

    @Override
    @Transactional
    public String checkTemplate(AssetInstallTemplateCheckRequest request) throws Exception {
        Integer loginUserId = LoginUserUtil.getLoginUser().getId();
        ActivityWaitingQuery query = new ActivityWaitingQuery();
        query.setUser(loginUserId.toString());
        query.setProcessDefinitionKey("installTemplate");
        List<WaitingTaskReponse> waitingTaskReponseList = activityClient.queryAllWaitingTask(query).getBody().stream().filter(v -> v.getBusinessId().equals(request.getStringId())).collect(Collectors.toList());
        if (waitingTaskReponseList.size() == 0) {
            throw new BusinessException("非法操作");
        }
        if (waitingTaskReponseList.size() > 1) {
            throw new BusinessException("任务重复");
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
        ActivityHandleRequest handleRequest = new ActivityHandleRequest();
        handleRequest.setTaskId(waitingTaskReponseList.get(0).getTaskId());
        Map<String, String> map = new HashMap<>();
        map.put("checkResult", String.valueOf(checkResult));
        handleRequest.setFormData(map);
        if (result != null && result == 1) {
            activityClient.completeTask(handleRequest);
            return "审核结果提交成功";
        }
        return "审核结果提交失败";
    }

    private ActionResponse sendTask(AssetInstallTemplateRequest request, AssetInstallTemplate template) {
        ManualStartActivityRequest activityRequest = new ManualStartActivityRequest();
        activityRequest.setAssignee(LoginUserUtil.getLoginUser().getStringId());
        activityRequest.setBusinessId(template.getStringId());
        activityRequest.setFormData(request.getFormData());
        activityRequest.setProcessDefinitionKey("installTemplate");
        return activityClient.manualStartProcess(activityRequest);
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

    private void sendMessage(AssetInstallTemplateRequest request) {

      List<SysMessageRequest> messageRequestList= request.getNextExecutor().stream().map(v->{
            SysMessageRequest messageRequest=new SysMessageRequest();
            messageRequest.setOrigin(1);
            messageRequest.setSendUserId(LoginUserUtil.getLoginUser().getId());
            messageRequest.setReceiveUserId(Integer.valueOf(aesEncoder.decode(v,LoginUserUtil.getLoginUser().getName())));
            messageRequest.setContent("您有一条由orang2提交的[模板审核]任务,请尽快处理");
            messageRequest.setTopic("模板审核");
            return  messageRequest;
        }).collect(Collectors.toList());
    //    sysMessageSender.batchSendMessage(messageRequestList);
    }
}
