package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.AssetLinkRelationDao;
import com.antiy.asset.dao.AssetOperationRecordDao;
import com.antiy.asset.entity.Asset;
import com.antiy.asset.entity.AssetOperationRecord;
import com.antiy.asset.intergration.ActivityClient;
import com.antiy.asset.service.IAssetStatusChangeProcessService;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.vo.enums.AssetFlowEnum;
import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.asset.vo.enums.AssetStatusJumpEnum;
import com.antiy.asset.vo.request.ActivityHandleRequest;
import com.antiy.asset.vo.request.AssetStatusJumpRequest;
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
import java.util.List;
import java.util.stream.Collectors;

/**
 * @auther: zhangyajun
 * @date: 2019/1/23 15:38
 * @description: 硬件资产状态跃迁
 */
@Service("assetStatusChangeFlowProcessImpl")
public class AssetStatusChangeFlowProcessImpl implements IAssetStatusChangeProcessService {
    private static final Logger logger = LogUtils.get(AssetStatusChangeFlowProcessImpl.class);
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
        BusinessExceptionUtils.isTrue(assetIdList.size() == assetsInDb.size() && CollectionUtils.isNotEmpty(assetsInDb), "未查询到所选资产信息,请稍后重试");

        // 当前所有资产的可执行操作与当前状态一致
        assetsInDb.forEach(asset -> {
            if (!asset.getAssetStatus().equals(statusJumpRequest.getAssetFlowEnum().getCurrentAssetStatus().getCode())) {
                throw new BusinessException("当前选中的资产已被其他人员操作,请刷新页面后重试");
            }
        });

        setInProcess(assetsInDb, statusJumpRequest.getAssetFlowEnum().getCurrentAssetStatus().getCode());

        // 2.提交至工作流
        boolean activitySuccess = true;
        // boolean activitySuccess = startActivity(statusJumpRequest);
        // if (!activitySuccess) {
        //     assetDao.updateAssetBatch(assetsInDb);
        //     LogUtils.warn(logger, "资产状态处理失败,statusJumpRequest:{}", statusJumpRequest);
        //     return ActionResponse.fail(RespBasicCode.BUSSINESS_EXCETION, "操作失败,请刷新后重试");
        // }

        // 3.数据库操作
        updateData(statusJumpRequest, assetsInDb);

        // 4.记录操作日志
        assetsInDb.forEach(asset -> LogUtils.recordOperLog(new BusinessData(statusJumpRequest.getAssetFlowEnum().getMsg(),
                asset.getId(), asset.getNumber(), statusJumpRequest, BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.NONE)));
        return ActionResponse.success();
    }

    private void setInProcess(List<Asset> assetsInDb, Integer lastAssetStatus) {
        // 更改为资产正在处理中 status=0,AssetStatus=99 IN_PROCESS
        List<Asset> updateAssetList = new ArrayList<>(assetsInDb.size());
        assetsInDb.forEach(e -> {
            Asset asset = new Asset();
            asset.setId(e.getId());
            // asset.setAssetStatus(AssetStatusEnum.IN_PROCESS.getCode());
            asset.setStatus(0);
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
        // 1.退役需要启动流程,其他步骤完成流程
        //TODO 退役启动流程
        if (AssetFlowEnum.RETIRE.getCurrentAssetStatus().equals(assetStatusRequest.getAssetFlowEnum().getCurrentAssetStatus())) {
            // 启动流程
            assetStatusRequest.getManualStartActivityRequest().setAssignee(LoginUserUtil.getLoginUser().getId().toString());
            try {
                ActionResponse actionResponse = activityClient.manualStartProcess(assetStatusRequest.getManualStartActivityRequest());
                LogUtils.info(logger, "请求工作流结果: {}", JsonUtil.object2Json(actionResponse));
                if (actionResponse == null || actionResponse.getHead().getCode().equals(RespBasicCode.SUCCESS.getResultCode())) {
                    return false;
                }
            } catch (Exception e) {
                LogUtils.error(logger, "请求工作流数据异常: {}", e);
                return false;
            }
        } else {
            // 非启动退役流程
            List<ActivityHandleRequest> requestList = new ArrayList<>();
            assetStatusRequest.getAssetInfoList().forEach(assetInfo -> {
                ActivityHandleRequest activityHandleRequest = new ActivityHandleRequest();
                activityHandleRequest.setFormData(assetStatusRequest.getFormData());
                activityHandleRequest.setTaskId(assetInfo.getTaskId());
                requestList.add(activityHandleRequest);
            });

            try {
                ActionResponse actionResponse = activityClient.completeTaskBatch(requestList);
                LogUtils.info(logger, "请求工作流结果: {}", actionResponse);
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
     * @param assetsInDb
     */
    private void updateData(AssetStatusJumpRequest statusJumpRequest, List<Asset> assetsInDb) {
        Long currentTime = System.currentTimeMillis();
        List<AssetOperationRecord> operationRecordList = new ArrayList<>();
        List<Asset> updateAssetList = new ArrayList<>();
        List<Integer> deleteLinkRelationIdList = new ArrayList<>();
        assetsInDb.forEach(asset -> {
            AssetStatusEnum nextStatus = AssetStatusJumpEnum.getNextStatus(statusJumpRequest.getAssetFlowEnum(), statusJumpRequest.getAgree(),
                    statusJumpRequest.getWaitCorrectToWaitRegister(), asset.getFirstEnterNett() != null);
            // 保存操作记录流程
            AssetOperationRecord assetOperationRecord = convertAssetOperationRecord(asset.getStringId(), statusJumpRequest.getAssetFlowEnum().getCurrentAssetStatus(),
                    nextStatus.getCode(), currentTime, statusJumpRequest.getAgree());
            operationRecordList.add(assetOperationRecord);

            // 首次入网,设置首次入网时间(检查资产主表时间为空时写入入网时间)
            if (nextStatus.getCode().equals(AssetStatusEnum.NET_IN.getCode()) && asset.getFirstEnterNett() == null) {
                asset.setFirstEnterNett(System.currentTimeMillis());
            }
            asset.setAssetStatus(nextStatus.getCode());
            asset.setGmtModified(System.currentTimeMillis());
            asset.setModifyUser(LoginUserUtil.getLoginUser() != null ? LoginUserUtil.getLoginUser().getId() : null);
            updateAssetList.add(asset);

            // 退役删除通联关系
            if (AssetFlowEnum.RETIRE.equals(statusJumpRequest.getAssetFlowEnum())) {
                deleteLinkRelationIdList.add(asset.getId());
            }
        });

        transactionTemplate.execute(transactionStatus -> {
            try {
                if (CollectionUtils.isNotEmpty(operationRecordList)) {
                    assetOperationRecordDao.insertBatch(operationRecordList);
                    Integer integer = assetDao.updateAssetBatch(updateAssetList);
                    LogUtils.debug(logger,"row{}", integer);
                }
                if (CollectionUtils.isNotEmpty(deleteLinkRelationIdList)) {
                    assetLinkRelationDao.deleteByAssetIdList(deleteLinkRelationIdList);
                }
            } catch (Exception e) {
                transactionStatus.setRollbackOnly();
                LogUtils.error(logger, "数据库操作异常:{},插入资产操作记录operationRecordList:{},updateAssetList:{},assetLinkRelationDao: {}",
                        e, operationRecordList, updateAssetList, deleteLinkRelationIdList);
                throw new BusinessException("操作失败,请稍后重试");
            }
            return null;
        });
    }

    /**
     * 转换操作记录
     *
     * @param assetId
     * @param currentAssetStatus
     * @param nextAssetStatus
     * @param gmtCreateTime      当前时间
     * @param agree              同意true/拒绝false
     * @return
     */
    private AssetOperationRecord convertAssetOperationRecord(String assetId, AssetStatusEnum currentAssetStatus, Integer nextAssetStatus, Long gmtCreateTime,
                                                             boolean agree) {
        AssetOperationRecord assetOperationRecord = new AssetOperationRecord();
        assetOperationRecord.setOriginStatus(currentAssetStatus.getCode());
        assetOperationRecord.setTargetStatus(nextAssetStatus);
        assetOperationRecord.setContent(AssetFlowEnum.getMsgByAssetStatus(currentAssetStatus));
        assetOperationRecord.setTargetObjectId(assetId);
        assetOperationRecord.setGmtCreate(gmtCreateTime);
        assetOperationRecord.setOperateUserId(LoginUserUtil.getLoginUser().getId());
        assetOperationRecord.setProcessResult(agree ? 1 : 0);
        assetOperationRecord.setOperateUserName(LoginUserUtil.getLoginUser().getName());
        assetOperationRecord.setCreateUser(LoginUserUtil.getLoginUser().getId());
        return assetOperationRecord;
    }
}

