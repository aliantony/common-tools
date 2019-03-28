package com.antiy.asset.dao;

import java.util.List;
import java.util.Map;

import com.antiy.asset.entity.AssetCategoryEntity;
import com.antiy.asset.entity.AssetGroupEntity;
import com.antiy.asset.vo.query.AssetReportCategoryCountQuery;
import com.antiy.asset.vo.request.ReportQueryRequest;

/**
 * @author: zhangbing
 * @date: 2019/3/26 11:01
 * @description:
 */
public interface AssetReportDao {

    /**
     * 获取所有的区域总数信息，并且按照资产个数排序
     * @return
     */
    List<Map<String, Integer>> getAllAssetWithArea(ReportQueryRequest reportQueryRequest);

    /**
     * 获取所有的品类型号新增资产信息
     * @return
     */
    List<AssetCategoryEntity> getNewAssetWithCategory(ReportQueryRequest reportQueryRequest);

    /**
     * 获取资产组top5和总数 图形
     * @return
     */
    List<AssetGroupEntity> getAssetConutWithGroup(ReportQueryRequest reportQueryRequest);

    List<AssetCategoryEntity> findCategoryCountByTime(AssetReportCategoryCountQuery query);

    /**
     * 获取资产组新增资产信息-日期显示到月
     * @param reportQueryRequest
     * @return
     */
    List<AssetGroupEntity> getNewAssetWithGroupByMonth(ReportQueryRequest reportQueryRequest);

}
