package com.antiy.asset.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.antiy.asset.service.IHomePageService;
import com.antiy.asset.vo.response.AssetCountIncludeResponse;
import com.antiy.asset.vo.response.AssetOnlineChartResponse;
import com.antiy.asset.vo.response.EnumCountResponse;
import com.antiy.common.base.ActionResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 首页
 * @author: zhangyajun
 * @date: 2020/2/28 10:57
 * @description:
 */
@Api(value = "AssetHomePageController", description = "首页接口")
@RestController
@RequestMapping("/api/v1/asset/homepage")
public class AssetHomePageController {

    @Resource
    private IHomePageService homePageService;

    /**
     * 纳入管理资产统计
     *
     * @return actionResponse
     */

    @ApiOperation(value = "纳入管理资产统计", notes = "无")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetCountIncludeResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/count/includeManage", method = RequestMethod.POST)
    public ActionResponse countIncludeManage() throws Exception {
        return ActionResponse.success(homePageService.countIncludeManage());
    }

    /**
     * 资产在线情况
     *
     * @return actionResponse
     */

    @ApiOperation(value = "资产在线情况", notes = "无")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetOnlineChartResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/chart/assetOnline", method = RequestMethod.POST)
    public ActionResponse assetOnlineChart() throws Exception {
        return ActionResponse.success(homePageService.assetOnlineChart());
    }

    /**
     * 资产重要程度分布
     *
     * @return actionResponse
     */

    @ApiOperation(value = "资产重要程度分布", notes = "无")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = EnumCountResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/pie/assetImportanceDegree", method = RequestMethod.POST)
    public ActionResponse assetImportanceDegreePie() throws Exception {
        return ActionResponse.success(homePageService.assetImportanceDegreePie());
    }

    /**
     * 持续监控天数
     *
     * @return actionResponse
     */

    @ApiOperation(value = "持续监控天数", notes = "无")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/count/runDay", method = RequestMethod.POST)
    public ActionResponse getRunDay() throws Exception {
        return ActionResponse.success(homePageService.getRunDay());
    }
}
