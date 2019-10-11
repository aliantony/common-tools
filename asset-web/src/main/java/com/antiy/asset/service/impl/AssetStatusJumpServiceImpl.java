package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.AssetLinkRelationDao;
import com.antiy.asset.dao.AssetOperationRecordDao;
import com.antiy.asset.entity.Asset;
import com.antiy.asset.entity.AssetOperationRecord;
import com.antiy.asset.intergration.ActivityClient;
import com.antiy.asset.service.IAssetStatusJumpService;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.vo.enums.AssetActivityTypeEnum;
import com.antiy.asset.vo.enums.AssetFlowEnum;
import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.asset.vo.enums.AssetStatusJumpEnum;
import com.antiy.asset.vo.request.ActivityHandleRequest;
import com.antiy.asset.vo.request.AssetStatusJumpRequest;
import com.antiy.asset.vo.request.ManualStartActivityRequest;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.BusinessData;
import com.antiy.common.base.RespBasicCode;
import com.antiy.common.encoder.AesEncoder;
import com.antiy.common.enums.BusinessModuleEnum;
import com.antiy.common.enums.BusinessPhaseEnum;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.utils.BusinessExceptionUtils;
import com.antiy.common.utils.JsonUtil;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private AesEncoder aesEncoder;
    @Resource
    private ActivityClient activityClient;
    @Resource
    private TransactionTemplate transactionTemplate;


    @Override
    public ActionResponse changeStatus(AssetStatusJumpRequest statusJumpRequest) throws Exception {
        // 1.校验参数信息,当前流程的资产是否都满足当前状态
        List<Integer> assetIdList = statusJumpRequest.getAssetInfoList().stream().map(e -> DataTypeUtils.stringToInteger(e.getAssetId())).collect(Collectors.toList());
        List<Asset> assetsInDb = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(assetIdList)) {
            assetsInDb = assetDao.findByIds(assetIdList);
        }
        BusinessExceptionUtils.isTrue(assetIdList.size() == assetsInDb.size() && CollectionUtils.isNotEmpty(assetsInDb), "所选资产已被操作,请刷新后重试");

        // 当前所有资产的可执行操作与当前状态一致
        assetsInDb.forEach(asset -> {
            if (!asset.getAssetStatus().equals(statusJumpRequest.getAssetFlowEnum().getCurrentAssetStatus().getCode())) {
                LogUtils.info(logger, "资产状态不匹配 request:{}", statusJumpRequest);
                throw new BusinessException("当前选中的资产已被其他人员操作,请刷新页面后重试");
            }
        });

        // 先更改为下一个状态,后续失败进行回滚
        setInProcess(statusJumpRequest, assetsInDb);

        // 2.提交至工作流
        boolean activitySuccess = startActivity(statusJumpRequest);
        if (!activitySuccess) {
            assetDao.updateAssetBatch(assetsInDb);
            LogUtils.warn(logger, "资产状态处理失败,statusJumpRequest:{}", statusJumpRequest);
            return ActionResponse.fail(RespBasicCode.BUSSINESS_EXCETION, "操作失败,请刷新页面后重试");
        }

        // 3.数据库操作
        updateData(statusJumpRequest, assetsInDb);

        // 4.记录操作日志
        assetsInDb.forEach(asset -> LogUtils.recordOperLog(new BusinessData(statusJumpRequest.getAssetFlowEnum().getMsg(),
                asset.getId(), asset.getNumber(), statusJumpRequest, BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.NONE)));
        return ActionResponse.success();
    }

    private void setInProcess(AssetStatusJumpRequest statusJumpRequest, List<Asset> assetsInDb) {
        Integer lastAssetStatus = statusJumpRequest.getAssetFlowEnum().getCurrentAssetStatus().getCode();

        List<Asset> updateAssetList = new ArrayList<>(assetsInDb.size());
        assetsInDb.forEach(e -> {
            Asset asset = new Asset();
            asset.setId(e.getId());
            asset.setAssetStatus(AssetStatusJumpEnum.getNextStatus(statusJumpRequest.getAssetFlowEnum(), statusJumpRequest.getAgree(),
                    statusJumpRequest.getWaitCorrectToWaitRegister(), asset.getFirstEnterNett() != null).getCode());
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

    private boolean startActivity(AssetStatusJumpRequest assetStatusRequest) {
        // 1.拟退役需要启动流程,其他步骤完成流程
        if (AssetFlowEnum.TO_WAIT_RETIRE.equals(assetStatusRequest.getAssetFlowEnum())) {
            // 启动流程
            ManualStartActivityRequest manualStartActivityRequest = new ManualStartActivityRequest();
            manualStartActivityRequest.setAssignee(LoginUserUtil.getLoginUser().getId().toString());
            manualStartActivityRequest.setBusinessId(assetStatusRequest.getAssetInfoList().get(0).getAssetId());
            manualStartActivityRequest.setProcessDefinitionKey(AssetActivityTypeEnum.HARDWARE_RETIRE.getCode());
            manualStartActivityRequest.setFormData(assetStatusRequest.getFormData());
            try {
                ActionResponse actionResponse = activityClient.manualStartProcess(manualStartActivityRequest);
                LogUtils.info(logger, "请求工作流结果: {}", JsonUtil.object2Json(actionResponse));
                if (actionResponse == null || !actionResponse.getHead().getCode().equals(RespBasicCode.SUCCESS.getResultCode())) {
                    return false;
                }
            } catch (Exception e) {
                LogUtils.error(logger, "请求工作流数据异常:manualStartActivityRequest:{}, {}", assetStatusRequest.getManualStartActivityRequest(), e);
                return false;
            }
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

            try {
                ActionResponse actionResponse = activityClient.completeTaskBatch(requestList);
                LogUtils.info(logger, "请求工作流结果: {}", JsonUtil.object2Json(actionResponse));
                // 如果流程引擎为空,直接返回错误信息
                if (null == actionResponse
                        || !RespBasicCode.SUCCESS.getResultCode().equals(actionResponse.getHead().getCode())) {
                    return false;
                }
            } catch (Exception e) {
                LogUtils.error(logger, "请求工作流数据异常: {}", e);
                return false;
            }
        }
        return true;
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
        Integer loginUserId = LoginUserUtil.getLoginUser() != null ? LoginUserUtil.getLoginUser().getId() : null;
        String loginUserName = LoginUserUtil.getLoginUser() != null ? LoginUserUtil.getLoginUser().getName() : "";

        assetsInDb.forEach(asset -> {
            AssetStatusEnum nextStatus = AssetStatusJumpEnum.getNextStatus(statusJumpRequest.getAssetFlowEnum(), statusJumpRequest.getAgree(),
                    statusJumpRequest.getWaitCorrectToWaitRegister(), asset.getFirstEnterNett() != null);

            // 首次入网,设置首次入网时间(检查资产主表时间为空时写入入网时间)
            if (AssetFlowEnum.NET_IN.equals(statusJumpRequest.getAssetFlowEnum()) && asset.getFirstEnterNett() == null) {
                asset.setFirstEnterNett(currentTime);
            } else if (AssetFlowEnum.RETIRE.equals(statusJumpRequest.getAssetFlowEnum())) {
                // 退役删除通联关系
                deleteLinkRelationIdList.add(asset.getId());
            }

            asset.setAssetStatus(nextStatus.getCode());
            asset.setGmtModified(currentTime);
            asset.setModifyUser(loginUserId);
            updateAssetList.add(asset);

            operationRecordList.add(convertAssetOperationRecord(statusJumpRequest, currentTime, loginUserId, loginUserName, asset.getStringId(), nextStatus.getCode()));
        });
        transactionTemplate.execute(transactionStatus -> {
            try {
                assetOperationRecordDao.insertBatch(operationRecordList);
                assetDao.updateAssetBatch(updateAssetList);
                if (CollectionUtils.isNotEmpty(deleteLinkRelationIdList)) {
                    assetLinkRelationDao.deleteByAssetIdList(deleteLinkRelationIdList);
                }
            } catch (Exception e) {
                transactionStatus.setRollbackOnly();
                LogUtils.error(logger, "数据库操作异常:{},插入资产操作记录operationRecordList:{},updateAssetList:{},assetLinkRelationDao: {}", e, operationRecordList, updateAssetList, deleteLinkRelationIdList);
                throw new BusinessException("操作失败,请联系运维人员进行解决");
            }
            return null;
        });
    }

    private AssetOperationRecord convertAssetOperationRecord(AssetStatusJumpRequest statusJumpRequest, Long currentTime, Integer loginUserId, String loginUserName, String assetId, Integer nextStatus) {
        AssetOperationRecord assetOperationRecord = new AssetOperationRecord();
        assetOperationRecord.setOriginStatus(statusJumpRequest.getAssetFlowEnum().getCurrentAssetStatus().getCode());
        assetOperationRecord.setTargetStatus(nextStatus);
        assetOperationRecord.setContent(AssetFlowEnum.getMsgByAssetStatus(statusJumpRequest.getAssetFlowEnum().getCurrentAssetStatus()));
        assetOperationRecord.setTargetObjectId(assetId);
        assetOperationRecord.setGmtCreate(currentTime);
        assetOperationRecord.setOperateUserId(loginUserId);
        assetOperationRecord.setProcessResult(AssetFlowEnum.TO_WAIT_RETIRE.equals(statusJumpRequest.getAssetFlowEnum()) ? null : Boolean.TRUE.equals(statusJumpRequest.getAgree()) ? 1 : 0);
        assetOperationRecord.setOperateUserName(loginUserName);
        assetOperationRecord.setCreateUser(loginUserId);
        assetOperationRecord.setNote(statusJumpRequest.getNote() == null ? "" : statusJumpRequest.getNote());
        assetOperationRecord.setFileInfo(statusJumpRequest.getFileInfo());
        return assetOperationRecord;
    }

}

