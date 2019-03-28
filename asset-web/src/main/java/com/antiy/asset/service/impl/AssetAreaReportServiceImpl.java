package com.antiy.asset.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.antiy.asset.util.ReportDateUtils;
import com.antiy.asset.vo.request.AssetAreaReportRequest;
import com.antiy.asset.vo.response.ReportData;
import com.antiy.biz.util.RedisKeyUtil;
import com.antiy.biz.util.RedisUtil;
import com.antiy.common.base.SysArea;
import com.antiy.common.enums.ModuleEnum;
import com.antiy.common.utils.DataTypeUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetReportDao;
import com.antiy.asset.service.IAssetAreaReportService;
import com.antiy.asset.vo.request.ReportQueryRequest;
import com.antiy.asset.vo.response.AssetReportResponse;

/**
 * @author: zhangbing
 * @date: 2019/3/26 14:09
 * @description:
 */
@Service
public class AssetAreaReportServiceImpl implements IAssetAreaReportService {

    @Resource
    private AssetReportDao assetReportDao;
    @Resource
    private RedisUtil      redisUtil;

    @Override
    public AssetReportResponse getAssetWithArea(ReportQueryRequest reportRequest) {
        AssetReportResponse assetReportResponse = new AssetReportResponse();
        List<ReportData> list = Lists.newArrayList();
        List<AssetAreaReportRequest> oldAssetAreaIds = reportRequest.getAssetAreaIds();
        // 1.查询TOP5的区域信息
        List<Integer> topAreaIds = getTopFive(reportRequest);
        // 2. 初始化横坐标信息
        List<AssetAreaReportRequest> assetAreaIds = reportRequest.getAssetAreaIds().stream()
            .filter(area -> topAreaIds.contains(area.getParentAreaId())).collect(Collectors.toList());
        // 横坐标
        List<String> abscissa = Lists.newArrayList();
        ReportDateUtils.getDate(DataTypeUtils.stringToInteger(reportRequest.getTimeType()),
            reportRequest.getStartTime(), reportRequest.getEndTime()).forEach((k, v) -> {
                abscissa.add(v);
            });
        assetReportResponse.setDate(abscissa);

        // 3.获取起始时间初始值
        List<Map<String, Integer>> initData = assetReportDao.queryAssetWithAreaByDate(reportRequest.getAssetAreaIds(),
            reportRequest.getStartTime());
        // 计算初始值
        topAreaIds.stream().forEach(top -> {
            boolean flag = false;
            for (Map iData : initData) {
                if (top.equals(iData.get("areaId"))) {
                    flag = true;
                }
            }
            if (!flag) {
                Map m = new HashMap(2);
                m.put("areaId", top);
                m.put("assetCount", 0);
                initData.add(m);
            }
        });
        // 4.获取每个地区在每个时间区间的增量
        List<Map<String, Integer>> addData = assetReportDao.queryAddAssetWithArea(reportRequest.getAssetAreaIds(),
            reportRequest.getStartTime(), reportRequest.getEndTime());
        topAreaIds.stream().forEach(top -> {
            ReportData reportData = new ReportData();
            //区域在区间总数
            List<Integer> total = Lists.newArrayList();
            //区域在区间增量
            List<Integer> add = Lists.newArrayList();
            String key = RedisKeyUtil.getKeyWhenGetObject(ModuleEnum.SYSTEM.getType(), SysArea.class, top);
            try {
                SysArea sysArea = redisUtil.getObject(key, SysArea.class);
                reportData.setClassify(sysArea.getFullName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 循环横坐标[2019-01,2019-02......]
            for (int i = 0; i < abscissa.size(); i++) {
                //遍历增量数据
                for (Map<String, Integer> data : addData) {
                    if (abscissa.get(i).equals(data.get("date"))) {
                        add.add(data.get(top));
                    } else {
                        add.add(0);
                    }
                }
            }
        });
        // 3.组装基础数据和总数

        return null;
    }

    private AssetReportResponse getAreaReportData(String timeType) {
        AssetReportResponse assetReportResponse = new AssetReportResponse();

        // 本周
        if ("1".equals(timeType)) {
            // assetReportResponse.setDate(ReportDateUtils.getDayOfWeek().values());
        }
        return null;

    }

    private List<Integer> getTopFive(ReportQueryRequest reportRequest) {

        // 1.查询当前条件所有的区域信息
        List<Map<String, Integer>> allAssetCount = assetReportDao.getAllAssetWithArea(reportRequest);
        if (CollectionUtils.isEmpty(allAssetCount)) {
            return null;
        }

        // 2.排序区域名字信息
        List<String> areaTop = getAreaSort(allAssetCount);

        // 3.获取前五个,并且返回区域Id
        if (areaTop.size() > 5) {
            areaTop = areaTop.subList(0, 5);
        }
        List<Integer> areaId = new ArrayList<>();
        areaTop.stream().forEach(areaName -> {
            reportRequest.getAssetAreaIds().forEach(assetAreaReportRequest -> {
                if (assetAreaReportRequest.getParentAreaName().equals(areaName)) {
                    areaId.add(assetAreaReportRequest.getParentAreaId());
                }
            });
        });
        return areaId;
    }

    /**
     * 获取区域统计数据的排序
     * @param allAssetCount
     * @return
     */
    private List<String> getAreaSort(List<Map<String, Integer>> allAssetCount) {
        Map<String, Integer> countMaps = new HashMap<>();
        for (Map<String, Integer> maps : allAssetCount) {
            if (MapUtils.isNotEmpty(maps)) {
                maps.keySet().stream().forEach(key -> {
                    if (countMaps.containsKey(key)) {
                        Integer count = countMaps.get(key);
                        count = count + maps.get(key);
                        countMaps.put(key, count);
                    } else {
                        countMaps.put(key, maps.get(key));
                    }
                });
            }
        }
        return sortByValueDescending(countMaps).entrySet().stream().map(entry -> entry.getKey())
            .collect(Collectors.toList());
    }

    private <K, V extends Comparable<? super V>> Map<K, V> sortByValueDescending(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                int compare = (o1.getValue()).compareTo(o2.getValue());
                return -compare;
            }
        });

        Map<K, V> result = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
}
