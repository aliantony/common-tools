package com.antiy.asset.dao;

import java.util.List;
import com.antiy.common.base.IBaseDao;
import com.antiy.asset.entity.AssetNetworkCard;
import com.antiy.asset.dto.AssetNetworkCardDTO;
import com.antiy.asset.vo.query.AssetNetworkCardQuery;

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
