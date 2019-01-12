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
     * 保存
     *
     * @param request
     * @return
     */
    Integer saveAssetOperationRecord(AssetOperationRecordRequest request) throws Exception;

    /**
     * 修改
     *
     * @param request
     * @return
     */
    Integer updateAssetOperationRecord(AssetOperationRecordRequest request) throws Exception;

    /**
     * 查询对象集合
     *
     * @param query
     * @return
     */
    List<AssetOperationRecordResponse> findListAssetOperationRecord(AssetOperationRecordQuery query) throws Exception;

    /**
     * 批量查询
     *
     * @param query
     * @return
     */
    PageResult<AssetOperationRecordResponse> findPageAssetOperationRecord(AssetOperationRecordQuery query) throws Exception;

}
