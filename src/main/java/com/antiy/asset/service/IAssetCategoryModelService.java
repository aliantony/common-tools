package com.antiy.asset.service;

import com.antiy.asset.entity.AssetCategoryModel;
import com.antiy.asset.vo.query.AssetCategoryModelQuery;
import com.antiy.asset.vo.request.AssetCategoryModelRequest;
import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;

import java.util.List;


/**
 * <p>
 * 品类型号表 服务类
 * </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
public interface IAssetCategoryModelService extends IBaseService<AssetCategoryModel> {

    /**
     * 保存
     *
     * @param request
     * @return
     */
    Integer saveAssetCategoryModel(AssetCategoryModelRequest request) throws Exception;

    /**
     * 修改
     *
     * @param request
     * @return
     */
    Integer updateAssetCategoryModel(AssetCategoryModelRequest request) throws Exception;

    /**
     * 查询对象集合
     *
     * @param query
     * @return
     */
    List<AssetCategoryModel> findListAssetCategoryModel(AssetCategoryModelQuery query) throws Exception;

    /**
     * 批量查询
     *
     * @param query
     * @return
     */
    PageResult<AssetCategoryModel> findPageAssetCategoryModel(AssetCategoryModelQuery query) throws Exception;

}
