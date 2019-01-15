package com.antiy.asset.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;

import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.entity.AssetCategoryModel;
import com.antiy.asset.vo.query.AssetQuery;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetCategoryModelDao;
import com.antiy.asset.service.IAssetCategoryModelService;
import com.antiy.asset.vo.query.AssetCategoryModelQuery;
import com.antiy.asset.vo.request.AssetCategoryModelRequest;
import com.antiy.asset.vo.response.AssetCategoryModelResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;

/**
 * <p> 品类型号表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
@Service
public class AssetCategoryModelServiceImpl extends BaseServiceImpl<AssetCategoryModel>
                                           implements IAssetCategoryModelService {

    @Resource
    private AssetCategoryModelDao                                         assetCategoryModelDao;
    @Resource
    private AssetDao                                                      assetDao;
    @Resource
    private BaseConverter<AssetCategoryModelRequest, AssetCategoryModel>  requestConverter;
    @Resource
    private BaseConverter<AssetCategoryModel, AssetCategoryModelResponse> responseConverter;

    @Override
    public Integer saveAssetCategoryModel(AssetCategoryModelRequest request) throws Exception {
        AssetCategoryModel assetCategoryModel = requestConverter.convert(request, AssetCategoryModel.class);
        if (setParentType(assetCategoryModel)) {
            assetCategoryModel.setGmtCreate(System.currentTimeMillis());
            assetCategoryModel.setStatus(1);
            assetCategoryModelDao.insert(assetCategoryModel);
            return assetCategoryModel.getId();
        }
        return -1;
    }

    /**
     * 设置资产类型与父品类的资产类型一致
     * @param assetCategoryModel
     * @return
     */
    private boolean setParentType(AssetCategoryModel assetCategoryModel) throws Exception {
        if (assetCategoryModel != null && assetCategoryModel.getParentId() != null) {
            Integer parentId = assetCategoryModel.getParentId();
            AssetCategoryModel parent = assetCategoryModelDao.getById(parentId);
            assetCategoryModel.setAssetType(parent.getAssetType());
            return true;
        }
        return false;
    }

    @Override
    public Integer updateAssetCategoryModel(AssetCategoryModelRequest request) throws Exception {
        AssetCategoryModel assetCategoryModel = requestConverter.convert(request, AssetCategoryModel.class);
        AssetCategoryModel assetCategoryModelById = assetCategoryModelDao.getById(assetCategoryModel.getId());
        // 判断是不是系统内置
        if (checkIsDefault(assetCategoryModelById)) {
            assetCategoryModel.setGmtModified(System.currentTimeMillis());
            assetCategoryModel.setStatus(1);
            assetCategoryModel.setParentId(null);
            assetCategoryModel.setAssetType(null);
            return assetCategoryModelDao.update(assetCategoryModel);
        }
        return 0;
    }

    /**
     * 判断是否是自定义的品类
     * @param assetCategoryModel
     * @return
     */
    private Boolean checkIsDefault(AssetCategoryModel assetCategoryModel) {
        return assetCategoryModel != null && assetCategoryModel.getIsDefault() != null
               && Objects.equals(assetCategoryModel.getIsDefault(), 1);
    }

    @Override
    public List<AssetCategoryModelResponse> findListAssetCategoryModel(AssetCategoryModelQuery query) throws Exception {
        List<AssetCategoryModel> assetCategoryModel = assetCategoryModelDao.findListAssetCategoryModel(query);
        List<AssetCategoryModelResponse> convert = responseConverter.convert(assetCategoryModel,
            AssetCategoryModelResponse.class);
        return convert;
    }

    public Integer findCountAssetCategoryModel(AssetCategoryModelQuery query) throws Exception {
        return assetCategoryModelDao.findCount(query);
    }

    @Override
    public PageResult<AssetCategoryModelResponse> findPageAssetCategoryModel(AssetCategoryModelQuery query) throws Exception {
        return new PageResult<>(query.getPageSize(), this.findCountAssetCategoryModel(query), query.getCurrentPage(),
            this.findListAssetCategoryModel(query));
    }

    /**
     * 删除品类
     *
     * @param id 删除的id，isConfirm是否已经确认
     * @return -1 表示存在资产，不能删除 -2 表示存在子品类，需要确认 -3 是系统内置品类，不能删除 >=0 表示删除的品类数
     */
    public Integer delete(Serializable id, Boolean isConfirm) throws Exception {
        if (isConfirm == null) {
            return 0;
        }
        AssetCategoryModel assetCategoryModel = assetCategoryModelDao.getById(id);
        // 判断是否自定义品类
        if (!checkIsDefault(assetCategoryModel)) {
            return -3;
        }
        // 是否是确认删除
        if (!isConfirm) {
            List<AssetCategoryModel> list = recursionSearch((Integer) id);
            // 判断是否存在子品类，若不存在判断是否存在资产
            if (list.size() > 1) {
                return -2;
            } else {
                return deleteById(id);
            }
        } else {
            return deleteAllById(id);
        }
    }

    /**
     * 删除品类,若存在资产则不能删（不进行递归）
     * @return -1 表示存在资产，不能删除 , 表示删除的品类数
     */
    @Override
    public Integer deleteById(Serializable id) throws Exception {
        AssetQuery assetQuery = new AssetQuery();
        assetQuery.setCategoryModel((Integer) id);
        Integer count = assetDao.findCountByCategoryModel(assetQuery);
        if (count > 0) {
            return -1;
        }
        return super.deleteById(id);
    }

    /**
     * 删除品类及其子品类,若存在资产则不能删（进行递归）
     * @return -1 表示存在资产，不能删除 , 表示删除的品类数
     */
    public Integer deleteAllById(Serializable id) throws Exception {
        List<AssetCategoryModel> list = recursionSearch((Integer) id);
        AssetQuery assetQuery = new AssetQuery();
        Integer[] ids = new Integer[list.size()];
        for (int i = 0; i < list.size(); i++) {
            ids[i] = list.get(i).getId();
        }
        assetQuery.setCategoryModels(ids);
        Integer i = assetDao.findCountByCategoryModel(assetQuery);
        // 存在资产
        if (i > 0) {
            return -1;
        }
        if (CollectionUtils.isNotEmpty(list)) {
            return assetCategoryModelDao.delete(list);
        } else {
            return 0;
        }
    }

    /**
     * 递归查询出所有的品类和其子品类
     *
     * @param id 查询的部门id
     */
    private List<AssetCategoryModel> recursionSearch(Integer id) throws Exception {
        List<AssetCategoryModel> list = assetCategoryModelDao.getAll();
        List<AssetCategoryModel> result = new ArrayList();
        for (AssetCategoryModel assetCategoryModel : list) {
            if (Objects.equals(assetCategoryModel.getId(), id) )
                result.add(assetCategoryModel);
        }
        recursion(result, list, id);
        return result;
    }

    /**
     * 递归查询出所有的品类和其子品类
     *
     * @param result 查询的结果集
     * @param list 查询的数据集
     * @param id 递归的参数
     */
    private void recursion(List<AssetCategoryModel> result, List<AssetCategoryModel> list, Integer id) {
        for (AssetCategoryModel assetCategoryModel : list) {
            if (Objects.equals(assetCategoryModel.getParentId(),id)) {
                result.add(assetCategoryModel);
                recursion(result, list, assetCategoryModel.getId());
            }
        }
    }
}
