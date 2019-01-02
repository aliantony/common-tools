package com.antiy.asset.dao;

import java.util.List;
import com.antiy.common.base.IBaseDao;
import com.antiy.asset.entity.AssetNetworkEquipment;
import com.antiy.asset.dto.AssetNetworkEquipmentDTO;
import com.antiy.asset.vo.query.AssetNetworkEquipmentQuery;

/**
 * <p>
 * 网络设备详情表 Mapper 接口
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
public interface AssetNetworkEquipmentDao extends IBaseDao<AssetNetworkEquipment> {

    List<AssetNetworkEquipmentDTO> findListAssetNetworkEquipment(AssetNetworkEquipmentQuery query) throws Exception;
}
