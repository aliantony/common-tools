package com.antiy.asset.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;
import io.swagger.annotations.*;
import javax.annotation.Resource;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.base.ActionResponse;
import com.antiy.asset.service.IAssetServicePortRelationService;
import com.antiy.asset.vo.request.AssetServicePortRelationRequest;
import com.antiy.asset.vo.response.AssetServicePortRelationResponse;
import com.antiy.asset.vo.query.AssetServicePortRelationQuery;

/**
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
@Api(value = "AssetServicePortRelation", description = "服务与端口表")
@RestController
@RequestMapping("/api/v1/asset/assetserviceportrelation")
public class AssetServicePortRelationController {

    @Resource
    public IAssetServicePortRelationService iAssetServicePortRelationService;

    /**
     * 保存
     *
     * @param assetServicePortRelationRequest
     * @return actionResponse
     */
    @ApiOperation(value = "保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    public ActionResponse saveSingle(@ApiParam(value = "assetServicePortRelation") @RequestBody AssetServicePortRelationRequest assetServicePortRelationRequest) throws Exception {
        return ActionResponse
            .success(iAssetServicePortRelationService.saveAssetServicePortRelation(assetServicePortRelationRequest));
    }

    /**
     * 修改
     *
     * @param assetServicePortRelationRequest
     * @return actionResponse
     */
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
    public ActionResponse updateSingle(@ApiParam(value = "assetServicePortRelation") AssetServicePortRelationRequest assetServicePortRelationRequest) throws Exception {
        return ActionResponse
            .success(iAssetServicePortRelationService.updateAssetServicePortRelation(assetServicePortRelationRequest));
    }

    /**
     * 批量查询
     *
     * @param assetServicePortRelationQuery
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetServicePortRelationResponse.class, responseContainer = "List"), })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    public ActionResponse queryList(@ApiParam(value = "assetServicePortRelation") AssetServicePortRelationQuery assetServicePortRelationQuery) throws Exception {
        return ActionResponse
            .success(iAssetServicePortRelationService.queryPageAssetServicePortRelation(assetServicePortRelationQuery));
    }

    /**
     * 通过ID查询
     *
     * @param queryCondition
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetServicePortRelationResponse.class), })
    @RequestMapping(value = "/query/id", method = RequestMethod.GET)
    public ActionResponse queryById(@ApiParam(value = "主键封装对象") QueryCondition queryCondition) throws Exception {
        return ActionResponse
            .success(iAssetServicePortRelationService.queryAssetServicePortRelationById(queryCondition));
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
        return ActionResponse.success(iAssetServicePortRelationService.deleteAssetServicePortRelationById(baseRequest));
    }
}