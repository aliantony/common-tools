package com.antiy.asset.dao;

import java.util.List;

import com.antiy.asset.entity.AssetOperationRecord;
import com.antiy.asset.entity.AssetOperationRecordBarPO;
import com.antiy.asset.vo.query.AssetOperationRecordQuery;
import com.antiy.common.base.IBaseDao;

/**
 * <p> 资产操作记录表 Mapper 接口 </p>
 *
 * @author zhangyajun
 * @since 2019-01-07
 */
public interface AssetOperationRecordDao extends IBaseDao<AssetOperationRecord> {

    List<AssetOperationRecordBarPO> findAssetOperationRecordBarByAssetId(AssetOperationRecordQuery assetOperationRecordQuery);
}
