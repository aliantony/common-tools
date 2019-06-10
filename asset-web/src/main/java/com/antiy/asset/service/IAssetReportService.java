package com.antiy.asset.service;

import javax.servlet.http.HttpServletRequest;

import com.antiy.asset.vo.query.AssetReportCategoryCountQuery;
import com.antiy.asset.vo.request.ReportQueryRequest;
import com.antiy.asset.vo.response.AssetReportResponse;
import com.antiy.asset.vo.response.AssetReportTableResponse;

/**
 * <p> 资产报表 服务类 </p>
 *
 * @author zhangyajun
 * @since 2019-03-26
 */
public interface IAssetReportService {

    /**
     * 根据时间条件分类统计查询
     * @param query
     * @return
     * @throws Exception
     */
    AssetReportResponse queryCategoryCountByTime(AssetReportCategoryCountQuery query) throws Exception;

    /**
     * 导出新增资产报表表格
     * @param assetReportCategoryCountQuery
     * @return
     * @throws Exception
     */
    void exportCategoryCount(AssetReportCategoryCountQuery assetReportCategoryCountQuery,
                             HttpServletRequest request) throws Exception;

    /**
     * 获取资产组top5信息
     * @param reportQueryRequest
     * @return
     * @throws Exception
     */
    AssetReportResponse getAssetConutWithGroup(ReportQueryRequest reportQueryRequest) throws Exception;

    /**
     * 根据时间条件分类统计查询数据，返回表格信息
     * @param query
     * @return
     * @throws Exception
     */
    AssetReportTableResponse queryCategoryCountByTimeToTable(AssetReportCategoryCountQuery query) throws Exception;

    /**
     * 资产组表格
     * @param reportQueryRequest
     * @return
     * @throws Exception
     */
    AssetReportTableResponse getAssetGroupReportTable(ReportQueryRequest reportQueryRequest) throws Exception;

    /**
     * 导出资产组表格
     * @param reportQueryRequest
     * @throws Exception
     */
    void exportAssetGroupTable(ReportQueryRequest reportQueryRequest) throws Exception;

}
