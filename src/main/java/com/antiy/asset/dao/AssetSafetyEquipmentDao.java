package com.antiy.asset.dao;

import java.util.List;
import com.antiy.common.base.IBaseDao;
import com.antiy.asset.entity.AssetSafetyEquipment;
import com.antiy.asset.asset.entity.vo.query.AssetSafetyEquipmentQuery;
import com.antiy.asset.asset.entity.vo.response.AssetSafetyEquipmentResponse;

/**
 * <p>
 * 安全设备详情表 Mapper 接口
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
public interface AssetSafetyEquipmentDao extends IBaseDao<AssetSafetyEquipment> {

    List<AssetSafetyEquipmentResponse> findListAssetSafetyEquipment(AssetSafetyEquipmentQuery query) throws Exception;
}
