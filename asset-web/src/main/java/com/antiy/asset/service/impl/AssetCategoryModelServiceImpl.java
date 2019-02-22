package com.antiy.asset.service.impl;

import static com.antiy.biz.file.FileHelper.logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;

import com.antiy.common.encoder.AesEncoder;
import com.antiy.common.utils.LoginUserUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.antiy.asset.convert.CategoryRequestConvert;
import com.antiy.asset.dao.AssetCategoryModelDao;
import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.entity.AssetCategoryModel;
import com.antiy.asset.service.IAssetCategoryModelService;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.util.LogHandle;
import com.antiy.asset.util.NodeUtilsConverter;
import com.antiy.asset.vo.enums.AssetEventEnum;
import com.antiy.asset.vo.query.AssetCategoryModelQuery;
import com.antiy.asset.vo.query.AssetQuery;
import com.antiy.asset.vo.request.AssetCategoryModelRequest;
import com.antiy.asset.vo.response.AssetCategoryModelNodeResponse;
import com.antiy.asset.vo.response.AssetCategoryModelResponse;
import com.antiy.common.base.*;
import com.antiy.common.enums.ModuleEnum;
import com.antiy.common.utils.BusinessExceptionUtils;
import com.antiy.common.utils.LogUtils;

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
    @Resource
    private AesEncoder              aesEncoder;
    // 存在状态常量
    private static final int        EXISTENCE_STATE               = 1;
    // 非系统默认常量
    private static final int        NOT_SYSTEM_DEFAULT            = 1;
    // 根节点名
    private static final String     ROOT_CATEGORY                 = "品类型号";
    // 硬件第一级节点
    private static final String     FIRST_LEVEL_ASSET_CATEGORY    = "硬件";
    // 软件第一级节点
    private static final String     FIRST_LEVEL_SOFTWARE_CATEGORY = "软件";

    /**
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public ActionResponse saveAssetCategoryModel(AssetCategoryModelRequest request) throws Exception {
        AssetCategoryModel assetCategoryModel = requestConverter.convert(request, AssetCategoryModel.class);
        request.setStringId(null);
        BusinessExceptionUtils.isTrue(!checkNameRepeat(request), "该品类名已存在");
        AssetCategoryModel parent = getParentCategory(assetCategoryModel);
        checkParentCategory(parent);
        assetCategoryModel.setAssetType(parent.getAssetType());
        assetCategoryModel.setGmtCreate(System.currentTimeMillis());
        assetCategoryModel.setStatus(EXISTENCE_STATE);
        // 新增的均为非系统内置的
        assetCategoryModel.setIsDefault(NOT_SYSTEM_DEFAULT);
        Integer result = assetCategoryModelDao.insert(assetCategoryModel);
        if (!Objects.equals(0, result)) {
            // 写入业务日志
            LogHandle.log(assetCategoryModel.toString(), AssetEventEnum.ASSET_CATEGORY_INSERT.getName(),
                AssetEventEnum.ASSET_CATEGORY_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
            LogUtils
                .info(logger, AssetEventEnum.ASSET_CATEGORY_INSERT.getName() + " {}", assetCategoryModel.toString());
        }
        return ActionResponse.success(aesEncoder.encode(assetCategoryModel.getStringId(), LoginUserUtil.getLoginUser()
            .getUsername()));

    }

    /**
     * 设置资产类型与父品类的资产类型一致
     * @param assetCategoryModel
     * @return
     */
    private AssetCategoryModel getParentCategory(AssetCategoryModel assetCategoryModel) throws Exception {
        String parentId = assetCategoryModel.getParentId();
        AssetCategoryModel parent = assetCategoryModelDao.getById(Integer.parseInt(parentId));

        return parent;
    }

    private void checkParentCategory(AssetCategoryModel parent) {
        BusinessExceptionUtils.isNull(parent, "父类型不存在");
        BusinessExceptionUtils.isTrue(!parent.getName().equals(ROOT_CATEGORY), "不能在第一，二级新增节点");
        BusinessExceptionUtils.isTrue(!parent.getName().equals(FIRST_LEVEL_ASSET_CATEGORY), "不能在第一，二级新增节点");
        BusinessExceptionUtils.isTrue(!parent.getName().equals(FIRST_LEVEL_SOFTWARE_CATEGORY), "不能在第一，二级新增节点");
    }

    @Override
    public ActionResponse updateAssetCategoryModel(AssetCategoryModelRequest request) throws Exception {
        AssetCategoryModel updateCategory = categoryRequestConvert.convert(request, AssetCategoryModel.class);
        BusinessExceptionUtils.isTrue(!request.getStringId().equals(request.getParentId()), "上级品类不能为自身");
        updateCategory.setId(DataTypeUtils.stringToInteger(request.getStringId()));
        BusinessExceptionUtils.isTrue(!checkNameRepeat(request), "该品类名已存在");
        AssetCategoryModel assetCategoryModelById = assetCategoryModelDao.getById(updateCategory.getId());
        // 判断是不是系统内置
        BusinessExceptionUtils.isTrue(checkIsDefault(assetCategoryModelById), "系统内置品类不能更新或删除");
        updateCategory.setStatus(EXISTENCE_STATE);
        BusinessExceptionUtils.isTrue(!checkParentType(updateCategory, assetCategoryModelById), "软硬件类型不同时不能更新");
        Integer result = assetCategoryModelDao.update(updateCategory);
        if (!Objects.equals(0, result)) {
            // 写入业务日志
            LogHandle.log(updateCategory.toString(), AssetEventEnum.ASSET_CATEGORY_UPDATE.getName(),
                AssetEventEnum.ASSET_CATEGORY_UPDATE.getStatus(), ModuleEnum.ASSET.getCode());
            LogUtils.info(logger, AssetEventEnum.ASSET_CATEGORY_UPDATE.getName() + " {}", updateCategory.toString());
        }
        return ActionResponse.success(result);
    }

    private boolean checkParentType(AssetCategoryModel updateCategory, AssetCategoryModel assetCategoryModelById)
                                                                                                                 throws Exception {
        AssetCategoryModel parent = getParentCategory(updateCategory);
        checkParentCategory(parent);
        if (!parent.getAssetType().equals(assetCategoryModelById.getAssetType())) {
            return true;
        }
        updateCategory.setAssetType(null);
        return false;
    }

    /**
     * 判断是否是自定义的品类
     * @param assetCategoryModel
     * @return
     */
    private Boolean checkIsDefault(AssetCategoryModel assetCategoryModel) {
        BusinessExceptionUtils.isNull(assetCategoryModel, "该类型不存在");
        if (assetCategoryModel.getIsDefault() != null) {
            return Objects.equals(assetCategoryModel.getIsDefault(), NOT_SYSTEM_DEFAULT);
        }
        return false;
    }

    @Override
    public List<AssetCategoryModelResponse> findListAssetCategoryModel(AssetCategoryModelQuery query) throws Exception {
        List<AssetCategoryModel> assetCategoryModel = assetCategoryModelDao.findListAssetCategoryModel(query);
        return responseConverter.convert(assetCategoryModel, AssetCategoryModelResponse.class);
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

    private boolean checkNameRepeat(AssetCategoryModelRequest request) throws Exception {
        if (Objects.nonNull(request.getName())) {
            AssetCategoryModelQuery assetDepartmentQuery = new AssetCategoryModelQuery();
            assetDepartmentQuery.setName(request.getName());
            return assetCategoryModelDao.findRepeatName(
                request.getStringId() == null ? null : DataTypeUtils.stringToInteger(request.getStringId()),
                request.getName()) >= 1;
        }
        return false;
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
        BusinessExceptionUtils.isTrue(checkIsDefault(assetCategoryModel),"系统内置品类不能更新或删除");
        return deleteAllById(id);
    }

    @Override
    public AssetCategoryModelNodeResponse queryCategoryNode() throws Exception {
        AssetCategoryModelQuery query = new AssetCategoryModelQuery();
        query.setPageSize(-1);
        List<AssetCategoryModel> assetCategoryModels = assetCategoryModelDao.findListAssetCategoryModel(query);
        NodeUtilsConverter<AssetCategoryModel, AssetCategoryModelNodeResponse> nodeConverter = new NodeUtilsConverter<>();
        List<AssetCategoryModelNodeResponse> assetDepartmentNodeResponses = nodeConverter.columnToNode(
            assetCategoryModels, AssetCategoryModelNodeResponse.class);
        return CollectionUtils.isNotEmpty(assetDepartmentNodeResponses) ? assetDepartmentNodeResponses.get(0) : null;
    }

    @Override
    public List<AssetCategoryModelResponse> getCategoryByName(String name) throws Exception {
        return responseConverter.convert(assetCategoryModelDao.getNextLevelCategoryByName(name),
            AssetCategoryModelResponse.class);
    }

    @Override
    public List<AssetCategoryModelResponse> findAssetCategoryModelById(Integer id) throws Exception {
        return responseConverter.convert(recursionSearch(assetCategoryModelDao.getAll(), id),
            AssetCategoryModelResponse.class);
    }

    @Override
    public List<Integer> findAssetCategoryModelIdsById(Integer id) throws Exception {
        List<AssetCategoryModelResponse> categoryModelResponses = responseConverter.convert(
            recursionSearch(assetCategoryModelDao.getAll(), id), AssetCategoryModelResponse.class);
        List<Integer> categoryModels = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(categoryModelResponses)) {
            categoryModelResponses.stream().forEach(assetCategoryModelResponse -> {
                categoryModels.add(DataTypeUtils.stringToInteger(assetCategoryModelResponse.getStringId()));
            });
        }
        return categoryModels;
    }

    /**
     * 删除品类及其子品类,若存在资产则不能删（进行递归）
     * @return ActionResponse
     */
    public ActionResponse deleteAllById(Serializable id) throws Exception {
        // 查询出所有品类及其子品类
        List<AssetCategoryModel> list = recursionSearch(assetCategoryModelDao.getAll(), (Integer) id);
        AssetQuery assetQuery = new AssetQuery();
        // 获取品类id数组
        String[] ids = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            ids[i] = Objects.toString(list.get(i).getId());
        }
        assetQuery.setCategoryModels(ids);
        if (checkExistAsset(assetQuery)) {
            return ActionResponse.fail(RespBasicCode.BUSSINESS_EXCETION, "存在资产，不能删除");
        }
        if (CollectionUtils.isNotEmpty(list)) {
            // 删除品类及其子品类
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

    private boolean checkExistAsset(AssetQuery assetQuery) throws Exception {
        Integer i = assetDao.findCountByCategoryModel(assetQuery);
        // 存在资产
        return i > 0;
    }

    /**
     * 递归查询出所有的品类和其子品类
     *
     * @param id 查询的部门id
     */
    @Override
    public List<AssetCategoryModel> recursionSearch(List<AssetCategoryModel> list, Integer id) throws Exception {
        List<AssetCategoryModel> result = new ArrayList<>();
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
