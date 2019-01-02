package com.antiy.asset.dao;

import com.antiy.asset.entity.AssetSafetyEquipment;
import com.antiy.asset.vo.query.AssetSafetyEquipmentQuery;
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

    List<AssetSafetyEquipment> findListAssetSafetyEquipment(AssetSafetyEquipmentQuery query) throws Exception;
}
