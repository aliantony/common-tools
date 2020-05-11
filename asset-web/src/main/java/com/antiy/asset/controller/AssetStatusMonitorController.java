package com.antiy.asset.controller;

import com.antiy.asset.service.IAssetStatusMonitorService;
import com.antiy.asset.vo.query.AssetStatusMonitorQuery;
import com.antiy.asset.vo.request.AssetStatusMonitorRequest;
import com.antiy.asset.vo.response.AssetStatusMonitorCountResponse;
import com.antiy.asset.vo.response.AssetStatusMonitorResponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.PageResult;
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
 * @since 2020-03-06
 */
@Api(value = "AssetStatusMonitor", description = "")
@RestController
@RequestMapping("/api/v1/asset/assetstatusmonitor")
public class AssetStatusMonitorController {

    @Resource
    public IAssetStatusMonitorService iAssetStatusMonitorService;

    /**
     * 保存
     *
     * @param assetStatusMonitorRequest
     * @return actionResponse
     */
    @ApiOperation(value = "保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Integer.class),
    })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    public ActionResponse saveSingle(@ApiParam(value = "assetStatusMonitor") @RequestBody AssetStatusMonitorRequest assetStatusMonitorRequest)throws Exception{
        return ActionResponse.success(iAssetStatusMonitorService.saveAssetStatusMonitor(assetStatusMonitorRequest));
    }

    /**
     * 根据根据id 查询性能
     *
     * @param assetStatusMonitorRequest
     * @return actionResponse
     */
    @ApiOperation(value = "资产基本性能监控", notes = "传入资产id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = AssetStatusMonitorResponse.class),
    })
    @RequestMapping(value = "/query/base/performance", method = RequestMethod.POST)
    public ActionResponse queryBasePerformance(@ApiParam(value = "queryCondition") @RequestBody QueryCondition queryCondition)throws Exception{
        AssetStatusMonitorResponse statusMonitorResponse=iAssetStatusMonitorService.queryBasePerformance(queryCondition.getPrimaryKey());
        return ActionResponse.success(statusMonitorResponse);
    }

    /**
     * 条数查询
     */
    @ApiOperation(value = "软件进程与服务条数", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = AssetStatusMonitorCountResponse.class),
    })
    @RequestMapping(value = "/query/count", method = RequestMethod.POST)
    public ActionResponse queryCount(@ApiParam(value = "queryCondition") @RequestBody AssetStatusMonitorQuery assetStatusMonitorQuery)throws Exception{
        AssetStatusMonitorCountResponse assetStatusMonitorCountResponse= iAssetStatusMonitorService.queryCount(assetStatusMonitorQuery);
        return ActionResponse.success(assetStatusMonitorCountResponse);
    }

    /**
     * 根据根据id 查询进程
     *
     * @param assetStatusMonitorRequest
     * @return actionResponse
     */
    @ApiOperation(value = "资产 进程/服务监控", notes = "传入资产id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = AssetStatusMonitorResponse.class),
    })
    @RequestMapping(value = "/query/monitor", method = RequestMethod.POST)
    public ActionResponse queryProcess(@ApiParam(value = "queryCondition") @RequestBody AssetStatusMonitorQuery assetStatusMonitorQuery)throws Exception{
        PageResult<AssetStatusMonitorResponse> pageResult=iAssetStatusMonitorService.queryMonitor(assetStatusMonitorQuery);
        return ActionResponse.success(pageResult);
    }
    /**
     * 修改
     *
     * @param assetStatusMonitorRequest
     * @return actionResponse
     */
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Integer.class),
    })
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
    public ActionResponse updateSingle(@ApiParam(value = "assetStatusMonitor")AssetStatusMonitorRequest assetStatusMonitorRequest)throws Exception{
        return ActionResponse.success(iAssetStatusMonitorService.updateAssetStatusMonitor(assetStatusMonitorRequest));
    }

    /**
     * 批量查询
     *
     * @param assetStatusMonitorQuery
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = AssetStatusMonitorResponse.class, responseContainer = "List"),
    })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    public ActionResponse queryList(@ApiParam(value = "assetStatusMonitor") AssetStatusMonitorQuery assetStatusMonitorQuery)throws Exception{
        return ActionResponse.success(iAssetStatusMonitorService.queryPageAssetStatusMonitor(assetStatusMonitorQuery));
    }

    /**
     * 通过ID查询
     *
     * @param queryCondition
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = AssetStatusMonitorResponse.class),
    })
    @RequestMapping(value = "/query/id", method = RequestMethod.GET)
    public ActionResponse queryById(@ApiParam(value = "主键封装对象") QueryCondition queryCondition)throws Exception{
        return ActionResponse.success(iAssetStatusMonitorService.queryAssetStatusMonitorById(queryCondition));
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
        return ActionResponse.success(iAssetStatusMonitorService.deleteAssetStatusMonitorById(baseRequest));
    }
}

