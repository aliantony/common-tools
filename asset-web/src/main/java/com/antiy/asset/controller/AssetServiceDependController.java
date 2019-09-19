package com.antiy.asset.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;
import io.swagger.annotations.*;
import javax.annotation.Resource;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.base.ActionResponse;
import com.antiy.asset.service.IAssetServiceDependService;
import com.antiy.asset.vo.request.AssetServiceDependRequest;
import com.antiy.asset.vo.response.AssetServiceDependResponse;
import com.antiy.asset.vo.query.AssetServiceDependQuery;

/**
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
@Api(value = "AssetServiceDepend", description = "服务依赖的服务表")
@RestController
@RequestMapping("/api/v1/asset/assetservicedepend")
public class AssetServiceDependController {

    @Resource
    public IAssetServiceDependService iAssetServiceDependService;

    /**
     * 保存
     *
     * @param assetServiceDependRequest
     * @return actionResponse
     */
    @ApiOperation(value = "保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    public ActionResponse saveSingle(@ApiParam(value = "assetServiceDepend") @RequestBody AssetServiceDependRequest assetServiceDependRequest) throws Exception {
        return ActionResponse.success(iAssetServiceDependService.saveAssetServiceDepend(assetServiceDependRequest));
    }

    /**
     * 修改
     *
     * @param assetServiceDependRequest
     * @return actionResponse
     */
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
    public ActionResponse updateSingle(@ApiParam(value = "assetServiceDepend") AssetServiceDependRequest assetServiceDependRequest) throws Exception {
        return ActionResponse.success(iAssetServiceDependService.updateAssetServiceDepend(assetServiceDependRequest));
    }

    /**
     * 批量查询
     *
     * @param assetServiceDependQuery
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetServiceDependResponse.class, responseContainer = "List"), })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    public ActionResponse queryList(@ApiParam(value = "assetServiceDepend") AssetServiceDependQuery assetServiceDependQuery) throws Exception {
        return ActionResponse.success(iAssetServiceDependService.queryPageAssetServiceDepend(assetServiceDependQuery));
    }

    /**
     * 通过ID查询
     *
     * @param queryCondition
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetServiceDependResponse.class), })
    @RequestMapping(value = "/query/id", method = RequestMethod.GET)
    public ActionResponse queryById(@ApiParam(value = "主键封装对象") QueryCondition queryCondition) throws Exception {
        return ActionResponse.success(iAssetServiceDependService.queryAssetServiceDependById(queryCondition));
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
        return ActionResponse.success(iAssetServiceDependService.deleteAssetServiceDependById(baseRequest));
    }
}
