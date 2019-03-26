package com.antiy.asset.service;

import com.antiy.asset.vo.request.ReportQueryRequest;
import com.antiy.asset.vo.response.AssetCategoryModelResponse;
import com.antiy.asset.vo.response.AssetReportResponse;
import com.antiy.common.base.ActionResponse;

/**
 * <p> 资产报表 服务类 </p>
 *
 * @author zhangyajun
 * @since 2019-03-26
 */
public interface IAssetReportService {

    AssetCategoryModelResponse queryCategoryCountByTime();

    /**
     * 获取本周的新增资产报表信息
     * @param reportQueryRequest
     * @return
     * @throws Exception
     */
    AssetReportResponse getNewAssetWithCategoryInWeek(ReportQueryRequest reportQueryRequest) throws Exception;

}
