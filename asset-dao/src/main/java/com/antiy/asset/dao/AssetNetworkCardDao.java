package com.antiy.asset.dao;

import com.antiy.asset.entity.AssetNetworkCard;
import com.antiy.asset.vo.query.AssetNetworkCardQuery;
import com.antiy.common.base.IBaseDao;

import java.util.List;

/**
 * <p> 网卡信息表 Mapper 接口 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
public interface AssetNetworkCardDao extends IBaseDao<AssetNetworkCard> {

    List<AssetNetworkCard> findListAssetNetworkCard(AssetNetworkCardQuery query) throws Exception;

    Integer updateBatch(List<AssetNetworkCard> assetNetworkCardList) throws Exception;

    Integer insertBatch(List<AssetNetworkCard> assetNetworkCardList);

    Integer deleteByAssetId(Integer id);

    List<AssetNetworkCard> findNetworkCardByAssetId(Integer id);
}
