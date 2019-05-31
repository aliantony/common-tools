package com.antiy.asset.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.AssetOperationRecordDao;
import com.antiy.asset.dao.AssetSoftwareDao;
import com.antiy.asset.dao.SchemeDao;
import com.antiy.asset.entity.Asset;
import com.antiy.asset.entity.AssetOperationRecord;
import com.antiy.asset.entity.AssetSoftware;
import com.antiy.asset.entity.Scheme;
import com.antiy.asset.service.IAssetService;
import com.antiy.asset.service.IAssetSoftwareRelationService;
import com.antiy.asset.service.impl.AssetStatusChangeFactory;
import com.antiy.asset.service.impl.AssetStatusChangeFlowProcessImpl;
import com.antiy.asset.service.impl.SoftWareStatusChangeProcessImpl;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.vo.enums.*;
import com.antiy.asset.vo.request.*;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.BusinessData;
import com.antiy.common.enums.BusinessModuleEnum;
import com.antiy.common.enums.BusinessPhaseEnum;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;

import io.swagger.annotations.*;

/**
 * 资产状态跃迁统一接口
 *
 * @author zhangyajun
 * @since 2019-01-04
 */
@Api(value = "statusjump", description = "资产状态跃迁")
@RestController
@RequestMapping("/api/v1/asset/statusjump")
public class AssetStatusJumpController {
    private Logger                        logger = LogUtils.get(this.getClass());
    @Resource
    private IAssetService                 assetService;
    @Resource
    AssetDao                              assetDao;
    @Resource
    AssetSoftwareDao                      softwareDao;
    @Resource
    AssetOperationRecordDao               operationRecordDao;
    @Resource
    SchemeDao                             schemeDao;

    @Resource
    private IAssetSoftwareRelationService softwareRelationService;

    /**
     * 资产状态跃迁
     *
     * @param assetStatusReqeust
     * @return actionResponse
     */
    @ApiOperation(value = "资产状态跃迁", notes = "传入实体对象信息")
    @PreAuthorize("hasAuthority('asset:statusjump')")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ActionResponse statusJump(@ApiParam(value = "assetStatusReqeust") @RequestBody(required = false) AssetStatusReqeust assetStatusReqeust) throws Exception {
        if (assetStatusReqeust.getSoftware()) {
            return AssetStatusChangeFactory.getStatusChangeProcess(SoftWareStatusChangeProcessImpl.class)
                .changeStatus(assetStatusReqeust);
        } else {
            return AssetStatusChangeFactory.getStatusChangeProcess(AssetStatusChangeFlowProcessImpl.class)
                .changeStatus(assetStatusReqeust);
        }
    }

    /**
     * 资产状态跃迁代配置使用
     *
     * @param assetStatusJumpRequst
     * @return actionResponse
     */
    @ApiOperation(value = "资产状态跃迁配置使用", notes = "传入实体对象信息")
    // @PreAuthorize("hasAuthority('asset:statusjump')")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/baseline", method = RequestMethod.POST)
    public ActionResponse statusJumpWithAsset(@ApiParam(value = "assetStatusJumpRequst") @RequestBody(required = false) AssetStatusJumpRequst assetStatusJumpRequst) throws Exception {
        return ActionResponse.success(assetService.changeToNextStatus(assetStatusJumpRequst));
    }

    @ApiOperation(value = "软件安装状态修改", notes = "传入实体对象信息")
    // @PreAuthorize("hasAuthority('asset:baseline:configurateSoftware')")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/baseline/configurateSoftware", method = RequestMethod.POST)
    public ActionResponse configurateSoftware(@ApiParam(value = "assetStatusJumpRequst") @RequestBody AssetRelationSoftRequest assetRelationSoftRequest) throws Exception {
        return ActionResponse.success(softwareRelationService.updateAssetReleation(assetRelationSoftRequest));
    }

    /**
     * 资产不予登记
     *
     * @param assetStatusChangeRequest
     * @return actionResponse
     */
    @ApiOperation(value = "资产不予登记", notes = "传入实体对象信息")
    @PreAuthorize("hasAuthority('asset:noRegister')")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/noRegister", method = RequestMethod.POST)
    public ActionResponse assetNoRegister(@ApiParam(value = "assetStatusChangeRequest") @RequestBody(required = false) AssetStatusChangeRequest assetStatusChangeRequest) throws Exception {
        String assetId = assetStatusChangeRequest.getAssetId();
        if (assetStatusChangeRequest.getSoftware()) {
            AssetSoftware assetSoftware = new AssetSoftware();
            assetSoftware.setId(DataTypeUtils.stringToInteger(assetStatusChangeRequest.getAssetId()));
            assetSoftware.setGmtModified(System.currentTimeMillis());
            if (LoginUserUtil.getLoginUser() == null) {
                LogUtils.info(logger, AssetEventEnum.GET_USER_INOF.getName() + " {}", "无法获取用户信息");
            } else {
                assetSoftware.setModifyUser(LoginUserUtil.getLoginUser().getId());
            }
            assetSoftware.setSoftwareStatus(SoftwareStatusEnum.NOT_REGSIST.getCode());
            operationRecord(assetStatusChangeRequest, assetId);
            LogUtils.recordOperLog(
                new BusinessData(AssetEventEnum.SOFT_NO_REGISTER.getName(), DataTypeUtils.stringToInteger(assetId),
                    softwareDao.getById(assetId).getName(), assetStatusChangeRequest, BusinessModuleEnum.SOFTWARE_ASSET,
                    BusinessPhaseEnum.NOT_REGISTER));
            LogUtils.info(logger, AssetEventEnum.SOFT_NO_REGISTER.getName() + " {}", assetStatusChangeRequest);

            return ActionResponse.success(softwareDao.update(assetSoftware));
        } else {
            Asset asset = new Asset();
            asset.setId(DataTypeUtils.stringToInteger(assetId));
            asset.setAssetStatus(AssetStatusEnum.NOT_REGSIST.getCode());
            operationRecord(assetStatusChangeRequest, assetId);

            // 记录日志
            LogUtils.recordOperLog(new BusinessData(AssetEventEnum.NO_REGISTER.getName(),
                DataTypeUtils.stringToInteger(assetId), assetDao.getNumberById(assetId), assetStatusChangeRequest,
                BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.NOT_REGISTER));
            LogUtils.info(logger, AssetEventEnum.NO_REGISTER.getName() + " {}", assetStatusChangeRequest);
            return ActionResponse.success(assetService.update(asset));
        }
    }

    private void operationRecord(@RequestBody(required = false) @ApiParam("assetStatusChangeRequest") AssetStatusChangeRequest assetStatusChangeRequest,
                                 String id) throws Exception {
        // 记录操作历史到数据库
        AssetOperationRecord operationRecord = new AssetOperationRecord();
        operationRecord.setTargetObjectId(id);
        operationRecord.setOriginStatus(assetStatusChangeRequest.getStatus());
        operationRecord.setTargetStatus(AssetStatusEnum.NOT_REGSIST.getCode());
        operationRecord.setGmtCreate(System.currentTimeMillis());
        if (!assetStatusChangeRequest.getSoftware()) {
            operationRecord.setAreaId(assetDao.getAreaIdById(id));
            operationRecord.setContent(AssetEventEnum.SOFT_UPDATE.getName());
        }
        operationRecord.setContent(AssetEventEnum.ASSET_MODIFY.getName());
        if (LoginUserUtil.getLoginUser() == null) {
            LogUtils.info(logger, AssetEventEnum.GET_USER_INOF.getName() + " {}", "无法获取用户信息");
        } else {
            operationRecord.setCreateUser(LoginUserUtil.getLoginUser().getId());
            operationRecord.setOperateUserId(LoginUserUtil.getLoginUser().getId());
        }
        operationRecordDao.insert(operationRecord);
    }

    /**
     * 资产配置验证拒绝变更资产状态(配置模块中对资产验证拒绝时才调用此接口)
     *
     * @param baseRequest
     * @return actionResponse
     */
    @ApiOperation(value = "资产配置验证拒绝变更资产状态", notes = "传入实体对象信息")
    @PreAuthorize("hasAuthority('asset:updateAssetStatus')")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/updateAssetStatus", method = RequestMethod.POST)
    public ActionResponse updateAssetStatus(@ApiParam(value = "baseRequest") @RequestBody(required = false) AssetConfigValidateRefuseReqeust baseRequest) throws Exception {
        Long currentTime = System.currentTimeMillis();
        Asset asset = new Asset();
        asset.setId(baseRequest.getId());
        asset.setGmtModified(currentTime);
        asset.setModifyUser(LoginUserUtil.getLoginUser() != null ? LoginUserUtil.getLoginUser().getId() : null);
        asset.setAssetStatus(AssetStatusEnum.WAIT_SETTING.getCode());
        // 记录操作记录
        AssetOperationRecord operationRecord = new AssetOperationRecord();
        operationRecord.setAreaId(assetDao.getAreaIdById(baseRequest.getStringId()));
        operationRecord.setTargetObjectId(baseRequest.getStringId());
        operationRecord.setOriginStatus(AssetStatusEnum.WAIT_VALIDATE.getCode());
        operationRecord.setTargetStatus(AssetStatusEnum.WAIT_SETTING.getCode());
        operationRecord.setGmtCreate(currentTime);
        operationRecord.setContent(AssetFlowEnum.HARDWARE_BASELINE_VALIDATE.getMsg());
        operationRecord
            .setCreateUser(LoginUserUtil.getLoginUser() != null ? LoginUserUtil.getLoginUser().getId() : null);
        operationRecord
            .setOperateUserId(LoginUserUtil.getLoginUser() != null ? LoginUserUtil.getLoginUser().getId() : null);
        operationRecord.setProcessResult(0);
        operationRecordDao.insert(operationRecord);
        LogUtils.info(logger, AssetEventEnum.ASSET_OPERATION_RECORD_INSERT.getName() + " {}",
            operationRecord.toString());

        // 记录验证拒绝的原因
        Scheme scheme = new Scheme();
        scheme.setGmtCreate(currentTime);
        scheme.setCreateUser(LoginUserUtil.getLoginUser() != null ? LoginUserUtil.getLoginUser().getId() : null);
        scheme.setMemo(baseRequest.getMemo());
        scheme.setType(6);
        scheme.setAssetId(baseRequest.getStringId());
        scheme.setSchemeSource(AssetTypeEnum.HARDWARE.getCode());
        scheme.setAssetNextStatus(AssetStatusEnum.WATI_REGSIST.getCode());
        schemeDao.insert(scheme);
        LogUtils.info(logger, AssetEventEnum.ASSET_SCHEME_INSERT.getName() + " {}", scheme.toString());
        return ActionResponse.success(assetDao.updateStatus(asset));
    }
}
