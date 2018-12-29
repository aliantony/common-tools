package com.antiy.asset.service;

import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;

import java.util.List;

import com.antiy.asset.asset.entity.vo.query.AssetCategoryModelQuery;
import com.antiy.asset.asset.entity.vo.request.AssetCategoryModelRequest;
import com.antiy.asset.asset.entity.vo.response.AssetCategoryModelResponse;
import com.antiy.asset.entity.AssetCategoryModel;


/**
 * <p>
 * 品类型号表 服务类
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
public interface IAssetCategoryModelService extends IBaseService<AssetCategoryModel> {

        /**
         * 保存
         * @param assetCategoryModelRequest
         * @return
         */
        Integer saveAssetCategoryModel(AssetCategoryModelRequest assetCategoryModelRequest) throws Exception;

        /**
         * 修改
         * @param assetCategoryModelRequest
         * @return
         */
        Integer updateAssetCategoryModel(AssetCategoryModelRequest assetCategoryModelRequest) throws Exception;

        /**
         * 查询对象集合
         * @param assetCategoryModelQuery
         * @return
         */
        List<AssetCategoryModelResponse> findListAssetCategoryModel(AssetCategoryModelQuery assetCategoryModelQuery) throws Exception;

        /**
         * 批量查询
         * @param assetCategoryModelQuery
         * @return
         */
        PageResult<AssetCategoryModelResponse> findPageAssetCategoryModel(AssetCategoryModelQuery assetCategoryModelQuery) throws Exception;

}
