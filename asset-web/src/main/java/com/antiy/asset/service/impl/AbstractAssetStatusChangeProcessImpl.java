package com.antiy.asset.service.impl;

import static com.antiy.biz.file.FileHelper.logger;

import javax.annotation.Resource;

import com.antiy.asset.dao.AssetOperationRecordDao;
import com.antiy.asset.dao.SchemeDao;
import com.antiy.asset.entity.AssetOperationRecord;
import com.antiy.asset.entity.Scheme;
import com.antiy.asset.intergration.ActivityClient;
import com.antiy.asset.intergration.WorkOrderClient;
import com.antiy.asset.service.IAssetStatusChangeProcessService;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.util.EnumUtil;
import com.antiy.asset.util.LogHandle;
import com.antiy.asset.vo.enums.*;
import com.antiy.asset.vo.request.AssetStatusReqeust;
import com.antiy.asset.vo.request.SchemeRequest;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.RespBasicCode;
import com.antiy.common.encoder.AesEncoder;
import com.antiy.common.enums.ModuleEnum;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;

/**
 * @auther: zhangbing
 * @date: 2019/1/22 15:06
 * @description:
 */
public abstract class AbstractAssetStatusChangeProcessImpl implements IAssetStatusChangeProcessService {

    @Resource
    private AssetOperationRecordDao              assetOperationRecordDao;

    @Resource
    private SchemeDao                            schemeDao;
    @Resource
    private BaseConverter<SchemeRequest, Scheme> schemeRequestToSchemeConverter;

    @Resource
    private ActivityClient                       activityClient;

    @Resource
    private WorkOrderClient                      workOrderClient;
    @Resource
    AesEncoder                                   aesEncoder;

    @Override
    public ActionResponse changeStatus(AssetStatusReqeust assetStatusReqeust) throws Exception {
        Scheme scheme = null;
        if (assetStatusReqeust.getSchemeRequest() != null) {
            // 1.保存方案信息
            scheme = convertScheme(assetStatusReqeust);
            schemeDao.insert(scheme);

            // 写入业务日志
            LogHandle.log(scheme.toString(), AssetEventEnum.ASSET_SCHEME_INSERT.getName(),
                AssetEventEnum.ASSET_SCHEME_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
            LogUtils.info(logger, AssetEventEnum.ASSET_SCHEME_INSERT.getName() + " {}", scheme.toString());
        }

        // 2.保存流程
        AssetOperationRecord assetOperationRecord = convertAssetOperationRecord(assetStatusReqeust);
        if (!assetStatusReqeust.getSoftware()) {
            assetOperationRecord.setOriginStatus(assetStatusReqeust.getAssetStatus().getCode());
        } else {
            assetOperationRecord.setOriginStatus(assetStatusReqeust.getSoftwareStatusEnum().getCode());
        }

        assetOperationRecord.setSchemeId(scheme != null ? scheme.getId() : null);
        assetOperationRecordDao.insert(assetOperationRecord);

        // 写入业务日志
        LogHandle.log(assetOperationRecord.toString(), AssetEventEnum.ASSET_OPERATION_RECORD_INSERT.getName(),
            AssetEventEnum.ASSET_OPERATION_RECORD_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
        LogUtils.info(logger, AssetEventEnum.ASSET_OPERATION_RECORD_INSERT.getName() + " {}",
            assetOperationRecord.toString());

        // 3.调用流程引擎
        ActionResponse actionResponse = null;
        if (assetStatusReqeust.getAssetFlowCategoryEnum().getCode()
            .equals(AssetFlowCategoryEnum.HARDWARE_RETIRE.getCode())
            || AssetFlowCategoryEnum.SOFTWARE_RETIRE.getCode()
                .equals(assetStatusReqeust.getAssetFlowCategoryEnum().getCode())
            || AssetFlowCategoryEnum.HARDWARE_UNINSTALL.getCode()
                .equals(assetStatusReqeust.getAssetFlowCategoryEnum().getCode())) {
            // 启动流程
            assetStatusReqeust.getManualStartActivityRequest().setAssignee(LoginUserUtil.getLoginUser().getName());
            actionResponse = activityClient.manualStartProcess(assetStatusReqeust.getManualStartActivityRequest());
        } else if (AssetFlowCategoryEnum.HARDWARE_REGISTER.getCode()
            .equals(assetStatusReqeust.getAssetFlowCategoryEnum().getCode())
                   || AssetFlowCategoryEnum.HARDWARE_CHANGE.getCode()
                       .equals(assetStatusReqeust.getAssetFlowCategoryEnum().getCode())
                   || AssetFlowCategoryEnum.HARDWARE_IMPL_RETIRE.getCode()
                       .equals(assetStatusReqeust.getAssetFlowCategoryEnum().getCode())) {
            // 硬件完成流程
            actionResponse = activityClient.completeTask(assetStatusReqeust.getActivityHandleRequest());
        } else if (AssetFlowCategoryEnum.SOFTWARE_REGISTER.getCode()
            .equals(assetStatusReqeust.getAssetFlowCategoryEnum().getCode())
                   || AssetFlowCategoryEnum.SOFTWARE_RETIRE.getCode()
                       .equals(assetStatusReqeust.getAssetFlowCategoryEnum().getCode())) {
            // 软件完成流程
            actionResponse = activityClient.completeTask(assetStatusReqeust.getActivityHandleRequest());
        }

        // 如果流程引擎为空,直接返回错误信息
        if (null == actionResponse
            || !RespBasicCode.SUCCESS.getResultCode().equals(actionResponse.getHead().getCode())) {
            return actionResponse == null ? ActionResponse.fail(RespBasicCode.BUSSINESS_EXCETION) : actionResponse;
        }

        // 4.调用工单系统
        if (null != assetStatusReqeust.getWorkOrderVO()) {
            actionResponse = workOrderClient.createWorkOrder(assetStatusReqeust.getWorkOrderVO());
            // 如果流程引擎为空,直接返回错误信息
            if (null == actionResponse
                || !RespBasicCode.SUCCESS.getResultCode().equals(actionResponse.getHead().getCode())) {
                return actionResponse == null ? ActionResponse.fail(RespBasicCode.BUSSINESS_EXCETION) : actionResponse;
            }
        }
        return ActionResponse.success();
    }

    /**
     * 转换流程
     *
     *
     * @param assetStatusReqeust
     * @return
     */
    private AssetOperationRecord convertAssetOperationRecord(AssetStatusReqeust assetStatusReqeust) {
        AssetOperationRecord assetOperationRecord = new AssetOperationRecord();
        if (assetStatusReqeust.getSoftware()) {
            SoftwareFlowEnum softwareFlowEnum = EnumUtil.getByCode(SoftwareFlowEnum.class,
                assetStatusReqeust.getSoftwareStatusEnum().getCode());
            assetOperationRecord.setOriginStatus(assetStatusReqeust.getSoftwareStatusEnum().getCode());
            assetOperationRecord.setContent(
                softwareFlowEnum != null ? softwareFlowEnum.getMsg() : RespBasicCode.PARAMETER_ERROR.getResultCode());
        } else {
            AssetFlowEnum assetFlowEnum = EnumUtil.getByCode(AssetFlowEnum.class,
                assetStatusReqeust.getAssetStatus().getCode());
            assetOperationRecord.setOriginStatus(assetStatusReqeust.getAssetStatus().getCode());
            assetOperationRecord.setContent(
                assetFlowEnum != null ? assetFlowEnum.getMsg() : RespBasicCode.PARAMETER_ERROR.getResultCode());
        }

        assetOperationRecord.setTargetType(assetStatusReqeust.getSoftware() ? AssetOperationTableEnum.SOFTWARE.getCode()
            : AssetOperationTableEnum.ASSET.getCode());

        assetOperationRecord.setTargetObjectId(assetStatusReqeust.getAssetId());
        assetOperationRecord.setGmtCreate(System.currentTimeMillis());
        assetOperationRecord.setOperateUserId(LoginUserUtil.getLoginUser().getId());
        assetOperationRecord.setProcessResult(assetStatusReqeust.getAgree() ? 1 : 0);
        assetOperationRecord.setOperateUserName(LoginUserUtil.getLoginUser().getUsername());
        assetOperationRecord.setCreateUser(LoginUserUtil.getLoginUser().getId());
        return assetOperationRecord;
    }

    /**
     * 转换方案
     * @param assetStatusReqeust
     * @return
     */
    private Scheme convertScheme(AssetStatusReqeust assetStatusReqeust) {
        Scheme scheme = schemeRequestToSchemeConverter.convert(assetStatusReqeust.getSchemeRequest(), Scheme.class);
        scheme.setGmtCreate(System.currentTimeMillis());
        scheme.setCreateUser(LoginUserUtil.getLoginUser().getId());
        if (null != assetStatusReqeust.getWorkOrderVO()) {
            scheme.setExpecteStartTime(Long.valueOf(assetStatusReqeust.getWorkOrderVO().getStartTime()));
            scheme.setExpecteEndTime(Long.valueOf(assetStatusReqeust.getWorkOrderVO().getEndTime()));
            scheme.setOrderLevel(assetStatusReqeust.getWorkOrderVO().getWorkLevel());
            scheme.setPutintoUserId(DataTypeUtils.stringToInteger(aesEncoder.decode(
                assetStatusReqeust.getWorkOrderVO().getExecuteUserId(), LoginUserUtil.getLoginUser().getUsername())));
        }
        scheme.setAssetId(assetStatusReqeust.getAssetId());
        return scheme;
    }
}