package com.antiy.asset.service.impl;

import static com.antiy.asset.util.StatusEnumUtil.getAssetUseableStatus;

import java.util.*;
import java.util.stream.Stream;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.AssetLinkRelationDao;
import com.antiy.asset.dao.AssetTopologyDao;
import com.antiy.asset.entity.Asset;
import com.antiy.asset.entity.AssetGroup;
import com.antiy.asset.entity.AssetLink;
import com.antiy.asset.entity.IdCount;
import com.antiy.asset.intergration.EmergencyClient;
import com.antiy.asset.intergration.OperatingSystemClient;
import com.antiy.asset.service.IAssetService;
import com.antiy.asset.service.IAssetTopologyService;
import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.asset.vo.query.AssetDetialCondition;
import com.antiy.asset.vo.query.AssetQuery;
import com.antiy.asset.vo.query.AssetTopologyQuery;
import com.antiy.asset.vo.response.*;
import com.antiy.biz.util.RedisKeyUtil;
import com.antiy.biz.util.RedisUtil;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.SysArea;
import com.antiy.common.encoder.AesEncoder;
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
    private AssetTopologyDao                    assetTopologyDao;
    @Resource
    private EmergencyClient                     emergencyClient;
    @Resource
    private BaseConverter<Asset, AssetResponse> converter;
    @Resource
    private RedisUtil                           redisUtil;
    @Resource
    private AesEncoder                          aesEncoder;
    @Resource
    private OperatingSystemClient               operatingSystemClient;
    @Resource
    private IAssetService                       iAssetService;
    @Value("#{'${topology.middlePoint}'.split(',')}")
    private List<Double>                        middlePoint;
    @Value("#{'${topology.cameraPos}'.split(',')}")
    private List<Double>                        cameraPos;
    @Value("#{'${topology.targetPos}'.split(',')}")
    private List<Double>                        targetPos;
    @Value("${topology.first.level.space}")
    private Double                              firstLevelSpacing;
    @Value("${topology.first.level.height}")
    private Double                              firstLevelHeight;
    @Value("${topology.second.level.space}")
    private Double                              secondLevelSpacing;
    @Value("${topology.second.level.height}")
    private Double                              secondLevelHeight;
    @Value("${topology.third.level.space}")
    private Double                              thirdLevelSpacing;
    @Value("${topology.third.level.height}")
    private Double                              thirdLevelHeight;

    @Value("${topology.fake.first.level.space}")
    private Double                              firstLevelSpacingFake;
    @Value("${topology.fake.first.level.height}")
    private Double                              firstLevelHeightFake;
    @Value("${topology.fake.second.level.space}")
    private Double                              secondLevelSpacingFake;
    @Value("${topology.fake.second.level.height}")
    private Double                              secondLevelHeightFake;
    @Value("${topology.fake.third.level.space}")
    private Double                              thirdLevelSpacingFake;
    @Value("${topology.fake.third.level.height}")
    private Double                              thirdLevelHeightFake;
    @Value("${topology.fake.forth.level.height}")
    private Double                              forthLevelHeightFake;
    @Value("${topology.fake.forth.level.space}")
    private Double                              forthLevelSpacingFake;

    @Override
    public List<String> queryCategoryModels() {
        return assetLinkRelationDao.queryCategoryModes();
    }

    @Override
    public TopologyAssetResponse queryAssetNodeInfo(String assetId) throws Exception {
        AssetDetialCondition assetDetialCondition = new AssetDetialCondition();
        assetDetialCondition.setPrimaryKey(assetId);
        AssetOuterResponse assetOuterResponse = iAssetService.getByAssetId(assetDetialCondition);
        AssetResponse assetResponse = assetOuterResponse.getAsset();
        TopologyAssetResponse topologyAssetResponse = new TopologyAssetResponse();
        // TopologyAssetResponse.TopologyNodeAsset topologyNodeAsset = topologyAssetResponse.new TopologyNodeAsset();
        // topologyNodeAsset.setAsset_id(assetResponse.getStringId());
        // topologyNodeAsset.setAssetGroup(assetResponse.getAssetGroup());
        // topologyNodeAsset.setHouseLocation(assetResponse.getHouseLocation());
        // topologyNodeAsset.setIp(assetResponse.getIp());
        // topologyNodeAsset.setInstallType(assetResponse.getInstallTypeName());
        // topologyNodeAsset.setOs(assetResponse.getOperationSystemName());
        // topologyNodeAsset.setTelephone(assetResponse.getContactTel());
        // topologyNodeAsset.setLocation(assetResponse.getLocation());
        // topologyNodeAsset.setAsset_name(assetResponse.getName());
        // topologyAssetResponse.setStatus("success");
        // topologyAssetResponse.setVersion(assetResponse.getNumber());
        // List<TopologyAssetResponse.TopologyNodeAsset> topologyNodeAssets = new ArrayList<>();
        // topologyNodeAssets.add(topologyNodeAsset);
        // topologyAssetResponse.setData(topologyNodeAssets);
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
        List<Integer> statusList = new ArrayList<>();
        statusList.add(AssetStatusEnum.WAIT_RETIRE.getCode());
        statusList.add(AssetStatusEnum.NET_IN.getCode());
        assetQuery.setAssetStatusList(statusList);
        List<AssetLink> assetLinks = assetLinkRelationDao.findLinkRelation(assetQuery);
        List<String> assetIdList = new ArrayList<>();
        for (AssetLink assetLink : assetLinks) {
            if (!assetIdList.contains(assetLink.getAssetId())) {
                assetIdList.add(assetLink.getAssetId());
            }
            if (!assetIdList.contains(assetLink.getParentAssetId())) {
                assetIdList.add(assetLink.getParentAssetId());
            }
        }
        resultMap.put("control", assetIdList.size() + "");
        AssetQuery alarmQuery = new AssetQuery();
        alarmQuery.setIds(DataTypeUtils.integerArrayToStringArray(assetIdList));
        List<IdCount> idCounts = assetDao.queryAlarmCountByAssetIds(alarmQuery);
        List<IdCount> decodeCounts = new ArrayList<>();
        for (IdCount idCount : idCounts) {
            IdCount newIdCount = new IdCount();
            newIdCount.setCount(idCount.getCount());
            newIdCount.setId(idCount.getId());
            decodeCounts.add(newIdCount);
        }
        // 查询已管控的产生告警的资产
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
        List<Integer> statusList = new ArrayList<>();
        statusList.add(AssetStatusEnum.WAIT_RETIRE.getCode());
        statusList.add(AssetStatusEnum.NET_IN.getCode());
        assetTopologyQuery.setAssetStatusList(statusList);
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
    public TopologyListResponse getTopologyList(AssetQuery query) throws Exception {
        initQuery(query);
        query.setQueryDepartmentName(false);
        query.setQueryPatchCount(true);
        query.setQueryVulCount(true);

        AssetQuery assetQuery = new AssetQuery();
        assetQuery.setAreaIds(
            DataTypeUtils.integerArrayToStringArray(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser()));
        List<Integer> statusList = new ArrayList<>();
        statusList.add(AssetStatusEnum.WAIT_RETIRE.getCode());
        statusList.add(AssetStatusEnum.NET_IN.getCode());
        assetQuery.setAssetStatusList(statusList);
        List<AssetLink> assetLinks = assetLinkRelationDao.findLinkRelation(assetQuery);
        List<String> assetIdList = new ArrayList<>();
        for (AssetLink assetLink : assetLinks) {
            if (!assetIdList.contains(assetLink.getAssetId())) {
                assetIdList.add(assetLink.getAssetId());
            }
            if (!assetIdList.contains(assetLink.getParentAssetId())) {
                assetIdList.add(assetLink.getParentAssetId());
            }
        }
        if (CollectionUtils.isEmpty(assetIdList)) {
            TopologyListResponse topologyListResponse = new TopologyListResponse();
            topologyListResponse.setStatus("success");
            topologyListResponse.setData(null);
            return topologyListResponse;
        }
        query.setIds(DataTypeUtils.integerArrayToStringArray(assetIdList));
        Integer count = assetTopologyDao.findTopologyListAssetCount(query);
        if (count != null && count > 0) {
            List<Asset> assetList = assetTopologyDao.findTopologyListAsset(query);
            List<AssetResponse> assetResponseList = converter.convert(assetList, AssetResponse.class);
            AssetQuery alarmQuery = new AssetQuery();
            alarmQuery.setIds(DataTypeUtils.integerArrayToStringArray(assetIdList));
            List<IdCount> idCounts = assetDao.queryAlarmCountByAssetIds(alarmQuery);
            setListAreaName(assetResponseList);
            setAlarmCount(assetResponseList, idCounts);
            assetResponseList.sort(Comparator.comparingInt(o -> -Integer.valueOf(o.getAlarmCount())));
            // PageResult pageResult = new PageResult<>(query.getPageSize(), count, query.getCurrentPage(),
            // assetResponseList);
            TopologyListResponse topologyListResponse = new TopologyListResponse();
            List<TopologyListResponse.TopologyNode> topologyNodes = new ArrayList<>();
            for (AssetResponse assetResponse : assetResponseList) {
                TopologyListResponse.TopologyNode topologyNode = topologyListResponse.new TopologyNode();
                topologyNode.setAsset_area(assetResponse.getAreaName());
                // topologyNode.setAsset_ip(assetResponse.getIp());
                topologyNode.setAsset_id(
                    aesEncoder.encode(assetResponse.getStringId(), LoginUserUtil.getLoginUser().getUsername()));
                topologyNode.setAsset_group(assetResponse.getAssetGroup());
                topologyNode.setAsset_type(assetResponse.getCategoryModelName());
                topologyNode.setPerson_name(assetResponse.getResponsibleUserName());
                topologyNode.setAsset_unrepair(assetResponse.getVulCount());
                topologyNode.setAsset_untreated_warning(assetResponse.getAlarmCount());
                topologyNode.setSystem_uninstall(assetResponse.getPatchCount());
                topologyNode.setAsset_name(assetResponse.getName());
                topologyNodes.add(topologyNode);
            }
            topologyListResponse.setData(topologyNodes);
            // topologyListResponse.setPageSize(pageResult.getPageSize());
            // topologyListResponse.setCurrentPage(pageResult.getCurrentPage());
            topologyListResponse.setStatus("success");
            // topologyListResponse.setTotal(pageResult.getTotalRecords());
            return topologyListResponse;
        }
        return null;
    }

    private void setStatusQuery(AssetQuery query) {
        List<Integer> statusList = new ArrayList<>();
        statusList.add(AssetStatusEnum.WAIT_RETIRE.getCode());
        statusList.add(AssetStatusEnum.NET_IN.getCode());
        query.setAssetStatusList(statusList);
    }

    private void setListAreaName(List<AssetResponse> assetResponseList) throws Exception {
        for (AssetResponse assetResponse : assetResponseList) {
            setAreaName(assetResponse);
        }
    }

    public TopologyCategoryCountResponse countTopologyCategory() throws Exception {
        // 查询第二级分类id
        TopologyCategoryCountResponse topologyCategoryCountResponse = new TopologyCategoryCountResponse();
        // List<TopologyCategoryCountResponse.CategoryResponse> categoryResponseList = new ArrayList<>();
        // List<EnumCountResponse> enumCountResponses = iAssetService.countCategoryByStatus(getAssetUseableStatus());
        // for (EnumCountResponse enumCountResponse : enumCountResponses) {
        // TopologyCategoryCountResponse.CategoryResponse categoryResponse = topologyCategoryCountResponse.new
        // CategoryResponse();
        // categoryResponse.setNum(Integer.valueOf(enumCountResponse.getNumber() + ""));
        // categoryResponse.setAsset_name(enumCountResponse.getMsg());
        // categoryResponseList.add(categoryResponse);
        // }
        // topologyCategoryCountResponse.setData(categoryResponseList);
        // topologyCategoryCountResponse.setStatus("success");
        return topologyCategoryCountResponse;

    }

    @Override
    public TopologyOsCountResponse countTopologyOs() throws Exception {
        return null;
    }

    @Override
    public AssetTopologyNodeResponse getTopologyGraph() throws Exception {
        return null;
    }

    @Override
    public AssetTopologyNodeResponse getTopologyGraphFake() throws Exception {
        return null;
    }

    // private AssetQuery setAssetQueryParam(List<Integer> areaIds, List<AssetCategoryModel> search, List<String>
    // osList) {
    // AssetQuery assetQuery = new AssetQuery();
    // if (search != null) {
    // List<Integer> list = new ArrayList<>();
    // search.forEach(x -> list.add(x.getId()));
    // assetQuery.setCategoryModels(ArrayTypeUtil.objectArrayToStringArray(list.toArray()));
    // }
    // if (osList != null) {
    // assetQuery.setOsList(osList);
    // }
    // if (areaIds != null) {
    // assetQuery.setAreaIds(ArrayTypeUtil.objectArrayToStringArray(areaIds.toArray()));
    // }
    // // 只统计已入网待退役
    // List<Integer> statusList = getAssetUseableStatus();
    // assetQuery.setAssetStatusList(statusList);
    // return assetQuery;
    // }

    // public TopologyOsCountResponse countTopologyOs() throws Exception {
    // List<Integer> areaIds = LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser();
    // // 查询第二级分类id
    // List<BaselineCategoryModelNodeResponse> osTreeList = operatingSystemClient.getInvokeOperatingSystemTree();
    // if (CollectionUtils.isNotEmpty(osTreeList)) {
    // TopologyOsCountResponse topologyOsCountResponse = new TopologyOsCountResponse();
    // List<TopologyOsCountResponse.OsResponse> osResponseList = new ArrayList<>();
    // List<String> otherId = new ArrayList<>();
    // for (BaselineCategoryModelNodeResponse nodeResponse : osTreeList) {
    // if (Objects.equals(nodeResponse.getName(), Constants.WINDOWS)
    // || Objects.equals(nodeResponse.getName(), Constants.LINUX)) {
    // List<String> idList = new ArrayList<>();
    // AssetQuery assetQuery = setAssetQueryParam(areaIds, null, idList);
    // TopologyOsCountResponse.OsResponse osResponse = topologyOsCountResponse.new OsResponse();
    // String osId = null;
    // if (Objects.equals(nodeResponse.getName(), Constants.WINDOWS)) {
    // osId = findOsId(osTreeList, Constants.WINDOWS);
    // osResponse.setOs_type(Constants.WINDOWS);
    // }
    // if (Objects.equals(nodeResponse.getName(), Constants.LINUX)) {
    // osId = findOsId(osTreeList, Constants.LINUX);
    // osResponse.setOs_type(Constants.LINUX);
    // }
    // recursionSearchOsSystem(idList, osTreeList, osId);
    // otherId.addAll(idList);
    // osResponse.setNum(assetDao.findCountByCategoryModel(assetQuery));
    // osResponseList.add(osResponse);
    // }
    // }
    // AssetQuery assetQuery = setAssetQueryParam(areaIds, null, otherId);
    // TopologyOsCountResponse.OsResponse osResponse = topologyOsCountResponse.new OsResponse();
    // osResponse.setOs_type(Constants.OTHER);
    // osResponse.setNum(assetTopologyDao.findOtherTopologyCountByCategory(assetQuery));
    // osResponseList.add(osResponse);
    // topologyOsCountResponse.setData(osResponseList);
    // topologyOsCountResponse.setStatus("success");
    // return topologyOsCountResponse;
    // }
    // return null;
    // }

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
                if (Objects.equals(idCount.getId(), assetResponse.getStringId())) {
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

    // public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
    // Map<K, V> result = new LinkedHashMap<>();
    // Stream<Map.Entry<K, V>> st = map.entrySet().stream();
    //
    // st.sorted(Comparator.comparing(e -> e.getValue())).forEach(e -> result.put(e.getKey(), e.getValue()));
    //
    // return result;
    // }

    // public AssetTopologyNodeResponse getTopologyGraphFake() throws Exception {
    // AssetTopologyNodeResponse assetTopologyNodeResponse = new AssetTopologyNodeResponse();
    // assetTopologyNodeResponse.setStatus("success");
    // List<Map<String, AssetTopologyRelation>> dataList = new ArrayList<>();
    // AssetTopologyRelation assetTopologyRelation = new AssetTopologyRelation();
    // // 设置拓扑图信息
    // assetTopologyRelation.setInfo("");
    // // 设置中心点
    // assetTopologyRelation.setMiddlePoint(middlePoint);
    // // 设置角度
    // AssetTopologyViewAngle assetTopologyViewAngle = new AssetTopologyViewAngle();
    // assetTopologyViewAngle.setCameraPos(cameraPos);
    // assetTopologyViewAngle.setTargetPos(targetPos);
    // assetTopologyRelation.setView_angle(assetTopologyViewAngle);
    // AssetTopologyJsonData assetTopologyJsonData = new AssetTopologyJsonData();
    // Map<String, List<List<Object>>> jsonData = new HashMap<>();
    // Map<String, String> map = iAssetCategoryModelService.getSecondCategoryMap();
    // List<AssetCategoryModel> categoryModelList = iAssetCategoryModelService.getAll();
    //
    // String computeDeviceId = "4";
    // String netDeviceId = "5";
    // for (AssetCategoryModel assetCategoryModel : categoryModelList) {
    // if (Objects.equals(assetCategoryModel.getName(), AssetSecondCategoryEnum.COMPUTE_DEVICE.getMsg())) {
    // computeDeviceId = assetCategoryModel.getStringId();
    // }
    // if (Objects.equals(assetCategoryModel.getName(), AssetSecondCategoryEnum.NETWORK_DEVICE.getMsg())) {
    // netDeviceId = assetCategoryModel.getStringId();
    // }
    // }
    //
    // AssetQuery assetQuery = new AssetQuery();
    // assetQuery.setIds(new String[] { "62163", "62198", "5583", "62019", "62271", "62794" });
    // assetQuery.setCategoryModels(DataTypeUtils.integerArrayToStringArray(iAssetCategoryModelService
    // .findAssetCategoryModelIdsById(Integer.valueOf(computeDeviceId), categoryModelList)));
    // assetQuery.setAreaIds(
    // DataTypeUtils.integerArrayToStringArray(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser()));
    // assetQuery.setAssetStatusList(getAssetUseableStatus());
    // List<Asset> pcs = assetTopologyDao.selectFakeAsset(assetQuery);
    //
    // assetQuery = new AssetQuery();
    // assetQuery.setCategoryModels(DataTypeUtils.integerArrayToStringArray(
    // iAssetCategoryModelService.findAssetCategoryModelIdsById(Integer.valueOf(netDeviceId), categoryModelList)));
    // assetQuery.setAreaIds(
    // DataTypeUtils.integerArrayToStringArray(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser()));
    // assetQuery.setAssetStatusList(getAssetUseableStatus());
    // List<Asset> nets = assetTopologyDao.selectFakeAsset(assetQuery);
    //
    // Map<String, List<String>> firstToSecond = new HashMap<>();
    // Map<String, List<String>> secondToThird = new HashMap<>();
    // Map<String, List<String>> thirdToForth = new HashMap<>();
    //
    // List<String> firstIds = new ArrayList<>();
    //
    // List<String> secondIds = new ArrayList<>();
    // List<String> thirdIds = new ArrayList<>();
    // List<String> forthIds = new ArrayList<>();
    //
    // if (!nets.isEmpty()) {
    // firstIds.add(aesEncoder.encode(nets.remove(0).getStringId(), LoginUserUtil.getLoginUser().getUsername()));
    // } else if (!pcs.isEmpty()) {
    // firstIds.add(aesEncoder.encode(pcs.remove(0).getStringId(), LoginUserUtil.getLoginUser().getUsername()));
    // }
    // for (int i = 0; i < 4; i++) {
    // if (!nets.isEmpty()) {
    // secondIds
    // .add(aesEncoder.encode(nets.remove(0).getStringId(), LoginUserUtil.getLoginUser().getUsername()));
    // } else if (!pcs.isEmpty()) {
    // secondIds
    // .add(aesEncoder.encode(pcs.remove(0).getStringId(), LoginUserUtil.getLoginUser().getUsername()));
    // } else {
    // break;
    // }
    // boolean flag = true;
    // for (int j = 0; j < 9; j++) {
    // if (flag) {
    // if (!nets.isEmpty()) {
    // String stringId = aesEncoder.encode(nets.remove(0).getStringId(),
    // LoginUserUtil.getLoginUser().getUsername());
    // thirdIds.add(stringId);
    // List<String> m = new ArrayList();
    // m.add(stringId);
    // secondToThird.put(secondIds.get(i), m);
    // } else if (!pcs.isEmpty()) {
    // String stringId = aesEncoder.encode(pcs.remove(0).getStringId(),
    // LoginUserUtil.getLoginUser().getUsername());
    // thirdIds.add(stringId);
    // List<String> m = new ArrayList();
    // m.add(stringId);
    // secondToThird.put(secondIds.get(i), m);
    // } else {
    // break;
    // }
    // flag = false;
    // } else {
    // if (!nets.isEmpty()) {
    // String stringId = aesEncoder.encode(nets.remove(0).getStringId(),
    // LoginUserUtil.getLoginUser().getUsername());
    // thirdIds.add(stringId);
    // secondToThird.get(secondIds.get(i)).add(stringId);
    // } else if (!pcs.isEmpty()) {
    // String stringId = aesEncoder.encode(pcs.remove(0).getStringId(),
    // LoginUserUtil.getLoginUser().getUsername());
    // thirdIds.add(stringId);
    // secondToThird.get(secondIds.get(i)).add(stringId);
    // } else {
    // break;
    // }
    // }
    // }
    // }
    // firstToSecond.put(firstIds.get(0), secondIds);
    //
    // int e = 0;
    // int j = 0;
    // for (String third : thirdIds) {
    // boolean flag = true;
    // e = j % 2;
    // int x = e + 3;
    // for (int i = 0; i < x * x; i++) {
    // if (flag) {
    // if (!nets.isEmpty()) {
    // String stringId = aesEncoder.encode(nets.remove(0).getStringId(),
    // LoginUserUtil.getLoginUser().getUsername());
    // forthIds.add(stringId);
    // List<String> m = new ArrayList();
    // m.add(stringId);
    // thirdToForth.put(third, m);
    // } else if (!pcs.isEmpty()) {
    // String stringId = aesEncoder.encode(pcs.remove(0).getStringId(),
    // LoginUserUtil.getLoginUser().getUsername());
    // forthIds.add(stringId);
    // List<String> m = new ArrayList();
    // m.add(stringId);
    // thirdToForth.put(third, m);
    // } else {
    // break;
    // }
    // flag = false;
    // } else {
    // if (!nets.isEmpty()) {
    // String stringId = aesEncoder.encode(nets.remove(0).getStringId(),
    // LoginUserUtil.getLoginUser().getUsername());
    // forthIds.add(stringId);
    // thirdToForth.get(third).add(stringId);
    // } else if (!pcs.isEmpty()) {
    // String stringId = aesEncoder.encode(pcs.remove(0).getStringId(),
    // LoginUserUtil.getLoginUser().getUsername());
    // forthIds.add(stringId);
    // thirdToForth.get(third).add(stringId);
    // } else {
    // break;
    // }
    // }
    // }
    // j++;
    // }
    //
    // Map<String, List<Double>> firstCoordinates = new HashMap<>();
    // List<Double> list = new ArrayList<>();
    // list.add(0D);
    // list.add(firstLevelHeightFake);
    // list.add(0D);
    // firstCoordinates.put(firstIds.get(0), list);
    // Map<String, List<Double>> secondCoordinates = new HashMap<>();
    // Map<String, List<Double>> thirdCoordinates = new HashMap<>();
    // Map<String, List<Double>> fourCoordinates = new HashMap<>();
    // List<List<Object>> points = new ArrayList<>();
    // List<Object> point = new ArrayList<>();
    // point.add(firstIds.get(0));
    // point.addAll(firstCoordinates.get(firstIds.get(0)));
    // point.add(new ArrayList<>());
    // point.add(firstCoordinates.get(firstIds.get(0)).get(0));
    // point.add(firstCoordinates.get(firstIds.get(0)).get(2));
    // point.add("");
    // points.add(point);
    // jsonData.put("sim_topo-router", points);
    //
    // settingLevelCoordinatesFake(firstToSecond, jsonData, firstCoordinates, secondCoordinates,
    // secondLevelSpacingFake, secondLevelHeightFake, "sim_topo-router");
    // settingLevelCoordinatesFake(secondToThird, jsonData, secondCoordinates, thirdCoordinates, thirdLevelSpacingFake,
    // thirdLevelHeightFake, "sim_topo-router");
    // settingLevelCoordinatesFake(thirdToForth, jsonData, thirdCoordinates, fourCoordinates, forthLevelSpacingFake,
    // forthLevelHeightFake, "sim_topo-host");
    //
    // assetTopologyJsonData.setJson_data0(jsonData);
    // assetTopologyRelation.setJson_data(assetTopologyJsonData);
    // Map<String, AssetTopologyRelation> dataMap = new HashMap<>(2);
    // dataMap.put("relationship", assetTopologyRelation);
    // dataList.add(dataMap);
    // assetTopologyNodeResponse.setData(dataList);
    // return assetTopologyNodeResponse;
    //
    // }

    private void settingLevelCoordinatesFake(Map<String, List<String>> map, Map<String, List<List<Object>>> jsonData,
                                             Map<String, List<Double>> sonCoordinates,
                                             Map<String, List<Double>> parentCoordinates, double space, double height,
                                             String category) {
        List<List<Object>> dataList = new ArrayList<>();

        // 排序
        Map<String, List<String>> result = new LinkedHashMap<>();
        Stream<Map.Entry<String, List<String>>> st = map.entrySet().stream();
        st.sorted(Comparator.comparing(e -> -e.getValue().size())).forEach(e -> result.put(e.getKey(), e.getValue()));

        // 缓存id和index的关系
        Map<String, Integer> cache = new HashMap<>();
        for (Map.Entry<String, List<String>> entry : result.entrySet()) {
            int i = 0;
            for (String s : entry.getValue()) {
                Integer index = cache.get(s);
                if (index != null) {
                    List<Object> point = dataList.get(index);
                    List<String> pointParent = (List<String>) point.get(4);
                    pointParent.add(entry.getKey());
                } else {
                    List<Double> coordinates = sonCoordinates.get(entry.getKey());
                    List<Object> point = new ArrayList<>();
                    double size = getSize(entry.getValue().size());
                    List<Double> coordinateByParent = getCoordinateByParent(size, i, space, height, coordinates.get(0),
                        coordinates.get(2));
                    parentCoordinates.put(s, coordinateByParent);
                    point.add(s);
                    point.addAll(coordinateByParent);
                    List<String> parent = new ArrayList<>();
                    parent.add(entry.getKey());
                    point.add(parent);
                    point.add(coordinateByParent.get(0));
                    point.add(coordinateByParent.get(2));
                    point.add("");
                    List<List<Object>> points = jsonData.get(category);
                    if (points == null) {
                        points = new ArrayList<>();
                    }
                    points.add(point);
                    dataList.add(point);
                    jsonData.put(category, points);
                    cache.put(s, dataList.size() - 1);
                    i++;

                }
            }
        }
    }

    /**
     * 获取拓扑图
     * @return
     * @throws Exception
     */
    // public AssetTopologyNodeResponse getTopologyGraph() throws Exception {
    // AssetTopologyNodeResponse assetTopologyNodeResponse = new AssetTopologyNodeResponse();
    // assetTopologyNodeResponse.setStatus("success");
    // List<Map<String, AssetTopologyRelation>> dataList = new ArrayList<>();
    // AssetTopologyRelation assetTopologyRelation = new AssetTopologyRelation();
    // // 设置拓扑图信息
    // assetTopologyRelation.setInfo("");
    // // 设置中心点
    // assetTopologyRelation.setMiddlePoint(middlePoint);
    // // 设置角度
    // AssetTopologyViewAngle assetTopologyViewAngle = new AssetTopologyViewAngle();
    // assetTopologyViewAngle.setCameraPos(cameraPos);
    // assetTopologyViewAngle.setTargetPos(targetPos);
    // assetTopologyRelation.setView_angle(assetTopologyViewAngle);
    //
    // AssetTopologyJsonData assetTopologyJsonData = new AssetTopologyJsonData();
    // Map<String, List<List<Object>>> jsonData = new HashMap<>();
    //
    // AssetQuery query = new AssetQuery();
    // query.setAreaIds(
    // DataTypeUtils.integerArrayToStringArray(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser()));
    // List<Integer> statusList = new ArrayList<>();
    // statusList.add(AssetStatusEnum.WAIT_RETIRE.getCode());
    // statusList.add(AssetStatusEnum.NET_IN.getCode());
    // query.setAssetStatusList(statusList);
    // List<AssetLink> assetLinks = assetLinkRelationDao.findLinkRelation(query);
    // List<AssetCategoryModel> categoryModelList = iAssetCategoryModelService.getAll();
    //
    // String computeDeviceId = "";
    // String routeId = "";
    // String switchId = "";
    // Set<String> querySet = new HashSet();
    // // 找到对应品类id
    // for (AssetCategoryModel assetCategoryModel : categoryModelList) {
    // if (Objects.equals(assetCategoryModel.getName(), AssetSecondCategoryEnum.COMPUTE_DEVICE.getMsg())) {
    // querySet.add(assetCategoryModel.getStringId());
    // computeDeviceId = assetCategoryModel.getStringId();
    // }
    // if (Objects.equals(assetCategoryModel.getName(), NetWorkDeviceEnum.Router.getMsg())) {
    // querySet.add(assetCategoryModel.getStringId());
    // routeId = assetCategoryModel.getStringId();
    // }
    // if (Objects.equals(assetCategoryModel.getName(), NetWorkDeviceEnum.Switch.getMsg())) {
    // querySet.add(assetCategoryModel.getStringId());
    // switchId = assetCategoryModel.getStringId();
    // }
    //
    // }
    //
    // Map<String, String> idCategory = new HashMap<>();
    // Map<String, String> cache = new HashMap<>();
    // for (AssetLink assetLink : assetLinks) {
    // // id加密
    // assetLink.setAssetId(aesEncoder.encode(assetLink.getAssetId(), LoginUserUtil.getLoginUser().getUsername()));
    // assetLink.setParentAssetId(
    // aesEncoder.encode(assetLink.getParentAssetId(), LoginUserUtil.getLoginUser().getUsername()));
    // }
    //
    // // 设置品类id与返回格式的映射
    // for (AssetLink assetLink : assetLinks) {
    //
    // String categoryId = idCategory.get(assetLink.getAssetId());
    // String cacheId = cache.get(assetLink.getCategoryModal().toString());
    // if (categoryId == null) {
    // if (cacheId == null) {
    // String result = iAssetCategoryModelService.recursionSearchParentCategory(
    // assetLink.getCategoryModal().toString(), categoryModelList, querySet);
    // String resultCategory = "";
    // if (result == null) {
    // resultCategory = "sim_topo-network";
    // }
    // if (Objects.equals(result, routeId)) {
    // resultCategory = "sim_topo-router";
    // }
    // if (Objects.equals(result, switchId)) {
    // resultCategory = "sim_topo-switch";
    // }
    // if (Objects.equals(result, computeDeviceId)) {
    // resultCategory = "sim_topo-host";
    // }
    // idCategory.put(assetLink.getAssetId(), resultCategory);
    // cache.put(assetLink.getCategoryModal() + "", resultCategory);
    // } else {
    // idCategory.put(assetLink.getAssetId(), cacheId);
    // }
    // }
    //
    // cacheId = cache.get(assetLink.getParentCategoryModal().toString());
    // String parentCategoryId = idCategory.get(assetLink.getParentAssetId());
    // if (parentCategoryId == null) {
    // if (cacheId == null) {
    // String result = iAssetCategoryModelService.recursionSearchParentCategory(
    // assetLink.getParentCategoryModal().toString(), categoryModelList, querySet);
    // String resultCategory = "";
    // if (result == null) {
    // resultCategory = "sim_topo-network";
    // }
    // if (Objects.equals(result, routeId)) {
    // resultCategory = "sim_topo-router";
    // }
    // if (Objects.equals(result, switchId)) {
    // resultCategory = "sim_topo-switch";
    // }
    // if (Objects.equals(result, computeDeviceId)) {
    // resultCategory = "sim_topo-host";
    // }
    // idCategory.put(assetLink.getParentAssetId(), resultCategory);
    // cache.put(assetLink.getParentCategoryModal() + "", resultCategory);
    // } else {
    // idCategory.put(assetLink.getParentAssetId(), cacheId);
    // }
    // }
    //
    // }
    // Map<String, String> secondCategoryMap = iAssetCategoryModelService.getSecondCategoryMap();
    // processLinkCount(assetLinks, secondCategoryMap);
    // // 找到各个的层级节点
    // Map<String, List<String>> firstMap = new HashMap<>();
    // Map<String, List<String>> secondMap = new HashMap<>();
    // Map<String, List<String>> secondThirdMap = new LinkedHashMap<>();
    // String networkDeviceId = "";
    // for (Map.Entry<String, String> entry : secondCategoryMap.entrySet()) {
    // if (Objects.equals(entry.getValue(), AssetSecondCategoryEnum.NETWORK_DEVICE.getMsg())) {
    // networkDeviceId = entry.getKey();
    // }
    // }
    // for (AssetLink assetLink : assetLinks) {
    // if (Objects.equals(String.valueOf(assetLink.getCategoryModal()), networkDeviceId)
    // && Objects.equals(String.valueOf(assetLink.getParentCategoryModal()), networkDeviceId)) {
    // // 构造第一，二级层级节点关系
    // flushMap(firstMap, assetLink);
    // flushParentMap(firstMap, assetLink);
    //
    // } else {
    // // 构造第二，三级层级节点关系
    // if (Objects.equals(String.valueOf(assetLink.getCategoryModal()), networkDeviceId)) {
    // flushMap(secondMap, assetLink);
    // } else {
    // flushParentMap(secondThirdMap, assetLink);
    // }
    // if (Objects.equals(String.valueOf(assetLink.getParentCategoryModal()), networkDeviceId)) {
    // flushParentMap(secondMap, assetLink);
    // } else {
    // flushMap(secondThirdMap, assetLink);
    // }
    // }
    // }
    // // 去掉第一层中不符合要求的数据
    // for (Map.Entry<String, List<String>> entry : secondMap.entrySet()) {
    // List<String> first = entry.getValue();
    // List<String> result = firstMap.remove(entry.getKey());
    // if (result != null) {
    // first.addAll(result);
    // secondMap.put(entry.getKey(), first);
    // }
    // }
    //
    // // 构造第一层坐标数据
    // settingFirstLevelCoordinates(firstMap, jsonData, idCategory);
    // Map<String, List<Double>> secondCoordinates = new HashMap<>();
    // // 构造第二层坐标数据
    // settingSecondLevelCoordinates(secondMap, jsonData, idCategory, secondCoordinates);
    // // 构造第三层坐标数据
    // settingThirdLevelCoordinates(secondThirdMap, jsonData, idCategory, secondCoordinates);
    // assetTopologyJsonData.setJson_data0(jsonData);
    // assetTopologyRelation.setJson_data(assetTopologyJsonData);
    // Map<String, AssetTopologyRelation> dataMap = new HashMap<>(2);
    // dataMap.put("relationship", assetTopologyRelation);
    // dataList.add(dataMap);
    // assetTopologyNodeResponse.setData(dataList);
    // return assetTopologyNodeResponse;
    // }

    @Override
    public AssetTopologyIpSearchResposne queryListByIp(AssetQuery query) throws Exception {
        initQuery(query);
        query.setQueryDepartmentName(true);
        query.setQueryVulCount(false);
        query.setQueryPatchCount(false);
        AssetQuery assetQuery = new AssetQuery();
        assetQuery.setAreaIds(
            DataTypeUtils.integerArrayToStringArray(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser()));
        List<Integer> statusList = new ArrayList<>();
        statusList.add(AssetStatusEnum.WAIT_RETIRE.getCode());
        statusList.add(AssetStatusEnum.NET_IN.getCode());
        assetQuery.setAssetStatusList(statusList);
        List<AssetLink> assetLinks = assetLinkRelationDao.findLinkRelation(assetQuery);
        List<String> assetIdList = new ArrayList<>();
        for (AssetLink assetLink : assetLinks) {
            if (!assetIdList.contains(assetLink.getAssetId())) {
                assetIdList.add(assetLink.getAssetId());
            }
            if (!assetIdList.contains(assetLink.getParentAssetId())) {
                assetIdList.add(assetLink.getParentAssetId());
            }
        }
        AssetTopologyIpSearchResposne assetTopologyIpSearchResposne = new AssetTopologyIpSearchResposne();
        assetTopologyIpSearchResposne.setStatus("success");
        query.setIds(DataTypeUtils.integerArrayToStringArray(assetIdList));
        if (CollectionUtils.isEmpty(assetIdList)) {
            assetTopologyIpSearchResposne.setData(null);
            return assetTopologyIpSearchResposne;
        }
        List<Asset> assetList = assetTopologyDao.findTopologyListAsset(query);
        List<AssetTopologyIpSearchResposne.IpSearch> ipSearchList = transferAssetToIpSearch(assetList);
        assetTopologyIpSearchResposne.setData(ipSearchList);
        return assetTopologyIpSearchResposne;
    }

    @Override
    public AssetTopologyAlarmResponse getAlarmTopology() throws Exception {
        return null;
    }

    @Override
    public AssetTopologyAlarmResponse getAlarmTopologyFake() throws Exception {
        return null;
    }

    private List<AssetTopologyIpSearchResposne.IpSearch> transferAssetToIpSearch(List<Asset> assetList) {
        List<AssetTopologyIpSearchResposne.IpSearch> ipSearchList = new ArrayList<>();
        for (Asset asset : assetList) {
            AssetTopologyIpSearchResposne.IpSearch ipSearch = new AssetTopologyIpSearchResposne().new IpSearch();
            ipSearch.setAsset_id(aesEncoder.encode(asset.getStringId(), LoginUserUtil.getLoginUser().getUsername()));
            ipSearch.setDepartment_name(asset.getDepartmentName());
            // ipSearch.setIp(asset.getIp());
            ipSearch.setPerson_name(asset.getResponsibleUserName());
            ipSearchList.add(ipSearch);
        }
        return ipSearchList;
    }

    /**
     * 设置第一层坐标数据
     * @param map
     * @return
     */
    private void settingFirstLevelCoordinates(Map<String, List<String>> map, Map<String, List<List<Object>>> jsonData,
                                              Map<String, String> idCategory) {
        double size = getSize(map.size());
        double space = firstLevelSpacing;
        double height = firstLevelHeight;
        int i = 0;
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            getDataList(size, space, height, i, entry, jsonData, idCategory);
            i++;

        }
    }

    /**
     * 获取第二层数据信息
     * @param map
     * @param secondCoordinates
     * @return
     */
    private List<List<Object>> settingSecondLevelCoordinates(Map<String, List<String>> map,
                                                             Map<String, List<List<Object>>> jsonData,
                                                             Map<String, String> idCategory,
                                                             Map<String, List<Double>> secondCoordinates) {
        double size = getSize(map.size());
        List<List<Object>> dataList = new ArrayList<>();
        double space = secondLevelSpacing;
        double height = secondLevelHeight;
        int i = 0;
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            List<Double> coordinateList = getDataList(size, space, height, i, entry, jsonData, idCategory);
            secondCoordinates.put(entry.getKey(), coordinateList);
            i++;
        }
        return dataList;
    }

    private List<Double> getDataList(double size, double space, double height, double i,
                                     Map.Entry<String, List<String>> entry, Map<String, List<List<Object>>> jsonData,
                                     Map<String, String> idCategory) {
        List<Double> coordinateList = getCoordinate(size, i, space, height);
        // 设置坐标数据
        List<Object> point = new ArrayList<>();
        point.add(entry.getKey());
        point.addAll(coordinateList);
        point.add(entry.getValue());
        point.add(coordinateList.get(0));
        point.add(coordinateList.get(2));
        point.add("");
        List<List<Object>> points = jsonData.get(idCategory.get(entry.getKey()));
        if (points == null) {
            points = new ArrayList<>();
        }
        points.add(point);
        jsonData.put(idCategory.get(entry.getKey()), points);
        return coordinateList;
    }

    /**
     * 获取第三层数据信息
     * @param map
     * @param secondCoordinates
     * @return
     */
    private void settingThirdLevelCoordinates(Map<String, List<String>> map, Map<String, List<List<Object>>> jsonData,
                                              Map<String, String> idCategory,
                                              Map<String, List<Double>> secondCoordinates) {
        List<List<Object>> dataList = new ArrayList<>();
        // 间隔
        double space = thirdLevelSpacing;
        // 高度
        double height = thirdLevelHeight;

        // 排序
        Map<String, List<String>> result = new LinkedHashMap<>();
        Stream<Map.Entry<String, List<String>>> st = map.entrySet().stream();
        st.sorted(Comparator.comparing(e -> -e.getValue().size())).forEach(e -> result.put(e.getKey(), e.getValue()));

        // 缓存id和index的关系
        Map<String, Integer> cache = new HashMap<>();
        for (Map.Entry<String, List<String>> entry : result.entrySet()) {
            int i = 0;
            for (String s : entry.getValue()) {
                Integer index = cache.get(s);
                if (index != null) {
                    List<Object> point = dataList.get(index);
                    List<String> pointParent = (List<String>) point.get(4);
                    pointParent.add(entry.getKey());
                } else {
                    List<Double> coordinates = secondCoordinates.get(entry.getKey());
                    List<Object> point = new ArrayList<>();
                    double size = getSize(entry.getValue().size());
                    List<Double> coordinateByParent = getCoordinateByParent(size, i, space, height, coordinates.get(0),
                        coordinates.get(2));
                    point.add(s);
                    point.addAll(coordinateByParent);
                    List<String> parent = new ArrayList<>();
                    parent.add(entry.getKey());
                    point.add(parent);
                    point.add(coordinateByParent.get(0));
                    point.add(coordinateByParent.get(2));
                    point.add("");
                    List<List<Object>> points = jsonData.get(idCategory.get(s));
                    if (points == null) {
                        points = new ArrayList<>();
                    }
                    points.add(point);
                    dataList.add(point);
                    jsonData.put(idCategory.get(s), points);
                    cache.put(s, dataList.size() - 1);
                    i++;
                }
            }
        }
    }

    /**
     * 获取自身坐标
     */
    private List<Double> getCoordinate(Double size, Double i, Double space, Double height) {
        Double x = ((Math.floor(i / size) - ((size - 1) / 2d)) * space);
        Double y = (((size - 1) / 2d - (i % size)) * space);
        List<Double> coordinate = new ArrayList<>();
        coordinate.add(x);
        coordinate.add(height);
        coordinate.add(y);
        return coordinate;
    }

    /**
     * 根据上级坐标获取本身坐标
     */
    private List<Double> getCoordinateByParent(double size, double i, double space, double height, double xx,
                                               double yy) {
        double x = (((Math.floor(i / size) - ((size - 1) / 2d)) * space) + xx);
        double y = ((((size - 1) / 2d - (i % size)) * space) + yy);
        List<Double> coordinate = new ArrayList<>();
        coordinate.add(x);
        coordinate.add(height);
        coordinate.add(y);

        return coordinate;
    }

    private double getSize(int size) {
        double d = Math.sqrt((double) size);
        return Math.ceil(d);
    }

    /**
     * 设置层级关系
     * @param secondMap
     * @param assetLink
     */

    private void flushParentMap(Map<String, List<String>> secondMap, AssetLink assetLink) {
        List<String> idList = secondMap.get(assetLink.getParentAssetId());
        if (idList != null) {
            idList.add(assetLink.getAssetId());
        } else {
            idList = new ArrayList<>();
            idList.add(assetLink.getAssetId());
            secondMap.put(assetLink.getParentAssetId(), idList);
        }
    }

    /**
     * 设置层级关系
     * @param secondMap
     * @param assetLink
     */
    private void flushMap(Map<String, List<String>> secondMap, AssetLink assetLink) {
        List<String> idList = secondMap.get(assetLink.getAssetId());
        if (idList != null) {
            idList.add(assetLink.getParentAssetId());
        } else {
            idList = new ArrayList<>();
            idList.add(assetLink.getParentAssetId());
            secondMap.put(assetLink.getAssetId(), idList);
        }
    }

    // 处理品类型号使其均为二级品类型号
    private void processLinkCount(List<AssetLink> assetLinks, Map<String, String> categoryMap) throws Exception {
        // 作为缓存使用，提高效率
        // Map<String, String> cache = new HashMap<>();
        // List<AssetCategoryModel> all = iAssetCategoryModelService.getAll();
        // for (AssetLink assetLink : assetLinks) {
        // String categoryModel = assetLink.getCategoryModal().toString();
        // String cacheId = cache.get(categoryModel);
        // if (Objects.nonNull(cacheId)) {
        // assetLink.setCategoryModal(Integer.valueOf(cacheId));
        // } else {
        // String second = iAssetCategoryModelService.recursionSearchParentCategory(categoryModel, all,
        // categoryMap.keySet());
        // if (Objects.nonNull(second)) {
        // assetLink.setCategoryModal(Integer.valueOf(second));
        // cache.put(categoryModel, second);
        // }
        // }
        // categoryModel = assetLink.getParentCategoryModal().toString();
        // cacheId = cache.get(categoryModel);
        // if (Objects.nonNull(cacheId)) {
        // assetLink.setParentCategoryModal(Integer.valueOf(cacheId));
        // } else {
        // String second = iAssetCategoryModelService.recursionSearchParentCategory(categoryModel, all,
        // categoryMap.keySet());
        // if (Objects.nonNull(second)) {
        // assetLink.setParentCategoryModal(Integer.valueOf(second));
        // cache.put(categoryModel, second);
        // }
        // }
        // }
    }

    // public AssetTopologyAlarmResponse getAlarmTopology() throws Exception {
    // AssetQuery query = new AssetQuery();
    // initQuery(query);
    // query.setQueryDepartmentName(true);
    // query.setQueryVulCount(false);
    // query.setQueryPatchCount(false);
    // AssetQuery assetQuery = new AssetQuery();
    // assetQuery.setAreaIds(
    // DataTypeUtils.integerArrayToStringArray(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser()));
    // List<Integer> statusList = new ArrayList<>();
    // statusList.add(AssetStatusEnum.WAIT_RETIRE.getCode());
    // statusList.add(AssetStatusEnum.NET_IN.getCode());
    // assetQuery.setAssetStatusList(statusList);
    // List<AssetLink> assetLinks = assetLinkRelationDao.findLinkRelation(assetQuery);
    // List<String> assetIdList = new ArrayList<>();
    // for (AssetLink assetLink : assetLinks) {
    // if (!assetIdList.contains(assetLink.getAssetId())) {
    // assetIdList.add(assetLink.getAssetId());
    // }
    // if (!assetIdList.contains(assetLink.getParentAssetId())) {
    // assetIdList.add(assetLink.getParentAssetId());
    // }
    // }
    // if (CollectionUtils.isEmpty(assetIdList)) {
    // AssetTopologyAlarmResponse assetTopologyAlarmResponse = new AssetTopologyAlarmResponse();
    // assetTopologyAlarmResponse.setStatus("success");
    // assetTopologyAlarmResponse.setVersion("");
    // assetTopologyAlarmResponse.setData(null);
    // return assetTopologyAlarmResponse;
    // }
    // query.setIds(DataTypeUtils.integerArrayToStringArray(assetIdList));
    // Integer count = assetTopologyDao.findTopologyListAssetCount(query);
    // if (count != null && count > 0) {
    // List<Asset> assetList = assetTopologyDao.findTopologyListAsset(query);
    // List<AssetResponse> assetResponseList = converter.convert(assetList, AssetResponse.class);
    // AssetQuery alarmQuery = new AssetQuery();
    // alarmQuery.setIds(DataTypeUtils.integerArrayToStringArray(assetIdList));
    // List<IdCount> idCounts = assetDao.queryAlarmCountByAssetIds(alarmQuery);
    // setListAreaName(assetResponseList);
    // setAlarmCount(assetResponseList, idCounts);
    // assetResponseList.sort(Comparator.comparingInt(o -> -Integer.valueOf(o.getAlarmCount())));
    // AssetTopologyAlarmResponse assetTopologyAlarmResponse = new AssetTopologyAlarmResponse();
    // assetTopologyAlarmResponse.setStatus("success");
    // assetTopologyAlarmResponse.setVersion("");
    // List<Map> topologyAlarms = transferAssetToMap(assetResponseList);
    // assetTopologyAlarmResponse.setData(topologyAlarms);
    // return assetTopologyAlarmResponse;
    // }
    // return null;
    // }

    // public AssetTopologyAlarmResponse getAlarmTopologyFake() throws Exception {
    // HashMap map = new HashMap();
    // AssetQuery query = new AssetQuery();
    // map.put("name", "网络设备");
    // AssetCategoryModel assetCategoryModel = (AssetCategoryModel) assetCategoryModelDao.getByWhere(map).get(0);
    // query.setAreaIds(
    // DataTypeUtils.integerArrayToStringArray(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser()));
    // query.setAssetStatusList(getAssetUseableStatus());
    // query.setCategoryModels(DataTypeUtils.integerArrayToStringArray(
    // iAssetCategoryModelService.findAssetCategoryModelIdsById(assetCategoryModel.getId())));
    // List<Asset> netAssetList = assetTopologyDao.selectFakeAsset(query);
    // map.put("name", "计算设备");
    // query.setIds(new String[] { "62163", "62198", "5583", "62019", "62271", "62794" });
    // assetCategoryModel = (AssetCategoryModel) assetCategoryModelDao.getByWhere(map).get(0);
    // query.setCategoryModels(DataTypeUtils.integerArrayToStringArray(
    // iAssetCategoryModelService.findAssetCategoryModelIdsById(assetCategoryModel.getId())));
    // List<Asset> pcAssetList = assetTopologyDao.selectFakeAsset(query);
    // List<Asset> assets = new ArrayList<>();
    // assets.addAll(netAssetList);
    // assets.addAll(pcAssetList);
    // List<AssetResponse> assetResponseList = converter.convert(assets, AssetResponse.class);
    // assetResponseList.sort(Comparator.comparingInt(o -> -Integer.valueOf(o.getAlarmCount())));
    // AssetTopologyAlarmResponse assetTopologyAlarmResponse = new AssetTopologyAlarmResponse();
    // assetTopologyAlarmResponse.setStatus("success");
    // assetTopologyAlarmResponse.setVersion("");
    // List<Map> topologyAlarms = transferAssetToMap(assetResponseList);
    // assetTopologyAlarmResponse.setData(topologyAlarms);
    // return assetTopologyAlarmResponse;
    // }

    private List<Map> transferAssetToMap(List<AssetResponse> assetResponseList) {
        List<Map> topologyAlarms = new ArrayList<>();
        for (AssetResponse assetResponse : assetResponseList) {
            Map<String, Object> map = new HashMap();
            map.put("ip", assetResponse.getIp());
            map.put("os", assetResponse.getOperationSystemName());
            map.put("person_name", assetResponse.getResponsibleUserName());
            map.put("alert", assetResponse.getAlarmCount());
            map.put("asset_id",
                aesEncoder.encode(assetResponse.getStringId(), LoginUserUtil.getLoginUser().getUsername()));
            map.put("asset_name", assetResponse.getName());
            map.put("firewall", null);
            map.put("rank", null);
            map.put("web", null);
            map.put("malware", null);
            map.put("iep", null);
            map.put("access", null);
            map.put("mail", null);
            map.put("loophole", null);
            map.put("infosystem", null);
            map.put("communicate", null);
            map.put("outreach", null);
            map.put("c2", null);
            map.put("database", null);
            map.put("oa", null);
            map.put("invade", null);
            map.put("credit", null);
            map.put("protected", null);
            topologyAlarms.add(map);
        }
        return topologyAlarms;
    }

    private void initQuery(AssetQuery query) throws Exception {
        if (query.getAreaIds() == null || query.getAreaIds().length == 0) {
            query.setAreaIds(
                DataTypeUtils.integerArrayToStringArray(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser()));
        }
        setStatusQuery(query);
    }
}
