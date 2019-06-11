package com.antiy.asset.service.impl;

import java.util.*;

import javax.annotation.Resource;

import com.antiy.asset.dao.AssetCategoryModelDao;
import com.antiy.asset.intergration.OperatingSystemClient;
import com.antiy.asset.util.ArrayTypeUtil;
import com.antiy.asset.util.Constants;
import com.antiy.asset.util.StatusEnumUtil;
import com.antiy.asset.vo.response.*;
import com.antiy.common.encoder.AesEncoder;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.AssetLinkRelationDao;
import com.antiy.asset.dao.AssetTopologyDao;
import com.antiy.asset.entity.Asset;
import com.antiy.asset.entity.AssetCategoryModel;
import com.antiy.asset.entity.AssetGroup;
import com.antiy.asset.entity.IdCount;
import com.antiy.asset.intergration.EmergencyClient;
import com.antiy.asset.service.IAssetCategoryModelService;
import com.antiy.asset.service.IAssetTopologyService;
import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.asset.vo.enums.InstallType;
import com.antiy.asset.vo.query.AssetQuery;
import com.antiy.asset.vo.query.AssetTopologyQuery;
import com.antiy.biz.util.RedisKeyUtil;
import com.antiy.biz.util.RedisUtil;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.ObjectQuery;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.SysArea;
import com.antiy.common.enums.ModuleEnum;
import com.antiy.common.utils.DataTypeUtils;
import com.antiy.common.utils.LoginUserUtil;

/**
 * 资产拓扑管理
 *
 * @author zhangxin
 * @date 2019/4/23 11:21
 */
@Service
public class AssetTopologyServiceImpl implements IAssetTopologyService {

    @Resource
    private AssetLinkRelationDao                assetLinkRelationDao;
    @Resource
    private AssetDao                            assetDao;
    @Resource
    private IAssetCategoryModelService          iAssetCategoryModelService;
    @Resource
    private AssetTopologyDao                    assetTopologyDao;
    @Resource
    private EmergencyClient                     emergencyClient;
    @Resource
    private BaseConverter<Asset, AssetResponse> converter;
    @Resource
    private RedisUtil                           redisUtil;
    @Resource
    private AssetCategoryModelDao               assetCategoryModelDao;
    @Resource
    private AesEncoder                          aesEncoder;
    @Resource
    private OperatingSystemClient               operatingSystemClient;

    @Override
    public List<String> queryCategoryModels() {
        return assetLinkRelationDao.queryCategoryModes();
    }

    @Override
    public AssetNodeInfoResponse queryAssetNodeInfo(String assetId) {
        AssetNodeInfoResponse assetNodeInfoResponse = assetLinkRelationDao.queryAssetNodeInfo(assetId);
        assetNodeInfoResponse
            .setInstallTypeName(InstallType.getInstallTypeByCode(assetNodeInfoResponse.getInstallType()).getStatus());
        return assetNodeInfoResponse;
    }

    @Override
    public Map<String, Integer> countAssetTopology() throws Exception {
        Map<String, Integer> resultMap = new HashMap<>(2);
        // 1.查询资产总数
        AssetQuery assetQuery = new AssetQuery();
        assetQuery.setAreaIds(
            DataTypeUtils.integerArrayToStringArray(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser()));
        // 1.1资产状态为已入网和待退役
        List<Integer> assetStatusList = new ArrayList<>(2);
        assetStatusList.add(AssetStatusEnum.NET_IN.getCode());
        assetStatusList.add(AssetStatusEnum.WAIT_RETIRE.getCode());
        assetQuery.setAssetStatusList(assetStatusList);
        // 1.2资产品类型号为计算设备和网络设备
        List<AssetCategoryModel> categoryModelList = iAssetCategoryModelService.getAll();
        List<Integer> categoryIdList = iAssetCategoryModelService.findAssetCategoryModelIdsById(4, categoryModelList);
        categoryIdList.addAll(iAssetCategoryModelService.findAssetCategoryModelIdsById(5, categoryModelList));
        assetQuery.setCategoryModels(DataTypeUtils.integerArrayToStringArray(categoryIdList));
        resultMap.put("totalNum", assetDao.findCountByCategoryModel(assetQuery));
        // 2.查询已管控的数量(已建立关联关系的资产数量)
        AssetTopologyQuery assetTopologyQuery = new AssetTopologyQuery();
        assetTopologyQuery.setUserAreaIds(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser());
        resultMap.put("inControlNum", assetLinkRelationDao.countAssetTopology(assetTopologyQuery));
        return resultMap;
    }

    @Override
    public List<SelectResponse> queryGroupList() {
        AssetTopologyQuery assetTopologyQuery = new AssetTopologyQuery();
        assetTopologyQuery.setUserAreaIds(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser());
        List<AssetGroup> assetGroupList = assetLinkRelationDao.queryGroupList(assetTopologyQuery);
        List<SelectResponse> selectResponseList = new ArrayList<>(assetGroupList.size());
        assetGroupList.stream().forEach(e -> {
            SelectResponse selectResponse = new SelectResponse();
            selectResponse.setValue(e.getName());
            selectResponse.setId(e.getStringId());
            selectResponseList.add(selectResponse);
        });
        return selectResponseList;
    }

    @Override
    public PageResult<AssetResponse> getTopologyList(AssetQuery query) throws Exception {
        if (query.getAreaIds() == null) {
            query.setAreaIds(
                DataTypeUtils.integerArrayToStringArray(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser()));
        }
        Integer count = assetTopologyDao.findTopologyListAssetCount(query);
        if (count != null && count > 0) {
            List<Asset> assetList = assetTopologyDao.findTopologyListAsset(query);
            List<AssetResponse> assetResponseList = converter.convert(assetList, AssetResponse.class);
            ObjectQuery objectQuery = new ObjectQuery();
            objectQuery.setPageSize(-1);
            PageResult<IdCount> idCountPageResult = emergencyClient.queryInvokeEmergencyCount(objectQuery);
            List<IdCount> idCounts = idCountPageResult.getItems();
            setListAreaName(assetResponseList);
            setAlarmCount(assetResponseList, idCounts);
            assetResponseList.sort(Comparator.comparingInt(o -> -Integer.valueOf(o.getAlarmCount())));
            return new PageResult<>(query.getPageSize(), count, query.getCurrentPage(), assetResponseList);
        }
        return null;
    }

    private void setListAreaName(List<AssetResponse> assetResponseList) throws Exception {
        for (AssetResponse assetResponse : assetResponseList) {
            setAreaName(assetResponse);
        }
    }

    public TopologyCategoryCountResponse countTopologyCategory() throws Exception {
        List<Integer> areaIds = LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser();
        // 查询第二级分类id
        List<AssetCategoryModel> secondCategoryModelList = assetCategoryModelDao.getNextLevelCategoryByName("硬件");
        List<AssetCategoryModel> categoryModelDaoAll = assetCategoryModelDao.getAll();
        if (CollectionUtils.isNotEmpty(secondCategoryModelList)) {
            TopologyCategoryCountResponse topologyCategoryCountResponse = new TopologyCategoryCountResponse();
            List<TopologyCategoryCountResponse.CategoryResponse> categoryResponseList = new ArrayList<>();
            for (AssetCategoryModel secondCategoryModel : secondCategoryModelList) {
                // 查询第二级每个分类下所有的分类id，并添加至list集合
                List<AssetCategoryModel> search = iAssetCategoryModelService.recursionSearch(categoryModelDaoAll,
                    secondCategoryModel.getId());
                // 设置查询资产条件参数，包括区域id，状态，资产品类型号
                AssetQuery assetQuery = setAssetQueryParam(areaIds, search, null);
                // 将查询结果放置结果集
                TopologyCategoryCountResponse.CategoryResponse categoryResponse = topologyCategoryCountResponse.new CategoryResponse();
                categoryResponse.setAsset_name(secondCategoryModel.getName());
                categoryResponse.setNum(assetTopologyDao.findTopologyCountByCategory(assetQuery));
                categoryResponseList.add(categoryResponse);
            }
            topologyCategoryCountResponse.setData(categoryResponseList);
            topologyCategoryCountResponse.setStatus("success");
            return topologyCategoryCountResponse;
        }
        return null;
    }

    private AssetQuery setAssetQueryParam(List<Integer> areaIds, List<AssetCategoryModel> search, List<String> osList) {
        AssetQuery assetQuery = new AssetQuery();
        if (search != null) {
            List<Integer> list = new ArrayList<>();
            search.forEach(x -> list.add(x.getId()));
            assetQuery.setCategoryModels(ArrayTypeUtil.objectArrayToStringArray(list.toArray()));
        }
        if (osList != null) {
            assetQuery.setOsList(osList);
        }
        if (areaIds != null) {
            assetQuery.setAreaIds(ArrayTypeUtil.objectArrayToStringArray(areaIds.toArray()));
        }
        // 需要排除已退役资产
        List<Integer> status = StatusEnumUtil.getAssetNotRetireStatus();
        assetQuery.setAssetStatusList(status);
        return assetQuery;
    }

    public TopologyOsCountResponse countTopologyOs() throws Exception {
        List<Integer> areaIds = LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser();
        // 查询第二级分类id
        List<BaselineCategoryModelNodeResponse> osTreeList = operatingSystemClient.getInvokeOperatingSystemTree();
        if (CollectionUtils.isNotEmpty(osTreeList)) {
            TopologyOsCountResponse topologyOsCountResponse = new TopologyOsCountResponse();
            List<TopologyOsCountResponse.OsResponse> osResponseList = new ArrayList<>();
            List<String> otherId = new ArrayList<>();
            for (BaselineCategoryModelNodeResponse nodeResponse : osTreeList) {
                if (Objects.equals(nodeResponse.getName(), Constants.WINDOWS)
                    || Objects.equals(nodeResponse.getName(), Constants.LINUX)) {
                    List<String> idList = new ArrayList<>();
                    AssetQuery assetQuery = setAssetQueryParam(areaIds, null, idList);
                    TopologyOsCountResponse.OsResponse osResponse = topologyOsCountResponse.new OsResponse();
                    String osId = null;
                    if (Objects.equals(nodeResponse.getName(), Constants.WINDOWS)) {
                        osId = findOsId(osTreeList, Constants.WINDOWS);
                        osResponse.setOs_type(Constants.WINDOWS);
                    }
                    if (Objects.equals(nodeResponse.getName(), Constants.LINUX)) {
                        osId = findOsId(osTreeList, Constants.LINUX);
                        osResponse.setOs_type(Constants.LINUX);
                    }
                    recursionSearchOsSystem(idList, osTreeList, osId);
                    otherId.addAll(idList);
                    osResponse.setNum(assetTopologyDao.findTopologyCountByCategory(assetQuery));
                    osResponseList.add(osResponse);
                }
            }
            AssetQuery assetQuery = setAssetQueryParam(areaIds, null, otherId);
            TopologyOsCountResponse.OsResponse osResponse = topologyOsCountResponse.new OsResponse();
            osResponse.setOs_type(Constants.OTHER);
            osResponse.setNum(assetTopologyDao.findOtherTopologyCountByCategory(assetQuery));
            osResponseList.add(osResponse);
            topologyOsCountResponse.setData(osResponseList);
            topologyOsCountResponse.setStatus("success");
            return topologyOsCountResponse;
        }
        return null;
    }

    private String findOsId(List<BaselineCategoryModelNodeResponse> baselineCategoryModelNodeResponses, String name) {
        for (BaselineCategoryModelNodeResponse baselineCategoryModelNodeResponse : baselineCategoryModelNodeResponses) {
            if (Objects.equals(baselineCategoryModelNodeResponse.getName(), name)) {
                return baselineCategoryModelNodeResponse.getStringId();
            }
        }
        return null;
    }

    private void recursionSearchOsSystem(List<String> result, List<BaselineCategoryModelNodeResponse> list,
                                         String id) throws Exception {
        for (BaselineCategoryModelNodeResponse baselineCategoryModelResponse : list) {
            if (Objects.equals(baselineCategoryModelResponse.getStringId(), id)) {
                result.add(aesEncoder.decode(baselineCategoryModelResponse.getStringId(),
                    LoginUserUtil.getLoginUser().getUsername()));
                recursionSearch(result, baselineCategoryModelResponse.getChildrenNode());
            }
        }
    }

    private void recursionSearch(List<String> result, List<BaselineCategoryModelNodeResponse> list) {
        for (BaselineCategoryModelNodeResponse baselineCategoryModelResponse : list) {
            result.add(aesEncoder.decode(baselineCategoryModelResponse.getStringId(),
                LoginUserUtil.getLoginUser().getUsername()));
            recursionSearch(result, baselineCategoryModelResponse.getChildrenNode());
        }

    }

    private void setAlarmCount(List<AssetResponse> assetResponseList, List<IdCount> idCounts) {
        for (AssetResponse assetResponse : assetResponseList) {
            assetResponse.setAlarmCount("0");
            for (IdCount idCount : idCounts) {
                if (idCount.getId().equals(assetResponse.getStringId())) {
                    assetResponse.setAlarmCount(idCount.getCount());
                }
            }
        }
    }

    private void setAreaName(AssetResponse response) throws Exception {
        String key = RedisKeyUtil.getKeyWhenGetObject(ModuleEnum.SYSTEM.getType(), SysArea.class,
            Integer.parseInt(response.getAreaId()));
        SysArea sysArea = redisUtil.getObject(key, SysArea.class);
        response.setAreaName(sysArea != null ? sysArea.getFullName() : null);
    }

}
