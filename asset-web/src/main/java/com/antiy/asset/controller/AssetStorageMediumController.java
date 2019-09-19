package com.antiy.asset.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;
import io.swagger.annotations.*;
import javax.annotation.Resource;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.base.ActionResponse;
import com.antiy.asset.service.IAssetStorageMediumService;
import com.antiy.asset.vo.request.AssetStorageMediumRequest;
import com.antiy.asset.vo.response.AssetStorageMediumResponse;
import com.antiy.asset.vo.query.AssetStorageMediumQuery;

/**
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
@Api(value = "AssetStorageMedium", description = "存数设备表")
@RestController
@RequestMapping("/api/v1/asset/assetstoragemedium")
public class AssetStorageMediumController {

    @Resource
    public IAssetStorageMediumService iAssetStorageMediumService;

    /**
     * 保存
     *
     * @param assetStorageMediumRequest
     * @return actionResponse
     */
    @ApiOperation(value = "保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    public ActionResponse saveSingle(@ApiParam(value = "assetStorageMedium") @RequestBody AssetStorageMediumRequest assetStorageMediumRequest) throws Exception {
        return ActionResponse.success(iAssetStorageMediumService.saveAssetStorageMedium(assetStorageMediumRequest));
    }

    /**
     * 修改
     *
     * @param assetStorageMediumRequest
     * @return actionResponse
     */
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
    public ActionResponse updateSingle(@ApiParam(value = "assetStorageMedium") AssetStorageMediumRequest assetStorageMediumRequest) throws Exception {
        return ActionResponse.success(iAssetStorageMediumService.updateAssetStorageMedium(assetStorageMediumRequest));
    }

    /**
     * 批量查询
     *
     * @param assetStorageMediumQuery
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetStorageMediumResponse.class, responseContainer = "List"), })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    public ActionResponse queryList(@ApiParam(value = "assetStorageMedium") AssetStorageMediumQuery assetStorageMediumQuery) throws Exception {
        return ActionResponse.success(iAssetStorageMediumService.queryPageAssetStorageMedium(assetStorageMediumQuery));
    }
}
