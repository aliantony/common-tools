package com.antiy.asset.service;

import com.antiy.asset.templet.ReportForm;
import com.antiy.asset.vo.request.ReportQueryRequest;
import com.antiy.asset.vo.response.AssetReportResponse;
import com.antiy.asset.vo.response.AssetReportTableResponse;

/**
 * @author: zhangbing
 * @date: 2019/3/26 10:54
 * @description:
 */
public interface IAssetAreaReportService {

    /**
     * 统计所有区域的资产信息
     * @return
     */
    AssetReportResponse getAssetWithArea(ReportQueryRequest reportRequest);

    /**
     * 统计所有区域表格数据
     * @param reportQueryRequest
     * @return
     */
    AssetReportTableResponse queryAreaTable(ReportQueryRequest reportQueryRequest);

    /**
     * 导出数据
     * @param reportQueryRequest
     * @return
     */
    ReportForm exportAreaTable(ReportQueryRequest reportQueryRequest);
}
