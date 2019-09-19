package com.antiy.asset.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;
import io.swagger.annotations.*;
import javax.annotation.Resource;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.base.ActionResponse;
import com.antiy.asset.service.IAssetMacRelationService;
import com.antiy.asset.vo.request.AssetMacRelationRequest;
import com.antiy.asset.vo.response.AssetMacRelationResponse;
import com.antiy.asset.vo.query.AssetMacRelationQuery;

/**
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
@Api(value = "AssetMacRelation", description = "资产-MAC关系表")
@RestController
@RequestMapping("/api/v1/asset/assetmacrelation")
public class AssetMacRelationController {

    @Resource
    public IAssetMacRelationService iAssetMacRelationService;

    /**
     * 保存
     *
     * @param assetMacRelationRequest
     * @return actionResponse
     */
    @ApiOperation(value = "保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    public ActionResponse saveSingle(@ApiParam(value = "assetMacRelation") @RequestBody AssetMacRelationRequest assetMacRelationRequest) throws Exception {
        return ActionResponse.success(iAssetMacRelationService.saveAssetMacRelation(assetMacRelationRequest));
    }

    /**
     * 修改
     *
     * @param assetMacRelationRequest
     * @return actionResponse
     */
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
    public ActionResponse updateSingle(@ApiParam(value = "assetMacRelation") AssetMacRelationRequest assetMacRelationRequest) throws Exception {
        return ActionResponse.success(iAssetMacRelationService.updateAssetMacRelation(assetMacRelationRequest));
    }

    /**
     * 批量查询
     *
     * @param assetMacRelationQuery
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetMacRelationResponse.class, responseContainer = "List"), })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    public ActionResponse queryList(@ApiParam(value = "assetMacRelation") AssetMacRelationQuery assetMacRelationQuery) throws Exception {
        return ActionResponse.success(iAssetMacRelationService.queryPageAssetMacRelation(assetMacRelationQuery));
    }

    /**
     * 通过ID查询
     *
     * @param queryCondition
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetMacRelationResponse.class), })
    @RequestMapping(value = "/query/id", method = RequestMethod.GET)
    public ActionResponse queryById(@ApiParam(value = "主键封装对象") QueryCondition queryCondition) throws Exception {
        return ActionResponse.success(iAssetMacRelationService.queryAssetMacRelationById(queryCondition));
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
        return ActionResponse.success(iAssetMacRelationService.deleteAssetMacRelationById(baseRequest));
    }
}
