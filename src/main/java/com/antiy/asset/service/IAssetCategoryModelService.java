package com.antiy.asset.service;

import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import java.io.Serializable;
import java.util.List;

import com.antiy.asset.entity.dto.AssetCategoryModelDO;
import com.antiy.asset.entity.vo.query.AssetCategoryModelQuery;
import com.antiy.asset.entity.vo.request.AssetCategoryModelRequest;
import com.antiy.asset.entity.vo.response.AssetCategoryModelResponse;
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
         * @param request
         * @return
         */
        Integer saveAssetCategoryModel(AssetCategoryModelRequest request) throws Exception;

        /**
         * 修改
         * @param request
         * @return
         */
        Integer updateAssetCategoryModel(AssetCategoryModelRequest request) throws Exception;

        /**
         * 查询对象集合
         * @param query
         * @return
         */
        @Override
        public List<AssetCategoryModelResponse> findListAssetCategoryModel(AssetCategoryModelQuery query) throws Exception;

        /**
         * 批量查询
         * @param query
         * @return
         */
        PageResult<AssetCategoryModelResponse> findPageAssetCategoryModel(AssetCategoryModelQuery query) throws Exception;

}
