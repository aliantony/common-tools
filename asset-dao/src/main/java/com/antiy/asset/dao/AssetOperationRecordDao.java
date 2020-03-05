package com.antiy.asset.dao;

import com.antiy.asset.entity.AssetOperationRecord;
import com.antiy.asset.entity.AssetStatusDetail;
import com.antiy.asset.entity.AssetStatusNote;
import com.antiy.asset.vo.query.AssetSchemeQuery;
import com.antiy.asset.vo.response.AssetOperationRecordResponse;
import com.antiy.common.base.IBaseDao;

import java.util.List;


/**
 * <p> 资产操作记录表 Mapper 接口 </p>
 *
 * @author zhangyajun
 * @since 2019-01-07
 */
public interface AssetOperationRecordDao extends IBaseDao<AssetOperationRecord> {

    /**
     * 新增操作记录
     * @param recordList 操作记录
     * @return 影响行数
     */
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

    /**
     * 查询最近一次执行[检查]的操作者
     * @param assetId
     * @return
     */
    Integer getCreateUserByAssetId(Integer assetId);

    /**
     * 根据资产id查询是否需要漏扫
     * @param assetId
     * @return
     */
    Integer getNeedVulScan(Integer assetId);

    /**
     * 根据资产id查询上一步状态
     * @param assetIds
     * @return
     */
    List<AssetOperationRecord> listByAssetIds(List<Integer> assetIds);

    List<AssetOperationRecordResponse> queryAssetSchemListByAssetIds(AssetSchemeQuery assetSchemeQuery);

    AssetOperationRecordResponse queryCheckSchemeByTaskId(Integer taskId);
}
