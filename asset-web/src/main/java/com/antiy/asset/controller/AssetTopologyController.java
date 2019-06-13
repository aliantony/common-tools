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
import com.antiy.common.base.BaseRequest;

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

    @ApiOperation(value = "查询节点信息", notes = "传stringId参数")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = BaseRequest.class), })
    @RequestMapping(value = "/query/assetNodeInfo", method = RequestMethod.POST)
    // @PreAuthorize(value = "hasAuthority('asset:topology:queryAssetNodeInfo')")
    public ActionResponse queryAssetNodeInfo(@ApiParam("条件") @RequestBody BaseRequest baseRequest) throws Exception {
//        return ActionResponse.success();
         return ActionResponse.success(iAssetTopologyService.queryAssetNodeInfo(baseRequest.getStringId()));
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
    @ApiOperation(value = "查询拓扑列表",notes = "     * 查询拓扑列表 查询参数传 \" +\n" +
            "     *             \"查品类参数 categoryModels Array[string]\" +\n" +
            "     *             \"查资产组参数 assetGroup \" +\n" +
            "     *             \"查区域参数 areaIds  Array[string]\" +\n" +
            "     *             \"查用户参数 responsibleUserName \n")
    @RequestMapping(value = "/get/topologyList", method = RequestMethod.POST)
    @ApiResponse(code = 200, message = "OK", response = SelectResponse.class, responseContainer = "actionResponse")
    // @PreAuthorize(value = "hasAuthority('asset:topology:getTopologyList')")
    public ActionResponse getTopologyList(@ApiParam("查询条件") @RequestBody AssetQuery query) throws Exception {
        return ActionResponse.success(iAssetTopologyService.getTopologyList(query));
    }

    /**
     * 查询拓扑列表
     * @return
     */
    @ApiOperation("品类统计")
    @RequestMapping(value = "/count/category", method = RequestMethod.POST)
    @ApiResponse(code = 200, message = "OK", response = SelectResponse.class, responseContainer = "actionResponse")
    // @PreAuthorize(value = "hasAuthority('asset:topology:countTopologyCategory')")
    public ActionResponse countTopologyCategory() throws Exception {
        return ActionResponse.success(iAssetTopologyService.countTopologyCategory());
    }

    /**
     * 查询拓扑列表
     * @return
     */
    @ApiOperation("操作系统统计")
    @RequestMapping(value = "/count/os", method = RequestMethod.POST)
    @ApiResponse(code = 200, message = "OK", response = SelectResponse.class, responseContainer = "actionResponse")
    // @PreAuthorize(value = "hasAuthority('asset:topology:countTopologyOs')")
    public ActionResponse countTopologyOs() throws Exception {
        return ActionResponse.success(iAssetTopologyService.countTopologyOs());
    }

    /**
     * 告警资产拓扑
     * @return
     */
    @ApiOperation("告警资产")
    @RequestMapping(value = "/alarm", method = RequestMethod.POST)
    @ApiResponse(code = 200, message = "OK", response = Object.class, responseContainer = "actionResponse")
    // @PreAuthorize(value = "hasAuthority('asset:topology:alarm')")
    public ActionResponse alarmTopology() throws Exception {
        return ActionResponse.success();
    }

    /**
     * 拓扑管理
     * @return
     */
    @ApiOperation("告警资产")
    @RequestMapping(value = "/home", method = RequestMethod.POST)
    @ApiResponse(code = 200, message = "OK", response = Object.class, responseContainer = "actionResponse")
    // @PreAuthorize(value = "hasAuthority('asset:topology:home')")
    public ActionResponse homeTopology() throws Exception {
        return ActionResponse.success();
    }

    /**
     * 列表查询
     * @return
     */
    @ApiOperation("列表查询")
    @RequestMapping(value = "/query/list", method = RequestMethod.POST)
    @ApiResponse(code = 200, message = "OK", response = Object.class, responseContainer = "actionResponse")
    // @PreAuthorize(value = "hasAuthority('asset:topology:queryList')")
    public ActionResponse queryList(@RequestBody AssetTopologyQuery topologyQuery) throws Exception {
        return ActionResponse.success();
    }

}
