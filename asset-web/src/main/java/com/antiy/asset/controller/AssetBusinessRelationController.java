package com.antiy.asset.controller;

import com.antiy.asset.service.IAssetBusinessRelationService;
import com.antiy.asset.vo.query.AssetBusinessRelationQuery;
import com.antiy.asset.vo.request.AssetBusinessRelationRequest;
import com.antiy.asset.vo.response.AssetBusinessRelationResponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.QueryCondition;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 *
 * @author zhangyajun
 * @since 2020-02-17
 */
@Api(value = "AssetBusinessRelation", description = "")
@RestController
@RequestMapping("/v1/asset/assetbusinessrelation")
public class AssetBusinessRelationController {

    @Resource
    public IAssetBusinessRelationService iAssetBusinessRelationService;

    /**
     * 保存
     *
     * @param assetBusinessRelationRequest
     * @return actionResponse
     */
    @ApiOperation(value = "保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Integer.class),
    })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    public ActionResponse saveSingle(@ApiParam(value = "assetBusinessRelation") @RequestBody AssetBusinessRelationRequest assetBusinessRelationRequest)throws Exception{
        return ActionResponse.success(iAssetBusinessRelationService.saveAssetBusinessRelation(assetBusinessRelationRequest));
    }

    /**
     * 修改
     *
     * @param assetBusinessRelationRequest
     * @return actionResponse
     */
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Integer.class),
    })
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
    public ActionResponse updateSingle(@ApiParam(value = "assetBusinessRelation") AssetBusinessRelationRequest assetBusinessRelationRequest)throws Exception{
        return ActionResponse.success(iAssetBusinessRelationService.updateAssetBusinessRelation(assetBusinessRelationRequest));
    }

    /**
     * 批量查询
     *
     * @param assetBusinessRelationQuery
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = AssetBusinessRelationResponse.class, responseContainer = "List"),
    })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    public ActionResponse queryList(@ApiParam(value = "assetBusinessRelation") AssetBusinessRelationQuery assetBusinessRelationQuery)throws Exception{
        return ActionResponse.success(iAssetBusinessRelationService.queryPageAssetBusinessRelation(assetBusinessRelationQuery));
    }

    /**
     * 通过ID查询
     *
     * @param queryCondition
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = AssetBusinessRelationResponse.class),
    })
    @RequestMapping(value = "/query/id", method = RequestMethod.GET)
    public ActionResponse queryById(@ApiParam(value = "主键封装对象") QueryCondition queryCondition)throws Exception{
        return ActionResponse.success(iAssetBusinessRelationService.queryAssetBusinessRelationById(queryCondition));
    }

    /**
     * 通过ID删除
     *
     * @param baseRequest
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID删除接口", notes = "主键封装对象")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Integer.class),
    })
    @RequestMapping(value = "/delete/id", method = RequestMethod.POST)
    public ActionResponse deleteById(@ApiParam(value = "主键封装对象") BaseRequest baseRequest)throws Exception{
        return ActionResponse.success(iAssetBusinessRelationService.deleteAssetBusinessRelationById(baseRequest));
    }
}

