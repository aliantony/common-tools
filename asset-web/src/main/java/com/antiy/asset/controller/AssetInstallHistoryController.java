package com.antiy.asset.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;
import io.swagger.annotations.*;
import javax.annotation.Resource;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.base.ActionResponse;
import com.antiy.asset.service.IAssetInstallHistoryService;
import com.antiy.asset.vo.request.AssetInstallHistoryRequest;
import com.antiy.asset.vo.response.AssetInstallHistoryResponse;
import com.antiy.asset.vo.query.AssetInstallHistoryQuery;

/**
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
@Api(value = "AssetInstallHistory", description = "安装记录表")
@RestController
@RequestMapping("/api/v1/asset/assetinstallhistory")
public class AssetInstallHistoryController {

    @Resource
    public IAssetInstallHistoryService iAssetInstallHistoryService;

    /**
     * 保存
     *
     * @param assetInstallHistoryRequest
     * @return actionResponse
     */
    @ApiOperation(value = "保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    public ActionResponse saveSingle(@ApiParam(value = "assetInstallHistory") @RequestBody AssetInstallHistoryRequest assetInstallHistoryRequest) throws Exception {
        return ActionResponse.success(iAssetInstallHistoryService.saveAssetInstallHistory(assetInstallHistoryRequest));
    }

    /**
     * 修改
     *
     * @param assetInstallHistoryRequest
     * @return actionResponse
     */
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
    public ActionResponse updateSingle(@ApiParam(value = "assetInstallHistory") AssetInstallHistoryRequest assetInstallHistoryRequest) throws Exception {
        return ActionResponse
            .success(iAssetInstallHistoryService.updateAssetInstallHistory(assetInstallHistoryRequest));
    }

    /**
     * 批量查询
     *
     * @param assetInstallHistoryQuery
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetInstallHistoryResponse.class, responseContainer = "List"), })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    public ActionResponse queryList(@ApiParam(value = "assetInstallHistory") AssetInstallHistoryQuery assetInstallHistoryQuery) throws Exception {
        return ActionResponse
            .success(iAssetInstallHistoryService.queryPageAssetInstallHistory(assetInstallHistoryQuery));
    }

    /**
     * 通过ID查询
     *
     * @param queryCondition
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetInstallHistoryResponse.class), })
    @RequestMapping(value = "/query/id", method = RequestMethod.GET)
    public ActionResponse queryById(@ApiParam(value = "主键封装对象") QueryCondition queryCondition) throws Exception {
        return ActionResponse.success(iAssetInstallHistoryService.queryAssetInstallHistoryById(queryCondition));
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
        return ActionResponse.success(iAssetInstallHistoryService.deleteAssetInstallHistoryById(baseRequest));
    }
}
