package com.antiy.asset.service.impl;

import static com.antiy.asset.util.StatusEnumUtil.getAssetUseableStatus;

import java.util.*;
import java.util.stream.Stream;

import javax.annotation.Resource;

import com.antiy.asset.vo.enums.AssetCategoryEnum;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.QueryCondition;
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

    @Override
    public List<String> queryCategoryModels() {
        return assetLinkRelationDao.queryCategoryModes();
    }

    @Override
    public TopologyAssetResponse queryAssetNodeInfo(String assetId) throws Exception {
        QueryCondition queryCondition = new QueryCondition();
        queryCondition.setPrimaryKey(assetId);
        AssetOuterResponse assetOuterResponse = iAssetService.getByAssetId(queryCondition);
        AssetResponse assetResponse = assetOuterResponse.getAsset();
        TopologyAssetResponse topologyAssetResponse = new TopologyAssetResponse();
        TopologyAssetResponse.TopologyNodeAsset topologyNodeAsset = topologyAssetResponse.new TopologyNodeAsset();
        topologyNodeAsset.setAsset_id(assetResponse.getStringId());
        topologyNodeAsset.setAssetGroup(assetResponse.getAssetGroup());
        topologyNodeAsset.setHouseLocation(assetResponse.getHouseLocation());
        if (CollectionUtils.isNotEmpty(assetResponse.getIp())) {
            StringBuilder ip = new StringBuilder();
            assetResponse.getIp().forEach(x -> ip.append(x.getIp()).append(","));
            topologyNodeAsset.setIp(ip.toString().substring(0, ip.toString().length() - 1));
        }
        topologyNodeAsset.setInstallType(assetResponse.getInstallTypeName());
        topologyNodeAsset.setOs(assetResponse.getOperationSystemName());
        topologyNodeAsset.setTelephone(assetResponse.getContactTel());
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
        AssetQuery query = new AssetQuery();
        query.setAreaIds(
            DataTypeUtils.integerArrayToStringArray(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser()));
        // 1.1资产状态除去不予登记和登记
        List<Integer> assetStatusList = getAssetUseableStatus();
        query.setAssetStatusList(assetStatusList);
        // 1.2所有资产
        resultMap.put("total", assetDao.findCountByCategoryModel(query).toString());
        // 2.查询已管控的数量(已建立关联关系的资产数量)
        List<String> assetIdList = getLinkRelationIdList();
        resultMap.put("control", assetIdList.size() + "");
        AssetQuery alarmQuery = new AssetQuery();
        alarmQuery.setIds(DataTypeUtils.integerArrayToStringArray(assetIdList));
        if (assetIdList.size() > 0) {
            List<IdCount> idCounts = assetDao.queryAlarmCountByAssetIds(alarmQuery);
            // 查询已管控的产生告警的资产
            resultMap.put("warning", Integer.toString(idCounts.size()));
        } else {
            resultMap.put("warning", "0");
        }
        CountTopologyResponse countTopologyResponse = new CountTopologyResponse();
        List<Map<String, String>> list = new ArrayList<>();
        list.add(resultMap);
        countTopologyResponse.setList(list);
        countTopologyResponse.setStatus("success");
        return countTopologyResponse;
    }

    private List<String> getLinkRelationIdList() {
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
        return assetIdList;
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
        query.setQueryAlarmCount(true);
        List<String> assetIdList = getLinkRelationIdList();
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
            setListAreaName(assetResponseList);
            assetResponseList.sort(Comparator.comparingInt(o -> -Integer.valueOf(o.getAlarmCount())));
            PageResult pageResult = new PageResult<>(query.getPageSize(), count, query.getCurrentPage(),
                assetResponseList);
            TopologyListResponse topologyListResponse = new TopologyListResponse();
            List<TopologyListResponse.TopologyNode> topologyNodes = new ArrayList<>();
            for (AssetResponse assetResponse : assetResponseList) {
                TopologyListResponse.TopologyNode topologyNode = topologyListResponse.new TopologyNode();
                topologyNode.setAsset_area(assetResponse.getAreaName());
                topologyNode.setAsset_ip(assetResponse.getIps());
                topologyNode.setAsset_id(
                    aesEncoder.encode(assetResponse.getStringId(), LoginUserUtil.getLoginUser().getUsername()));
                topologyNode.setAsset_group(assetResponse.getAssetGroup());
                topologyNode.setAsset_type(AssetCategoryEnum.getNameByCode(assetResponse.getCategoryModel()));
                topologyNode.setPerson_name(assetResponse.getResponsibleUserName());
                topologyNode.setAsset_unrepair(assetResponse.getVulCount());
                topologyNode.setAsset_untreated_warning(assetResponse.getAlarmCount());
                topologyNode.setSystem_uninstall(assetResponse.getPatchCount());
                topologyNode.setAsset_name(assetResponse.getName());
                topologyNodes.add(topologyNode);
            }
            topologyListResponse.setData(topologyNodes);
            topologyListResponse.setPageSize(pageResult.getPageSize());
            topologyListResponse.setCurrentPage(pageResult.getCurrentPage());
            topologyListResponse.setStatus("success");
            topologyListResponse.setTotal(pageResult.getTotalRecords());
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
        List<Map<String, Object>> result = assetDao
            .countCategoryModel(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser(), getAssetUseableStatus());
        for (Map<String, Object> map : result) {
            TopologyCategoryCountResponse.CategoryResponse categoryResponse = topologyCategoryCountResponse.new CategoryResponse();
            categoryResponse.setNum(Integer.valueOf(map.get("value") + ""));
            categoryResponse.setAsset_name(AssetCategoryEnum.getNameByCode(Integer.valueOf(map.get("key").toString())));
            categoryResponseList.add(categoryResponse);
        }
        topologyCategoryCountResponse.setData(categoryResponseList);
        topologyCategoryCountResponse.setStatus("success");
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

    @Override
    public AssetTopologyIpSearchResposne queryListByIp(AssetQuery query) throws Exception {
        initQuery(query);
        query.setQueryDepartmentName(true);
        query.setQueryVulCount(false);
        query.setQueryPatchCount(false);
        List<String> assetIdList = getLinkRelationIdList();
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

    public AssetTopologyAlarmResponse getAlarmTopology() throws Exception {
        AssetQuery query = new AssetQuery();
        initQuery(query);
        query.setQueryVulCount(false);
        query.setQueryAlarmCount(true);
        query.setQueryPatchCount(false);
        List<String> assetIdList = getLinkRelationIdList();
        if (CollectionUtils.isEmpty(assetIdList)) {
            AssetTopologyAlarmResponse assetTopologyAlarmResponse = new AssetTopologyAlarmResponse();
            assetTopologyAlarmResponse.setStatus("success");
            assetTopologyAlarmResponse.setVersion("");
            assetTopologyAlarmResponse.setData(null);
            return assetTopologyAlarmResponse;
        }
        query.setIds(DataTypeUtils.integerArrayToStringArray(assetIdList));
        Integer count = assetTopologyDao.findTopologyListAssetCount(query);
        if (count != null && count > 0) {
            List<Asset> assetList = assetTopologyDao.findTopologyListAsset(query);
            List<AssetResponse> assetResponseList = converter.convert(assetList, AssetResponse.class);
            AssetQuery alarmQuery = new AssetQuery();
            alarmQuery.setIds(DataTypeUtils.integerArrayToStringArray(assetIdList));
            setListAreaName(assetResponseList);
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
            map.put("ip", assetResponse.getIps());
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
