package com.antiy.asset.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;

import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.entity.AssetCategoryModel;
import com.antiy.asset.util.ArrayTypeUtil;
import com.antiy.asset.vo.query.AssetQuery;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetCategoryModelDao;
import com.antiy.asset.entity.AssetCategoryModel;
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
        assetCategoryModel.setIsDefault(1);
        assetCategoryModel.setStatus(1);
        return assetCategoryModel.getId();
    }

    @Override
    public Integer updateAssetCategoryModel(AssetCategoryModelRequest request) throws Exception {
        AssetCategoryModel assetCategoryModel = requestConverter.convert(request, AssetCategoryModel.class);
        // 判断是否是系统内置
        if (assetCategoryModel.getIsDefault().equals(0)) {
            return 0;
        }
        assetCategoryModel.setStatus(1);
        return assetCategoryModelDao.update(assetCategoryModel);
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
     * 删除品类及其子品类,若存在资产则不能删
     * @return -1 表示存在资产，不能删除 -3 是系统内置品类，不能删除 >=0 表示删除的品类数
     */
    public Integer deleteAllById(Serializable id) throws Exception {
        AssetCategoryModel assetCategoryModel = assetCategoryModelDao.getById(id);
        // 判断是否是系统内置
        if (Objects.equals(assetCategoryModel.getIsDefault(),0)) {
            return -3;
        }
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
            if (assetCategoryModel.getId() == id)
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
            if (assetCategoryModel.getParentId() == id) {
                result.add(assetCategoryModel);
                recursion(result, list, assetCategoryModel.getId());
            }
        }
    }
}
