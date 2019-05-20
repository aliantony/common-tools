package com.antiy.asset.controller;

import javax.annotation.Resource;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.antiy.asset.entity.Asset;
import com.antiy.asset.service.IAssetService;
import com.antiy.asset.service.IAssetSoftwareRelationService;
import com.antiy.asset.service.impl.AssetStatusChangeFactory;
import com.antiy.asset.service.impl.AssetStatusChangeFlowProcessImpl;
import com.antiy.asset.service.impl.SoftWareStatusChangeProcessImpl;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.asset.vo.request.AssetRelationSoftRequest;
import com.antiy.asset.vo.request.AssetStatusChangeRequest;
import com.antiy.asset.vo.request.AssetStatusJumpRequst;
import com.antiy.asset.vo.request.AssetStatusReqeust;
import com.antiy.common.base.ActionResponse;

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

    @Resource
    private IAssetService                 assetService;

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
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ActionResponse assetNoRegister(@ApiParam(value = "assetStatusChangeRequest") @RequestBody(required = false) AssetStatusChangeRequest assetStatusChangeRequest) throws Exception {
        if (assetStatusChangeRequest.getSoftware()) {
            // 待启用
            return null;
        } else {
            Asset asset = new Asset();
            asset.setId(DataTypeUtils.stringToInteger(assetStatusChangeRequest.getAssetId()));
            asset.setAssetStatus(AssetStatusEnum.NOT_REGSIST.getCode());
            return ActionResponse.success(assetService.update(asset));
        }
    }
}
