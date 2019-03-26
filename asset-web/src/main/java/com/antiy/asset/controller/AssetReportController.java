package com.antiy.asset.controller;

import javax.annotation.Resource;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.antiy.asset.service.IAssetAreaReportService;
import com.antiy.asset.service.IAssetReportService;
import com.antiy.asset.vo.request.ReportQueryRequest;
import com.antiy.asset.vo.response.AssetReportResponse;
import com.antiy.common.base.ActionResponse;

import io.swagger.annotations.*;

/**
 * @author zhangyajun
 * @since 2019-03-26
 */
@Api(value = "AssetReport", description = "资产报表")
@RestController
@RequestMapping("/api/v1/asset/report")
public class AssetReportController {

    @Resource
    private IAssetReportService     iAssetReportService;

    @Resource
    private IAssetAreaReportService iAssetAreaReportService;

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

    @ApiOperation(value = "根据时间条件查询分类统计资产数量", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetReportResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/queryAreaCount", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('asset:report:queryAreaCount')")
    public ActionResponse queryAreaCount(@ApiParam(value = "查询条件") @RequestBody ReportQueryRequest reportQueryRequest) {
        iAssetAreaReportService.getAssetWithArea(reportQueryRequest);
        return null;
    }


    /**
     * 根据时间条件查询分类统计资产新增数量
     *
     * @return actionResponse
     */
    @ApiOperation(value = "根据时间条件查询分类统计资产新增数量", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/newAsset/week", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('asset:report:categoryAmountByTime')")
    public ActionResponse getNewAssetWithCategoryInWeek(ReportQueryRequest reportQueryRequest) throws Exception {
        return ActionResponse.success(iAssetReportService.getNewAssetWithCategoryInWeek(reportQueryRequest));
    }
}
