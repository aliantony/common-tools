package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetBusinessRelationDao;
import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.AssetLinkRelationDao;
import com.antiy.asset.dao.AssetOperationRecordDao;
import com.antiy.asset.dto.StatusJumpAssetInfo;
import com.antiy.asset.entity.Asset;
import com.antiy.asset.entity.AssetOperationRecord;
import com.antiy.asset.entity.RollbackEntity;
import com.antiy.asset.intergration.ActivityClient;
import com.antiy.asset.intergration.BaseLineClient;
import com.antiy.asset.service.IAssetStatusJumpService;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.util.EnumUtil;
import com.antiy.asset.vo.enums.*;
import com.antiy.asset.vo.request.*;
import com.antiy.asset.vo.response.AssetCorrectIInfoResponse;
import com.antiy.biz.entity.SysMessageRequest;
import com.antiy.biz.message.SysMessageSender;
import com.antiy.common.base.*;
import com.antiy.common.encoder.AesEncoder;
import com.antiy.common.enums.BusinessModuleEnum;
import com.antiy.common.enums.BusinessPhaseEnum;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.utils.*;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @auther: zhangyajun
 * @date: 2019/1/23 15:38
 * @description: 硬件资产状态跃迁
 */
@Service
public class AssetStatusJumpServiceImpl implements IAssetStatusJumpService {
    private static final Logger logger = LogUtils.get(AssetStatusJumpServiceImpl.class);
    @Resource
    private AssetDao assetDao;
    @Resource
    private AssetLinkRelationDao assetLinkRelationDao;
    @Resource
    private AssetOperationRecordDao assetOperationRecordDao;

    @Resource
    private AssetBusinessRelationDao assetBusinessRelationDao;

    @Resource
    private AesEncoder aesEncoder;
    @Resource
    private ActivityClient activityClient;
    @Resource
    private TransactionTemplate transactionTemplate;
    @Resource
    private BaseLineClient baseLineClient;
    @Resource
    private AssetServiceImpl assetService;
    @Resource
    private AssetEntryServiceImpl entryService;
    @Autowired
    private HttpServletRequest servletRequest;
    @Resource
    private BaseConverter baseConverter;
    @Resource
    private SysMessageSender messageSender;
    private Object lock = new Object();

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ActionResponse changeStatus(AssetStatusJumpRequest statusJumpRequest) throws Exception {
        LogUtils.info(logger, "资产状态处理开始,参数request:{}", statusJumpRequest);
        LoginUser loginUser = LoginUserUtil.getLoginUser();

        // 配置调接口时是加密类型的id,(不用@Encode)使用手动解密
        if (loginUser != null) {
            statusJumpRequest.getAssetInfoList().forEach(e -> {
                // 不是数字类型,进行解密
                if (!StringUtils.isNumeric(e.getAssetId())) {
                    e.setAssetId(aesEncoder.decode(e.getAssetId(), loginUser.getUsername()));
                }
            });
        }

        // 1.校验参数信息,当前流程的资产是否都满足当前状态
        List<Integer> assetIdList = statusJumpRequest.getAssetInfoList().stream().map(e -> DataTypeUtils.stringToInteger(e.getAssetId())).collect(Collectors.toList());

        List<Asset> assetsInDb = assetDao.findByIds(assetIdList);
        BusinessExceptionUtils.isTrue(CollectionUtils.isNotEmpty(assetsInDb) && assetIdList.size() == assetsInDb.size(), "所选资产已被操作,请刷新后重试");

        // 当前所有资产的可执行操作与当前状态一致
        assetsInDb.forEach(asset -> {
            if (!asset.getAssetStatus().equals(statusJumpRequest.getAssetFlowEnum().getCurrentAssetStatus().getCode())) {
                LogUtils.info(logger, "资产状态不匹配 request:{}", statusJumpRequest);
                throw new BusinessException("当前选中的资产已被其他人员操作,请刷新页面后重试");
            }
        });

        // 2.不是变更完成,提交至工作流
        if (!AssetFlowEnum.CHANGE_COMPLETE.equals(statusJumpRequest.getAssetFlowEnum())) {
            // 先更改为下一个状态,后续失败进行回滚
//            setInProcess(statusJumpRequest, assetsInDb);

            List<String> procInstIds = startActivity(statusJumpRequest, loginUser);
            try{
                updateData(statusJumpRequest, assetsInDb);
            }catch (Exception e){
                ActionResponse actionResponse= baseLineClient.deleteProcessInstance(procInstIds);
                if (actionResponse == null ||!actionResponse.getHead().getCode().equals(RespBasicCode.SUCCESS.getResultCode())) {
                    LogUtils.error(logger, "删除流程失败");
                    throw new BusinessException("删除工作流失败");
                }
                throw new BusinessException(e.getMessage());
            }
            if(statusJumpRequest.getAssetFlowEnum().equals(AssetFlowEnum.RETIRE_EXECUTEE) &&statusJumpRequest.getAgree().equals(true)){
                /**
                 * 配置
                 */
                ActionResponse actionResponse= baseLineClient.removeAsset(assetIdList);
                if (null == actionResponse || !RespBasicCode.SUCCESS.getResultCode().equals(actionResponse.getHead().getCode())) {
                    throw new BusinessException("资产退役调用漏洞模块失败");
                }
                /**
                 * 准入
                 */
                List<ActivityHandleRequest> requestList = new ArrayList<>();
                    statusJumpRequest.getAssetInfoList().forEach(assetInfo -> {
                    ActivityHandleRequest activityHandleRequest = new ActivityHandleRequest();

                    activityHandleRequest.setId(assetInfo.getAssetId());

                    requestList.add(activityHandleRequest);
                });
                AssetEntryRequest assetEntryRequest=new AssetEntryRequest();
                assetEntryRequest.setAssetActivityRequests(requestList);
                assetEntryRequest.setUpdateStatus(DataTypeUtils.integerToString(AssetEnterStatusEnum.NO_ENTER.getCode()));
                assetEntryRequest.setEntrySource(AssetEntrySourceEnum.ASSET_RETIRE);
                entryService.updateEntryStatus(assetEntryRequest);
            }

        } else {
            for (int i = 0; i < assetsInDb.size(); i++) {
                //变更失败回滚信息并发送消息 只针对操作系统、组件、软件信息的回滚(不适用所有)
                boolean isSuccess = statusJumpRequest.getAssetInfoList().get(i).isSuccess();
                Asset asset = assetsInDb.get(i);
                String assetId =asset.getStringId();
                if (!isSuccess) {
                    AssetRollbackRequest rollbackRequest = new AssetRollbackRequest();
                    List<RollbackEntity> rollbackEntities = assetDao.queryRollackInnfo(assetId);
                    rollbackRequest.setAssetId(assetId);
                    rollbackRequest.setGmtModified(System.currentTimeMillis());
                    rollbackRequest.setModifyUser(Objects.isNull(LoginUserUtil.getLoginUser()) ? "0" : LoginUserUtil.getLoginUser().getStringId());
                    rollbackRequest.setRollBackInfo(baseConverter.convert(rollbackEntities, AssetRollbackRequest.RollBack.class));
                    assetDao.startRollback(rollbackRequest);

                    //消息发送到变更人
                    StringBuilder content = new StringBuilder();
                    content.append("由于");
                    HashSet<String> addInfo = new HashSet<>();
                    HashSet<String> deleteInfo = new HashSet<>();
                    HashSet<String> updateInfo = new HashSet<>();
                    rollbackEntities.forEach(v -> {
                        if (EnumUtil.equals(v.getOperationType(), OperationTypeEnum.ADD)) {
                            addInfo.add(v.getName());
                        } else if (EnumUtil.equals(v.getOperationType(), OperationTypeEnum.DELETE)) {
                            deleteInfo.add(v.getName());
                        } else if (EnumUtil.equals(v.getOperationType(), OperationTypeEnum.UPDATE)
                                && "operation_system_name".equals(v.getFiledName())) {
                            updateInfo.add(v.getFiledValue());
                        }
                    });
                    if (CollectionUtils.isNotEmpty(addInfo)) {
                        content.append("增加:");
                        addInfo.forEach(v->content.append(v).append(","));
                        content.replace(content.length() - 1,content.length()," ");
                    }
                    if (CollectionUtils.isNotEmpty(deleteInfo)) {
                        content.append("删除:");
                        deleteInfo.forEach(v->content.append(v).append(","));
                        content.replace(content.length() - 1,content.length()," ");
                    }
                    if (CollectionUtils.isNotEmpty(updateInfo)) {
                        content.append("变更:");
                        updateInfo.forEach(v->content.append(v));
                        content.append("操作系统为").append(asset.getOperationSystemName());
                    }
                    content.append("导致配置失败,").append(asset.getName()).append("资产变更失败.");
                    SysMessageRequest request = new SysMessageRequest();
                    request.setTopic("代办任务");
                    request.setSummary("资产变更");
                    request.setContent(content.toString());
                    request.setSendUserId(Objects.isNull(LoginUserUtil.getLoginUser()) ? 0 : LoginUserUtil.getLoginUser().getId());
                    request.setOrigin(1);//资产管理来源
                    request.setReceiveUserId(asset.getModifyUser());
                    request.setOther("{\"id\":" + asset.getStringId() + "}");
                    messageSender.sendMessage(request);
                }
                //变更成功触发漏扫
                else  {
                    Integer needVulScan = assetOperationRecordDao.getNeedVulScan(DataTypeUtils.stringToInteger(assetId));
                    if (needVulScan != null && needVulScan == 1) {
                        try {
                            ActionResponse actionResponse = baseLineClient.scan(assetId);
                            LogUtils.info(logger, "调用漏洞模块,assetId:{}, 结果:{}", assetId, JsonUtil.object2Json(actionResponse));
                            if (null == actionResponse || !RespBasicCode.SUCCESS.getResultCode().equals(actionResponse.getHead().getCode())) {
                                LogUtils.error(logger, "调用漏洞模块失败");
                            }
                        } catch (Exception ex) {
                            LogUtils.error(logger, "调用漏洞模块异常:{}", ex);
                        }
                    }
                }
            }
            updateData(statusJumpRequest, assetsInDb);
        }

        // 3.数据库操作
        // updateData(statusJumpRequest, assetsInDb);
        // 4.记录操作日志
        if (!AssetFlowEnum.CHANGE_COMPLETE.equals(statusJumpRequest.getAssetFlowEnum())) {
            assetsInDb.forEach(asset -> LogUtils.recordOperLog(new BusinessData(statusJumpRequest.getAssetFlowEnum().getNextOperaLog(),
                    asset.getId(), asset.getNumber(), statusJumpRequest, BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.NONE)));
        }
        return ActionResponse.success();
    }


    @Override
    public ActionResponse statusJump(AssetStatusJumpRequest statusJumpRequest) throws Exception {

        return null;
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer continueNetIn(String primaryKey) {
        Integer result = assetDao.updateAssetStatusById(primaryKey, AssetStatusEnum.NET_IN_LEADER_CHECK.getCode());
        assetBusinessRelationDao.updateSourceByassetId(primaryKey);
        return result;
    }

    private AssetCorrectIInfoResponse correctingAssetOfbaseLine(ActionResponse<AssetCorrectIInfoResponse> baseLineResponse,ActionResponse<AssetCorrectIInfoResponse> vlunResponse,String assetId) throws Exception {
        AssetCorrectIInfoResponse assetCorrectIInfoResponse = vlunResponse.getBody();
        //资产漏洞或配置模块 “修复”失败
        if(baseLineResponse.getBody().equals("成功") &&assetCorrectIInfoResponse.getFailureCount()>0 || baseLineResponse.getBody().equals("失败")){
            assetCorrectIInfoResponse.setNeedManualPush("1");

        }
        //资产漏洞和配置模块 “修复”成功
        else if(baseLineResponse.getBody().equals("成功") &&assetCorrectIInfoResponse.getFailureCount()<=0 ){
            // 改变资产状态
            Asset asset=new Asset();
            asset.setId(DataTypeUtils.stringToInteger(assetId));
            asset.setAssetStatus(AssetStatusEnum.NET_IN.getCode());
            assetDao.update(asset);
            assetCorrectIInfoResponse.setNeedManualPush("0");

        }
        //配置未完成
        else{
            assetCorrectIInfoResponse.setNeedManualPush("0");
        }
        assetCorrectIInfoResponse.setCheckStatus(baseLineResponse.getBody().getCheckStatus());
        assetCorrectIInfoResponse.setConfigStatus(baseLineResponse.getBody().getConfigStatus());
        return  assetCorrectIInfoResponse;
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AssetCorrectIInfoResponse assetCorrectingInfo(AssetCorrectingRequest activityHandleRequest) throws Exception {
        Asset assetOfDB = assetDao.getById(activityHandleRequest.getStringId());
        if(assetOfDB==null){
            throw new BusinessException("资产不存在！");
        }
        ActionResponse<AssetCorrectIInfoResponse> vlunResponse= baseLineClient.situationOfVul(activityHandleRequest.getStringId());

        if (null == vlunResponse || !RespBasicCode.SUCCESS.getResultCode().equals(vlunResponse.getHead().getCode())) {
            LogUtils.error(logger, "调用漏洞模块失败");
            throw  new BusinessException("调用漏洞模块失败");
        }
        AssetCorrectIInfoResponse assetCorrectIInfoResponse = vlunResponse.getBody();

        //整改流程--漏洞步骤处于进行中状态
        if(!assetCorrectIInfoResponse.getScan()||! assetCorrectIInfoResponse.getDeal()){
            assetCorrectIInfoResponse.setNeedManualPush("0");
            return  assetCorrectIInfoResponse;
        }
        ActionResponse actionResponse = vlunActivity(assetCorrectIInfoResponse, activityHandleRequest.getBaseLineActivity());
        if (null == actionResponse || !RespBasicCode.SUCCESS.getResultCode().equals(actionResponse.getHead().getCode())) {
            LogUtils.error(logger, "资产整改漏洞工作流异常!");
            throw  new BusinessException("资产整改漏洞工作流异常");
        }
        //整改流程--漏洞步骤完成  -进入配置流程（计算机设备 ）
        ActionResponse<AssetCorrectIInfoResponse> baseLineResponse=baseLineClient.rectification( "assetId");
        if (null == baseLineResponse || !RespBasicCode.SUCCESS.getResultCode().equals(baseLineResponse.getHead().getCode())) {
            LogUtils.error(logger, "调用配置模块失败");
            throw  new BusinessException("调用配置模块失败");
        }
        if(AssetCategoryEnum.COMPUTER.getCode().equals(assetOfDB.getCategoryModel())){
            //配置工作流
            ActionResponse baseLineActivityResponse = baseLineActivity(baseLineResponse, activityHandleRequest.getBaseLineActivity());
            if (null == baseLineActivityResponse || !RespBasicCode.SUCCESS.getResultCode().equals(baseLineActivityResponse.getHead().getCode())) {
                LogUtils.error(logger, "资产整改配置工作流异常!");
                throw  new BusinessException("资产整改配置工作流异常");
            }
            return  correctingAssetOfbaseLine(baseLineResponse,vlunResponse,activityHandleRequest.getStringId());
        }
        //整改流程--漏洞步骤完成  （非计算机设备 ）
        if(assetCorrectIInfoResponse.getFailureCount()<=0){
            //改变资产状态
            Asset asset=new Asset();
            asset.setId(DataTypeUtils.stringToInteger(activityHandleRequest.getStringId()));
            asset.setAssetStatus(AssetStatusEnum.NET_IN.getCode());
            assetDao.update(asset);
            assetCorrectIInfoResponse.setNeedManualPush("0");
        }else {
            assetCorrectIInfoResponse.setNeedManualPush("1");
        }
        return  assetCorrectIInfoResponse;
    }

    private ActionResponse baseLineActivity(ActionResponse<AssetCorrectIInfoResponse> baseLineResponse, ActivityHandleRequest activityHandleRequest) {
        Map<String,String> formData=new HashMap<>(1);
        formData.put("baselineRectifyResult","success");
        activityHandleRequest.setFormData(formData);
        if(baseLineResponse.getBody().equals(AssetBaseLineEnum.SUCCESS.getMsg())){
             return activityClient.completeTask(activityHandleRequest);
        }else if(baseLineResponse.getBody().equals(AssetBaseLineEnum.FALI.getMsg())) {
            formData.put("baselineRectifyResult","fail");
            return activityClient.completeTask(activityHandleRequest);
        }
        return ActionResponse.success();
    }
    private ActionResponse vlunActivity( AssetCorrectIInfoResponse assetCorrectIInfoResponse,ActivityHandleRequest activityHandleRequest){

        Map<String,String> formData=new HashMap<>(1);
        formData.put("vulRectifyResult","success");
        activityHandleRequest.setFormData(formData);
        if(assetCorrectIInfoResponse.getFailureCount()<=0){
            return activityClient.completeTask(activityHandleRequest);
        }else {
            formData.put("vulRectifyResult","fail");
           return activityClient.completeTask(activityHandleRequest);
        }
    }
    private void setInProcess(AssetStatusJumpRequest statusJumpRequest, List<Asset> assetsInDb) {
        Integer lastAssetStatus = statusJumpRequest.getAssetFlowEnum().getCurrentAssetStatus().getCode();

        List<Asset> updateAssetList = new ArrayList<>(assetsInDb.size());
        assetsInDb.forEach(e -> {
            Asset asset = new Asset();
            asset.setId(e.getId());
            asset.setAssetStatus(AssetStatusJumpEnum.getNextStatus(statusJumpRequest.getAssetFlowEnum(), statusJumpRequest.getAgree()).getCode());
            updateAssetList.add(asset);
        });

        transactionTemplate.execute(transactionStatus -> {
            updateAssetList.forEach(asset -> {
                try {
                    int effectRow = assetDao.updateAssetStatusWithLock(asset, lastAssetStatus);
                    if (effectRow <= 0) {
                        throw new BusinessException("资产更新失败");
                    }
                } catch (Exception e) {
                    transactionStatus.setRollbackOnly();
                    LogUtils.warn(logger, "资产更新失败{}", e);
                    throw new BusinessException("操作失败,请刷新页面后重试");
                }
            });
            return null;
        });
    }

    private List<String>  startActivity(AssetStatusJumpRequest assetStatusRequest, LoginUser loginUser) throws Exception {
        ParamterExceptionUtils.isNull(assetStatusRequest.getFormData(), "formData参数错误");
        //整改后面做
        // 为满足需求,同时工作流模块无法达到要求;"整改"不通过退回至待检查时,重置formData:将执行意见改为1,将下一步人设置为上一步"检查"的操作人
        if (assetStatusRequest.getAssetFlowEnum().equals(AssetFlowEnum.CORRECT)
                && Boolean.FALSE.equals(assetStatusRequest.getWaitCorrectToWaitRegister())) {
            // 整改是单个资产
            Integer lastCheckUser = assetOperationRecordDao.getCreateUserByAssetId(DataTypeUtils.stringToInteger(assetStatusRequest.getAssetInfoList().get(0).getAssetId()));
            Map<String, Object> formData = new HashMap<>(1);
            formData.put("safetyChangeResult", "1");
            formData.put("safetyCheckUser", aesEncoder.encode(lastCheckUser.toString(), loginUser.getUsername()));
            assetStatusRequest.setFormData(formData);
        }
        // 1.退役申请需要启动流程,其他步骤完成流程
        if (AssetFlowEnum.RETIRE_APPLICATION.equals(assetStatusRequest.getAssetFlowEnum())
                || AssetFlowEnum.SCRAP_APPLICATION.equals(assetStatusRequest.getAssetFlowEnum())
        ) {
            // 启动流程
            List<StatusJumpAssetInfo> assetInfoList = assetStatusRequest.getAssetInfoList();
            List<String> procInstIds=new ArrayList<>();
            for (StatusJumpAssetInfo assetInfo : assetInfoList) {
                ManualStartActivityRequest manualStartActivityRequest = new ManualStartActivityRequest();
                manualStartActivityRequest.setAssignee(loginUser.getId().toString());
                manualStartActivityRequest.setBusinessId(assetInfo.getAssetId());
                if (AssetFlowEnum.RETIRE_APPLICATION.equals(assetStatusRequest.getAssetFlowEnum())) {
                    manualStartActivityRequest.setProcessDefinitionKey(AssetActivityTypeEnum.ASSET_RETIRE.getCode());
                } else {
                    manualStartActivityRequest.setProcessDefinitionKey(AssetActivityTypeEnum.ASSET_SCRAP.getCode());
                }
                manualStartActivityRequest.setFormData(assetStatusRequest.getFormData());

                ActionResponse actionResponse = activityClient.manualStartProcess(manualStartActivityRequest);

                LogUtils.info(logger, "请求工作流参数:{},请求工作流结果: {}", manualStartActivityRequest, JsonUtil.object2Json(actionResponse));
                if (actionResponse == null) {
                    throw new BusinessException("启动工作流失败");

                }
                if (!actionResponse.getHead().getCode().equals(RespBasicCode.SUCCESS.getResultCode())) {
                    throw new BusinessException((String) actionResponse.getBody());
                }
                procInstIds.add((String)actionResponse.getBody());
            }
            return  procInstIds;

        } else {
            // 非启动退役流程
            List<ActivityHandleRequest> requestList = new ArrayList<>();
            assetStatusRequest.getAssetInfoList().forEach(assetInfo -> {
                ActivityHandleRequest activityHandleRequest = new ActivityHandleRequest();
                // 由于工作流传参接收问题,此处不能直接使用同一个formData对象,必须new新对象
                activityHandleRequest.setFormData(new HashMap<Object, Object>(assetStatusRequest.getFormData()));
                activityHandleRequest.setTaskId(assetInfo.getTaskId());

                requestList.add(activityHandleRequest);
            });

                ActionResponse actionResponse = activityClient.completeTaskBatch(requestList);
                LogUtils.info(logger, "请求工作流结果: {}", JsonUtil.object2Json(actionResponse));
                if (actionResponse == null) {
                    throw new BusinessException("启动工作流失败");

                }
                if (!actionResponse.getHead().getCode().equals(RespBasicCode.SUCCESS.getResultCode())) {
                    throw new BusinessException(actionResponse.getBody().toString());
                }
            return Lists.newArrayList();
        }
    }

    /**
     * 更新数据库:资产、操作记录<br>
     * 如果是退役,删除通联关系
     *
     * @param statusJumpRequest
     * @param assetsInDb        库中的数据
     */
    private void updateData(AssetStatusJumpRequest statusJumpRequest, List<Asset> assetsInDb) {
        List<AssetOperationRecord> operationRecordList = new ArrayList<>();
        List<Asset> updateAssetList = new ArrayList<>();
        List<Integer> deleteLinkRelationIdList = new ArrayList<>();

        Long currentTime = System.currentTimeMillis();
        LoginUser loginUser;
        // 变更->配置完成可能没有token',没有用户信息;保存record表需要使用该字段,默认为0存储
        if (Objects.isNull(loginUser = LoginUserUtil.getLoginUser())) {
            loginUser = new LoginUser();
            loginUser.setId(0);
            loginUser.setName("");
        }

        for (Asset asset : assetsInDb) {
            AssetStatusEnum nextStatus = statusJumpRequest.getAgree() == true ? statusJumpRequest.getAssetFlowEnum().getAgreeStatus()
                    : statusJumpRequest.getAssetFlowEnum().getRefuseStatus();
            if (AssetFlowEnum.RETIRED.equals(statusJumpRequest.getAssetFlowEnum())) {
                // 退役删除通联关系
                deleteLinkRelationIdList.add(asset.getId());
            }
            asset.setAssetStatus(nextStatus.getCode());
            asset.setGmtModified(currentTime);
            asset.setModifyUser(loginUser.getId());
            updateAssetList.add(asset);

            // 保存操作记录
            operationRecordList.add(convertAssetOperationRecord(statusJumpRequest, currentTime, loginUser.getId(), loginUser.getName(), asset.getStringId(), nextStatus.getCode()));
        }
        assetOperationRecordDao.insertBatch(operationRecordList);
        assetDao.updateAssetBatch(updateAssetList);
        if (CollectionUtils.isNotEmpty(deleteLinkRelationIdList)) {
            assetLinkRelationDao.deleteByAssetIdList(deleteLinkRelationIdList);
        }

    }

    private AssetOperationRecord convertAssetOperationRecord(AssetStatusJumpRequest statusJumpRequest, Long currentTime, Integer loginUserId, String loginUserName, String assetId, Integer nextStatus) {
        AssetOperationRecord assetOperationRecord = new AssetOperationRecord();
        assetOperationRecord.setOriginStatus(statusJumpRequest.getAssetFlowEnum().getCurrentAssetStatus().getCode());
        assetOperationRecord.setTargetStatus(nextStatus);
        assetOperationRecord.setContent(statusJumpRequest.getAssetFlowEnum().getNextOperaLog());
        assetOperationRecord.setTargetObjectId(assetId);
        assetOperationRecord.setGmtCreate(currentTime);
        assetOperationRecord.setOperateUserId(loginUserId);
        if(AssetFlowEnum.NET_IN_CHECK.equals(statusJumpRequest.getAssetFlowEnum())
                || AssetFlowEnum.RETIRE_CHECK.equals(statusJumpRequest.getAssetFlowEnum())
                ||AssetFlowEnum.SCRAP_CHECK.equals(statusJumpRequest.getAssetFlowEnum())){
            assetOperationRecord.setProcessResult(Boolean.TRUE.equals(statusJumpRequest.getAgree()) ? 1 : 0);
        }else {
            assetOperationRecord.setProcessResult(null);
        }
        assetOperationRecord.setOperateUserName(loginUserName);
        assetOperationRecord.setCreateUser(loginUserId);
        assetOperationRecord.setNote(statusJumpRequest.getNote() == null ? "" : statusJumpRequest.getNote());
        assetOperationRecord.setFileInfo(statusJumpRequest.getFileInfo() == null ? "" : statusJumpRequest.getFileInfo());
        assetOperationRecord.setCheckUserId(statusJumpRequest.getCheckUserId()==null ?0:DataTypeUtils.stringToInteger(statusJumpRequest.getCheckUserId()));
        assetOperationRecord.setCheckUserName(statusJumpRequest.getCheckUserName()==null?"":statusJumpRequest.getCheckUserName());
        assetOperationRecord.setExecuteUserId(statusJumpRequest.getExecuteUserId()==null?0:DataTypeUtils.stringToInteger(statusJumpRequest.getExecuteUserId()));
        assetOperationRecord.setExecuteUserName(statusJumpRequest.getExecuteUserName()==null?"":statusJumpRequest.getExecuteUserName());
        if (AssetFlowEnum.RETIRE_APPLICATION.equals(statusJumpRequest.getAssetFlowEnum())
                || AssetFlowEnum.RETIRE_DISAGREE_APPLICATION.equals(statusJumpRequest.getAssetFlowEnum())
        ) {
            Integer taskId = getTaksIdByBusinessKey(AssetActivityTypeEnum.ASSET_RETIRE.getCode() + "-" + assetId);
            assetOperationRecord.setTaskId(taskId);
        }
        if( AssetFlowEnum.SCRAP_APPLICATION.equals(statusJumpRequest.getAssetFlowEnum())
             ||AssetFlowEnum.SCRAP_DISAGREE_APPLICATION.equals(statusJumpRequest.getAssetFlowEnum())
        ){
           /* try{
                Integer taskId = getTaksIdByBusinessKey(AssetActivityTypeEnum.ASSET_SCRAP.getCode() + "-" + assetId);
                assetOperationRecord.setTaskId(taskId);
            }catch (Exception e){
                throw new BusinessException(e.getMessage());
            }*/
        }
        return assetOperationRecord;
    }

    private Integer getTaksIdByBusinessKey(String key){
        ActionResponse actionResponse=activityClient.getTaksIdByBusinessKey(key);
        if(actionResponse==null || !actionResponse.getHead().getCode().equals(RespBasicCode.SUCCESS.getResultCode())){
            throw new BusinessException("通过businessKey获取taksId 出错");
        }
        String taskId=(String)actionResponse.getBody();
        return  Integer.valueOf(taskId);
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String entryExecution(AssetEntryRequest request) {
        List<Integer> assetIds = request.getAssetActivityRequests().stream()
                .map(ActivityHandleRequest::getId).collect(Collectors.toList());
        List<Asset> oldAssets = assetDao.findByIds(assetIds);
        List<Asset> newAssets;
        synchronized (lock) {
            //并发处理 验证资产状态
            for (Asset oldAsset : oldAssets) {
                if (!EnumUtil.equals(assetDao.getByAssetId(oldAsset.getStringId()).getAssetStatus(), AssetStatusEnum.NET_IN_CHECK)) {
                    throw new BusinessException("编号为[" + oldAsset.getName() + "]的资产已被他人操作，请勿重复操作");
                }
            }
            newAssets = oldAssets.stream().map(v -> {
                v.setFirstEnterNett(System.currentTimeMillis());
                v.setModifyUser(LoginUserUtil.getLoginUser().getId());
                v.setGmtModified(System.currentTimeMillis());
                v.setAssetStatus(AssetStatusEnum.NET_IN.getCode());
                return v;
            }).collect(Collectors.toList());
            //更新资产状态
            assetDao.updateAssetBatch(newAssets);
        }
        //推动工作流
        ActionResponse response=activityClient.completeTaskBatch(request.getAssetActivityRequests());
        if (response==null || !response.getHead().getCode().equals(RespBasicCode.SUCCESS.getResultCode())){
            logger.warn("工作流参数：{}",request.getAssetActivityRequests());
            throw new BusinessException("调用工作流模块失败");
        }
        //记录操作日志
        newAssets.forEach(v -> LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ENTRY_EXECUTION.getName(), v.getId()
                , v.getNumber(), v, BusinessModuleEnum.ASSET_INFO_MANAGE, BusinessPhaseEnum.NET_IN)));
        //下发准入指令
        SecurityContext context=SecurityContextHolder.getContext();
        new Thread(() -> {
            SecurityContext finalContext = context;
            entryService.updateEntryStatus(request,finalContext );
        }).start();

        return "";
    }
}

