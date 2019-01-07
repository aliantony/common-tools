package com.antiy.asset.controller;

import com.antiy.asset.service.IAssetTopologyService;
import com.antiy.asset.vo.query.AssetTopologyQuery;
import com.antiy.asset.vo.request.AssetTopologyRequest;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.ParamterExceptionUtils;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * @author zhangyajun
 * @since 2019-01-07
 */
@Api(value = "AssetTopology", description = "资产拓扑表")
@RestController
@RequestMapping("/v1/asset/assettopology")
public class AssetTopologyController {
    private static final Logger logger = LogUtils.get();

    @Resource
    public IAssetTopologyService iAssetTopologyService;

    /**
     * 保存
     *
     * @param assetTopologyRequest
     * @return actionResponse
     */
    @ApiOperation(value = "保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    public ActionResponse saveSingle(@ApiParam(value = "assetTopology") @RequestBody AssetTopologyRequest assetTopologyRequest) throws Exception {
        iAssetTopologyService.saveAssetTopology(assetTopologyRequest);
        return ActionResponse.success();
    }

    /**
     * 修改
     *
     * @param assetTopologyRequest
     * @return actionResponse
     */
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
    public ActionResponse updateSingle(@ApiParam(value = "assetTopology") AssetTopologyRequest assetTopologyRequest) throws Exception {
        iAssetTopologyService.updateAssetTopology(assetTopologyRequest);
        return ActionResponse.success();
    }

    /**
     * 批量查询
     *
     * @param assetTopologyQuery
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    public ActionResponse queryList(@ApiParam(value = "assetTopology") AssetTopologyQuery assetTopologyQuery) throws Exception {
        return ActionResponse.success(iAssetTopologyService.findPageAssetTopology(assetTopologyQuery));
    }

    /**
     * 通过ID查询
     *
     * @param id
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/query/{id}", method = RequestMethod.GET)
    public ActionResponse queryById(@ApiParam(value = "assetTopology") @PathVariable("id") Integer id) throws Exception {
        ParamterExceptionUtils.isNull(id, "ID不能为空");
        return ActionResponse.success(iAssetTopologyService.getById(id));
    }

    /**
     * 通过ID删除
     *
     * @param id
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID删除接口", notes = "主键封装对象")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ActionResponse deleteById(@ApiParam(value = "id") @PathVariable("id") Integer id) throws Exception {
        ParamterExceptionUtils.isNull(id, "ID不能为空");
        return ActionResponse.success(iAssetTopologyService.deleteById(id));
    }
}

