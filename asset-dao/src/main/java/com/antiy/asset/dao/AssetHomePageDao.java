package com.antiy.asset.dao;

import com.antiy.asset.entity.AssetImportanceDegreeCondition;
import com.antiy.asset.entity.AssetIncludeManageCondition;
import com.antiy.asset.entity.AssetOnlineChartCondition;
import com.antiy.asset.vo.response.AssetImportancePie;

/**
 * <p> 首页 Mapper 接口 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
public interface AssetHomePageDao {
    Integer countIncludeManage(AssetIncludeManageCondition condition) throws Exception;

    Integer countUnincludeManage(AssetIncludeManageCondition condition) throws Exception;

    Integer assetOnlineChart(AssetOnlineChartCondition condition) throws Exception;

    AssetImportancePie assetImportanceDegreePie(AssetImportanceDegreeCondition condition) throws Exception;
}
