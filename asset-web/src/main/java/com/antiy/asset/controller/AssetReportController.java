package com.antiy.asset.controller;

import com.antiy.asset.service.IAssetAreaReportService;
import com.antiy.asset.service.IAssetReportService;
import com.antiy.asset.util.ExcelUtils;
import com.antiy.asset.vo.query.AssetReportCategoryCountQuery;
import com.antiy.asset.vo.request.ReportQueryRequest;
import com.antiy.asset.vo.response.AssetReportResponse;
import com.antiy.asset.vo.response.AssetReportTableResponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.utils.LoginUserUtil;
import com.antiy.common.utils.ParamterExceptionUtils;
import io.swagger.annotations.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
    public ActionResponse queryCategoryCountByTime(AssetReportCategoryCountQuery query) throws Exception {
        return ActionResponse.success(iAssetReportService.queryCategoryCountByTime(query));
    }

    /**
     * 根据时间条件查询分类统计资产数量,返回表格数据
     *
     * @return actionResponse
     */
    @ApiOperation(value = "根据时间条件查询分类统计资产数量（表格数据）", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetReportTableResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/categoryCountByTimeToTable", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('asset:report:categoryCountByTimeToTable')")
    public ActionResponse queryCategoryCountByTimeToTable(AssetReportCategoryCountQuery query) throws Exception {
        return ActionResponse.success(iAssetReportService.queryCategoryCountByTimeToTable(query));
    }

    /**
     * 根据时间条件、区域资产数量
     * @param reportQueryRequest
     * @return
     */
    @ApiOperation(value = "根据时间条件、区域资产数量", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetReportResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/queryAreaCount", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('asset:report:queryAreaCount')")
    public ActionResponse queryAreaCount(@ApiParam(value = "查询条件") @RequestBody ReportQueryRequest reportQueryRequest) {
        ParamterExceptionUtils.isEmpty(reportQueryRequest.getAssetAreaIds(), "请指定要统计的区域");
        reportQueryRequest.setTopFive(true);
        return ActionResponse.success(iAssetAreaReportService.getAssetWithArea(reportQueryRequest));
    }

    /**
     * 根据时间条件、区域资产表格数据
     * @param reportQueryRequest
     * @return
     */
    @ApiOperation(value = "根据时间条件、区域查询资产表格数据", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetReportResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/queryAreaTable", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('asset:report:queryAreaTable')")
    public ActionResponse queryAreaTable(@ApiParam(value = "查询条件") @RequestBody ReportQueryRequest reportQueryRequest) {
        ParamterExceptionUtils.isEmpty(reportQueryRequest.getAssetAreaIds(), "请指定要统计的区域");
        reportQueryRequest.setTopFive(false);
        return ActionResponse.success(iAssetAreaReportService.queryAreaTable(reportQueryRequest));
    }

    /**
     * 根据时间条件、区域导出资产表格数据
     * @param reportQueryRequest
     * @return
     */
    @ApiOperation(value = "根据时间条件、区域资产表格数据", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetReportResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/exportAreaTable", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('asset:report:exportAreaTable')")
    public void exportAreaTable(@ApiParam(value = "查询条件")@RequestBody ReportQueryRequest reportQueryRequest) {
        ParamterExceptionUtils.isEmpty(reportQueryRequest.getAssetAreaIds(), "请指定要统计的区域");
        reportQueryRequest.setTopFive(false);
        ExcelUtils.exportFormToClient(iAssetAreaReportService.exportAreaTable(reportQueryRequest), "资产区域报表数据.xlsx");
    }

    /**
     * 根据时间条件查询资产组统计资产总数及新增数量
     *
     * @return actionResponse
     */
    @ApiOperation(value = "根据条件查询资产组top5", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/groupCountTop", method = RequestMethod.GET)
    // @PreAuthorize("hasAuthority('asset:report:categoryAmountByTime')")
    public ActionResponse getAssetConutWithGroup(ReportQueryRequest reportQueryRequest) throws Exception {
        reportQueryRequest.setAreaIds(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser());
        return ActionResponse.success(iAssetReportService.getAssetConutWithGroup(reportQueryRequest));
    }

    /**
     * 导出资产品类型号报表
     *
     * @param assetReportCategoryCountQuery
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "导出资产品类型号报表", notes = "导出资产品类型号报表")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/export/category/newAsset", method = RequestMethod.GET)
    public void getNewAssetWithGroup(@ApiParam("报表查询对象") AssetReportCategoryCountQuery assetReportCategoryCountQuery) throws Exception {
        assetReportCategoryCountQuery.setAreaIds(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser());
        iAssetReportService.exportCategoryCount(assetReportCategoryCountQuery);
    }

    /**
     * 根据时间查询资产组表格
     *
     * @param reportQueryRequest
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "资产组表格", notes = "根据时间查询资产组表格")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/queryAssetGroupTable", method = RequestMethod.GET)
    public ActionResponse queryAssetGroupTable(ReportQueryRequest reportQueryRequest) throws Exception {
        reportQueryRequest.setAreaIds(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser());
        return ActionResponse.success(iAssetReportService.getAssetGroupReportTable(reportQueryRequest));
    }

    /**
     * 导出资产组表格
     * @param reportQueryRequest
     * @return
     */
    @ApiOperation(value = "导出资产组表格", notes = "导出资产组表格")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetReportResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/exportAssetGroupTable", method = RequestMethod.GET)
    public void exportAssetGroupTable(@ApiParam(value = "查询条件") ReportQueryRequest reportQueryRequest) throws Exception {
        reportQueryRequest.setAreaIds(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser());
        ExcelUtils.exportFormToClient(iAssetReportService.exportAssetGroupTable(reportQueryRequest), "导出资产组报表.xlsx");
    }
}
