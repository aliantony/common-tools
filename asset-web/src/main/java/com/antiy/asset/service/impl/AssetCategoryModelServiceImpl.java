package com.antiy.asset.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;

import com.antiy.asset.convert.CategoryRequestConvert;
import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.entity.AssetCategoryModel;
import com.antiy.asset.vo.query.AssetQuery;
import com.antiy.biz.entity.ErrorMessage;
import com.antiy.common.base.*;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.utils.BusinessExceptionUtils;
import com.antiy.common.utils.ParamterExceptionUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetCategoryModelDao;
import com.antiy.asset.service.IAssetCategoryModelService;
import com.antiy.asset.vo.query.AssetCategoryModelQuery;
import com.antiy.asset.vo.request.AssetCategoryModelRequest;
import com.antiy.asset.vo.response.AssetCategoryModelResponse;

/**
 * <p> 品类型号表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
@Service
public class AssetCategoryModelServiceImpl extends BaseServiceImpl<AssetCategoryModel> implements
                                                                                      IAssetCategoryModelService {

    @Resource
    private AssetCategoryModelDao                                         assetCategoryModelDao;
    @Resource
    private AssetDao                                                      assetDao;
    @Resource
    private CategoryRequestConvert                                        requestConverter;
    @Resource
    private BaseConverter<AssetCategoryModel, AssetCategoryModelResponse> responseConverter;
    @Resource
    private CategoryRequestConvert                                        categoryRequestConvert;

    /**
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public ActionResponse saveAssetCategoryModel(AssetCategoryModelRequest request) throws Exception {
        AssetCategoryModel assetCategoryModel = requestConverter.convert(request, AssetCategoryModel.class);
        // 新增的品类必须和父品类的资产类型一致
        if (setParentType(assetCategoryModel)) {
            assetCategoryModel.setGmtCreate(System.currentTimeMillis());
            assetCategoryModel.setStatus(1);
            assetCategoryModelDao.insert(assetCategoryModel);
            // 新增的均为非系统内置的
            assetCategoryModel.setIsDefault(1);
            return ActionResponse.success(assetCategoryModel.getId());
        }
        return ActionResponse.fail(RespBasicCode.BUSSINESS_EXCETION, "存在资产，不能删除");
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
            ParamterExceptionUtils.isNull(parent, "父类型不存在");
            assetCategoryModel.setAssetType(parent.getAssetType());
            return true;
        }
        return false;
    }

    @Override
    public ActionResponse updateAssetCategoryModel(AssetCategoryModelRequest request) throws Exception {
        AssetCategoryModel assetCategoryModel = categoryRequestConvert.convert(request, AssetCategoryModel.class);
        AssetCategoryModel assetCategoryModelById = assetCategoryModelDao.getById(assetCategoryModel.getId());
        // 判断是不是系统内置
        if (checkIsDefault(assetCategoryModelById)) {
            assetCategoryModel.setStatus(1);
            assetCategoryModel.setParentId(null);
            assetCategoryModel.setAssetType(null);
            return ActionResponse.success(assetCategoryModelDao.update(assetCategoryModel));
        }
        return ActionResponse.fail(RespBasicCode.BUSSINESS_EXCETION, "系统内置品类不能更新或删除");
    }

    /**
     * 判断是否是自定义的品类
     * @param assetCategoryModel
     * @return
     */
    private Boolean checkIsDefault(AssetCategoryModel assetCategoryModel) {
        ParamterExceptionUtils.isNull(assetCategoryModel, "该类型不存在");
        if (assetCategoryModel.getIsDefault() != null) {
            return Objects.equals(assetCategoryModel.getIsDefault(), 1);
        }
        return false;
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
    public PageResult<AssetCategoryModelResponse> findPageAssetCategoryModel(AssetCategoryModelQuery query)
                                                                                                           throws Exception {
        return new PageResult<>(query.getPageSize(), this.findCountAssetCategoryModel(query), query.getCurrentPage(),
            this.findListAssetCategoryModel(query));
    }

    /**
     * 删除品类
     *
     * @param id 删除的id，isConfirm是否已经确认
     * @return ActionResponse
     */
    public ActionResponse delete(Serializable id, Boolean isConfirm) throws Exception {
        ParamterExceptionUtils.isNull(isConfirm, "二次确认不能为空");
        ParamterExceptionUtils.isNull(id, "id不能为空");
        AssetCategoryModel assetCategoryModel = assetCategoryModelDao.getById(id);
        // 判断是否自定义品类
        if (!checkIsDefault(assetCategoryModel)) {
            return ActionResponse.fail(RespBasicCode.BUSSINESS_EXCETION, "系统内置品类不能更新或删除");
        }
        // 是否是确认删除
        if (!isConfirm) {
            List<AssetCategoryModel> list = recursionSearch((Integer) id);
            // 判断是否存在子品类，若不存在判断是否存在资产
            if (list.size() > 1) {
                return ActionResponse.fail(RespBasicCode.BUSSINESS_EXCETION, "表示存在子品类，需要确认");
            } else {
                return delete(id);
            }
        } else {
            return deleteAllById(id);
        }
    }

    /**
     * 删除品类,若存在资产则不能删（不进行递归）
     * @return ActionResponse
     */
    public ActionResponse delete(Serializable id) throws Exception {
        AssetQuery assetQuery = new AssetQuery();
        assetQuery.setCategoryModel((Integer) id);
        Integer count = assetDao.findCountByCategoryModel(assetQuery);
        if (count > 0) {
            return ActionResponse.fail(RespBasicCode.BUSSINESS_EXCETION, "存在资产，不能删除");
        }
        return ActionResponse.success(super.deleteById(id));
    }

    /**
     * 删除品类及其子品类,若存在资产则不能删（进行递归）
     * @return ActionResponse
     */
    public ActionResponse deleteAllById(Serializable id) throws Exception {
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
            return ActionResponse.fail(RespBasicCode.BUSSINESS_EXCETION, "存在资产，不能删除");
        }
        if (CollectionUtils.isNotEmpty(list)) {
            return ActionResponse.success(assetCategoryModelDao.delete(list));
        } else {
            return ActionResponse.fail(RespBasicCode.BUSSINESS_EXCETION, "品类不存在，删除失败");
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
            if (Objects.equals(assetCategoryModel.getId(), id)) {
                result.add(assetCategoryModel);
            }
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
            if (Objects.equals(assetCategoryModel.getParentId(), id)) {
                result.add(assetCategoryModel);
                recursion(result, list, assetCategoryModel.getId());
            }
        }
    }
}
