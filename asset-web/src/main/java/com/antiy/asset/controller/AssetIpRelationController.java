package com.antiy.asset.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;
import io.swagger.annotations.*;
import javax.annotation.Resource;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.base.ActionResponse;
import com.antiy.asset.service.IAssetIpRelationService;
import com.antiy.asset.vo.request.AssetIpRelationRequest;
import com.antiy.asset.vo.response.AssetIpRelationResponse;
import com.antiy.asset.vo.query.AssetIpRelationQuery;

/**
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
@Api(value = "AssetIpRelation", description = "资产-IP关系表")
@RestController
@RequestMapping("/api/v1/asset/assetiprelation")
public class AssetIpRelationController {

    @Resource
    public IAssetIpRelationService iAssetIpRelationService;

    /**
     * 保存
     *
     * @param assetIpRelationRequest
     * @return actionResponse
     */
    @ApiOperation(value = "保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    public ActionResponse saveSingle(@ApiParam(value = "assetIpRelation") @RequestBody AssetIpRelationRequest assetIpRelationRequest) throws Exception {
        return ActionResponse.success(iAssetIpRelationService.saveAssetIpRelation(assetIpRelationRequest));
    }

    /**
     * 修改
     *
     * @param assetIpRelationRequest
     * @return actionResponse
     */
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
    public ActionResponse updateSingle(@ApiParam(value = "assetIpRelation") AssetIpRelationRequest assetIpRelationRequest) throws Exception {
        return ActionResponse.success(iAssetIpRelationService.updateAssetIpRelation(assetIpRelationRequest));
    }

    /**
     * 批量查询
     *
     * @param assetIpRelationQuery
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetIpRelationResponse.class, responseContainer = "List"), })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    public ActionResponse queryList(@ApiParam(value = "assetIpRelation") AssetIpRelationQuery assetIpRelationQuery) throws Exception {
        return ActionResponse.success(iAssetIpRelationService.queryPageAssetIpRelation(assetIpRelationQuery));
    }

    /**
     * 通过ID查询
     *
     * @param queryCondition
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetIpRelationResponse.class), })
    @RequestMapping(value = "/query/id", method = RequestMethod.GET)
    public ActionResponse queryById(@ApiParam(value = "主键封装对象") QueryCondition queryCondition) throws Exception {
        return ActionResponse.success(iAssetIpRelationService.queryAssetIpRelationById(queryCondition));
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
        return ActionResponse.success(iAssetIpRelationService.deleteAssetIpRelationById(baseRequest));
    }
}
