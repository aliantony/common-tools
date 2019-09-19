package com.antiy.asset.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;
import io.swagger.annotations.*;
import javax.annotation.Resource;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.base.ActionResponse;
import com.antiy.asset.service.IAssetProtocolService;
import com.antiy.asset.vo.request.AssetProtocolRequest;
import com.antiy.asset.vo.response.AssetProtocolResponse;
import com.antiy.asset.vo.query.AssetProtocolQuery;

/**
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
@Api(value = "AssetProtocol", description = "协议表")
@RestController
@RequestMapping("/api/v1/asset/assetprotocol")
public class AssetProtocolController {

    @Resource
    public IAssetProtocolService iAssetProtocolService;

    /**
     * 保存
     *
     * @param assetProtocolRequest
     * @return actionResponse
     */
    @ApiOperation(value = "保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    public ActionResponse saveSingle(@ApiParam(value = "assetProtocol") @RequestBody AssetProtocolRequest assetProtocolRequest) throws Exception {
        return ActionResponse.success(iAssetProtocolService.saveAssetProtocol(assetProtocolRequest));
    }

    /**
     * 修改
     *
     * @param assetProtocolRequest
     * @return actionResponse
     */
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
    public ActionResponse updateSingle(@ApiParam(value = "assetProtocol") AssetProtocolRequest assetProtocolRequest) throws Exception {
        return ActionResponse.success(iAssetProtocolService.updateAssetProtocol(assetProtocolRequest));
    }

    /**
     * 批量查询
     *
     * @param assetProtocolQuery
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetProtocolResponse.class, responseContainer = "List"), })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    public ActionResponse queryList(@ApiParam(value = "assetProtocol") AssetProtocolQuery assetProtocolQuery) throws Exception {
        return ActionResponse.success(iAssetProtocolService.queryPageAssetProtocol(assetProtocolQuery));
    }

    /**
     * 通过ID查询
     *
     * @param queryCondition
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetProtocolResponse.class), })
    @RequestMapping(value = "/query/id", method = RequestMethod.GET)
    public ActionResponse queryById(@ApiParam(value = "主键封装对象") QueryCondition queryCondition) throws Exception {
        return ActionResponse.success(iAssetProtocolService.queryAssetProtocolById(queryCondition));
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
    public ActionResponse deleteById(@ApiParam(value = "主键封装对象") BaseRequest baseRequest) throws Exception {
        return ActionResponse.success(iAssetProtocolService.deleteAssetProtocolById(baseRequest));
    }
}
