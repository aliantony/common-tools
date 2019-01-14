package com.antiy.asset.service;

import java.util.List;

import com.antiy.asset.entity.AssetOperationRecord;
import com.antiy.asset.vo.query.AssetOperationRecordQuery;
import com.antiy.asset.vo.request.AssetOperationRecordRequest;
import com.antiy.asset.vo.response.AssetOperationRecordResponse;
import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;

/**
 * <p> 资产操作记录表 服务类 </p>
 *
 * @author zhangyajun
 * @since 2019-01-07
 */
public interface IAssetOperationRecordService extends IBaseService<AssetOperationRecord> {

    /**
     * 通过资产主键查询资产操作记录
     * @param assetOperationRecordQuery
     * @return
     */
    List<AssetOperationRecordResponse> findAssetOperationRecordByAssetId(AssetOperationRecordQuery assetOperationRecordQuery);
}
