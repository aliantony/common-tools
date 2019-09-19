package com.antiy.asset.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;
import io.swagger.annotations.*;
import javax.annotation.Resource;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.base.ActionResponse;
import com.antiy.asset.service.IAssetSoftProtocolRelationService;
import com.antiy.asset.vo.request.AssetSoftProtocolRelationRequest;
import com.antiy.asset.vo.response.AssetSoftProtocolRelationResponse;
import com.antiy.asset.vo.query.AssetSoftProtocolRelationQuery;

/**
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
@Api(value = "AssetSoftProtocolRelation", description = "软件与协议表")
@RestController
@RequestMapping("/api/v1/asset/assetsoftprotocolrelation")
public class AssetSoftProtocolRelationController {

    @Resource
    public IAssetSoftProtocolRelationService iAssetSoftProtocolRelationService;

    /**
     * 保存
     *
     * @param assetSoftProtocolRelationRequest
     * @return actionResponse
     */
    @ApiOperation(value = "保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    public ActionResponse saveSingle(@ApiParam(value = "assetSoftProtocolRelation") @RequestBody AssetSoftProtocolRelationRequest assetSoftProtocolRelationRequest) throws Exception {
        return ActionResponse
            .success(iAssetSoftProtocolRelationService.saveAssetSoftProtocolRelation(assetSoftProtocolRelationRequest));
    }

    /**
     * 修改
     *
     * @param assetSoftProtocolRelationRequest
     * @return actionResponse
     */
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
    public ActionResponse updateSingle(@ApiParam(value = "assetSoftProtocolRelation") AssetSoftProtocolRelationRequest assetSoftProtocolRelationRequest) throws Exception {
        return ActionResponse.success(
            iAssetSoftProtocolRelationService.updateAssetSoftProtocolRelation(assetSoftProtocolRelationRequest));
    }

    /**
     * 批量查询
     *
     * @param assetSoftProtocolRelationQuery
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetSoftProtocolRelationResponse.class, responseContainer = "List"), })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    public ActionResponse queryList(@ApiParam(value = "assetSoftProtocolRelation") AssetSoftProtocolRelationQuery assetSoftProtocolRelationQuery) throws Exception {
        return ActionResponse.success(
            iAssetSoftProtocolRelationService.queryPageAssetSoftProtocolRelation(assetSoftProtocolRelationQuery));
    }

    /**
     * 通过ID查询
     *
     * @param queryCondition
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetSoftProtocolRelationResponse.class), })
    @RequestMapping(value = "/query/id", method = RequestMethod.GET)
    public ActionResponse queryById(@ApiParam(value = "主键封装对象") QueryCondition queryCondition) throws Exception {
        return ActionResponse
            .success(iAssetSoftProtocolRelationService.queryAssetSoftProtocolRelationById(queryCondition));
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
            .success(iAssetSoftProtocolRelationService.deleteAssetSoftProtocolRelationById(baseRequest));
    }
}
