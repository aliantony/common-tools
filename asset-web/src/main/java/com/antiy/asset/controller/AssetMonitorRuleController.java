package com.antiy.asset.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.antiy.asset.service.IAssetMonitorRuleService;
import com.antiy.asset.vo.query.AssetMonitorRuleQuery;
import com.antiy.asset.vo.request.AssetMonitorRuleRequest;
import com.antiy.asset.vo.response.AssetMonitorRuleResponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.QueryCondition;

import io.swagger.annotations.*;

/**
 *
 * @author zhangyajun
 * @since 2020-03-02
 */
@Api(value = "AssetMonitorRule", description = "资产监控规则表")
@RestController
@RequestMapping("/api/v1/asset/monitorrule")
public class AssetMonitorRuleController {

    @Resource
    public IAssetMonitorRuleService iAssetMonitorRuleService;

    /**
     * 保存
     *
     * @param assetMonitorRuleRequest
     * @return actionResponse
     */
    @ApiOperation(value = "保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    public ActionResponse saveSingle(@ApiParam(value = "assetMonitorRule") @RequestBody AssetMonitorRuleRequest assetMonitorRuleRequest) throws Exception {
        return ActionResponse.success(iAssetMonitorRuleService.saveAssetMonitorRule(assetMonitorRuleRequest));
    }

    /**
     * 修改
     *
     * @param assetMonitorRuleRequest
     * @return actionResponse
     */
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
    public ActionResponse updateSingle(@ApiParam(value = "assetMonitorRule") AssetMonitorRuleRequest assetMonitorRuleRequest) throws Exception {
        return ActionResponse.success(iAssetMonitorRuleService.updateAssetMonitorRule(assetMonitorRuleRequest));
    }

    /**
     * 批量查询
     *
     * @param assetMonitorRuleQuery
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetMonitorRuleResponse.class, responseContainer = "List"), })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    public ActionResponse queryList(@ApiParam(value = "assetMonitorRule") AssetMonitorRuleQuery assetMonitorRuleQuery) throws Exception {
        return ActionResponse.success(iAssetMonitorRuleService.queryPageAssetMonitorRule(assetMonitorRuleQuery));
    }

    /**
     * 通过ID查询
     *
     * @param queryCondition
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetMonitorRuleResponse.class), })
    @RequestMapping(value = "/query/id", method = RequestMethod.GET)
    public ActionResponse queryById(@ApiParam(value = "主键封装对象") QueryCondition queryCondition) throws Exception {
        return ActionResponse.success(iAssetMonitorRuleService.queryAssetMonitorRuleById(queryCondition));
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
        return ActionResponse.success(iAssetMonitorRuleService.deleteAssetMonitorRuleById(baseRequest));
    }
}
