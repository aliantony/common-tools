package com.antiy.asset.dao;

import java.util.List;

import com.antiy.asset.entity.AssetCategoryEntity;
import com.antiy.asset.vo.query.AssetReportCategoryCountQuery;

/**
 * <p> 处理器表 Mapper 接口 </p>
 *
 * @author zhangyajun
 * @since 2019-03-26
 */
public interface AssetReportServiceDao {

    List<AssetCategoryEntity> findCategoryCountByTime(AssetReportCategoryCountQuery query);
}
