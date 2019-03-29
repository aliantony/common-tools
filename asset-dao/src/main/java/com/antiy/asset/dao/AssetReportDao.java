package com.antiy.asset.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.antiy.asset.entity.AssetCategoryEntity;
import com.antiy.asset.entity.AssetGroupEntity;
import com.antiy.asset.vo.query.AssetReportCategoryCountQuery;
import com.antiy.asset.vo.request.AssetAreaReportRequest;
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
     * 统计至开始时间每个区域总的资产数（初始值）
     * @param list
     * @param startTime
     * @return
     */
    List<Map<String, Integer>> queryAssetWithAreaByDate(@Param("list") List<AssetAreaReportRequest> list,
                                                        @Param("startTime") Long startTime);

    /**
     * 统计时间区间内每个区域资产增量
     * @param list
     * @param startTime
     * @param endTime
     * @return
     */
    List<Map<String, String>> queryAddAssetWithArea(@Param("list") List<AssetAreaReportRequest> list,
                                                    @Param("startTime") Long startTime, @Param("endTime") Long endTime,
                                                    @Param("timeType") String timeType);

    /**
     * 获取开始时间以前的数据
     * @return
     */
    List<AssetCategoryEntity> findCategoryCountPrevious(AssetReportCategoryCountQuery assetReportCategoryCountQuery);

    /**
     * 获取资产组top5和总数 图形
     * @return
     */
    List<AssetGroupEntity> getAssetConutWithGroup(ReportQueryRequest reportQueryRequest);

    List<AssetCategoryEntity> findCategoryCountByTime(AssetReportCategoryCountQuery query);

    /**
     * 查询资产组新增资产信息-通用
     * @param reportQueryRequest
     * @return
     */
    List<AssetGroupEntity> getNewAssetWithGroup(ReportQueryRequest reportQueryRequest);

    List<AssetGroupEntity> myTest(ReportQueryRequest reportQueryRequest);

    /**
     * 获取初始化资产组信息
     * @param reportQueryRequest
     * @return
     */
    List<AssetGroupEntity> getInitGroupData(ReportQueryRequest reportQueryRequest);

    /**
     * 按时间分类统计资产总数
     * @param map
     * @return
     */
    Integer findCategoryCountAmount(Map<String, Object> map);
}
