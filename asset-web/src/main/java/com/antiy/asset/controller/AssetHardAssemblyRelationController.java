package com.antiy.asset.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;
import io.swagger.annotations.*;
import javax.annotation.Resource;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.base.ActionResponse;
import com.antiy.asset.service.IAssetHardAssemblyRelationService;
import com.antiy.asset.vo.request.AssetHardAssemblyRelationRequest;
import com.antiy.asset.vo.response.AssetHardAssemblyRelationResponse;
import com.antiy.asset.vo.query.AssetHardAssemblyRelationQuery;

/**
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
@Api(value = "AssetHardAssemblyRelation", description = "硬件与组件关系表")
@RestController
@RequestMapping("/api/v1/asset/assethardassemblyrelation")
public class AssetHardAssemblyRelationController {

    @Resource
    public IAssetHardAssemblyRelationService iAssetHardAssemblyRelationService;

    /**
     * 保存
     *
     * @param assetHardAssemblyRelationRequest
     * @return actionResponse
     */
    @ApiOperation(value = "保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    public ActionResponse saveSingle(@ApiParam(value = "assetHardAssemblyRelation") @RequestBody AssetHardAssemblyRelationRequest assetHardAssemblyRelationRequest) throws Exception {
        return ActionResponse
            .success(iAssetHardAssemblyRelationService.saveAssetHardAssemblyRelation(assetHardAssemblyRelationRequest));
    }

    /**
     * 修改
     *
     * @param assetHardAssemblyRelationRequest
     * @return actionResponse
     */
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
    public ActionResponse updateSingle(@ApiParam(value = "assetHardAssemblyRelation") AssetHardAssemblyRelationRequest assetHardAssemblyRelationRequest) throws Exception {
        return ActionResponse.success(
            iAssetHardAssemblyRelationService.updateAssetHardAssemblyRelation(assetHardAssemblyRelationRequest));
    }

    /**
     * 批量查询
     *
     * @param assetHardAssemblyRelationQuery
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetHardAssemblyRelationResponse.class, responseContainer = "List"), })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    public ActionResponse queryList(@ApiParam(value = "assetHardAssemblyRelation") AssetHardAssemblyRelationQuery assetHardAssemblyRelationQuery) throws Exception {
        return ActionResponse.success(
            iAssetHardAssemblyRelationService.queryPageAssetHardAssemblyRelation(assetHardAssemblyRelationQuery));
    }

    /**
     * 通过ID查询
     *
     * @param queryCondition
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetHardAssemblyRelationResponse.class), })
    @RequestMapping(value = "/query/id", method = RequestMethod.GET)
    public ActionResponse queryById(@ApiParam(value = "主键封装对象") QueryCondition queryCondition) throws Exception {
        return ActionResponse
            .success(iAssetHardAssemblyRelationService.queryAssetHardAssemblyRelationById(queryCondition));
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
            .success(iAssetHardAssemblyRelationService.deleteAssetHardAssemblyRelationById(baseRequest));
    }
}
