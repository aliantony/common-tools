package com.antiy.asset.controller;

import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.AssetOperationRecordDao;
import com.antiy.asset.dao.AssetSoftwareDao;
import com.antiy.asset.entity.Asset;
import com.antiy.asset.entity.AssetOperationRecord;
import com.antiy.asset.intergration.ActivityClient;
import com.antiy.asset.service.IAssetService;
import com.antiy.asset.service.IAssetSoftwareRelationService;
import com.antiy.asset.service.IAssetStatusJumpService;
import com.antiy.asset.vo.enums.AssetEventEnum;
import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.asset.vo.request.AssetConfigValidateRefuseReqeust;
import com.antiy.asset.vo.request.AssetStatusChangeRequest;
import com.antiy.asset.vo.request.AssetStatusJumpRequest;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

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


    @Value("213213")
    @Resource
    private IAssetService                 assetService;
    @Resource
    private AssetDao                      assetDao;
    @Resource
    private AssetSoftwareDao              softwareDao;
    @Resource
    private AssetOperationRecordDao       operationRecordDao;
    @Resource
    private ActivityClient                activityClient;

    @Resource
    private IAssetSoftwareRelationService softwareRelationService;

    @Resource
    private IAssetStatusJumpService assetStatusJumpService;

    /**
     * 资产状态跃迁
     *
     * @param statusJumpRequest
     * @return actionResponse
     */
    @ApiOperation(value = "资产状态跃迁", notes = "传入实体对象信息")
    @PreAuthorize("hasAuthority('asset:statusjump')")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ActionResponse statusJump(@ApiParam(value = "statusJumpRequest") @NotNull @RequestBody(required = false) AssetStatusJumpRequest statusJumpRequest) throws Exception {
        return assetStatusJumpService.changeStatus(statusJumpRequest);
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

        Integer count = assetService.assetNoRegister(assetStatusChangeRequest);
        return ActionResponse.success(count);
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
        // TODO AssetStatusEnum
        // asset.setAssetStatus(AssetStatusEnum.WAIT_SETTING.getCode());
        // 记录操作记录
        AssetOperationRecord operationRecord = new AssetOperationRecord();
        operationRecord.setTargetObjectId(baseRequest.getStringId());
        operationRecord.setOriginStatus(AssetStatusEnum.WAIT_VALIDATE.getCode());
        // operationRecord.setTargetStatus(AssetStatusEnum.WAIT_SETTING.getCode());
        operationRecord.setGmtCreate(currentTime);
        // operationRecord.setContent(AssetFlowEnum.HARDWARE_BASELINE_VALIDATE.getMsg());
        operationRecord
            .setCreateUser(LoginUserUtil.getLoginUser() != null ? LoginUserUtil.getLoginUser().getId() : null);
        operationRecord
            .setOperateUserId(LoginUserUtil.getLoginUser() != null ? LoginUserUtil.getLoginUser().getId() : null);
        operationRecord.setOperateUserName(LoginUserUtil.getLoginUser().getName());
        operationRecord.setProcessResult(0);

        // 记录验证拒绝的原因

        operationRecordDao.insert(operationRecord);
        LogUtils.info(logger, AssetEventEnum.ASSET_OPERATION_RECORD_INSERT.getName() + " {}",
            operationRecord.toString());
        return ActionResponse.success(assetDao.updateStatus(asset));
    }
}
