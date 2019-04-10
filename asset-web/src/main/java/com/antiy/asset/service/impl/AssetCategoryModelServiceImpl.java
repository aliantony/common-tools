package com.antiy.asset.service.impl;

import com.antiy.asset.convert.CategoryRequestConvert;
import com.antiy.asset.dao.AssetCategoryModelDao;
import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.entity.AssetCategoryModel;
import com.antiy.asset.service.IAssetCategoryModelService;
import com.antiy.asset.util.Constants;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.util.LogHandle;
import com.antiy.asset.util.NodeUtilsConverter;
import com.antiy.asset.vo.enums.AssetEventEnum;
import com.antiy.asset.vo.enums.AssetSecondCategoryEnum;
import com.antiy.asset.vo.query.AssetCategoryModelQuery;
import com.antiy.asset.vo.query.AssetQuery;
import com.antiy.asset.vo.request.AssetCategoryModelRequest;
import com.antiy.asset.vo.response.AssetCategoryModelNodeResponse;
import com.antiy.asset.vo.response.AssetCategoryModelResponse;
import com.antiy.common.base.*;
import com.antiy.common.encoder.AesEncoder;
import com.antiy.common.enums.BusinessModuleEnum;
import com.antiy.common.enums.BusinessPhaseEnum;
import com.antiy.common.enums.ModuleEnum;
import com.antiy.common.utils.BusinessExceptionUtils;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;
import com.antiy.common.utils.ParamterExceptionUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.*;

import static com.antiy.biz.file.FileHelper.logger;

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

    /**
     *
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
        // 新增的均为非系统内置的
        assetCategoryModel.setIsDefault(Constants.NOT_SYSTEM_DEFAULT_CATEGORY);
        Integer result = assetCategoryModelDao.insert(assetCategoryModel);
        if (!Objects.equals(0, result)) {
            // 写入业务日志
            LogHandle.log(assetCategoryModel.toString(), AssetEventEnum.ASSET_CATEGORY_INSERT.getName(),
                AssetEventEnum.ASSET_CATEGORY_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
            // 记录操作日志和运行日志
            LogUtils.recordOperLog(
                new BusinessData(AssetEventEnum.ASSET_CATEGORY_INSERT.getName(), result, assetCategoryModel.getName(),
                    assetCategoryModel, BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.NONE));
            LogUtils.info(logger, AssetEventEnum.ASSET_CATEGORY_INSERT.getName() + " {}",
                assetCategoryModel.toString());
        }
        return ActionResponse
            .success(aesEncoder.encode(assetCategoryModel.getStringId(), LoginUserUtil.getLoginUser().getUsername()));

    }

    /**
     * 获取父品类
     * @param assetCategoryModel
     * @return
     */
    private AssetCategoryModel getParentCategory(AssetCategoryModel assetCategoryModel) throws Exception {
        String parentId = assetCategoryModel.getParentId();
        AssetCategoryModel parent = assetCategoryModelDao.getById(Integer.parseInt(parentId));

        return parent;
    }

    /**
     * 判断父品类是否新增满足条件
     * @param parent
     */
    private void checkParentCategory(AssetCategoryModel parent) {
        BusinessExceptionUtils.isNull(parent, "父类型不存在");
        BusinessExceptionUtils.isTrue(!parent.getName().equals(Constants.ROOT_CATEGORY_NAME), "不能在第一，二级新增节点");
        BusinessExceptionUtils.isTrue(!parent.getName().equals(Constants.FIRST_LEVEL_ASSET_CATEGORY_NAME),
            "不能在第一，二级新增节点");
        BusinessExceptionUtils.isTrue(!parent.getName().equals(Constants.FIRST_LEVEL_SOFTWARE_CATEGORY_NAME),
            "不能在第一，二级新增节点");
    }

    @Override
    @Transactional
    public ActionResponse updateAssetCategoryModel(AssetCategoryModelRequest request) throws Exception {
        AssetCategoryModel updateCategory = categoryRequestConvert.convert(request, AssetCategoryModel.class);
        BusinessExceptionUtils.isTrue(!request.getStringId().equals(request.getParentId()), "上级品类不能为自身");
        BusinessExceptionUtils.isTrue(!checkNameRepeat(request), "该品类名已存在");
        updateCategory.setId(DataTypeUtils.stringToInteger(request.getStringId()));
        AssetCategoryModel assetCategoryModelById = assetCategoryModelDao.getById(updateCategory.getId());
        // 判断是不是系统内置
        BusinessExceptionUtils.isTrue(checkIsDefault(assetCategoryModelById), "系统内置品类不能更新或删除");
        updateCategory.setStatus(Constants.EXISTENCE_STATUS);
        BusinessExceptionUtils.isTrue(!checkParentType(updateCategory, assetCategoryModelById), "软硬件类型不同时不能更新");
        Integer result = assetCategoryModelDao.update(updateCategory);
        if (!Objects.equals(0, result)) {
            // 写入业务日志
            LogHandle.log(updateCategory.toString(), AssetEventEnum.ASSET_CATEGORY_UPDATE.getName(),
                AssetEventEnum.ASSET_CATEGORY_UPDATE.getStatus(), ModuleEnum.ASSET.getCode());
            // 记录操作日志和运行日志
            LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_CATEGORY_UPDATE.getName(), result,
                updateCategory.getName(), updateCategory, BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.NONE));
            LogUtils.info(logger, AssetEventEnum.ASSET_CATEGORY_UPDATE.getName() + " {}", updateCategory.toString());
        }
        return ActionResponse.success(result);
    }

    /**
     * 判断父品类的资产类型和子品类的资产类型是否一致
     */
    private boolean checkParentType(AssetCategoryModel updateCategory,
                                    AssetCategoryModel assetCategoryModelById) throws Exception {
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
            return Objects.equals(assetCategoryModel.getIsDefault(), Constants.NOT_SYSTEM_DEFAULT_CATEGORY);
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
    public PageResult<AssetCategoryModelResponse> findPageAssetCategoryModel(AssetCategoryModelQuery query) throws Exception {
        return new PageResult<>(query.getPageSize(), this.findCountAssetCategoryModel(query), query.getCurrentPage(),
            this.findListAssetCategoryModel(query));
    }

    /**
     * 判断是否是自定义的品类
     * @param request
     * @return 判断是否重名
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
        LogHandle.log(assetCategoryModel.toString(), AssetEventEnum.ASSET_CATEGORY_DELETE.getName(),
            AssetEventEnum.ASSET_CATEGORY_DELETE.getStatus(), ModuleEnum.ASSET.getCode());
        // 记录操作日志和运行日志
        LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_CATEGORY_UPDATE.getName(),
            assetCategoryModel.getId(), assetCategoryModel.getName(), assetCategoryModel, BusinessModuleEnum.HARD_ASSET,
            BusinessPhaseEnum.NONE));
        LogUtils.info(logger, AssetEventEnum.ASSET_CATEGORY_DELETE.getName() + " {}", assetCategoryModel.toString());
        return deleteAllById(id);
    }

    /**
     * 查询品类树
     * @return
     */
    @Override
    public AssetCategoryModelNodeResponse queryCategoryNode() throws Exception {
        AssetCategoryModelQuery query = new AssetCategoryModelQuery();
        query.setPageSize(Constants.ALL_PAGE);
        List<AssetCategoryModel> assetCategoryModels = assetCategoryModelDao.findListAssetCategoryModel(query);
        return getAssetCategoryModelNodeResponse(assetCategoryModels);
    }

    /**
     * 通过类型查询品类树
     * @param type 1--软件 2--硬件
     * @return
     */
    @Override
    public AssetCategoryModelNodeResponse queryCategoryNode(int type) throws Exception {
        AssetCategoryModelQuery query = new AssetCategoryModelQuery();
        query.setAssetType(type);
        query.setPageSize(Constants.ALL_PAGE);
        List<AssetCategoryModel> assetCategoryModels = assetCategoryModelDao.findListAssetCategoryModel(query);
        assetCategoryModels.add(getRootCategory());
        AssetCategoryModelNodeResponse assetCategoryModelNodeResponse = getAssetCategoryModelNodeResponse(
            assetCategoryModels);
        return assetCategoryModelNodeResponse == null ? null : assetCategoryModelNodeResponse.getChildrenNode().get(0);
    }

    /**
     * 查询二级品类组合树 若参数为4和5 则结果为硬件(根节点)-(计算设备+网络设备) 树
     * @param types 4-计算设备 5-网络设备 6-存储设备 7-安全设备 8-其他设备
     * @return
     */
    public AssetCategoryModelNodeResponse querySecondCategoryNode(String[] types,
                                                                  Map<String, String> initMap) throws Exception {
        checkParameterTypes(types);
        AssetCategoryModelQuery query = new AssetCategoryModelQuery();
        query.setPageSize(Constants.ALL_PAGE);
        List<AssetCategoryModel> assetCategoryModels = assetCategoryModelDao.findListAssetCategoryModel(query);
        AssetCategoryModelNodeResponse assetCategoryModelNodeResponse = getAssetCategoryModelNodeResponse(
            assetCategoryModels);
        if (assetCategoryModelNodeResponse != null) {
            List<AssetCategoryModelNodeResponse> nodeResponseList = new ArrayList<>();
            for (String type : types) {
                AssetCategoryModelNodeResponse secondCategoryModelNode = getSecondCategoryModelNodeResponse(
                    assetCategoryModelNodeResponse, type, initMap);
                nodeResponseList.add(secondCategoryModelNode);
            }
            for (AssetCategoryModelNodeResponse nodeResponse : assetCategoryModelNodeResponse.getChildrenNode()) {
                if (nodeResponse.getName().equals(Constants.FIRST_LEVEL_ASSET_CATEGORY_NAME)) {
                    assetCategoryModelNodeResponse = nodeResponse;
                }
            }
            assetCategoryModelNodeResponse.setChildrenNode(nodeResponseList);
            return assetCategoryModelNodeResponse;
        }
        return null;
    }

    /**
     * 获取计算设备和网络设备树
     * @return
     */
    public AssetCategoryModelNodeResponse queryComputeAndNetCategoryNode(Boolean searchNetworkDevice) throws Exception {
        Map<String, String> secondCategoryMap = this.getSecondCategoryMap();
        if (searchNetworkDevice == null || !searchNetworkDevice) {
            String[] category = new String[2];
            int i = 0;
            for (Map.Entry<String, String> entry : secondCategoryMap.entrySet()) {
                if (entry.getValue().equals(AssetSecondCategoryEnum.COMPUTE_DEVICE.getMsg())) {
                    category[i++] = entry.getKey();
                }
                if (entry.getValue().equals(AssetSecondCategoryEnum.NETWORK_DEVICE.getMsg())) {
                    category[i++] = entry.getKey();
                }
            }
            return querySecondCategoryNode(category, secondCategoryMap);
        } else {
            String[] category = new String[1];
            int i = 0;
            for (Map.Entry<String, String> entry : secondCategoryMap.entrySet()) {
                if (entry.getValue().equals(AssetSecondCategoryEnum.NETWORK_DEVICE.getMsg())) {
                    category[i++] = entry.getKey();
                }
            }
            return querySecondCategoryNode(category, secondCategoryMap);
        }
    }

    private void checkParameterTypes(String[] types) {
        ParamterExceptionUtils.isTrue(types.length <= 5, "参数错误");

        for (String type : types) {
            int itype = Integer.parseInt(type);
            ParamterExceptionUtils.isTrue(itype >= 4 && itype <= 8, "参数错误");
        }
    }

    /**
     * 获取二级品类型号 id和name的映射
     * @return
     * @throws Exception
     */
    public Map<String, String> getSecondCategoryMap() throws Exception {
        List<AssetCategoryModelResponse> secondList = getNextLevelCategoryByName(
            Constants.FIRST_LEVEL_ASSET_CATEGORY_NAME);
        Map<String, String> secondMap = new HashMap<>();
        for (AssetCategoryModelResponse secondResponse : secondList) {
            secondMap.put(secondResponse.getStringId(), secondResponse.getName());
        }
        return secondMap;
    }

    /**
     * 从整棵树中获取指定的二级品类树
     *
     * @param assetCategoryModelNodeResponse
     * @return
     */
    private AssetCategoryModelNodeResponse getSecondCategoryModelNodeResponse(AssetCategoryModelNodeResponse assetCategoryModelNodeResponse,
                                                                              String type,
                                                                              Map<String, String> initMap) {
        List<AssetCategoryModelNodeResponse> secondNodeList = assetCategoryModelNodeResponse.getChildrenNode();
        AssetCategoryModelNodeResponse hardwareCategory = getCategoryByNameFromList(secondNodeList, "硬件");
        return hardwareCategory == null ? null
            : getCategoryByNameFromList(hardwareCategory.getChildrenNode(), initMap.get(type));
    }

    /**
     * 根据名字查询AssetCategoryModelNodeResponseList中匹配的值,若不存在则返回Null
     *
     * @param assetCategoryModelNodeResponses
     * @param name
     * @return
     */
    private AssetCategoryModelNodeResponse getCategoryByNameFromList(List<AssetCategoryModelNodeResponse> assetCategoryModelNodeResponses,
                                                                     String name) {
        if (Objects.isNull(assetCategoryModelNodeResponses)) {
            return null;
        }
        for (AssetCategoryModelNodeResponse secondNode : assetCategoryModelNodeResponses) {
            if (secondNode.getName().equals(name)) {
                return secondNode;
            }
        }
        return null;
    }

    private AssetCategoryModelNodeResponse getAssetCategoryModelNodeResponse(List<AssetCategoryModel> assetCategoryModels) {
        NodeUtilsConverter<AssetCategoryModel, AssetCategoryModelNodeResponse> nodeConverter = new NodeUtilsConverter<>();
        List<AssetCategoryModelNodeResponse> assetDepartmentNodeResponses = nodeConverter
            .columnToNode(assetCategoryModels, AssetCategoryModelNodeResponse.class);
        return CollectionUtils.isNotEmpty(assetDepartmentNodeResponses) ? assetDepartmentNodeResponses.get(0) : null;
    }

    /**
     * 递归查询该品类所属的二级id
     * @param categoryId 品类id
     * @param all 查询的列表范围，默认为所有品类
     * @param secondCategorys 二级品类集合
     * @return 二级品类id
     */
    public String recursionSearchParentCategory(String categoryId, List<AssetCategoryModel> all,
                                                Set<String> secondCategorys) {
        int id = Integer.parseInt(categoryId);
        // 若id不符合要求
        if (id <= Constants.FIRST_LEVEL_ASSET_CATEGORY_ID) {
            return null;
        }
        if (secondCategorys.contains(categoryId)) {
            return categoryId;
        }
        for (AssetCategoryModel assetCategoryModel : all) {
            if (assetCategoryModel.getStringId().equals(categoryId)
                && !categoryId.equals(assetCategoryModel.getParentId())) {
                return recursionSearchParentCategory(assetCategoryModel.getParentId(), all, secondCategorys);
            }
        }
        return null;

    }

    /**
     * 获取根节点
     * @return
     * @throws Exception
     */
    private AssetCategoryModel getRootCategory() throws Exception {
        AssetCategoryModelQuery assetCategoryModelQuery = new AssetCategoryModelQuery();
        assetCategoryModelQuery.setName(Constants.ROOT_CATEGORY_NAME);
        return assetCategoryModelDao.findListAssetCategoryModel(assetCategoryModelQuery).get(0);
    }

    @Override
    public List<AssetCategoryModelResponse> getNextLevelCategoryByName(String name) throws Exception {
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
        List<AssetCategoryModelResponse> categoryModelResponses = responseConverter
            .convert(recursionSearch(assetCategoryModelDao.getAll(), id), AssetCategoryModelResponse.class);
        return getSonCategory(categoryModelResponses);
    }

    @Override
    public List<Integer> findAssetCategoryModelIdsById(Integer id,
                                                       List<AssetCategoryModel> assetCategoryModels) throws Exception {
        List<AssetCategoryModelResponse> categoryModelResponses = responseConverter
            .convert(recursionSearch(assetCategoryModels, id), AssetCategoryModelResponse.class);
        return getSonCategory(categoryModelResponses);
    }

    private List<Integer> getSonCategory(List<AssetCategoryModelResponse> categoryModelResponses) {
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
        String[] ids = getIdList(list);
        assetQuery.setCategoryModels(ids);
        BusinessExceptionUtils.isTrue(!checkExistAsset(assetQuery), "存在资产，不能删除");
        BusinessExceptionUtils.isEmpty(list, "品类不存在，删除失败");
        // 删除品类及其子品类
        Integer result = assetCategoryModelDao.delete(list);
        if (!Objects.equals(0, result)) {
            // 写入业务日志
            LogHandle.log(list.toString(), AssetEventEnum.ASSET_CATEGORY_DELETE.getName(),
                AssetEventEnum.ASSET_CATEGORY_DELETE.getStatus(), ModuleEnum.ASSET.getCode());
            // 记录操作日志和运行日志
            LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_CATEGORY_UPDATE.getName(), 0, "", assetQuery,
                BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.NONE));
            LogUtils.info(logger, AssetEventEnum.ASSET_CATEGORY_DELETE.getName() + " {}", list.toString());
        }
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

    /**
     * 获取品类型号中的列表id
     * @param search
     * @return
     */
    public List<String> getCategoryIdList(List<AssetCategoryModel> search) {
        List<String> list = new ArrayList<>();
        for (AssetCategoryModel assetCategoryModel : search) {
            list.add(assetCategoryModel.getStringId());
        }
        return list;
    }
}

@Component
class CategoryResponseConvert extends BaseConverter<AssetCategoryModel, AssetCategoryModelResponse> {
    @Override
    protected void convert(AssetCategoryModel assetCategoryModel,
                           AssetCategoryModelResponse assetCategoryModelResponse) {
        assetCategoryModelResponse.setParentId(Objects.toString(assetCategoryModel.getParentId()));
        super.convert(assetCategoryModel, assetCategoryModelResponse);
    }
}
