package com.antiy.asset.service.impl;

import java.util.*;

import javax.annotation.Resource;

import com.antiy.asset.dao.AssetCategoryModelDao;
import com.antiy.asset.intergration.OperatingSystemClient;
import com.antiy.asset.service.IAssetService;
import com.antiy.asset.util.ArrayTypeUtil;
import com.antiy.asset.util.Constants;
import com.antiy.asset.vo.response.*;
import com.antiy.common.base.*;
import com.antiy.common.encoder.AesEncoder;
import com.antiy.common.exception.BusinessException;
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
import com.antiy.common.enums.ModuleEnum;
import com.antiy.common.utils.DataTypeUtils;
import com.antiy.common.utils.LoginUserUtil;

import static com.antiy.asset.util.StatusEnumUtil.getAssetUseableStatus;
import static com.antiy.asset.util.StatusEnumUtil.getSoftwareNotRetireStatusList;

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
    @Resource
    private IAssetService                       iAssetService;

    @Override
    public List<String> queryCategoryModels() {
        return assetLinkRelationDao.queryCategoryModes();
    }

    @Override
    public TopologyAssetResponse queryAssetNodeInfo(String assetId) {
        AssetNodeInfoResponse assetNodeInfoResponse = assetLinkRelationDao.queryAssetNodeInfo(assetId);
        assetNodeInfoResponse
            .setInstallTypeName(InstallType.getInstallTypeByCode(assetNodeInfoResponse.getInstallType()).getStatus());
        TopologyAssetResponse topologyAssetResponse = new TopologyAssetResponse();
        TopologyAssetResponse.TopologyNodeAsset topologyNodeAsset = topologyAssetResponse.new TopologyNodeAsset();
        topologyNodeAsset.setAsset_id(assetNodeInfoResponse.getStringId());
        topologyNodeAsset.setAssetGroup(assetNodeInfoResponse.getAssetGroup());
        topologyNodeAsset.setHouseLocation(assetNodeInfoResponse.getHouseLocation());
        topologyNodeAsset.setIp(assetNodeInfoResponse.getIp());
        topologyNodeAsset.setInstallType(assetNodeInfoResponse.getInstallTypeName());
        topologyNodeAsset.setOs(assetNodeInfoResponse.getOperationSystemName());
        topologyNodeAsset.setTelephone(assetNodeInfoResponse.getContactTel());
        topologyAssetResponse.setStatus("success");
        topologyAssetResponse.setVersion(assetNodeInfoResponse.getNumber());
        List<TopologyAssetResponse.TopologyNodeAsset> topologyNodeAssets = new ArrayList<>();
        topologyNodeAssets.add(topologyNodeAsset);
        topologyAssetResponse.setData(topologyNodeAssets);
        return topologyAssetResponse;
    }

    @Override
    public CountTopologyResponse countAssetTopology() throws Exception {
        Map<String, String> resultMap = new HashMap<>(2);
        // 1.查询资产总数
        AssetQuery assetQuery = new AssetQuery();
        assetQuery.setAreaIds(
            DataTypeUtils.integerArrayToStringArray(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser()));
        // 1.1资产状态出去不予登记和登记
        List<Integer> assetStatusList = getAssetUseableStatus();
        assetQuery.setAssetStatusList(assetStatusList);
        // 1.2所有资产
        resultMap.put("total", assetDao.findCountByCategoryModel(assetQuery).toString());
        // 2.查询已管控的数量(已建立关联关系的资产数量)
        assetQuery = new AssetQuery();
        assetQuery.setAreaIds(
            DataTypeUtils.integerArrayToStringArray(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser()));
        assetQuery.setAssetStatusList(assetStatusList);
        List<String> assetIdList = assetTopologyDao.findTopologyIdByCategory(assetQuery);
        resultMap.put("control", assetIdList == null ? "0" : assetIdList.size() + "");
        ObjectQuery objectQuery = new ObjectQuery();
        objectQuery.setPageSize(-1);
        PageResult<IdCount> idCountPageResult = emergencyClient.queryInvokeEmergencyCount(objectQuery);
        List<IdCount> idCounts = idCountPageResult.getItems();
        List<IdCount> decodeCounts = new ArrayList<>();
        for (IdCount idCount : idCounts) {
            IdCount newIdCount = new IdCount();
            newIdCount.setCount(idCount.getCount());
            newIdCount.setId(aesEncoder.decode(idCount.getId(), LoginUserUtil.getLoginUser().getUsername()));
            decodeCounts.add(newIdCount);
        }
        int warningNum = 0;
        warningNum = getWarningNum(assetIdList, decodeCounts, warningNum);
        resultMap.put("warning", Integer.toString(warningNum));
        CountTopologyResponse countTopologyResponse = new CountTopologyResponse();
        List<Map<String, String>> list = new ArrayList<>();
        list.add(resultMap);
        countTopologyResponse.setList(list);
        countTopologyResponse.setStatus("success");
        return countTopologyResponse;
    }

    private int getWarningNum(List<String> assetIdList, List<IdCount> decodeCounts, int warningNum) {
        if (assetIdList == null) {
            return 0;
        }
        for (IdCount idCount : decodeCounts) {
            for (String id : assetIdList) {
                if (Objects.equals(idCount.getId(), id)) {
                    warningNum++;
                }
            }
        }
        return warningNum;
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
        // 查询第二级分类id
        TopologyCategoryCountResponse topologyCategoryCountResponse = new TopologyCategoryCountResponse();
        List<TopologyCategoryCountResponse.CategoryResponse> categoryResponseList = new ArrayList<>();
        List<EnumCountResponse> enumCountResponses = iAssetService.countCategoryByStatus(getAssetUseableStatus());
        for (EnumCountResponse enumCountResponse : enumCountResponses) {
            TopologyCategoryCountResponse.CategoryResponse categoryResponse = topologyCategoryCountResponse.new CategoryResponse();
            categoryResponse.setNum(Integer.valueOf(enumCountResponse.getNumber() + ""));
            categoryResponse.setAsset_name(enumCountResponse.getMsg());
            categoryResponseList.add(categoryResponse);
        }
        topologyCategoryCountResponse.setData(categoryResponseList);
        topologyCategoryCountResponse.setStatus("success");
        return topologyCategoryCountResponse;

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
        // 只统计已入网待退役
        List<Integer> statusList = getAssetUseableStatus();
        assetQuery.setAssetStatusList(statusList);
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
                    osResponse.setNum(assetDao.findCountByCategoryModel(assetQuery));
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
                if (Objects.equals(aesEncoder.decode(idCount.getId(), LoginUserUtil.getLoginUser().getUsername()),
                    assetResponse.getStringId())) {
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
