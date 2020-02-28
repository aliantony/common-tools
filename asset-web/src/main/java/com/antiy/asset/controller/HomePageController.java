package com.antiy.asset.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.antiy.asset.service.IHomePageService;
import com.antiy.asset.vo.response.AlarmAssetResponse;
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
@Api(value = "HomePageController", description = "首页接口")
@RestController
@RequestMapping("/api/v1/asset/homepage")
public class HomePageController {

    @Resource
    private IHomePageService homePageService;

    /**
     * 纳入管理资产统计
     *
     * @return actionResponse
     */

    @ApiOperation(value = "纳入管理资产统计", notes = "无")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AlarmAssetResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/count/includeManage", method = RequestMethod.POST)
    public ActionResponse countIncludeManage() throws Exception {
        return ActionResponse.success(homePageService.countIncludeManage());
    }
}
