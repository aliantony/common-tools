package com.antiy.asset.controller;

import com.antiy.asset.service.IAssetAssemblyService;
import com.antiy.asset.vo.query.AssetAssemblyQuery;
import com.antiy.asset.vo.request.AssetAssemblyRequest;
import com.antiy.asset.vo.response.AssetAssemblyResponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.QueryCondition;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
@Api(value = "AssetAssembly", description = "资产组件关系表")
@RestController
@RequestMapping("/v1/asset/assetassembly")
public class AssetAssemblyController {

    @Resource
    public IAssetAssemblyService iAssetAssemblyService;

    /**
     * 保存
     *
     * @param assetAssemblyRequest
     * @return actionResponse
     */
    @ApiOperation(value = "保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    public ActionResponse saveSingle(@ApiParam(value = "assetAssembly") @RequestBody AssetAssemblyRequest assetAssemblyRequest) throws Exception {
        return ActionResponse.success(iAssetAssemblyService.saveAssetAssembly(assetAssemblyRequest));
    }

    /**
     * 修改
     *
     * @param assetAssemblyRequest
     * @return actionResponse
     */
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
    public ActionResponse updateSingle(@ApiParam(value = "assetAssembly") AssetAssemblyRequest assetAssemblyRequest) throws Exception {
        return ActionResponse.success(iAssetAssemblyService.updateAssetAssembly(assetAssemblyRequest));
    }

    /**
     * 批量查询
     *
     * @param assetAssemblyQuery
     * @return actionResponse
     */
    @ApiOperation(value = "可添加组件列表查询", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetAssemblyResponse.class, responseContainer = "List"), })
    @RequestMapping(value = "/query/enableList", method = RequestMethod.POST)
    public ActionResponse queryList(@ApiParam(value = "assetAssembly") @RequestBody AssetAssemblyQuery assetAssemblyQuery) throws Exception {
        return ActionResponse.success(iAssetAssemblyService.queryPageAssetAssembly(assetAssemblyQuery));
    }

    /**
     * 通过ID查询
     *
     * @param queryCondition
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetAssemblyResponse.class), })
    @RequestMapping(value = "/query/id", method = RequestMethod.GET)
    public ActionResponse queryById(@ApiParam(value = "主键封装对象") QueryCondition queryCondition) throws Exception {
        return ActionResponse.success(iAssetAssemblyService.queryAssetAssemblyById(queryCondition));
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
        return ActionResponse.success(iAssetAssemblyService.deleteAssetAssemblyById(baseRequest));
    }
}
