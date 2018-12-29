package com.antiy.asset.asset.dao;

import java.util.List;
import com.antiy.common.base.IBaseDao;
import com.antiy.asset.asset.entity.AssetNetworkEquipment;
import com.antiy.asset.asset.entity.vo.query.AssetNetworkEquipmentQuery;
import com.antiy.asset.asset.entity.vo.response.AssetNetworkEquipmentResponse;

/**
 * <p>
 * 网络设备详情表 Mapper 接口
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
public interface AssetNetworkEquipmentDao extends IBaseDao<AssetNetworkEquipment> {

    List<AssetNetworkEquipmentResponse> findListAssetNetworkEquipment(AssetNetworkEquipmentQuery query) throws Exception;
}
