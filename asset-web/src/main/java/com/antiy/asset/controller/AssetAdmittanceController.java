package com.antiy.asset.controller;

import javax.annotation.Resource;

import com.antiy.asset.entity.Asset;
import com.antiy.asset.service.IAssetService;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.vo.query.AssetQuery;
import com.antiy.asset.vo.request.AssetRequest;
import com.antiy.asset.vo.response.AssetOuterResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.antiy.asset.service.IAssetCategoryModelService;
import com.antiy.asset.vo.query.AssetCategoryModelQuery;
import com.antiy.asset.vo.request.AssetCategoryModelRequest;
import com.antiy.asset.vo.response.AssetCategoryModelResponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.encoder.Encode;
import com.antiy.common.utils.ParamterExceptionUtils;

import io.swagger.annotations.*;

/**
 * @author 吕梁
 * @since 2019-01-19
 */
@Api(value = "AssetAdmittance", description = "准入管理")
@RestController
@RequestMapping("/api/v1/asset/admittance")
public class AssetAdmittanceController {

    @Resource
    public IAssetService assetService;

    /**
     * 更改资产准入状态
     * @param assetRequest
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "更改资产准入状态", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    @PreAuthorize(value="hasAuthority('asset:admittance:changeStatus')")
    public ActionResponse changeStatus(@RequestBody(required = false) @ApiParam(value = "asset") AssetRequest assetRequest) throws Exception {
        Asset asset = new Asset();
        asset.setId(DataTypeUtils.stringToInteger(assetRequest.getId()));
        asset.setAssetdmittanceStatus(assetRequest.getAdmittanceStatus());
        assetService.update(asset);
        return ActionResponse.success();
    }


    /**
     * 批量查询
     *
     * @param asset
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetOuterResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
//    @PreAuthorize(value = "hasAuthority('asset:admittance:queryList')")
    public ActionResponse queryList(@ApiParam(value = "asset") AssetQuery asset) throws Exception {
        return ActionResponse.success(assetService.findPageAsset(asset));
    }

}
