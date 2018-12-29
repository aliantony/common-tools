package com.antiy.asset.service;

import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;

import java.util.List;

import com.antiy.asset.asset.entity.vo.query.AssetQuery;
import com.antiy.asset.asset.entity.vo.request.AssetRequest;
import com.antiy.asset.asset.entity.vo.response.AssetResponse;
import com.antiy.asset.entity.Asset;


/**
 * <p>
 * 资产主表 服务类
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
public interface IAssetService extends IBaseService<Asset> {

        /**
         * 保存
         * @param assetRequest
         * @return
         */
        Integer saveAsset(AssetRequest assetRequest) throws Exception;

        /**
         * 修改
         * @param assetRequest
         * @return
         */
        Integer updateAsset(AssetRequest assetRequest) throws Exception;

        /**
         * 查询对象集合
         * @param assetQuery
         * @return
         */
        List<AssetResponse> findListAsset(AssetQuery assetQuery) throws Exception;

        /**
         * 批量查询
         * @param assetQuery
         * @return
         */
        PageResult<AssetResponse> findPageAsset(AssetQuery assetQuery) throws Exception;

}
