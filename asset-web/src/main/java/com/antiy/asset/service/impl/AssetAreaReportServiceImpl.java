package com.antiy.asset.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
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

    @Override
    public AssetReportResponse getAssetWithArea(ReportQueryRequest reportRequest) {

        // 1.查询TOP5的区域信息
        List<Integer> topAreaIds = getTopFive(reportRequest);
        // 2. 初始化横坐标信息

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
