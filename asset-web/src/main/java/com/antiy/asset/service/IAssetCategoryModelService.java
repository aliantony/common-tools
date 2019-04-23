package com.antiy.asset.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.antiy.asset.entity.AssetCategoryModel;
import com.antiy.asset.vo.query.AssetCategoryModelQuery;
import com.antiy.asset.vo.request.AssetCategoryModelRequest;
import com.antiy.asset.vo.response.AssetCategoryModelNodeResponse;
import com.antiy.asset.vo.response.AssetCategoryModelResponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import io.swagger.annotations.ApiParam;

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
     * @param initMap 二级品类 id 和 name的映射 若没有这个参数 可直接调用getSecondCategoryMap方法作为传参
     * @return
     */
    AssetCategoryModelNodeResponse querySecondCategoryNode(String[] types, Map<String, String> initMap)
                                                                                                       throws Exception;

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
     * 通过id查询所有品类和子品类id
     *
     * @return
     */
    List<Integer> findAssetCategoryModelIdsById(Integer id) throws Exception;

    /**
     * 通过id查询所有品类和子品类id
     *
     * @return
     */
    List<Integer> findAssetCategoryModelIdsById(Integer id, List<AssetCategoryModel> assetCategoryModels)
                                                                                                         throws Exception;

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

    /**
     * 递归查询该品类所属的二级id
     * @param categoryId 品类id
     * @param all 查询的列表范围，默认为所有品类
     * @param secondCategorys 二级品类集合
     * @return 二级品类id
     */
    String recursionSearchParentCategory(String categoryId, List<AssetCategoryModel> all, Set<String> secondCategorys);

    /**
     * 获取二级品类型号 id和name的映射
     * @return
     * @throws Exception
     */
    Map<String, String> getSecondCategoryMap() throws Exception;

    /**
     * 获取安全设备和网络设备树
     */
    AssetCategoryModelNodeResponse queryComputeAndNetCategoryNode(Integer isNet) throws Exception;

}
