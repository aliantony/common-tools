package com.antiy.asset.controller;

import com.antiy.asset.service.IAssetMonitorRuleService;
import com.antiy.asset.vo.query.AssetMonitorRuleQuery;
import com.antiy.asset.vo.query.BaseQuery;
import com.antiy.asset.vo.query.UniqueKeyQuery;
import com.antiy.asset.vo.request.AssetMonitorRuleRequest;
import com.antiy.asset.vo.request.UniqueKeyRquest;
import com.antiy.asset.vo.response.AssetMonitorRuleResponse;
import com.antiy.asset.vo.response.AssetResponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.PageResult;
import com.antiy.common.utils.ParamterExceptionUtils;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
     * 规则禁用/启用
     */
    @ApiOperation(value = "规则禁用/启用", notes = "传入监控规则唯一键")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/edit/rule/status", method = RequestMethod.POST)
    public ActionResponse editRuleStatus(@ApiParam(value = "assetMonitorRule") @RequestBody  UniqueKeyRquest uniqueKeyRquest) throws Exception {
        ParamterExceptionUtils.isNull(uniqueKeyRquest.getUniqueId(),"唯一键不能为空！");
        ParamterExceptionUtils.isNull(uniqueKeyRquest.getUseFlag(),"启用/禁用标志不能为空！");
       Integer result= iAssetMonitorRuleService.editRuleStatus(uniqueKeyRquest.getUniqueId(),uniqueKeyRquest.getUseFlag());
        return ActionResponse.success(result);
    }
    /**
     * 规则删除
     */
    @ApiOperation(value = "规则删除", notes = "传入唯一键")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/delete/uniqueId", method = RequestMethod.POST)
    public ActionResponse deleteByUniqueId(@ApiParam(value = "uniqueKeyRquest") @RequestBody UniqueKeyRquest uniqueKeyRquest) throws Exception {
        ParamterExceptionUtils.isNull(uniqueKeyRquest.getUniqueIds(),"唯一键不能为空！");
        Integer result=iAssetMonitorRuleService.deleteByUniqueId(uniqueKeyRquest.getUniqueIds());
        return ActionResponse.success(result);
    }
    /**
     * 规则详情
     */
    /**
     * 规则删除
     */
    @ApiOperation(value = "规则详情", notes = "传入唯一键")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetMonitorRuleResponse.class), })
    @RequestMapping(value = "query/rule/info", method = RequestMethod.POST)
    public ActionResponse queryRuleInfo(@ApiParam(value = "uniqueKeyRquest") @RequestBody UniqueKeyRquest uniqueKeyRquest) throws Exception {
        ParamterExceptionUtils.isNull(uniqueKeyRquest.getUniqueId(),"唯一键不能为空！");
        AssetMonitorRuleResponse assetMonitorRuleResponse=iAssetMonitorRuleService.queryByUniqueId(uniqueKeyRquest.getUniqueId());
        return ActionResponse.success(assetMonitorRuleResponse);
    }
    /**
     * 监控规则关联资产
     */
    @ApiOperation(value = "规则关联资产", notes = "传入唯一键")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetMonitorRuleResponse.class ,responseContainer = "List"), })
    @RequestMapping(value = "query/relation/asset", method = RequestMethod.POST)
    public ActionResponse queryRelationAsset(@ApiParam(value = "uniqueKeyQuery") @ RequestBody UniqueKeyQuery uniqueKeyQuery) throws Exception {
        ParamterExceptionUtils.isNull(uniqueKeyQuery.getUniqueId(),"唯一键不能为空！");
        PageResult<AssetResponse> pageResult=iAssetMonitorRuleService.queryAssetByUniqueId(uniqueKeyQuery);
        return ActionResponse.success(pageResult);
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
    public ActionResponse updateSingle(@RequestBody @ApiParam(value = "assetMonitorRule") AssetMonitorRuleRequest assetMonitorRuleRequest) throws Exception {
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
    @RequestMapping(value = "/query/list", method = RequestMethod.POST)
    public ActionResponse queryList(@RequestBody @ApiParam(value = "assetMonitorRule") AssetMonitorRuleQuery assetMonitorRuleQuery) throws Exception {
        return ActionResponse.success(iAssetMonitorRuleService.queryPageAssetMonitorRule(assetMonitorRuleQuery));
    }

    /**
     * 通过ID查询
     *
     * @param query
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象",hidden = true)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetMonitorRuleResponse.class), })
    @RequestMapping(value = "/query/id", method = RequestMethod.POST)
    public ActionResponse queryById(@RequestBody @ApiParam(value = "主键封装对象") BaseQuery query) throws Exception {
        return ActionResponse.success(iAssetMonitorRuleService.queryAssetMonitorRuleById(query));
    }

    /**
     * 规则名称去重
     *
     * @param query
     * @return actionResponse
     */
    @ApiOperation(value = "规则名称去重", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/nameNoRepeat", method = RequestMethod.POST)
    public ActionResponse nameNoRepeat(@RequestBody @ApiParam(value = "主键封装对象") AssetMonitorRuleQuery query) throws Exception {
        return ActionResponse.success(iAssetMonitorRuleService.nameNoRepeat(query));
    }
}
