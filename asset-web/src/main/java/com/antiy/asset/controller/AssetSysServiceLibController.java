package com.antiy.asset.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;
import io.swagger.annotations.*;
import javax.annotation.Resource;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.base.ActionResponse;
import com.antiy.asset.service.IAssetSysServiceLibService;
import com.antiy.asset.vo.request.AssetSysServiceLibRequest;
import com.antiy.asset.vo.response.AssetSysServiceLibResponse;
import com.antiy.asset.vo.query.AssetSysServiceLibQuery;

/**
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
@Api(value = "AssetSysServiceLib", description = "服务表")
@RestController
@RequestMapping("/api/v1/asset/assetsysservicelib")
public class AssetSysServiceLibController {

    @Resource
    public IAssetSysServiceLibService iAssetSysServiceLibService;

    /**
     * 保存
     *
     * @param assetSysServiceLibRequest
     * @return actionResponse
     */
    @ApiOperation(value = "保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    public ActionResponse saveSingle(@ApiParam(value = "assetSysServiceLib") @RequestBody AssetSysServiceLibRequest assetSysServiceLibRequest) throws Exception {
        return ActionResponse.success(iAssetSysServiceLibService.saveAssetSysServiceLib(assetSysServiceLibRequest));
    }

    /**
     * 修改
     *
     * @param assetSysServiceLibRequest
     * @return actionResponse
     */
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
    public ActionResponse updateSingle(@ApiParam(value = "assetSysServiceLib") @RequestBody AssetSysServiceLibRequest assetSysServiceLibRequest) throws Exception {
        return ActionResponse.success(iAssetSysServiceLibService.updateAssetSysServiceLib(assetSysServiceLibRequest));
    }

    /**
     * 批量查询
     *
     * @param assetSysServiceLibQuery
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetSysServiceLibResponse.class, responseContainer = "List"), })
    @RequestMapping(value = "/query/list", method = RequestMethod.POST)
    public ActionResponse queryList(@ApiParam(value = "assetSysServiceLib") @RequestBody AssetSysServiceLibQuery assetSysServiceLibQuery) throws Exception {
        return ActionResponse.success(iAssetSysServiceLibService.queryPageAssetSysServiceLib(assetSysServiceLibQuery));
    }

    /**
     * 通过ID查询
     *
     * @param queryCondition
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetSysServiceLibResponse.class), })
    @RequestMapping(value = "/query/id", method = RequestMethod.POST)
    public ActionResponse queryById(@ApiParam(value = "主键封装对象") @RequestBody QueryCondition queryCondition) throws Exception {
        return ActionResponse.success(iAssetSysServiceLibService.queryAssetSysServiceLibById(queryCondition));
    }

    /**
     * 通过ID删除
     *
     * @param baseRequest
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID删除接口", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/delete/id", method = RequestMethod.POST)
    public ActionResponse deleteById(@ApiParam(value = "主键封装对象") @RequestBody BaseRequest baseRequest) throws Exception {
        return ActionResponse.success(iAssetSysServiceLibService.deleteAssetSysServiceLibById(baseRequest));
    }
}
