package com.antiy.asset.dao;

import java.util.List;

import com.antiy.asset.entity.AssetAreaEntity;
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
    List<AssetAreaEntity> getAllAssetWithArea(ReportQueryRequest reportQueryRequest);
}