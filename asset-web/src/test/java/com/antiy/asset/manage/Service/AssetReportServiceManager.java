package com.antiy.asset.manage.Service;


import com.antiy.asset.entity.AssetCategoryEntity;
import com.antiy.asset.entity.AssetCategoryModel;
import com.antiy.asset.entity.AssetGroupEntity;
import com.antiy.asset.vo.enums.ShowCycleType;
import com.antiy.asset.vo.query.AssetReportCategoryCountQuery;
import com.antiy.asset.vo.request.ReportQueryRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cuirentao
 * @date 2019-04-12
 * 提供AssetReport Service用到的相关vo初始化
 * 注：业务上关心的字段传参，不关心的字段给默认值。
 */
@Component
public class AssetReportServiceManager {

    /**
     * @param showCycleType 周期类型
     * @param beginTime 开始时间
     * @param endTime 结束时间
     * @return 查询vo
     */
    public AssetReportCategoryCountQuery initCategoryCountQuery(ShowCycleType showCycleType, Long beginTime, Long endTime){
        AssetReportCategoryCountQuery assetReportCategoryCountQuery = new AssetReportCategoryCountQuery();
        assetReportCategoryCountQuery.setShowCycleType(showCycleType);
        assetReportCategoryCountQuery.setBeginTime(beginTime);
        assetReportCategoryCountQuery.setEndTime(endTime);
        return assetReportCategoryCountQuery;
    }

    /**
     * @param name 名称
     * @return ModelList
     */
    public List<AssetCategoryModel> initCategoryModeList(String name){
        AssetCategoryModel assetCategoryModel = new AssetCategoryModel();
        assetCategoryModel.setId(1);
        assetCategoryModel.setName(name);
        assetCategoryModel.setType(1);
        assetCategoryModel.setAssetType(1);
        assetCategoryModel.setParentId("2");
        assetCategoryModel.setDescription("ss");
        assetCategoryModel.setIsDefault(0);
        assetCategoryModel.setGmtCreate(1551422114000L);
        assetCategoryModel.setGmtModified(1551422114000L);
        assetCategoryModel.setMemo("ss");
        assetCategoryModel.setCreateUser(1);
        assetCategoryModel.setModifyUser(1);
        assetCategoryModel.setStatus(1);
        List<AssetCategoryModel> assetCategoryModelList = new ArrayList<>();
        assetCategoryModelList.add(assetCategoryModel);
        return assetCategoryModelList;
    }

    /**
     *
     * @param date 时间
     * @return CategoryEntityList
     */
    public  List<AssetCategoryEntity> initCategoryEntityList(String date){
        AssetCategoryEntity assetCategoryEntity = new AssetCategoryEntity();
        assetCategoryEntity.setCategoryCount(1);
        assetCategoryEntity.setCategoryModel(1);
        assetCategoryEntity.setParentId("1");
        assetCategoryEntity.setCategoryName("ss");
        assetCategoryEntity.setDate(date);
        List<AssetCategoryEntity> assetCategoryEntityList = new ArrayList<>();
        assetCategoryEntityList.add(assetCategoryEntity);
        return assetCategoryEntityList;
    }

    /**
     *
     * @param timeType 时间类型
     * @return ReportQueryRequest
     */
    public ReportQueryRequest initReportQueryRequest(String timeType){
        ReportQueryRequest reportQueryRequest = new ReportQueryRequest();
        reportQueryRequest.setAssetStatus(1);
        reportQueryRequest.setStartTime(1551422114000L);
        reportQueryRequest.setEndTime(1551423114000L);
        reportQueryRequest.setTimeType(timeType);
        return reportQueryRequest;
    }

    /**
     *
     * @return GroupEntityList
     */
    public List<AssetGroupEntity> initGroupEntityList(){
        AssetGroupEntity assetGroupEntity = new AssetGroupEntity();
        assetGroupEntity.setDate("2018,5,1");
        assetGroupEntity.setGroupCount(1);
        assetGroupEntity.setGroupId(1);
        assetGroupEntity.setName("ss");
        List<AssetGroupEntity> assetGroupEntityList = new ArrayList<>();
        assetGroupEntityList.add(assetGroupEntity);
        return assetGroupEntityList;
    }
}
