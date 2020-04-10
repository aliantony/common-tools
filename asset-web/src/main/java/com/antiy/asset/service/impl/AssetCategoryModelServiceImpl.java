package com.antiy.asset.service.impl;

import static com.antiy.biz.file.FileHelper.logger;

import java.io.Serializable;
import java.util.*;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.antiy.asset.cache.AssetBaseDataCache;
import com.antiy.asset.convert.CategoryRequestConvert;
import com.antiy.asset.dao.AssetCategoryModelDao;
import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.entity.AssetCategoryModel;
import com.antiy.asset.service.IAssetCategoryModelService;
import com.antiy.asset.util.Constants;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.util.NodeUtilsConverter;
import com.antiy.asset.vo.enums.AssetEventEnum;
import com.antiy.asset.vo.query.AssetCategoryModelQuery;
import com.antiy.asset.vo.query.AssetQuery;
import com.antiy.asset.vo.request.AssetCategoryModelRequest;
import com.antiy.asset.vo.response.AssetCategoryModelNodeResponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.BusinessData;
import com.antiy.common.encoder.AesEncoder;
import com.antiy.common.enums.BusinessModuleEnum;
import com.antiy.common.enums.BusinessPhaseEnum;
import com.antiy.common.utils.BusinessExceptionUtils;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;

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
    private AssetCategoryModelDao       assetCategoryModelDao;
    @Resource
    private AssetDao                    assetDao;
    @Resource
    private CategoryRequestConvert      requestConverter;
    @Resource
    private CategoryRequestConvert      categoryRequestConvert;
    @Resource
    private AesEncoder                  aesEncoder;
    private static Map<String, Integer> parentMap = new HashMap<>();
    @Resource
    private AssetBaseDataCache          assetBaseDataCache;
    static {
        parentMap.put("0", 0);
    }

    /**
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public ActionResponse saveAssetCategoryModel(AssetCategoryModelRequest request) throws Exception {
        AssetCategoryModel assetCategoryModel = requestConverter.convert(request, AssetCategoryModel.class);
        request.setStringId(null);
        BusinessExceptionUtils.isTrue(!checkNameRepeat(request), "该品类名已存在");
        AssetCategoryModel parent = getParentCategory(assetCategoryModel);
        checkParentCategory(parent);

        assetCategoryModel.setAssetType(parent.getAssetType());
        assetCategoryModel.setGmtCreate(System.currentTimeMillis());
        assetCategoryModel.setStatus(Constants.EXISTENCE_STATUS);
        assetCategoryModel.setType(parent.getType());
        // 新增的均为非系统内置的
        assetCategoryModel.setIsDefault(Constants.NOT_SYSTEM_DEFAULT_CATEGORY);

        Integer result = assetCategoryModelDao.insert(assetCategoryModel);
        // 更新缓存
        assetBaseDataCache.put(AssetBaseDataCache.ASSET_CATEGORY_MODEL, assetCategoryModel);
        if (!Objects.equals(0, result)) {
            // 写入业务日志
            // LogHandle.log(assetCategoryModel.toString(), AssetEventEnum.ASSET_CATEGORY_INSERT.getName(),
            // AssetEventEnum.ASSET_CATEGORY_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
            // 记录操作日志和运行日志
            LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_CATEGORY_INSERT.getName(),
                assetCategoryModel.getId(), assetCategoryModel.getName(), assetCategoryModel,
                BusinessModuleEnum.PRODUCT_TYPE_NUMBER, BusinessPhaseEnum.NONE));
            LogUtils.info(logger, AssetEventEnum.ASSET_CATEGORY_INSERT.getName() + " {}",
                assetCategoryModel.toString());
        }
        return ActionResponse
            .success(aesEncoder.encode(assetCategoryModel.getStringId(), LoginUserUtil.getLoginUser().getUsername()));

    }

    /**
     * 获取父品类
     *
     * @param assetCategoryModel
     * @return
     */
    private AssetCategoryModel getParentCategory(AssetCategoryModel assetCategoryModel) throws Exception {
        String parentId = assetCategoryModel.getParentId();
        AssetCategoryModel parent = assetCategoryModelDao.getById(Integer.parseInt(parentId));

        return parent;
    }

    /**
     * 判断父品类是否满足新增条件
     *
     * @param parent
     */
    private void checkParentCategory(AssetCategoryModel parent) throws Exception {
        BusinessExceptionUtils.isNull(parent, "父类型不存在");
        BusinessExceptionUtils.isTrue(!parent.getName().equals(Constants.ASSET_CATEGORY), "不能在第一级新增节点");
        int levelCount = 1;
        while (levelCount < 4) {
            if ((parent = getParentCategory(parent)) == null) {
                break;
            }
            levelCount++;
        }
        // 包括资产类型，最大为4级
        BusinessExceptionUtils.isTrue(levelCount < 4 && levelCount >= 2, "资产类型限制为3级");
    }

    @Override
    @Transactional
    public ActionResponse updateAssetCategoryModel(AssetCategoryModelRequest request) throws Exception {
        AssetCategoryModel updateCategory = categoryRequestConvert.convert(request, AssetCategoryModel.class);
        BusinessExceptionUtils.isTrue(!request.getStringId().equals(request.getParentId()), "上级类型不能为自身");
        BusinessExceptionUtils.isTrue(!checkNameRepeat(request), "该类型名已存在");
        updateCategory.setId(DataTypeUtils.stringToInteger(request.getStringId()));
        AssetCategoryModel assetCategoryModelById = assetCategoryModelDao.getById(updateCategory.getId());
        // 判断是不是系统内置
        BusinessExceptionUtils.isTrue(checkIsDefault(assetCategoryModelById), "系统内置品类不能更新或删除");
        updateCategory.setModifyUser(LoginUserUtil.getLoginUser().getId());
        updateCategory.setGmtModified(System.currentTimeMillis());
        Integer result = assetCategoryModelDao.update(updateCategory);
        // 更新缓存
        assetBaseDataCache.update(AssetBaseDataCache.ASSET_CATEGORY_MODEL, updateCategory);
        if (!Objects.equals(0, result)) {
            // 写入业务日志
            // LogHandle.log(updateCategory.toString(), AssetEventEnum.ASSET_CATEGORY_UPDATE.getName(),
            // AssetEventEnum.ASSET_CATEGORY_UPDATE.getStatus(), ModuleEnum.ASSET.getCode());
            // 记录操作日志和运行日志
            LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_CATEGORY_UPDATE.getName(),
                updateCategory.getId(), updateCategory.getName(), updateCategory,
                BusinessModuleEnum.PRODUCT_TYPE_NUMBER, BusinessPhaseEnum.NONE));
            LogUtils.info(logger, AssetEventEnum.ASSET_CATEGORY_UPDATE.getName() + " {}", updateCategory.toString());
        }
        return ActionResponse.success(result);
    }

    /**
     * 判断是否是自定义的品类
     *
     * @param assetCategoryModel
     * @return
     */
    private Boolean checkIsDefault(AssetCategoryModel assetCategoryModel) {
        BusinessExceptionUtils.isNull(assetCategoryModel, "该类型不存在");
        if (assetCategoryModel.getIsDefault() != null) {
            return Objects.equals(assetCategoryModel.getIsDefault(), Constants.NOT_SYSTEM_DEFAULT_CATEGORY);
        }
        return false;
    }

    /**
     * 判断是否重名
     *
     * @param request
     * @return
     */
    private boolean checkNameRepeat(AssetCategoryModelRequest request) throws Exception {
        if (Objects.nonNull(request.getName())) {
            AssetCategoryModelQuery assetCategoryModelQuery = new AssetCategoryModelQuery();
            assetCategoryModelQuery.setName(request.getName());
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
    @Transactional
    public ActionResponse delete(Serializable id) throws Exception {
        AssetCategoryModel assetCategoryModel = assetCategoryModelDao.getById(id);
        // 判断是否自定义品类
        BusinessExceptionUtils.isTrue(checkIsDefault(assetCategoryModel), "系统内置品类不能更新或删除");
        // 写入业务日志
        // LogHandle.log(assetCategoryModel.toString(), AssetEventEnum.ASSET_CATEGORY_DELETE.getName(),
        // AssetEventEnum.ASSET_CATEGORY_DELETE.getStatus(), ModuleEnum.ASSET.getCode());
        // 记录操作日志和运行日志
        LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_CATEGORY_DELETE.getName(),
            assetCategoryModel.getId(), assetCategoryModel.getName(), assetCategoryModel,
            BusinessModuleEnum.PRODUCT_TYPE_NUMBER, BusinessPhaseEnum.NONE));
        LogUtils.info(logger, AssetEventEnum.ASSET_CATEGORY_DELETE.getName() + " {}", assetCategoryModel.toString());
        return deleteAllById(id);
    }

    /**
     * 通过类型查询品类树
     *
     * @return
     */
    @Override
    public AssetCategoryModelNodeResponse queryCategoryNodeCount() throws Exception {
        List<AssetCategoryModel> categoryCount = assetCategoryModelDao.findAllCategoryCount();
        AssetCategoryModelNodeResponse categoryModelNodeResponses = getNextNodeResponse(categoryCount);
        // 加密数据
        String userName = LoginUserUtil.getLoginUser().getUsername();
        aesEncode(categoryModelNodeResponses, userName);
        return categoryModelNodeResponses;
    }

    @Override
    public List<AssetCategoryModelNodeResponse> queryCategoryWithOutRootNode(boolean sourceOfLend) throws Exception {
        List<AssetCategoryModel> models = assetCategoryModelDao.findAllCategory(sourceOfLend);
        NodeUtilsConverter<AssetCategoryModel, AssetCategoryModelNodeResponse> nodeConverter = new NodeUtilsConverter<>();
        List<AssetCategoryModelNodeResponse> nodeResponses = nodeConverter
                .columnToNode(models, AssetCategoryModelNodeResponse.class);
        if (CollectionUtils.isEmpty(nodeResponses)) {
            return Collections.EMPTY_LIST;
        }
        //去掉根节点
        List<AssetCategoryModelNodeResponse> nodes = nodeResponses.get(0).getChildrenNode();
        //如果来源是出借管理，需要过滤无下挂资产的类型
        if (BooleanUtils.isTrue(sourceOfLend)) {

            List<AssetCategoryModelNodeResponse> result = new ArrayList<>();
//            for (AssetCategoryModelNodeResponse node : nodes) {
//                AssetCategoryModelNodeResponse topNode = new AssetCategoryModelNodeResponse();
//                BeanUtils.copyProperties(node,topNode,"childrenNode");
//                findTreeWithAssets(node.getChildrenNode(), null, node, topNode, result,true);
//            }
            findTreeWithAssets(nodes, null, null, null, result, true);

            nodes = result;

        }
        return nodes;
    }

    private void findTreeWithAssets(List<AssetCategoryModelNodeResponse> nodes, List<AssetCategoryModelNodeResponse> currentChilds
            , AssetCategoryModelNodeResponse currentNode, AssetCategoryModelNodeResponse topNode, List<AssetCategoryModelNodeResponse> result, boolean isFirstIn) {
        if (CollectionUtils.isNotEmpty(nodes)) {
            int childSize = 0;
            if (BooleanUtils.isFalse(isFirstIn)) {
                childSize = nodes.size();
            }
            for (AssetCategoryModelNodeResponse node : nodes) {
                if (BooleanUtils.isTrue(isFirstIn)) {
                    currentNode = node;
                    topNode = currentNode;
                } else {
                    boolean hasAsset = node.getCount() >= 1;
                    if (BooleanUtils.isTrue(hasAsset)) {
                        currentChilds = Objects.isNull(currentChilds) ? new ArrayList<>() : currentChilds;
                        currentChilds.add(node);
                        currentNode.setChildrenNode(currentChilds);
                    } else {
                        childSize--;
                    }
//                    AssetCategoryModelNodeResponse parent = currentNode;
//                    parent.setChildrenNode(currentChilds);
                    currentNode = node;
                }
                findTreeWithAssets(currentNode.getChildrenNode(), null, currentNode, topNode, result, false);
            }
            if (childSize <= 0) {
                currentNode.setChildrenNode(null);
            }
        }
        if (Objects.isNull(result)) {
            result = new ArrayList<>();
        }
        if ((topNode.getCount() >= 1 || CollectionUtils.isNotEmpty(topNode.getChildrenNode()))
                && !result.contains(topNode)) {
            result.add(topNode);
        }

    }

    private void aesEncode(AssetCategoryModelNodeResponse nodeResponse, String userName) {

        if (nodeResponse != null && CollectionUtils.isNotEmpty(nodeResponse.getChildrenNode())) {
            List<AssetCategoryModelNodeResponse> childrenNodeList = nodeResponse.getChildrenNode();
            childrenNodeList.forEach(e -> {
                e.setStringId(aesEncoder.encode(e.getStringId(), userName));
                e.setParentId(aesEncoder.encode(e.getParentId(), userName));
                if (CollectionUtils.isNotEmpty(e.getChildrenNode())) {
                    aesEncode(e, userName);
                }
            });
        }

    }

    private AssetCategoryModelNodeResponse getNextNodeResponse(List<AssetCategoryModel> softWareCategoryCount) throws Exception {
        return getAssetCategoryModelNodeResponse(softWareCategoryCount);
    }

    private AssetCategoryModelNodeResponse getAssetCategoryModelNodeResponse(List<AssetCategoryModel> assetCategoryModels) throws Exception {
        NodeUtilsConverter<AssetCategoryModel, AssetCategoryModelNodeResponse> nodeConverter = new NodeUtilsConverter<>();
        List<AssetCategoryModelNodeResponse> assetDepartmentNodeResponses = nodeConverter
            .columnToNode(assetCategoryModels, AssetCategoryModelNodeResponse.class);
        // 处理层级和权限
        dealLevel(assetDepartmentNodeResponses, assetCategoryModels);
        return CollectionUtils.isNotEmpty(assetDepartmentNodeResponses) ? assetDepartmentNodeResponses.get(0) : null;
    }

    private void dealLevel(List<AssetCategoryModelNodeResponse> assetCategoryModelNodeResponses,
                           List<AssetCategoryModel> assetCategoryModels) throws Exception {
        if (CollectionUtils.isNotEmpty(assetCategoryModelNodeResponses)) {
            for (AssetCategoryModelNodeResponse assetCategoryModelNodeResponse : assetCategoryModelNodeResponses) {
                if (!parentMap.containsKey(assetCategoryModelNodeResponse.getParentId())) {
                    assetCategoryModelNodeResponse.setLevelType(1);
                    parentMap.put(assetCategoryModelNodeResponse.getStringId(), 1);
                } else {
                    parentMap.put(assetCategoryModelNodeResponse.getStringId(),
                        parentMap.get(assetCategoryModelNodeResponse.getParentId()) + 1);
                    assetCategoryModelNodeResponse
                        .setLevelType(parentMap.get(assetCategoryModelNodeResponse.getParentId()) + 1);
                }
                // 删除节点权限
                boolean deleteAuthority = false;
                // 添加子节点权限
                boolean addAuthority = true;
                // 修改节点权限
                boolean changeAuthority = false;
                // 用户自定义类型
                int defaultNumber = 1;
                if (assetCategoryModelNodeResponse.getIsDefault().equals(defaultNumber)) {
                    deleteAuthority = true;
                    changeAuthority = true;
                    // 通过判断该节点下是否有资产来设置删除权限
                    if (assetCategoryModelNodeResponse.getCount() != null
                        && assetCategoryModelNodeResponse.getCount() > 0) {
                        deleteAuthority = false;
                    } else if (CollectionUtils.isNotEmpty(assetCategoryModelNodeResponse.getChildrenNode())) {
                        List<AssetCategoryModel> categoryModels = recursionSearch(assetCategoryModels,
                            DataTypeUtils.stringToInteger(assetCategoryModelNodeResponse.getStringId()));
                        for (AssetCategoryModel categoryModel : categoryModels) {
                            if (categoryModel.getCount() != null && categoryModel.getCount() > 0) {
                                deleteAuthority = false;
                                break;
                            }
                        }
                    }

                }
                if (assetCategoryModelNodeResponse.getLevelType() >= 4
                    || assetCategoryModelNodeResponse.getLevelType() <= 1) {
                    addAuthority = false;
                }

                assetCategoryModelNodeResponse.setAddOnly(addAuthority);
                assetCategoryModelNodeResponse.setChangeOnly(changeAuthority);
                assetCategoryModelNodeResponse.setDeleteOnly(deleteAuthority);
                dealLevel(assetCategoryModelNodeResponse.getChildrenNode(), assetCategoryModels);
            }
        }
    }

    /**
     * 删除品类及其子品类,若存在资产则不能删（进行递归）
     *
     * @return ActionResponse
     */
    public ActionResponse deleteAllById(Serializable id) throws Exception {
        // 查询出所有品类及其子品类
        List<AssetCategoryModel> list = recursionSearch(assetCategoryModelDao.getAll(), (Integer) id);
        AssetQuery assetQuery = new AssetQuery();
        // 获取品类id数组
        String[] ids = getIdList(list);

        assetQuery.setCategoryModels(DataTypeUtils.stringArrayToIntegerArray(ids));
        BusinessExceptionUtils.isTrue(!checkExistAsset(assetQuery), "存在资产，不能删除");
        // AssetSoftwareQuery assetSoftwareQuery = new AssetSoftwareQuery();
        // assetSoftwareQuery.setCategoryModels(ids);
        // BusinessExceptionUtils.isTrue(!checkExistSoftware(assetSoftwareQuery), "存在资产，不能删除");
        BusinessExceptionUtils.isEmpty(list, "品类不存在，删除失败");
        // 删除品类及其子品类
        Integer result = assetCategoryModelDao.delete(list);
        return ActionResponse.success(result);
    }

    private String[] getIdList(List<AssetCategoryModel> list) {
        String[] ids = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            ids[i] = Objects.toString(list.get(i).getId());
        }
        return ids;
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
