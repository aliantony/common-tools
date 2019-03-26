package com.antiy.asset.controller;

import javax.annotation.Resource;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.antiy.asset.service.IAssetReportService;
import com.antiy.common.base.ActionResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author zhangyajun
 * @since 2019-03-26
 */
@Api(value = "AssetReport", description = "资产报表")
@RestController
@RequestMapping("/api/v1/asset/report")
public class AssetReportController {

    @Resource
    IAssetReportService iAssetReportService;

    /**
     * 根据时间条件查询分类统计资产数量
     *
     * @return actionResponse
     */
    @ApiOperation(value = "根据时间条件查询分类统计资产数量", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/categoryCountByTime", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('asset:report:categoryAmountByTime')")
    public ActionResponse queryCategoryCountByTime() throws Exception {
        return ActionResponse.success(iAssetReportService.queryCategoryCountByTime());
    }

}
