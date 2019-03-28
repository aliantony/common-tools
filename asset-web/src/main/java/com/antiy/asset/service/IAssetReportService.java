package com.antiy.asset.service;

import com.antiy.asset.vo.query.AssetReportCategoryCountQuery;
import com.antiy.asset.vo.request.ReportQueryRequest;
import com.antiy.asset.vo.response.AssetReportResponse;

/**
 * <p> 资产报表 服务类 </p>
 *
 * @author zhangyajun
 * @since 2019-03-26
 */
public interface IAssetReportService {

    AssetReportResponse queryCategoryCountByTime(AssetReportCategoryCountQuery query) throws Exception;

    /**
     * 获取新增资产报表信息
     * @param assetReportCategoryCountQuery
     * @return
     * @throws Exception
     */
    AssetReportResponse getNewAssetWithCategory(AssetReportCategoryCountQuery assetReportCategoryCountQuery) throws Exception;

    /**
     * 导出新增资产报表表格
     * @param assetReportCategoryCountQuery
     * @return
     * @throws Exception
     */
    void exportCategoryCount(AssetReportCategoryCountQuery assetReportCategoryCountQuery) throws Exception;

    /**
     * 获取资产组top5信息
     * @param reportQueryRequest
     * @return
     * @throws Exception
     */
    AssetReportResponse getAssetConutWithGroup(ReportQueryRequest reportQueryRequest) throws Exception;

    /**
     * 查询资产组的新增资产信息
     * @param reportQueryRequest
     * @return
     * @throws Exception
     */
    AssetReportResponse getNewAssetWithGroup(ReportQueryRequest reportQueryRequest) throws Exception;

}
