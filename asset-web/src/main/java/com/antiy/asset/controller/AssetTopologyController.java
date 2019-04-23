package com.antiy.asset.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.antiy.asset.service.IAssetTopologyService;

import io.swagger.annotations.Api;

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

}
