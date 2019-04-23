package com.antiy.asset.controller;

import javax.annotation.Resource;

import com.antiy.common.base.ActionResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.antiy.asset.service.IAssetTopologyService;

import io.swagger.annotations.Api;

import java.util.List;

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
    @RequestMapping(value = "/query/categoryModels", method = RequestMethod.GET)
    public ActionResponse queryCategoryModels() throws Exception {
        return ActionResponse.success(iAssetTopologyService.queryCategoryModels());
    }
}
