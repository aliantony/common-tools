package com.antiy.asset.service;

import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;

import java.util.List;

import com.antiy.asset.asset.entity.vo.query.AssetMainboradQuery;
import com.antiy.asset.asset.entity.vo.request.AssetMainboradRequest;
import com.antiy.asset.asset.entity.vo.response.AssetMainboradResponse;
import com.antiy.asset.entity.AssetMainborad;


/**
 * <p>
 * 主板表 服务类
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
public interface IAssetMainboradService extends IBaseService<AssetMainborad> {

        /**
         * 保存
         * @param assetMainboradRequest
         * @return
         */
        Integer saveAssetMainborad(AssetMainboradRequest assetMainboradRequest) throws Exception;

        /**
         * 修改
         * @param assetMainboradRequest
         * @return
         */
        Integer updateAssetMainborad(AssetMainboradRequest assetMainboradRequest) throws Exception;

        /**
         * 查询对象集合
         * @param assetMainboradQuery
         * @return
         */
        List<AssetMainboradResponse> findListAssetMainborad(AssetMainboradQuery assetMainboradQuery) throws Exception;

        /**
         * 批量查询
         * @param assetMainboradQuery
         * @return
         */
        PageResult<AssetMainboradResponse> findPageAssetMainborad(AssetMainboradQuery assetMainboradQuery) throws Exception;

}
