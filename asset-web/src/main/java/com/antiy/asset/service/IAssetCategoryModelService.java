package com.antiy.asset.service;

import java.io.Serializable;
import java.util.List;

import com.antiy.asset.entity.AssetCategoryModel;
import com.antiy.asset.vo.query.AssetCategoryModelQuery;
import com.antiy.asset.vo.request.AssetCategoryModelRequest;
import com.antiy.asset.vo.response.AssetCategoryModelNodeResponse;
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
     */
    ActionResponse delete(Serializable id) throws Exception;

    /**
     * 查询品类树
     *
     * @return
     */
    AssetCategoryModelNodeResponse queryCategoryNode() throws Exception;

    /**
     * 通过类型查询品类树
     * @param type 1--软件 2--硬件
     * @return
     */
    AssetCategoryModelNodeResponse queryCategoryNode(int type) throws Exception;

    /**
     * 查询二级品类组合树 若参数为4和5 则结果为硬件(根节点)-(计算设备+网络设备) 树
     * @param types 4-计算设备 5-网络设备 6-存储设备 7-安全设备 8-其他设备
     * @return
     */
    AssetCategoryModelNodeResponse querySecondCategoryNode(String[] types) throws Exception;

    /**
     * 通过名称查询下一级的品类
     *
     * @return
     */
    List<AssetCategoryModelResponse> getNextLevelCategoryByName(String name) throws Exception;

    /**
     * 通过名称查询下一级的品类
     *
     * @return
     */
    List<AssetCategoryModelResponse> findAssetCategoryModelById(Integer id) throws Exception;

    /**
     * 通过名称查询下一级的品类ID
     *
     * @return
     */
    List<Integer> findAssetCategoryModelIdsById(Integer id) throws Exception;

    /**
     * 递归查询品类子节点
     *
     * @return
     */
    List<AssetCategoryModel> recursionSearch(List<AssetCategoryModel> list, Integer id) throws Exception;

    /**
     * 获取品类型号中的列表id
     * @param search
     * @return
     */
    List<String> getCategoryIdList(List<AssetCategoryModel> search);

}
