package com.antiy.asset.service;

import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;

import java.util.List;

import com.antiy.asset.entity.vo.query.AssetMemoryQuery;
import com.antiy.asset.entity.vo.request.AssetMemoryRequest;
import com.antiy.asset.entity.vo.response.AssetMemoryResponse;
import com.antiy.asset.entity.AssetMemory;


/**
 * <p>
 * 内存表 服务类
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
public interface IAssetMemoryService extends IBaseService<AssetMemory> {

        /**
         * 保存
         * @param assetMemoryRequest
         * @return
         */
        Integer saveAssetMemory(AssetMemoryRequest assetMemoryRequest) throws Exception;

        /**
         * 修改
         * @param assetMemoryRequest
         * @return
         */
        Integer updateAssetMemory(AssetMemoryRequest assetMemoryRequest) throws Exception;

        /**
         * 查询对象集合
         * @param assetMemoryQuery
         * @return
         */
        List<AssetMemoryResponse> findListAssetMemory(AssetMemoryQuery assetMemoryQuery) throws Exception;

        /**
         * 批量查询
         * @param assetMemoryQuery
         * @return
         */
        PageResult<AssetMemoryResponse> findPageAssetMemory(AssetMemoryQuery assetMemoryQuery) throws Exception;

}
