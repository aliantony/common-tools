package com.antiy.asset.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.antiy.asset.service.IAssetMonitorRuleRelationService;
import com.antiy.asset.vo.query.AssetMonitorRuleRelationQuery;
import com.antiy.asset.vo.request.AssetMonitorRuleRelationRequest;
import com.antiy.asset.vo.response.AssetMonitorRuleRelationResponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.QueryCondition;

import io.swagger.annotations.*;

/**
 *
 * @author zhangyajun
 * @since 2020-03-02
 */
@Api(value = "AssetMonitorRuleRelation", description = "资产监控规则与资产关系表")
@RestController
@RequestMapping("/v1/asset/assetmonitorrulerelation")
public class AssetMonitorRuleRelationController {

    @Resource
    public IAssetMonitorRuleRelationService iAssetMonitorRuleRelationService;

    /**
     * 保存
     *
     * @param assetMonitorRuleRelationRequest
     * @return actionResponse
     */
    @ApiOperation(value = "保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    public ActionResponse saveSingle(@ApiParam(value = "assetMonitorRuleRelation") @RequestBody AssetMonitorRuleRelationRequest assetMonitorRuleRelationRequest) throws Exception {
        return ActionResponse
            .success(iAssetMonitorRuleRelationService.saveAssetMonitorRuleRelation(assetMonitorRuleRelationRequest));
    }

    /**
     * 修改
     *
     * @param assetMonitorRuleRelationRequest
     * @return actionResponse
     */
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
    public ActionResponse updateSingle(@ApiParam(value = "assetMonitorRuleRelation") AssetMonitorRuleRelationRequest assetMonitorRuleRelationRequest) throws Exception {
        return ActionResponse
            .success(iAssetMonitorRuleRelationService.updateAssetMonitorRuleRelation(assetMonitorRuleRelationRequest));
    }

    /**
     * 批量查询
     *
     * @param assetMonitorRuleRelationQuery
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetMonitorRuleRelationResponse.class, responseContainer = "List"), })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    public ActionResponse queryList(@ApiParam(value = "assetMonitorRuleRelation") AssetMonitorRuleRelationQuery assetMonitorRuleRelationQuery) throws Exception {
        return ActionResponse
            .success(iAssetMonitorRuleRelationService.queryPageAssetMonitorRuleRelation(assetMonitorRuleRelationQuery));
    }

    /**
     * 通过ID查询
     *
     * @param queryCondition
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetMonitorRuleRelationResponse.class), })
    @RequestMapping(value = "/query/id", method = RequestMethod.GET)
    public ActionResponse queryById(@ApiParam(value = "主键封装对象") QueryCondition queryCondition) throws Exception {
        return ActionResponse
            .success(iAssetMonitorRuleRelationService.queryAssetMonitorRuleRelationById(queryCondition));
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
        return ActionResponse.success(iAssetMonitorRuleRelationService.deleteAssetMonitorRuleRelationById(baseRequest));
    }
}
