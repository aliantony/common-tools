package com.antiy.asset.dao;

import com.antiy.asset.dto.AssetNetworkCardDTO;
import com.antiy.asset.entity.AssetNetworkCard;
import com.antiy.asset.vo.query.AssetNetworkCardQuery;
import com.antiy.common.base.IBaseDao;

import java.util.List;

/**
 * <p>
 * 网卡信息表 Mapper 接口
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
public interface AssetNetworkCardDao extends IBaseDao<AssetNetworkCard> {

    List<AssetNetworkCardDTO> findListAssetNetworkCard(AssetNetworkCardQuery query) throws Exception;
}
