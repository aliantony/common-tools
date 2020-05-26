package com.antiy.asset.dao;

import com.antiy.asset.vo.query.AssetEntryQuery;
import com.antiy.asset.vo.request.AssetEntryRecordRequest;
import com.antiy.asset.vo.request.AssetEntryRequest;
import com.antiy.asset.vo.response.AssetEntryRecordResponse;
import com.antiy.asset.vo.response.AssetEntryResponse;
import com.antiy.asset.vo.response.AssetEntryStatusResponse;
import com.antiy.asset.vo.response.AssetEntrySystemResponse;
import com.antiy.common.base.IBaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AssetEntryDao extends IBaseDao<AssetEntryResponse> {
       Integer updateEntryStatus(AssetEntryRequest request);

        Integer insertRecord(AssetEntryRecordRequest recordRequest);
        Integer insertRecordBatch(@Param("recordList") List<AssetEntryRecordRequest> recordList);

    List<AssetEntryRecordResponse> queryEntryRecord(AssetEntryQuery query);

    List<AssetEntryStatusResponse> queryEntryStatus(@Param("assetIds") List<String> assetIds);

    /**
     * 全量查询资产的准入情况(对接准入系统）
     * @param categoryIds
     * @return
     */
    List<AssetEntrySystemResponse> queryAllEntryStatus(List<String> categoryIds);


//       Integer saveEntryRecord();
}
