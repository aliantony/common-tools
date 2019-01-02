package com.antiy.asset.dao;

import com.antiy.asset.dto.AssetSafetyEquipmentDTO;
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
 * @since 2018-12-29
 */
public interface AssetSafetyEquipmentDao extends IBaseDao<AssetSafetyEquipment> {

    List<AssetSafetyEquipmentDTO> findListAssetSafetyEquipment(AssetSafetyEquipmentQuery query) throws Exception;
}
