package com.antiy.asset.controller;

import com.antiy.asset.service.IAssetSoftwareRelationService;
import com.antiy.asset.vo.query.AssetSoftwareRelationQuery;
import com.antiy.asset.vo.request.AssetSoftwareRelationRequest;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.utils.ParamterExceptionUtils;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * @author zhangyajun
 * @since 2019-01-02
 */
@Api(value = "AssetSoftwareRelation", description = "资产软件关系信息")
@RestController
@RequestMapping("/v1/asset/assetsoftwarerelation")
public class AssetSoftwareRelationController {

    @Resource
    public IAssetSoftwareRelationService iAssetSoftwareRelationService;

    /**
     * 保存
     *
     * @param assetSoftwareRelationRequest
     * @return actionResponse
     */
    @ApiOperation(value = "保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    public ActionResponse saveSingle(@RequestBody @ApiParam(value = "assetSoftwareRelationRequest") AssetSoftwareRelationRequest assetSoftwareRelationRequest) throws Exception {
        iAssetSoftwareRelationService.saveAssetSoftwareRelation(assetSoftwareRelationRequest);
        return ActionResponse.success();
    }

    /**
     * 修改
     *
     * @param assetSoftwareRelationRequest
     * @return actionResponse
     */
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
    public ActionResponse updateSingle(@RequestBody @ApiParam(value = "assetSoftwareRelationRequest") AssetSoftwareRelationRequest assetSoftwareRelationRequest) throws Exception {
        iAssetSoftwareRelationService.updateAssetSoftwareRelation(assetSoftwareRelationRequest);
        return ActionResponse.success();
    }

    /**
     * 批量查询
     *
     * @param assetSoftwareRelationRequest
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    public ActionResponse queryList(@ApiParam(value = "assetSoftwareRelationRequest") AssetSoftwareRelationQuery assetSoftwareRelationRequest) throws Exception {
        return ActionResponse.success(iAssetSoftwareRelationService.findPageAssetSoftwareRelation(assetSoftwareRelationRequest));
    }

    /**
     * 通过ID查询
     *
     * @param query 主键封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/query/id", method = RequestMethod.GET)
    public ActionResponse queryById(@ApiParam(value = "assetSoftwareRelationRequest") QueryCondition query) throws Exception {
        ParamterExceptionUtils.isBlank(query.getPrimaryKey(), "ID不能为空");
        return ActionResponse.success(iAssetSoftwareRelationService.getById(Integer.valueOf(query.getPrimaryKey())));
    }

    /**
     * 通过ID删除
     *
     * @param query 主键封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID删除接口", notes = "主键封装对象")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/delete/id", method = RequestMethod.POST)
    public ActionResponse deleteById(@ApiParam(value = "query") QueryCondition query) throws Exception {
        ParamterExceptionUtils.isBlank(query.getPrimaryKey(), "ID不能为空");
        return ActionResponse.success(iAssetSoftwareRelationService.deleteById(Integer.valueOf(query.getPrimaryKey())));
    }

    /**
     * 通过资产ID查询关联软件信息
     *
     * @param assetId
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "资产ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/query/{assetId}", method = RequestMethod.GET)
    public ActionResponse queryList(@ApiParam(value = "assetId") @PathVariable("assetId") Integer assetId) throws Exception {
        return ActionResponse.success(iAssetSoftwareRelationService.getSoftByAssetId(assetId));
    }
}

