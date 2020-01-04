package com.antiy.asset.dao;

import com.antiy.asset.entity.Asset;
import com.antiy.asset.entity.AssetCountResult;
import com.antiy.asset.entity.AssetTopology;
import com.antiy.asset.vo.query.AssetQuery;
import com.antiy.asset.vo.request.AssetIdRequest;
import com.antiy.common.base.IBaseDao;

import java.util.List;

/**
 * <p> 资产拓扑表 Mapper 接口 </p>
 *
 * @author zhangyajun
 * @since 2019-01-07
 */
public interface AssetTopologyDao extends IBaseDao<AssetTopology> {
    Integer findTopologyListAssetCount(AssetQuery query);

    List<Asset> findTopologyListAsset(AssetQuery query);

    List<Integer> findAlarmCountByAssetId(AssetIdRequest query);

}
