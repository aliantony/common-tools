package com.antiy.asset.dao;

import java.util.List;

import com.antiy.asset.entity.*;
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

    /** 批量查询资产对应的上一步日志
     *
     * @param ids 资产id列表
     * @return 上一步资产备注列表
     **/
    List<AssetStatusNote> queryAssetPreStatusInfo(List<String> ids);
    /** 通过资产id查询该资产所有状态流转信息
     *
     * @param id 资产id
     * @return 资产状态列表
     **/
    List<AssetStatusDetail> queryAssetAllStatusInfo(String id);
}
