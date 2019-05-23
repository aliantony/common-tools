package com.antiy.asset.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.antiy.asset.service.IAssetTopologyService;
import com.antiy.asset.vo.query.AssetQuery;
import com.antiy.asset.vo.query.AssetTopologyQuery;
import com.antiy.asset.vo.response.AssetNodeInfoResponse;
import com.antiy.asset.vo.response.SelectResponse;
import com.antiy.common.base.ActionResponse;

import io.swagger.annotations.*;

/**
 * 资产拓扑管理
 * @author zhangxin
 * @date 2019/4/23 11:17
 */
@Api(value = "AssetTopologyController", description = "资产拓扑结构管理")
@RestController
@RequestMapping("/api/v1/asset/assetTopology")
public class AssetTopologyController {

    @Resource
    private IAssetTopologyService iAssetTopologyService;

    /**
     * 查询品类型号
     *
     * @param
     * @return actionResponse
     */
    @ApiOperation(value = "查询品类型号", notes = "查询拓扑管理的品类型号")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = List.class), })
    @RequestMapping(value = "/query/categoryModels", method = RequestMethod.POST)
    // @PreAuthorize(value = "hasAuthority('asset:topology:queryCategoryModels')")
    public ActionResponse queryCategoryModels() throws Exception {
        return ActionResponse.success(iAssetTopologyService.queryCategoryModels());
    }

    @ApiOperation(value = "查询节点信息", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetNodeInfoResponse.class), })
    @RequestMapping(value = "/query/assetNodeInfo", method = RequestMethod.POST)
    // @PreAuthorize(value = "hasAuthority('asset:topology:queryAssetNodeInfo')")
    public ActionResponse queryAssetNodeInfo(@ApiParam("条件") @RequestBody AssetTopologyQuery assetTopologyQuery) throws Exception {
        return ActionResponse.success(iAssetTopologyService.queryAssetNodeInfo(assetTopologyQuery.getAssetId()));
    }

    /**
     * 统计总资产/已管控拓扑管理的资产数量
     * @return
     */
    @ApiOperation("查询总资产/已管控拓扑管理的资产数量")
    @RequestMapping(value = "/countAsset", method = RequestMethod.POST)
    @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse")
    // @PreAuthorize(value = "hasAuthority('asset:topology:countAsset')")
    public ActionResponse countAssetTopology() throws Exception {
        return ActionResponse.success(iAssetTopologyService.countAssetTopology());
    }

    /**
     * 已管控拓扑管理的资产的资产组信息(下拉)
     * @return
     */
    @ApiOperation("查询已管控拓扑管理的资产组下拉")
    @RequestMapping(value = "/queryGroupList", method = RequestMethod.POST)
    @ApiResponse(code = 200, message = "OK", response = SelectResponse.class, responseContainer = "actionResponse")
    // @PreAuthorize(value = "hasAuthority('asset:topology:queryGroupList')")
    public ActionResponse queryGroupList() throws Exception {
        return ActionResponse.success(iAssetTopologyService.queryGroupList());
    }

    /**
     * 查询拓扑列表
     * @return
     */
    @ApiOperation("查询拓扑列表")
    @RequestMapping(value = "/get/topologyList", method = RequestMethod.POST)
    @ApiResponse(code = 200, message = "OK", response = SelectResponse.class, responseContainer = "actionResponse")
    // @PreAuthorize(value = "hasAuthority('asset:topology:getTopologyList')")
    public ActionResponse getTopologyList(@ApiParam("查询条件") @RequestBody AssetQuery query) throws Exception {
        return ActionResponse.success(iAssetTopologyService.getTopologyList(query));
    }

}
