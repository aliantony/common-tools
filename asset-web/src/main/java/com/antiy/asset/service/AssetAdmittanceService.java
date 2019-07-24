package com.antiy.asset.service;

import java.util.List;

import com.antiy.asset.vo.query.AssetQuery;
import com.antiy.asset.vo.response.AssetResponse;
import com.antiy.common.base.PageResult;

/**
 * @Author: lvliang
 * @Date: 2019/7/24 15:07
 */
public interface AssetAdmittanceService {

    /**
     * 查询对象集合
     *
     * @param query
     * @return
     */
    List<AssetResponse> findListAsset(AssetQuery query) throws Exception;

    /**
     * 批量查询
     *
     * @param query
     * @return
     */
    PageResult<AssetResponse> findPageAsset(AssetQuery query) throws Exception;
}
