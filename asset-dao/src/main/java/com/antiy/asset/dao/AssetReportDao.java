package com.antiy.asset.dao;

import java.util.List;
import java.util.Map;

import com.antiy.asset.entity.AssetAreaEntity;
import com.antiy.asset.entity.AssetCategoryEntity;
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
     * 获取所有的品类型号新增资产信息 日期显示到天
     * @return
     */
    List<AssetCategoryEntity> getNewAssetWithCategoryByDay(ReportQueryRequest reportQueryRequest);

    /**
     * 获取所有的品类型号新增资产信息 日期显示到月
     * @return
     */
    List<AssetCategoryEntity> getNewAssetWithCategoryByMonth(ReportQueryRequest reportQueryRequest);
}
