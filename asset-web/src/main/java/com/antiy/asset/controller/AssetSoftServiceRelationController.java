package com.antiy.asset.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;
import io.swagger.annotations.*;
import javax.annotation.Resource;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.base.ActionResponse;
import com.antiy.asset.service.IAssetSoftServiceRelationService;
import com.antiy.asset.vo.request.AssetSoftServiceRelationRequest;
import com.antiy.asset.vo.response.AssetSoftServiceRelationResponse;
import com.antiy.asset.vo.query.AssetSoftServiceRelationQuery;

/**
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
@Api(value = "AssetSoftServiceRelation", description = "软件依赖的服务")
@RestController
@RequestMapping("/api/v1/asset/assetsoftservicerelation")
public class AssetSoftServiceRelationController {

    @Resource
    public IAssetSoftServiceRelationService iAssetSoftServiceRelationService;

    /**
     * 保存
     *
     * @param assetSoftServiceRelationRequest
     * @return actionResponse
     */
    @ApiOperation(value = "保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    public ActionResponse saveSingle(@ApiParam(value = "assetSoftServiceRelation") @RequestBody AssetSoftServiceRelationRequest assetSoftServiceRelationRequest) throws Exception {
        return ActionResponse
            .success(iAssetSoftServiceRelationService.saveAssetSoftServiceRelation(assetSoftServiceRelationRequest));
    }

    /**
     * 修改
     *
     * @param assetSoftServiceRelationRequest
     * @return actionResponse
     */
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
    public ActionResponse updateSingle(@ApiParam(value = "assetSoftServiceRelation") AssetSoftServiceRelationRequest assetSoftServiceRelationRequest) throws Exception {
        return ActionResponse
            .success(iAssetSoftServiceRelationService.updateAssetSoftServiceRelation(assetSoftServiceRelationRequest));
    }

    /**
     * 批量查询
     *
     * @param assetSoftServiceRelationQuery
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetSoftServiceRelationResponse.class, responseContainer = "List"), })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    public ActionResponse queryList(@ApiParam(value = "assetSoftServiceRelation") AssetSoftServiceRelationQuery assetSoftServiceRelationQuery) throws Exception {
        return ActionResponse
            .success(iAssetSoftServiceRelationService.queryPageAssetSoftServiceRelation(assetSoftServiceRelationQuery));
    }

    /**
     * 通过ID查询
     *
     * @param queryCondition
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetSoftServiceRelationResponse.class), })
    @RequestMapping(value = "/query/id", method = RequestMethod.GET)
    public ActionResponse queryById(@ApiParam(value = "主键封装对象") QueryCondition queryCondition) throws Exception {
        return ActionResponse
            .success(iAssetSoftServiceRelationService.queryAssetSoftServiceRelationById(queryCondition));
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
        return ActionResponse.success(iAssetSoftServiceRelationService.deleteAssetSoftServiceRelationById(baseRequest));
    }
}
