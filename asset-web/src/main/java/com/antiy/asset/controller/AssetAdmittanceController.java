package com.antiy.asset.controller;

import javax.annotation.Resource;

import com.antiy.asset.annotation.ExcelField;
import com.antiy.asset.entity.Asset;
import com.antiy.asset.service.IAssetService;
import com.antiy.asset.templet.AccessExport;
import com.antiy.asset.util.BeanConvert;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.util.ExcelUtils;
import com.antiy.asset.vo.query.AssetQuery;
import com.antiy.asset.vo.request.AssetRequest;
import com.antiy.asset.vo.response.AssetOuterResponse;
import com.antiy.asset.vo.response.AssetResponse;
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

import java.util.List;

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
     * 批量查询
     *
     * @param asset
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    // @PreAuthorize(value = "hasAuthority('asset:admittance:queryList')")
    public ActionResponse queryList(@ApiParam(value = "asset") AssetQuery asset) throws Exception {
        return ActionResponse.success(assetService.findPageAsset(asset));
    }

    /**
     * 准入管理
     *
     * @return
     */
    @ApiOperation(value = "准入管理", notes = "准入管理")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/access/anagement", method = RequestMethod.POST)
    @PreAuthorize(value = "hasAuthority('asset:asset:accessManagement ')")
    public ActionResponse anagement(@ApiParam(value = "assetRequest") @RequestBody @Encode String assetId,
                                    Integer status) throws Exception {
        Asset asset = new Asset();
        asset.setId(DataTypeUtils.stringToInteger(assetId));
        asset.setAdmittanceStatus(status);
        return ActionResponse.success(assetService.update(asset));
    }

    /**
     * 准入管理导出
     *
     * @return
     */
    @ApiOperation(value = "准入管理导出", notes = "准入管理")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/access/export", method = RequestMethod.GET)
    @PreAuthorize(value = "hasAuthority('asset:asset:export ')")
    public ActionResponse export(@ApiParam(value = "asset") Integer status) throws Exception {
        AssetQuery assetQuery = new AssetQuery();
        assetQuery.setAdmittanceStatus(status);
        List<AssetResponse> assetList = assetService.findListAsset(assetQuery);
        List<AccessExport> accessExportList = BeanConvert.convert(assetList, AccessExport.class);
        ExcelUtils.exportToClient(AccessExport.class, "资产准入管理.xlsx", "", accessExportList);
        return ActionResponse.success();
    }
}
