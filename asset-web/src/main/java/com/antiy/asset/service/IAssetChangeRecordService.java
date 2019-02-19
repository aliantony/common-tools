package com.antiy.asset.service;

import com.antiy.asset.entity.AssetChangeRecord;
import com.antiy.asset.vo.query.AssetChangeRecordQuery;
import com.antiy.asset.vo.request.AssetChangeRecordRequest;
import com.antiy.asset.vo.response.AssetChangeRecordResponse;
import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;

import java.util.List;


/**
 * <p>
 * 变更记录表
 服务类
 * </p>
 *
 * @author why
 * @since 2019-02-19
 */
public interface IAssetChangeRecordService extends IBaseService<AssetChangeRecord> {

        /**
         * 保存
         * @param request
         * @return
         */
        Integer saveAssetChangeRecord(AssetChangeRecordRequest request) throws Exception;

        /**
         * 修改
         * @param request
         * @return
         */
        Integer updateAssetChangeRecord(AssetChangeRecordRequest request) throws Exception;

        /**
         * 查询对象集合
         * @param query
         * @return
         */
        List<AssetChangeRecordResponse> findListAssetChangeRecord(AssetChangeRecordQuery query) throws Exception;

        /**
         * 批量查询
         * @param query
         * @return
         */
        PageResult<AssetChangeRecordResponse> findPageAssetChangeRecord(AssetChangeRecordQuery query) throws Exception;

}
