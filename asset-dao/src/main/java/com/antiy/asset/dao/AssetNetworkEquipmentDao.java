package com.antiy.asset.dao;

import java.util.List;

import com.antiy.asset.entity.AssetNetworkEquipment;
import com.antiy.asset.vo.query.AssetNetworkEquipmentQuery;
import com.antiy.common.base.IBaseDao;

/**
 * <p> 网络设备详情表 Mapper 接口 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
public interface AssetNetworkEquipmentDao extends IBaseDao<AssetNetworkEquipment> {

    List<AssetNetworkEquipment> findListAssetNetworkEquipment(AssetNetworkEquipmentQuery query) throws Exception;

    /**
     * 通过ID查询设备端口
     *
     * @param query
     * @return
     */
    List<AssetNetworkEquipment> findPortById(AssetNetworkEquipmentQuery query);

    Integer findPortAmount(AssetNetworkEquipmentQuery query);
}
