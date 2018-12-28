package com.antiy.asset.dao;

import java.util.List;
import com.antiy.common.base.IBaseDao;
import com.antiy.asset.entity.AssetNetworkCard;
import com.antiy.asset.entity.vo.query.AssetNetworkCardQuery;
import com.antiy.asset.entity.vo.response.AssetNetworkCardResponse;

/**
 * <p>
 * 网卡信息表 Mapper 接口
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-28
 */
public interface AssetNetworkCardDao extends IBaseDao<AssetNetworkCard> {

    List<AssetNetworkCardResponse> findListAssetNetworkCard(AssetNetworkCardQuery query) throws Exception;
}
