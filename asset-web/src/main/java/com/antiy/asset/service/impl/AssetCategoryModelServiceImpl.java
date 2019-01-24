package com.antiy.asset.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;

import com.antiy.asset.convert.CategoryRequestConvert;
import com.antiy.asset.convert.NodeConverter;
import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.entity.AssetCategoryModel;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.util.LogHandle;
import com.antiy.asset.util.NodeUtilsConverter;
import com.antiy.asset.vo.enums.AssetEventEnum;
import com.antiy.asset.vo.query.AssetQuery;
import com.antiy.asset.vo.response.AssetCategoryModelNodeResponse;
import com.antiy.biz.entity.ErrorMessage;
import com.antiy.common.base.*;
import com.antiy.common.enums.ModuleEnum;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.utils.BusinessExceptionUtils;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.ParamterExceptionUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.hadoop.util.hash.Hash;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetCategoryModelDao;
import com.antiy.asset.service.IAssetCategoryModelService;
import com.antiy.asset.vo.query.AssetCategoryModelQuery;
import com.antiy.asset.vo.request.AssetCategoryModelRequest;
import com.antiy.asset.vo.response.AssetCategoryModelResponse;

import static com.antiy.biz.file.FileHelper.logger;

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
    private AssetCategoryModelDao   assetCategoryModelDao;
    @Resource
    private AssetDao                assetDao;
    @Resource
    private CategoryRequestConvert  requestConverter;
    @Resource
    private CategoryResponseConvert responseConverter;
    @Resource
    private CategoryRequestConvert  categoryRequestConvert;

    /**
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public ActionResponse saveAssetCategoryModel(AssetCategoryModelRequest request) throws Exception {
        AssetCategoryModel assetCategoryModel = requestConverter.convert(request, AssetCategoryModel.class);
        if (checkNameRepeat(request)) {
            return ActionResponse.fail(RespBasicCode.BUSSINESS_EXCETION, "该品类名已存在");
        }
        setParentType(assetCategoryModel);
        assetCategoryModel.setGmtCreate(System.currentTimeMillis());
        assetCategoryModel.setStatus(1);
        Integer result = assetCategoryModelDao.insert(assetCategoryModel);
        // 新增的均为非系统内置的
        assetCategoryModel.setIsDefault(1);
        if (!Objects.equals(0, result)) {
            // 写入业务日志
            LogHandle.log(assetCategoryModel.toString(), AssetEventEnum.ASSET_CATEGORY_INSERT.getName(),
                AssetEventEnum.ASSET_CATEGORY_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
            LogUtils
                .info(logger, AssetEventEnum.ASSET_CATEGORY_INSERT.getName() + " {}", assetCategoryModel.toString());
        }
        return ActionResponse.success(assetCategoryModel.getId());

    }

    Boolean checkNameRepeat(AssetCategoryModelRequest request) throws Exception {
        if (Objects.nonNull(request.getName())) {
            AssetCategoryModelQuery assetCategoryModelQuery = new AssetCategoryModelQuery();
            assetCategoryModelQuery.setName(request.getName());
            return assetCategoryModelDao.findCount(assetCategoryModelQuery) >= 1;
        }
        return false;
    }

    /**
     * 设置资产类型与父品类的资产类型一致
     * @param assetCategoryModel
     * @return
     */
    private void setParentType(AssetCategoryModel assetCategoryModel) throws Exception {
        String parentId = assetCategoryModel.getParentId();
        AssetCategoryModel parent = assetCategoryModelDao.getById(parentId);
        BusinessExceptionUtils.isNull(parent, "父类型不存在");
        assetCategoryModel.setAssetType(parent.getAssetType());
    }

    @Override
    public ActionResponse updateAssetCategoryModel(AssetCategoryModelRequest request) throws Exception {
        AssetCategoryModel assetCategoryModel = categoryRequestConvert.convert(request, AssetCategoryModel.class);
        if (checkNameRepeat(request)) {
            return ActionResponse.fail(RespBasicCode.BUSSINESS_EXCETION, "该品类名已存在");
        }
        AssetCategoryModel assetCategoryModelById = assetCategoryModelDao.getById(assetCategoryModel.getId());
        // 判断是不是系统内置
        if (checkIsDefault(assetCategoryModelById)) {
            assetCategoryModel.setStatus(1);
            assetCategoryModel.setParentId(null);
            assetCategoryModel.setAssetType(null);
            Integer result = assetCategoryModelDao.update(assetCategoryModel);
            if (!Objects.equals(0, result)) {
                // 写入业务日志
                LogHandle.log(assetCategoryModel.toString(), AssetEventEnum.ASSET_CATEGORY_UPDATE.getName(),
                    AssetEventEnum.ASSET_CATEGORY_UPDATE.getStatus(), ModuleEnum.ASSET.getCode());
                LogUtils.info(logger, AssetEventEnum.ASSET_CATEGORY_UPDATE.getName() + " {}",
                    assetCategoryModel.toString());
            }
            return ActionResponse.success(result);
        }
        return ActionResponse.fail(RespBasicCode.BUSSINESS_EXCETION, "系统内置品类不能更新或删除");
    }

    /**
     * 判断是否是自定义的品类
     * @param assetCategoryModel
     * @return
     */
    private Boolean checkIsDefault(AssetCategoryModel assetCategoryModel) {
        BusinessExceptionUtils.isNull(assetCategoryModel, "该类型不存在");
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
     * @return ActionResponse
     */
    @Override
    public ActionResponse delete(Serializable id) throws Exception {
        AssetCategoryModel assetCategoryModel = assetCategoryModelDao.getById(id);
        // 判断是否自定义品类
        if (!checkIsDefault(assetCategoryModel)) {
            return ActionResponse.fail(RespBasicCode.BUSSINESS_EXCETION, "系统内置品类不能更新或删除");
        }
        return deleteAllById(id);
    }

    @Override
    public AssetCategoryModelNodeResponse queryCategoryNode() throws Exception {
        AssetCategoryModelQuery query = new AssetCategoryModelQuery();
        query.setPageSize(-1);
        List<AssetCategoryModel> assetCategoryModels = assetCategoryModelDao.findListAssetCategoryModel(query);
        NodeConverter nodeConverter = new NodeConverter();
        List<AssetCategoryModelNodeResponse> assetDepartmentNodeResponses = nodeConverter.columnToNode(
            assetCategoryModels, AssetCategoryModelNodeResponse.class);
        return CollectionUtils.isNotEmpty(assetDepartmentNodeResponses) ? assetDepartmentNodeResponses.get(0) : null;
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
            Integer result = assetCategoryModelDao.delete(list);
            if (!Objects.equals(0, result)) {
                // 写入业务日志
                LogHandle.log(list.toString(), AssetEventEnum.ASSET_CATEGORY_DELETE.getName(),
                    AssetEventEnum.ASSET_CATEGORY_DELETE.getStatus(), ModuleEnum.ASSET.getCode());
                LogUtils.info(logger, AssetEventEnum.ASSET_CATEGORY_DELETE.getName() + " {}", list.toString());
            }
            return ActionResponse.success(result);
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
            if (Objects.equals(assetCategoryModel.getParentId(), Objects.toString(id))) {
                result.add(assetCategoryModel);
                recursion(result, list, assetCategoryModel.getId());
            }
        }
    }
}

@Component
class CategoryResponseConvert extends BaseConverter<AssetCategoryModel, AssetCategoryModelResponse> {
    @Override
    protected void convert(AssetCategoryModel assetCategoryModel, AssetCategoryModelResponse assetCategoryModelResponse) {
        assetCategoryModelResponse.setParentId(Objects.toString(assetCategoryModel.getParentId()));
        super.convert(assetCategoryModel, assetCategoryModelResponse);
    }
}
