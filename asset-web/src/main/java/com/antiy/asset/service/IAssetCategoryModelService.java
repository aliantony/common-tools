package com.antiy.asset.service;

import java.io.Serializable;
import java.util.List;

import com.antiy.asset.entity.AssetCategoryModel;
import com.antiy.asset.vo.query.AssetCategoryModelQuery;
import com.antiy.asset.vo.request.AssetCategoryModelRequest;
import com.antiy.asset.vo.response.AssetCategoryModelNodeResponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.IBaseService;

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
     * 删除品类
     *
     * @param id 删除的id，isConfirm是否已经确认
     */
    ActionResponse delete(Serializable id) throws Exception;


    /**
     * 递归查询品类子节点
     *
     * @return
     */
    List<AssetCategoryModel> recursionSearch(List<AssetCategoryModel> list, Integer id) throws Exception;


    /**
     * 查询节点树
     * @return
     */
    AssetCategoryModelNodeResponse queryCategoryNodeCount() throws Exception;

    /**
     * 查询节点树(去掉根节点）
     * @return
     */
    List<AssetCategoryModelNodeResponse> queryCategoryWithOutRootNode(AssetCategoryModelQuery query) throws Exception;


}
