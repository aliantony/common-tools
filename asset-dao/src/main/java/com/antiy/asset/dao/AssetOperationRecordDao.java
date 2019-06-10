package com.antiy.asset.dao;

import java.util.List;

import com.antiy.asset.entity.AssetOperationRecord;
import com.antiy.asset.entity.AssetOperationRecordBarPO;
import com.antiy.asset.vo.query.AssetOperationRecordQuery;
import com.antiy.common.base.IBaseDao;
import org.apache.ibatis.annotations.Param;

import javax.validation.constraints.Pattern;

/**
 * <p> 资产操作记录表 Mapper 接口 </p>
 *
 * @author zhangyajun
 * @since 2019-01-07
 */
public interface AssetOperationRecordDao extends IBaseDao<AssetOperationRecord> {

    List<AssetOperationRecordBarPO> findAssetOperationRecordBarByAssetId(AssetOperationRecordQuery assetOperationRecordQuery);

    Integer findCountAssetOperationRecordBarByAssetId(AssetOperationRecordQuery assetOperationRecordQuery);

    /**
     * 通过ID获取最新的记录创建时间
     * @param assetId
     * @return
     */
    Long getTimeByAssetId(String assetId);

    Integer insertBatch(List<AssetOperationRecord> recordList);
}
