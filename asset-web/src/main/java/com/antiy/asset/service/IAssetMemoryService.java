package com.antiy.asset.service;

import com.antiy.asset.entity.AssetMemory;
import com.antiy.asset.vo.query.AssetMemoryQuery;
import com.antiy.asset.vo.request.AssetMemoryRequest;
import com.antiy.asset.vo.response.AssetMemoryResponse;
import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;

import java.util.List;


/**
 * <p>
 * 内存表 服务类
 * </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
public interface IAssetMemoryService extends IBaseService<AssetMemory> {

    /**
     * 保存
     *
     * @param request
     * @return
     */
    Integer saveAssetMemory(AssetMemoryRequest request) throws Exception;

    /**
     * 修改
     *
     * @param request
     * @return
     */
    Integer updateAssetMemory(AssetMemoryRequest request) throws Exception;

    /**
     * 查询对象集合
     *
     * @param query
     * @return
     */
    List<AssetMemoryResponse> findListAssetMemory(AssetMemoryQuery query) throws Exception;

    /**
     * 批量查询
     *
     * @param query
     * @return
     */
    PageResult<AssetMemoryResponse> findPageAssetMemory(AssetMemoryQuery query) throws Exception;

}
