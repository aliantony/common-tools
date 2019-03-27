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

    AssetReportResponse queryCategoryCountByTime(AssetReportCategoryCountQuery query);

    /**
     * 获取本周的新增资产报表信息
     * @param reportQueryRequest
     * @return
     * @throws Exception
     */
    AssetReportResponse getNewAssetWithCategory(ReportQueryRequest reportQueryRequest) throws Exception;




    /**
     * 获取资产组top5信息
     * @param reportQueryRequest
     * @return
     * @throws Exception
     */
    AssetReportResponse getAssetConutWithGroup(ReportQueryRequest reportQueryRequest) throws Exception;

}
