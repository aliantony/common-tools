package com.antiy.asset.service.impl;

import static com.antiy.asset.util.StatusEnumUtil.getAssetUseableStatus;

import java.util.*;
import java.util.stream.Stream;

import javax.annotation.Resource;

import com.antiy.asset.entity.*;
import com.antiy.asset.vo.enums.AssetSecondCategoryEnum;
import com.antiy.asset.vo.query.AssetDetialCondition;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetCategoryModelDao;
import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.AssetLinkRelationDao;
import com.antiy.asset.dao.AssetTopologyDao;
import com.antiy.asset.intergration.EmergencyClient;
import com.antiy.asset.intergration.OperatingSystemClient;
import com.antiy.asset.service.IAssetCategoryModelService;
import com.antiy.asset.service.IAssetService;
import com.antiy.asset.service.IAssetTopologyService;
import com.antiy.asset.util.ArrayTypeUtil;
import com.antiy.asset.util.Constants;
import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.asset.vo.enums.InstallType;
import com.antiy.asset.vo.query.AssetQuery;
import com.antiy.asset.vo.query.AssetTopologyQuery;
import com.antiy.asset.vo.response.*;
import com.antiy.biz.util.RedisKeyUtil;
import com.antiy.biz.util.RedisUtil;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.ObjectQuery;
import com.antiy.common.base.PageResult;
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
    @Value("#{'${topology.middlePoint}'.split(',')}")
    private List<Integer>                       middlePoint;
    @Value("#{'${topology.cameraPos}'.split(',')}")
    private List<Integer>                       cameraPos;
    @Value("#{'${topology.targetPos}'.split(',')}")
    private List<Integer>                       targetPos;
    @Value("${topology.first.level.space}")
    private Integer                             firstLevelSpacing;
    @Value("${topology.first.level.height}")
    private Integer                             firstLevelHeight;
    @Value("${topology.second.level.space}")
    private Integer                             secondLevelSpacing;
    @Value("${topology.second.level.height}")
    private Integer                             secondLevelHeight;
    @Value("${topology.third.level.space}")
    private Integer                             thirdLevelSpacing;
    @Value("${topology.third.level.height}")
    private Integer                             thirdLevelHeight;

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
        TopologyAssetResponse.TopologyNodeAsset topologyNodeAsset = topologyAssetResponse.new TopologyNodeAsset();
        topologyNodeAsset.setAsset_id(assetResponse.getStringId());
        topologyNodeAsset.setAssetGroup(assetResponse.getAssetGroup());
        topologyNodeAsset.setHouseLocation(assetResponse.getHouseLocation());
        topologyNodeAsset.setIp(assetResponse.getIp());
        topologyNodeAsset.setInstallType(assetResponse.getInstallTypeName());
        topologyNodeAsset.setOs(assetResponse.getOperationSystemName());
        topologyNodeAsset.setTelephone(assetResponse.getContactTel());
        topologyNodeAsset.setLocation(assetResponse.getLocation());
        topologyNodeAsset.setAsset_name(assetResponse.getName());
        topologyAssetResponse.setStatus("success");
        topologyAssetResponse.setVersion(assetResponse.getNumber());
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
            // PageResult pageResult = new PageResult<>(query.getPageSize(), count, query.getCurrentPage(),
            // assetResponseList);
            TopologyListResponse topologyListResponse = new TopologyListResponse();
            List<TopologyListResponse.TopologyNode> topologyNodes = new ArrayList<>();
            for (AssetResponse assetResponse : assetResponseList) {
                TopologyListResponse.TopologyNode topologyNode = topologyListResponse.new TopologyNode();
                topologyNode.setAsset_area(assetResponse.getAreaName());
                topologyNode.setAsset_ip(assetResponse.getIp());
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

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        Map<K, V> result = new LinkedHashMap<>();
        Stream<Map.Entry<K, V>> st = map.entrySet().stream();

        st.sorted(Comparator.comparing(e -> e.getValue())).forEach(e -> result.put(e.getKey(), e.getValue()));

        return result;
    }

    /**
     * 获取拓扑图
     * @return
     * @throws Exception
     */
    public AssetTopologyNodeResponse getTopologyGraph() throws Exception {
        AssetTopologyNodeResponse assetTopologyNodeResponse = new AssetTopologyNodeResponse();
        assetTopologyNodeResponse.setStatus("success");
        List<Map<String, AssetTopologyRelation>> dataList = new ArrayList<>();
        AssetTopologyRelation assetTopologyRelation = new AssetTopologyRelation();
        // 设置拓扑图信息
        assetTopologyRelation.setInfo("");
        // 设置中心点
        assetTopologyRelation.setMiddlePoint(middlePoint);
        // 设置角度
        AssetTopologyViewAngle assetTopologyViewAngle = new AssetTopologyViewAngle();
        assetTopologyViewAngle.setCameraPos(cameraPos);
        assetTopologyViewAngle.setTargetPos(targetPos);
        assetTopologyRelation.setView_angle(assetTopologyViewAngle);

        AssetTopologyJsonData assetTopologyJsonData = new AssetTopologyJsonData();
        Map<String, List<List<Object>>> jsonData = new HashMap<>();

        AssetQuery query = new AssetQuery();
        query.setAreaIds(
            DataTypeUtils.integerArrayToStringArray(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser()));
        List<Integer> statusList = new ArrayList<>();
        statusList.add(AssetStatusEnum.WAIT_RETIRE.getCode());
        statusList.add(AssetStatusEnum.NET_IN.getCode());
        query.setAssetStatusList(statusList);
        List<AssetLink> assetLinks = assetLinkRelationDao.findLinkRelation(query);
        // id加密
        for (AssetLink assetLink : assetLinks) {
            assetLink.setAssetId(aesEncoder.encode(assetLink.getAssetId(), LoginUserUtil.getLoginUser().getUsername()));
            assetLink.setParentAssetId(
                aesEncoder.encode(assetLink.getParentAssetId(), LoginUserUtil.getLoginUser().getUsername()));
        }
        Map<String, String> secondCategoryMap = iAssetCategoryModelService.getSecondCategoryMap();
        processLinkCount(assetLinks, secondCategoryMap);
        // 找到各个的层级节点
        Map<String, List<String>> firstMap = new HashMap<>();
        Map<String, List<String>> secondMap = new HashMap<>();
        Map<String, List<String>> thirdMap = new HashMap<>();
        Map<String, List<String>> secondThirdMap = new LinkedHashMap<>();
        String networkDeviceId = "";
        for (Map.Entry<String, String> entry : secondCategoryMap.entrySet()) {
            if (Objects.equals(entry.getValue(), AssetSecondCategoryEnum.NETWORK_DEVICE.getMsg())) {
                networkDeviceId = entry.getKey();
            }
        }
        for (AssetLink assetLink : assetLinks) {
            if (Objects.equals(String.valueOf(assetLink.getCategoryModal()), networkDeviceId)
                && Objects.equals(String.valueOf(assetLink.getParentCategoryModal()), networkDeviceId)) {
                // 构造第一，二级层级节点关系
                flushMap(firstMap, assetLink);
                flushParentMap(firstMap, assetLink);
                // 构造第二级节点关系
                flushMap(secondMap, assetLink);
                flushParentMap(secondMap, assetLink);

            } else {
                // 构造第二，三级层级节点关系
                if (Objects.equals(String.valueOf(assetLink.getCategoryModal()), networkDeviceId)) {
                    flushMap(secondMap, assetLink);
                } else {
                    flushParentMap(secondThirdMap, assetLink);
                    flushMap(thirdMap, assetLink);
                }
                if (Objects.equals(String.valueOf(assetLink.getParentCategoryModal()), networkDeviceId)) {
                    flushParentMap(secondMap, assetLink);
                } else {
                    flushMap(secondThirdMap, assetLink);
                    flushParentMap(thirdMap, assetLink);
                }
            }
        }
        // 去掉第一层中不符合要求的数据
        for (Map.Entry<String, List<String>> entry : secondMap.entrySet()) {
            firstMap.remove(entry.getKey());
        }
        // 去掉第二层中不符合要求的数据
        for (Map.Entry<String, List<String>> entry : firstMap.entrySet()) {
            secondMap.remove(entry.getKey());
        }

        // 构造第一层坐标数据
        List<List<Object>> simTopoRouter = new ArrayList<>();
        simTopoRouter.addAll(settingFirstLevelCoordinates(firstMap));
        Map<String, List<Integer>> secondCoordinates = new HashMap<>();
        // 构造第二层坐标数据
        simTopoRouter.addAll(settingSecondLevelCoordinates(secondMap, secondCoordinates));
        jsonData.put("sim_topo-router", simTopoRouter);
        // 构造第三层坐标数据
        List<List<Object>> simTopoHost = new ArrayList<>(
            settingThirdLevelCoordinates(secondThirdMap, secondCoordinates));
        jsonData.put("sim_topo-host", simTopoHost);
        assetTopologyJsonData.setJson_data0(jsonData);
        assetTopologyRelation.setJson_data(assetTopologyJsonData);
        Map<String, AssetTopologyRelation> dataMap = new HashMap<>(2);
        dataMap.put("relationship", assetTopologyRelation);
        dataList.add(dataMap);
        assetTopologyNodeResponse.setData(dataList);
        return assetTopologyNodeResponse;
    }

    @Override
    public AssetTopologyIpSearchResposne queryListByIp(AssetQuery query) throws Exception {
        initQuery(query);
        query.setQueryDepartmentName(true);
        query.setQueryVulCount(false);
        query.setQueryPatchCount(false);
        List<Asset> assetList = assetTopologyDao.findTopologyListAsset(query);
        AssetTopologyIpSearchResposne assetTopologyIpSearchResposne = new AssetTopologyIpSearchResposne();
        assetTopologyIpSearchResposne.setStatus("success");
        List<AssetTopologyIpSearchResposne.IpSearch> ipSearchList = transferAssetToIpSearch(assetList);
        assetTopologyIpSearchResposne.setData(ipSearchList);
        return assetTopologyIpSearchResposne;
    }

    private List<AssetTopologyIpSearchResposne.IpSearch> transferAssetToIpSearch(List<Asset> assetList) {
        List<AssetTopologyIpSearchResposne.IpSearch> ipSearchList = new ArrayList<>();
        for (Asset asset : assetList) {
            AssetTopologyIpSearchResposne.IpSearch ipSearch = new AssetTopologyIpSearchResposne().new IpSearch();
            ipSearch.setAsset_id(aesEncoder.encode(asset.getStringId(), LoginUserUtil.getLoginUser().getUsername()));
            ipSearch.setDepartment_name(asset.getDepartmentName());
            ipSearch.setIp(asset.getIp());
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
    private List<List<Object>> settingFirstLevelCoordinates(Map<String, List<String>> map) {
        int size = getSize(map.size());
        List<List<Object>> dataList = new ArrayList<>();
        int space = firstLevelSpacing;
        int height = firstLevelHeight;
        int i = 0;
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            getDataList(size, dataList, space, height, i, entry);
            i++;

        }
        return dataList;
    }

    /**
     * 获取第二层数据信息
     * @param map
     * @param secondCoordinates
     * @return
     */
    private List<List<Object>> settingSecondLevelCoordinates(Map<String, List<String>> map,
                                                             Map<String, List<Integer>> secondCoordinates) {
        int size = getSize(map.size());
        List<List<Object>> dataList = new ArrayList<>();
        int space = secondLevelSpacing;
        int height = secondLevelHeight;
        int i = 0;
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            List<Integer> coordinateList = getDataList(size, dataList, space, height, i, entry);
            secondCoordinates.put(entry.getKey(), coordinateList);
            i++;
        }
        return dataList;
    }

    private List<Integer> getDataList(int size, List<List<Object>> dataList, int space, int height, int i,
                                      Map.Entry<String, List<String>> entry) {
        List<Integer> coordinateList = getCoordinate(size, i, space, height);
        // 设置坐标数据
        List<Object> point = new ArrayList<>();
        point.add(entry.getKey());
        point.addAll(coordinateList);
        point.add(entry.getValue());
        point.add(coordinateList.get(0));
        point.add(coordinateList.get(2));
        point.add("");
        dataList.add(point);
        return coordinateList;
    }

    /**
     * 获取第三层数据信息
     * @param map
     * @param secondCoordinates
     * @return
     */
    private List<List<Object>> settingThirdLevelCoordinates(Map<String, List<String>> map,
                                                            Map<String, List<Integer>> secondCoordinates) {
        List<List<Object>> dataList = new ArrayList<>();
        // 间隔
        int space = thirdLevelSpacing;
        // 高度
        int height = thirdLevelHeight;

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
                    List<Integer> coordinates = secondCoordinates.get(entry.getKey());
                    List<Object> point = new ArrayList<>();
                    int size = getSize(entry.getValue().size());
                    List<Integer> coordinateByParent = getCoordinateByParent(size, i, space, height, coordinates.get(0),
                        coordinates.get(2));
                    point.add(s);
                    point.addAll(coordinateByParent);
                    List<String> parent = new ArrayList<>();
                    parent.add(entry.getKey());
                    point.add(parent);
                    point.add(coordinateByParent.get(0));
                    point.add(coordinateByParent.get(2));
                    point.add("");
                    dataList.add(point);
                    cache.put(s, dataList.size() - 1);
                    i++;
                }
            }
        }
        return dataList;
    }

    /**
     * 获取自身坐标
     */
    private List<Integer> getCoordinate(int size, int i, int space, int height) {
        int x = (int) ((i / size - ((size - 1) / 2f)) * space);
        int y = (int) (((size - 1) / 2f - (i % size)) * space);
        List<Integer> coordinate = new ArrayList<>();
        coordinate.add(x);
        coordinate.add(height);
        coordinate.add(y);
        return coordinate;
    }

    /**
     * 根据上级坐标获取本身坐标
     */
    private List<Integer> getCoordinateByParent(int size, int i, int space, int height, int xx, int yy) {
        int x = (int) (((i / size - ((size - 1) / 2f)) * space) + xx);
        int y = (int) ((((size - 1) / 2f - (i % size)) * space) + yy);
        List<Integer> coordinate = new ArrayList<>();
        coordinate.add(x);
        coordinate.add(height);
        coordinate.add(y);
        return coordinate;
    }

    private int getSize(int size) {
        double d = Math.sqrt((double) size);
        if (isIntegerValue(d)) {
            return (int) d;
        } else {
            return (int) (d + 1);
        }

    }

    private static boolean isIntegerValue(double d) {
        return ((long) d) + 0.0 == d;
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
        Map<String, String> cache = new HashMap<>();
        List<AssetCategoryModel> all = iAssetCategoryModelService.getAll();
        for (AssetLink assetLink : assetLinks) {
            String categoryModel = assetLink.getCategoryModal().toString();
            String cacheId = cache.get(categoryModel);
            if (Objects.nonNull(cacheId)) {
                assetLink.setCategoryModal(Integer.valueOf(cacheId));
            } else {
                String second = iAssetCategoryModelService.recursionSearchParentCategory(categoryModel, all,
                    categoryMap.keySet());
                if (Objects.nonNull(second)) {
                    assetLink.setCategoryModal(Integer.valueOf(second));
                    cache.put(categoryModel, second);
                }
            }
            categoryModel = assetLink.getParentCategoryModal().toString();
            cacheId = cache.get(categoryModel);
            if (Objects.nonNull(cacheId)) {
                assetLink.setParentCategoryModal(Integer.valueOf(cacheId));
            } else {
                String second = iAssetCategoryModelService.recursionSearchParentCategory(categoryModel, all,
                    categoryMap.keySet());
                if (Objects.nonNull(second)) {
                    assetLink.setParentCategoryModal(Integer.valueOf(second));
                    cache.put(categoryModel, second);
                }
            }
        }
    }

    public AssetTopologyAlarmResponse getAlarmTopology() throws Exception {
        AssetQuery query = new AssetQuery();
        initQuery(query);
        query.setQueryDepartmentName(true);
        query.setQueryVulCount(false);
        query.setQueryPatchCount(false);
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
            AssetTopologyAlarmResponse assetTopologyAlarmResponse = new AssetTopologyAlarmResponse();
            assetTopologyAlarmResponse.setStatus("success");
            assetTopologyAlarmResponse.setVersion("");
            List<Map> topologyAlarms = transferAssetToMap(assetResponseList);
            assetTopologyAlarmResponse.setData(topologyAlarms);
            return assetTopologyAlarmResponse;
        }
        return null;
    }

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
