package com.antiy.asset.service;

import java.util.List;

import com.antiy.asset.entity.AssetMainborad;
import com.antiy.asset.vo.query.AssetMainboradQuery;
import com.antiy.asset.vo.request.AssetMainboradRequest;
import com.antiy.asset.vo.response.AssetMainboradResponse;
import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;

/**
 * <p> 主板表 服务类 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
public interface IAssetMainboradService extends IBaseService<AssetMainborad> {

    /**
     * 保存
     *
     * @param request
     * @return
     */
    Integer saveAssetMainborad(AssetMainboradRequest request) throws Exception;

    /**
     * 修改
     *
     * @param request
     * @return
     */
    Integer updateAssetMainborad(AssetMainboradRequest request) throws Exception;

    /**
     * 查询对象集合
     *
     * @param query
     * @return
     */
    List<AssetMainboradResponse> findListAssetMainborad(AssetMainboradQuery query) throws Exception;

    /**
     * 批量查询
     *
     * @param query
     * @return
     */
    PageResult<AssetMainboradResponse> findPageAssetMainborad(AssetMainboradQuery query) throws Exception;

}
