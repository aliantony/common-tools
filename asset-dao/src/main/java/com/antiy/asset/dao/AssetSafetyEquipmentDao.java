package com.antiy.asset.dao;

import com.antiy.asset.entity.AssetSafetyEquipment;
import com.antiy.common.base.IBaseDao;

import java.util.List;

/**
 * <p>
 * 安全设备详情表 Mapper 接口
 * </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
public interface AssetSafetyEquipmentDao extends IBaseDao<AssetSafetyEquipment> {

    Integer insertBatch(List<AssetSafetyEquipment> assetSafetyEquipments);
}
