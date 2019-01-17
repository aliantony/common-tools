package com.antiy.asset.service;

import java.io.Serializable;
import java.util.List;

import com.antiy.asset.entity.AssetCategoryModel;
import com.antiy.asset.vo.query.AssetCategoryModelQuery;
import com.antiy.asset.vo.request.AssetCategoryModelRequest;
import com.antiy.asset.vo.response.AssetCategoryModelResponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;

/**
 * <p> 品类型号表 服务类 </p>
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
    ActionResponse saveAssetCategoryModel(AssetCategoryModelRequest request) throws Exception;

    /**
     * 修改
     *
     * @param request
     * @return
     */
    ActionResponse updateAssetCategoryModel(AssetCategoryModelRequest request) throws Exception;

    /**
     * 查询对象集合
     *
     * @param query
     * @return
     */
    List<AssetCategoryModelResponse> findListAssetCategoryModel(AssetCategoryModelQuery query) throws Exception;

    /**
     * 批量查询
     *
     * @param query
     * @return
     */
    PageResult<AssetCategoryModelResponse> findPageAssetCategoryModel(AssetCategoryModelQuery query) throws Exception;
    /**
     * 删除品类
     *
     * @param id 删除的id，isConfirm是否已经确认
     * @return -1 表示存在资产，不能删除 -2 表示存在子品类，需要确认 -3 是系统内置品类，不能删除 >=0 表示删除的品类数
     */
    ActionResponse delete(Serializable id, Boolean isConfirm) throws Exception;


}
