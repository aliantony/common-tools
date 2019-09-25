package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.AssetLinkRelationDao;
import com.antiy.asset.dao.AssetOperationRecordDao;
import com.antiy.asset.entity.Asset;
import com.antiy.asset.entity.AssetOperationRecord;
import com.antiy.asset.intergration.ActivityClient;
import com.antiy.asset.intergration.BaseLineClient;
import com.antiy.asset.service.IAssetStatusChangeProcessService;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.vo.enums.AssetFlowEnum;
import com.antiy.asset.vo.enums.AssetOperationTableEnum;
import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.asset.vo.enums.AssetStatusJumpEnum;
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
    private BaseLineClient baseLineClient;
    @Resource
    private ActivityClient activityClient;
    @Resource
    private TransactionTemplate transactionTemplate;

    @Override
    public ActionResponse changeStatus(AssetStatusJumpRequest statusJumpRequest) throws Exception {
        // 1.校验参数信息,当前流程的资产是否都满足当前状态(所有资产状态与页面状态一致,当前资产的可执行操作与本次操作一致),待整改有两种情况
        List<Integer> assetIdList = statusJumpRequest.getAssetIdList().stream().map(DataTypeUtils::stringToInteger).collect(Collectors.toList());
        List<Asset> assetsInDb = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(assetIdList)) {
            assetsInDb = assetDao.findByIds(assetIdList);
        }
        BusinessExceptionUtils.isTrue(CollectionUtils.isNotEmpty(assetsInDb), "未查询到所选资产信息,请稍后重试");
        assetsInDb.forEach(e -> {
            if (!e.getAssetStatus().equals(statusJumpRequest.getAssetFlowEnum().getCurrentAssetStatus().getCode())) {
                throw new BusinessException("当前选中的资产已被其他人员操作,请刷新页面后重试");
            }
        });

        // 2.提交至工作流
        // startActivity(statusJumpRequest);
        // 3.更新资产状态
        updateData(statusJumpRequest, assetsInDb);
        // 4.记录操作日志
        logRecordOperLog(statusJumpRequest, assetsInDb);
        return ActionResponse.success();
    }


    private ActionResponse startActivity(AssetStatusJumpRequest assetStatusRequest) throws Exception {
        // 1.封装数据
        List<ManualStartActivityRequest> manualStartActivityRequestList = new ArrayList<>(assetStatusRequest.getAssetIdList().size());

        // TODO 与流程引擎对接数据
        // assetStatusRequest.getManualStartActivityRequest().forEach(assetStatusRequest -> {
        //     assetStatusRequest.getManualStartActivityRequest()
        //             .setAssignee(LoginUserUtil.getLoginUser().getId().toString());
        //     ManualStartActivityRequest manualStartActivityRequest = new ManualStartActivityRequest();
        //     manualStartActivityRequestList.add(manualStartActivityRequest);
        // });

        if (AssetFlowEnum.RETIRE.getCurrentAssetStatus().equals(assetStatusRequest.getAssetFlowEnum().getCurrentAssetStatus())) {
            // 记录操作日志
            // LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_RETIRE_START.getName(),
            //         DataTypeUtils.stringToInteger(assetStatusReqeust.getAssetId()),
            //         assetDao.getNumberById(assetStatusReqeust.getAssetId()), assetStatusReqeust,
            //         BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.WAIT_RETIRE));
            // 启动流程
            assetStatusRequest.getManualStartActivityRequest()
                    .setAssignee(LoginUserUtil.getLoginUser().getId().toString());
            // TODO退役
            // actionResponse = activityClient.manualStartProcess(assetStatusReqeust.getManualStartActivityRequest());
        }

        try {
            ActionResponse actionResponse = activityClient.startProcessWithoutFormBatch(manualStartActivityRequestList);
            LogUtils.info(logger, "请求工作流数据结果 {}", actionResponse);
            // 如果流程引擎为空,直接返回错误信息
            if (null == actionResponse
                    || !RespBasicCode.SUCCESS.getResultCode().equals(actionResponse.getHead().getCode())) {
                // 记录流程失败的资产节点信息，用于资产状态的定时任务
                // AssetStatusTask assetStatusTask = new AssetStatusTask();
                // assetStatusTask.setAssetId(assetStatusReqeust.getAssetId());
                // assetStatusTask.setTaskId(assetStatusReqeust.getActivityHandleRequest().getTaskId());
                // assetStatusTask.setGmtCreate(System.currentTimeMillis());
                // assetStatusTask.setCreateUser(LoginUserUtil.getLoginUser() != null ? LoginUserUtil.getLoginUser().getId()
                // : null);
                // statusTaskDao.insert(assetStatusTask);
                return actionResponse == null ? ActionResponse.fail(RespBasicCode.BUSSINESS_EXCETION) : actionResponse;
            }
        } catch (Exception e) {
            LogUtils.error(logger, "请求工作流数据异常: {}", e);
            throw new BusinessException("数据错误,请稍后重试");
        }
        return ActionResponse.success();
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
        // TODO 核对批量是否能共用一个状态
        AssetStatusEnum nextStatus = AssetStatusJumpEnum.getNextStatus(statusJumpRequest.getAssetFlowEnum(), statusJumpRequest.getAgree(),
                statusJumpRequest.getWaitCorrectToWaitRegister(), assetsInDb.get(0).getFirstEnterNett() != null);
        List<AssetOperationRecord> operationRecordList = new ArrayList<>();
        List<Asset> updateAssetList = new ArrayList<>();
        List<Integer> deleteLinkRelationIdList = new ArrayList<>();
        assetsInDb.forEach(asset -> {
            // 保存操作记录流程
            AssetOperationRecord assetOperationRecord = convertAssetOperationRecord(asset.getStringId(), statusJumpRequest.getAssetFlowEnum().getCurrentAssetStatus(),
                    nextStatus.getCode(), currentTime, statusJumpRequest.getAgree(), asset.getAreaId());
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
                    assetDao.updateAssetBatch(updateAssetList);
                }
                if (CollectionUtils.isNotEmpty(deleteLinkRelationIdList)) {
                    assetLinkRelationDao.deleteByAssetIdList(deleteLinkRelationIdList);
                }
            } catch (Exception e) {
                transactionStatus.setRollbackOnly();
                LogUtils.error(logger, "数据库操作异常 {}", e);
                throw new BusinessException("操作失败,请稍后重试");
            }
            return 1;
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
     * @param areaId
     * @return
     */
    private AssetOperationRecord convertAssetOperationRecord(String assetId, AssetStatusEnum currentAssetStatus, Integer nextAssetStatus, Long gmtCreateTime,
                                                             boolean agree, String areaId) {
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


    /**
     * 记录操作日志
     */
    private void logRecordOperLog(AssetStatusJumpRequest assetStatusJumpRequest, List<Asset> assetList) {
        assetList.forEach(asset -> {
            String logEvent = assetStatusJumpRequest.getAssetFlowEnum().getMsg();
            // TODO 业务阶段
            BusinessPhaseEnum currentBusinessPhase = BusinessPhaseEnum.WAIT_REGISTER;;
            switch (assetStatusJumpRequest.getAssetFlowEnum()) {
                case REGISTER:
                    currentBusinessPhase = BusinessPhaseEnum.WAIT_REGISTER;
                    break;
                case TEMPLATE_IMPL:
                    currentBusinessPhase = BusinessPhaseEnum.RETIRE;
                    break;
                case VALIDATE:
                    currentBusinessPhase = BusinessPhaseEnum.RETIRE;
                    break;
                case NET_IN:
                    currentBusinessPhase = BusinessPhaseEnum.WAIT_NET;
                    break;
                case CHECK:
                    currentBusinessPhase = BusinessPhaseEnum.WAIT_CHECK;
                    break;
                case CORRECT:
                    currentBusinessPhase = BusinessPhaseEnum.RETIRE;
                    break;
                case TO_WAIT_RETIRE:
                    currentBusinessPhase = BusinessPhaseEnum.RETIRE;
                    break;
                case RETIRE:
                    currentBusinessPhase = BusinessPhaseEnum.RETIRE;
                    break;
                default:
                    break;
            }
            LogUtils.recordOperLog(new BusinessData(logEvent,
                    asset.getId(),
                    asset.getNumber(), assetStatusJumpRequest,
                    BusinessModuleEnum.HARD_ASSET, currentBusinessPhase));
        });
    }

}
