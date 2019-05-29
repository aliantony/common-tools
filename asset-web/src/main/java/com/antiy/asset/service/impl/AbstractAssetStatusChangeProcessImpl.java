package com.antiy.asset.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.web.util.HtmlUtils;

import com.alibaba.fastjson.JSONObject;
import com.antiy.asset.dao.*;
import com.antiy.asset.entity.Asset;
import com.antiy.asset.entity.AssetOperationRecord;
import com.antiy.asset.entity.AssetSoftware;
import com.antiy.asset.entity.Scheme;
import com.antiy.asset.intergration.ActivityClient;
import com.antiy.asset.intergration.WorkOrderClient;
import com.antiy.asset.service.IAssetStatusChangeProcessService;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.util.EnumUtil;
import com.antiy.asset.vo.enums.*;
import com.antiy.asset.vo.request.AssetStatusReqeust;
import com.antiy.asset.vo.request.SchemeRequest;
import com.antiy.asset.vo.request.WorkOrderVO;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BusinessData;
import com.antiy.common.base.RespBasicCode;
import com.antiy.common.encoder.AesEncoder;
import com.antiy.common.enums.BusinessModuleEnum;
import com.antiy.common.enums.BusinessPhaseEnum;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;
import com.antiy.common.utils.ParamterExceptionUtils;

/**
 * @auther: zhangbing
 * @date: 2019/1/22 15:06
 * @description:
 */
public abstract class AbstractAssetStatusChangeProcessImpl implements IAssetStatusChangeProcessService {
    private Logger                               logger = LogUtils.get(this.getClass());
    @Resource
    private AssetOperationRecordDao              assetOperationRecordDao;
    @Resource
    private SchemeDao                            schemeDao;
    @Resource
    private AssetDao                             assetDao;
    @Resource
    private AssetSoftwareDao                     assetSoftwareDao;
    @Resource
    private BaseConverter<SchemeRequest, Scheme> schemeRequestToSchemeConverter;

    @Resource
    private ActivityClient                       activityClient;

    @Resource
    private WorkOrderClient                      workOrderClient;
    @Resource
    private AesEncoder                           aesEncoder;
    @Resource
    AssetStatusTaskDao                           statusTaskDao;
    @Override
    public ActionResponse changeStatus(AssetStatusReqeust assetStatusReqeust) throws Exception {
        Long gmtCreateTime = System.currentTimeMillis();
        Scheme scheme = null;
        if (assetStatusReqeust.getSchemeRequest() != null) {
            // 1.保存方案信息
            scheme = convertScheme(assetStatusReqeust, gmtCreateTime);
            schemeDao.insert(scheme);
        }

        // 2.保存流程
        AssetOperationRecord assetOperationRecord = convertAssetOperationRecord(assetStatusReqeust, gmtCreateTime);

        if (!assetStatusReqeust.getSoftware()) {
            assetOperationRecord.setOriginStatus(assetStatusReqeust.getAssetStatus().getCode());
            assetOperationRecord.setAreaId(assetDao.getById(assetStatusReqeust.getAssetId()).getAreaId());
        } else {
            assetOperationRecord.setOriginStatus(assetStatusReqeust.getSoftwareStatusEnum().getCode());
        }
        assetOperationRecord.setSchemeId(scheme != null ? scheme.getId() : null);

        assetOperationRecordDao.insert(assetOperationRecord);
        // 3.调用流程引擎
        ActionResponse actionResponse = null;
        if (assetStatusReqeust.getAssetFlowCategoryEnum().getCode()
            .equals(AssetFlowCategoryEnum.HARDWARE_RETIRE.getCode())) {
            // 启动流程
            assetStatusReqeust.getManualStartActivityRequest()
                .setAssignee(LoginUserUtil.getLoginUser().getId().toString());
            actionResponse = activityClient.manualStartProcess(assetStatusReqeust.getManualStartActivityRequest());
        } else if (AssetFlowCategoryEnum.HARDWARE_REGISTER.getCode()
            .equals(assetStatusReqeust.getAssetFlowCategoryEnum().getCode())
                   || AssetFlowCategoryEnum.HARDWARE_IMPL_RETIRE.getCode()
                       .equals(assetStatusReqeust.getAssetFlowCategoryEnum().getCode())) {
            // 硬件完成流程
            actionResponse = activityClient.completeTask(assetStatusReqeust.getActivityHandleRequest());
        } else if (AssetFlowCategoryEnum.SOFTWARE_REGISTER.getCode()
            .equals(assetStatusReqeust.getAssetFlowCategoryEnum().getCode())) {
            // 软件完成流程
            actionResponse = activityClient.completeTask(assetStatusReqeust.getActivityHandleRequest());
        }

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

        // 4.调用工单系统(选择自己不发工单，选择它人发起工单)
        if (null != assetStatusReqeust.getWorkOrderVO() && !LoginUserUtil.getLoginUser().getId().toString()
            .equals(assetStatusReqeust.getSchemeRequest().getPutintoUserId())) {
            // 参数校验
            WorkOrderVO workOrderVO = assetStatusReqeust.getWorkOrderVO();
            workOrderVO.setOrderSource(1);
            ParamterExceptionUtils.isNull(workOrderVO.getName(), "工单名称不能为空");
            ParamterExceptionUtils.isNull(workOrderVO.getOrderSource(), "工单来源不能为空");
            ParamterExceptionUtils.isNull(workOrderVO.getOrderType(), "工单类型不能为空");
            ParamterExceptionUtils.isNull(workOrderVO.getWorkLevel(), "工单级别不能为空");
            ParamterExceptionUtils.isNull(workOrderVO.getContent(), "工单内容不能为空");
            ParamterExceptionUtils.isNull(workOrderVO.getExecuteUserId(), "执行人不能为空");
            ParamterExceptionUtils.isNull(workOrderVO.getStartTime(), "工单开始时间不能为空");
            ParamterExceptionUtils.isNull(workOrderVO.getEndTime(), "工单结束时间不能为空");
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
    private AssetOperationRecord convertAssetOperationRecord(AssetStatusReqeust assetStatusReqeust,
                                                             Long gmtCreateTime) {
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

            // 记录操作日志
            if (assetFlowEnum != null) {
                String number = null;
                // 获取资产编号
                try {
                    number = assetDao.getNumberById(assetStatusReqeust.getAssetId());
                } catch (Exception e) {
                    LogUtils.warn(logger, "获取资产编号失败");
                }
                LogUtils.recordOperLog(new BusinessData(assetFlowEnum.getMsg(),
                    DataTypeUtils.stringToInteger(assetStatusReqeust.getAssetId()),
                    assetDao.getNumberById(assetStatusReqeust.getAssetId()), assetStatusReqeust,
                    BusinessModuleEnum.HARD_ASSET,
                    BusinessPhaseEnum.getByStatus(assetStatusReqeust.getAssetStatus().getCode())));
                LogUtils.info(logger, assetFlowEnum.getMsg() + " {}", number);
            }
        }

        assetOperationRecord.setTargetType(assetStatusReqeust.getSoftware() ? AssetOperationTableEnum.SOFTWARE.getCode()
            : AssetOperationTableEnum.ASSET.getCode());

        assetOperationRecord.setTargetObjectId(assetStatusReqeust.getAssetId());
        assetOperationRecord.setGmtCreate(gmtCreateTime);
        assetOperationRecord.setOperateUserId(LoginUserUtil.getLoginUser().getId());
        assetOperationRecord.setProcessResult(assetStatusReqeust.getAgree() ? 1 : 0);
        assetOperationRecord.setOperateUserName(LoginUserUtil.getLoginUser().getName());
        assetOperationRecord.setCreateUser(LoginUserUtil.getLoginUser().getId());
        return assetOperationRecord;
    }

    /**
     * 转换方案
     * @param assetStatusReqeust
     * @return
     */
    private Scheme convertScheme(AssetStatusReqeust assetStatusReqeust, Long gmtCreateTime) throws Exception {
        Scheme scheme = schemeRequestToSchemeConverter.convert(assetStatusReqeust.getSchemeRequest(), Scheme.class);
        if (StringUtils.isNotEmpty(scheme.getContent()) && scheme.getMemo() == null) {
            scheme.setMemo(scheme.getContent());
        }
        if (scheme.getFileInfo() != null && scheme.getFileInfo().length() > 0) {
            JSONObject.parse(HtmlUtils.htmlUnescape(scheme.getFileInfo()));
        }
        scheme.setGmtCreate(gmtCreateTime);
        scheme.setCreateUser(LoginUserUtil.getLoginUser().getId());
        if (null != assetStatusReqeust.getWorkOrderVO()) {
            ParamterExceptionUtils.isNull(assetStatusReqeust.getWorkOrderVO().getStartTime(), "工单开始时间不能为空");
            ParamterExceptionUtils.isNull(assetStatusReqeust.getWorkOrderVO().getEndTime(), "工单结束时间不能为空");
            ParamterExceptionUtils.isNull(assetStatusReqeust.getWorkOrderVO().getWorkLevel(), "工单级别不能为空");
            ParamterExceptionUtils.isNull(assetStatusReqeust.getWorkOrderVO().getExecuteUserId(), "执行人不能为空");
            scheme.setExpecteStartTime(Long.valueOf(assetStatusReqeust.getWorkOrderVO().getStartTime()));
            scheme.setExpecteEndTime(Long.valueOf(assetStatusReqeust.getWorkOrderVO().getEndTime()));
            scheme.setOrderLevel(assetStatusReqeust.getWorkOrderVO().getWorkLevel());
            if (scheme.getPutintoUserId() == null) {
                scheme.setPutintoUserId(DataTypeUtils
                    .stringToInteger(aesEncoder.decode(assetStatusReqeust.getWorkOrderVO().getExecuteUserId(),
                        LoginUserUtil.getLoginUser().getUsername())));
            }
        }
        scheme.setAssetId(assetStatusReqeust.getAssetId());

        if (assetStatusReqeust.getSoftware()) {
            scheme.setSchemeSource(AssetTypeEnum.SOFTWARE.getCode());
            scheme.setAssetNextStatus(this.getNextSoftwareStatus(assetStatusReqeust).getCode());
        } else {
            scheme.setSchemeSource(AssetTypeEnum.HARDWARE.getCode());
            // 记录方案内容
            scheme.setAssetNextStatus(this.getNextAssetStatus(assetStatusReqeust).getCode());
        }

        return scheme;
    }

    protected SoftwareStatusEnum getNextSoftwareStatus(AssetStatusReqeust assetStatusReqeust) throws Exception {
        SoftwareStatusEnum softwareStatusEnum;
        if (assetStatusReqeust.getAssetFlowCategoryEnum().getCode()
            .equals(AssetFlowCategoryEnum.SOFTWARE_IMPL_RETIRE.getCode())) {
            softwareStatusEnum = SoftwareStatusJumpEnum.getNextStatus(assetStatusReqeust.getSoftwareStatusEnum(),
                assetStatusReqeust.getAgree());
        } else if (assetStatusReqeust.getAssetFlowCategoryEnum().getCode()
            .equals(AssetFlowCategoryEnum.SOFTWARE_IMPL_UNINSTALL.getCode())) {
            softwareStatusEnum = SoftwareStatusJumpEnum
                .getNextStatusUninstall(assetStatusReqeust.getSoftwareStatusEnum(), assetStatusReqeust.getAgree());
        } else {
            softwareStatusEnum = SoftwareStatusJumpEnum.getNextStatus(assetStatusReqeust.getSoftwareStatusEnum(),
                assetStatusReqeust.getAgree());
        }

        if (softwareStatusEnum == null) {
            throw new BusinessException("软件资产跃迁状态获取失败");
        }

        // 资产状态判断
        AssetSoftware software = assetSoftwareDao.getById(assetStatusReqeust.getAssetId());
        if (softwareStatusEnum.getCode().equals(software.getSoftwareStatus())) {
            throw new BusinessException("请勿重复提交，当前资产状态是：" + softwareStatusEnum.getMsg());
        }

        return softwareStatusEnum;
    }

    protected AssetStatusEnum getNextAssetStatus(AssetStatusReqeust assetStatusReqeust) throws Exception {
        AssetStatusEnum assetStatusEnum = AssetStatusJumpEnum.getNextStatus(assetStatusReqeust.getAssetStatus(),
            assetStatusReqeust.getAgree());
        // 资产状态判断
        Asset asesetStatus = assetDao.getById(assetStatusReqeust.getAssetId());
        if (assetStatusEnum.getCode().equals(asesetStatus.getAssetStatus())) {
            throw new BusinessException("请勿重复提交，当前资产状态是：" + assetStatusEnum.getMsg());
        }
        return assetStatusEnum;
    }
}