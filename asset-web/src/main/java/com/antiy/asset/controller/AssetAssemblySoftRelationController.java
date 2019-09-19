package com.antiy.asset.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;
import io.swagger.annotations.*;
import javax.annotation.Resource;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.base.ActionResponse;
import com.antiy.asset.service.IAssetAssemblySoftRelationService;
import com.antiy.asset.vo.request.AssetAssemblySoftRelationRequest;
import com.antiy.asset.vo.response.AssetAssemblySoftRelationResponse;
import com.antiy.asset.vo.query.AssetAssemblySoftRelationQuery;

/**
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
@Api(value = "AssetAssemblySoftRelation", description = "组件与软件关系表")
@RestController
@RequestMapping("/api/v1/asset/assetassemblysoftrelation")
public class AssetAssemblySoftRelationController {

    @Resource
    public IAssetAssemblySoftRelationService iAssetAssemblySoftRelationService;

    /**
     * 保存
     *
     * @param assetAssemblySoftRelationRequest
     * @return actionResponse
     */
    @ApiOperation(value = "保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    public ActionResponse saveSingle(@ApiParam(value = "assetAssemblySoftRelation") @RequestBody AssetAssemblySoftRelationRequest assetAssemblySoftRelationRequest) throws Exception {
        return ActionResponse
            .success(iAssetAssemblySoftRelationService.saveAssetAssemblySoftRelation(assetAssemblySoftRelationRequest));
    }

    /**
     * 修改
     *
     * @param assetAssemblySoftRelationRequest
     * @return actionResponse
     */
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
    public ActionResponse updateSingle(@ApiParam(value = "assetAssemblySoftRelation") AssetAssemblySoftRelationRequest assetAssemblySoftRelationRequest) throws Exception {
        return ActionResponse.success(
            iAssetAssemblySoftRelationService.updateAssetAssemblySoftRelation(assetAssemblySoftRelationRequest));
    }

    /**
     * 批量查询
     *
     * @param assetAssemblySoftRelationQuery
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetAssemblySoftRelationResponse.class, responseContainer = "List"), })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    public ActionResponse queryList(@ApiParam(value = "assetAssemblySoftRelation") AssetAssemblySoftRelationQuery assetAssemblySoftRelationQuery) throws Exception {
        return ActionResponse.success(
            iAssetAssemblySoftRelationService.queryPageAssetAssemblySoftRelation(assetAssemblySoftRelationQuery));
    }

    /**
     * 通过ID查询
     *
     * @param queryCondition
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetAssemblySoftRelationResponse.class), })
    @RequestMapping(value = "/query/id", method = RequestMethod.GET)
    public ActionResponse queryById(@ApiParam(value = "主键封装对象") QueryCondition queryCondition) throws Exception {
        return ActionResponse
            .success(iAssetAssemblySoftRelationService.queryAssetAssemblySoftRelationById(queryCondition));
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
        return ActionResponse
            .success(iAssetAssemblySoftRelationService.deleteAssetAssemblySoftRelationById(baseRequest));
    }
}
