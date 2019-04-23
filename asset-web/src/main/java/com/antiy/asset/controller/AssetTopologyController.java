package com.antiy.asset.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.antiy.asset.service.IAssetTopologyService;
import com.antiy.common.base.ActionResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;

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
     * 统计总资产/已管控拓扑管理的资产数量
     * @return
     */
    @ApiOperation("查询总资产/已管控拓扑管理的资产数量")
    @RequestMapping(value = "/countAsset", method = RequestMethod.GET)
    @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse")
    public ActionResponse countAssetTopology() throws Exception {
        return ActionResponse.success(iAssetTopologyService.countAssetTopology());
    }
}
