package com.antiy.asset.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang.BooleanUtils;
import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetReportDao;
import com.antiy.asset.service.IAssetAreaReportService;
import com.antiy.asset.templet.ReportForm;
import com.antiy.asset.util.ReportDateUtils;
import com.antiy.asset.vo.request.AssetAreaReportRequest;
import com.antiy.asset.vo.request.ReportQueryRequest;
import com.antiy.asset.vo.response.AssetReportResponse;
import com.antiy.asset.vo.response.AssetReportTableResponse;
import com.antiy.asset.vo.response.ReportData;
import com.antiy.asset.vo.response.ReportTableHead;
import com.antiy.common.utils.DataTypeUtils;

/**
 * @author: zhangbing
 * @date: 2019/3/26 14:09
 * @description:
 */
@Service
public class AssetAreaReportServiceImpl implements IAssetAreaReportService {

    @Resource
    private AssetReportDao      assetReportDao;
    private static final String TRUE = "true";

    @Override
    public AssetReportResponse getAssetWithArea(ReportQueryRequest reportRequest) {
        dealArea(reportRequest);
        AssetReportResponse assetReportResponse = new AssetReportResponse();
        List<ReportData> reportDataList = Lists.newArrayList();
        // 总数
        List<Integer> allDataList = Lists.newArrayList();
        // 总增量
        List<Integer> allAddList = Lists.newArrayList();
        List<String> topAreaIds;
        // 是否需要top5
        if (BooleanUtils.isTrue(reportRequest.getTopFive())) {
            // 1.查询TOP5的区域信息
            topAreaIds = getTopFive(reportRequest);
            reportRequest.setAssetAreaIds(reportRequest.getAssetAreaIds().stream()
                .filter(report -> topAreaIds.contains(report.getParentAreaId())).collect(Collectors.toList()));
        } else {
            topAreaIds = reportRequest.getAssetAreaIds().stream().map(AssetAreaReportRequest::getParentAreaId)
                .collect(Collectors.toList());
        }
        // 横坐标
        List<String> abscissa = Lists.newArrayList();
        Map<String, String> dateList = ReportDateUtils.getDate(
            DataTypeUtils.stringToInteger(reportRequest.getTimeType()), reportRequest.getStartTime(),
            reportRequest.getEndTime());
        // 初始化横坐标名称
        dateList.forEach((k, v) -> {
            abscissa.add(v);
        });
        assetReportResponse.setDate(abscissa);

        // 3.获取起始时间初始值
        List<Map<String, Integer>> initData = assetReportDao.queryAssetWithAreaByDate(reportRequest.getAssetAreaIds(),
            reportRequest.getStartTime());
        // 计算初始值
        if (CollectionUtils.isNotEmpty(topAreaIds)) {
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
                    m.put("assetCount", Integer.valueOf(0));
                    initData.add(m);
                }
            });
        }
        // 4.获取每个地区在每个时间区间的增量
        List<Map<String, String>> addData = assetReportDao.queryAddAssetWithArea(reportRequest.getAssetAreaIds(),
            reportRequest.getStartTime(), reportRequest.getEndTime(),
            ReportDateUtils.getTimeType(DataTypeUtils.stringToInteger(reportRequest.getTimeType())));
        topAreaIds.stream().forEach(top -> {
            ReportData reportData = new ReportData();
            // 区域在区间总数
            List<Integer> totalList = Lists.newArrayList();
            // 区域在区间增量
            List<Integer> addList = Lists.newArrayList();
            reportData.setClassify(getAreaNameById(top, reportRequest.getAssetAreaIds()));
            // 循环横坐标[2019-01,2019-02......]
            for (String date : dateList.keySet()) {
                boolean flag = false;
                // 遍历增量数据
                for (Map<String, String> data : addData) {
                    if (date.equals(data.get("date"))) {
                        addList.add(DataTypeUtils.stringToInteger(
                            String.valueOf(data.get(getAreaNameById(top, reportRequest.getAssetAreaIds())))));
                        flag = true;
                    }
                }
                if (!flag) {
                    addList.add(Integer.valueOf(0));
                }
            }
            // 计算每个区间总数
            for (int i = 0; i < addList.size(); i++) {
                // 第一列，初始值加上本区间增量
                if (i == 0) {
                    for (Map<String, Integer> init : initData) {
                        if (top.equals(init.get("areaId"))) {
                            totalList.add(DataTypeUtils.stringToInteger(String.valueOf(init.get("assetCount")))
                                          + Integer.valueOf(addList.get(i) + ""));
                            break;
                        }
                    }
                }
                // 上个区间加上本区间增量
                else {
                    int t = Integer.valueOf(totalList.get(i - 1) + "");
                    int a = Integer.valueOf(addList.get(i) + "");
                    totalList.add(t + a);
                }
            }
            // 设置增量数据
            reportData.setAdd(addList);
            // 设置区域区间总数
            reportData.setData(totalList);
            reportDataList.add(reportData);
        });
        for (int i = 0; i < abscissa.size(); i++) {
            int allData = 0;
            int allAdd = 0;
            for (int j = 0; j < topAreaIds.size(); j++) {
                allData += reportDataList.get(j).getData().get(i);
                allAdd += reportDataList.get(j).getAdd().get(i);
            }
            allDataList.add(allData);
            allAddList.add(allAdd);
        }
        // 3.组装基础数据和总数
        assetReportResponse.setList(reportDataList);
        assetReportResponse.setAlldata(allDataList);
        assetReportResponse.setAllAdd(allAddList);
        return assetReportResponse;
    }

    private void dealArea(ReportQueryRequest reportRequest) {
        if (!Objects.isNull(reportRequest)) {
            List<AssetAreaReportRequest> assetAreaReportRequestList = reportRequest.getAssetAreaIds();
            if (CollectionUtils.isNotEmpty(assetAreaReportRequestList) && assetAreaReportRequestList.size() > 1
                && assetAreaReportRequestList.stream()
                    .filter(a -> reportRequest.getTopAreaId().equals(a.getParentAreaId()))
                    .collect(Collectors.toList()).size() == 0) {
                AssetAreaReportRequest assetAreaReportRequest = new AssetAreaReportRequest();
                assetAreaReportRequest.setChildrenAradIds(Lists.newArrayList());
                assetAreaReportRequest.setParentAreaId(reportRequest.getTopAreaId());
                assetAreaReportRequest.setParentAreaName(reportRequest.getTopAreaName());
                assetAreaReportRequestList.add(assetAreaReportRequestList.size(), assetAreaReportRequest);
            }
        }
    }

    @Override
    public AssetReportTableResponse queryAreaTable(ReportQueryRequest reportQueryRequest) {
        int index = 1;
        AssetReportResponse assetReportResponse = this.getAssetWithArea(reportQueryRequest);
        AssetReportTableResponse assetReportTableResponse = new AssetReportTableResponse();
        List<Map<String, String>> rows = Lists.newArrayList();
        List<ReportTableHead> children = Lists.newArrayList();
        assetReportTableResponse.setFormName("资产区域统计表格");
        // 表头
        children.add(new ReportTableHead("", "classifyName"));
        for (String date : assetReportResponse.getDate()) {
            ReportTableHead reportTableHead = new ReportTableHead();
            reportTableHead.setName(date);
            reportTableHead.setKey(String.valueOf(index++));
            children.add(reportTableHead);
        }
        assetReportTableResponse.setChildren(children);
        // 数据
        for (ReportData reportData : assetReportResponse.getList()) {
            index = 1;
            Map map = new HashMap();
            map.put("classifyName", reportData.getClassify());
            for (Integer data : reportData.getData()) {
                map.put(String.valueOf(index++), String.valueOf(data));
            }
            rows.add(map);
        }
        // 数据
        Map total = new HashMap();
        total.put("classifyName", "总数");
        index = 1;
        for (Integer allData : assetReportResponse.getAlldata()) {
            total.put(String.valueOf(index++), String.valueOf(allData));
        }
        rows.add(total);
        Map add = new HashMap();
        add.put("classifyName", "新增数量");
        index = 1;
        for (Integer addData : assetReportResponse.getAllAdd()) {
            add.put(String.valueOf(index++), String.valueOf(addData));
        }
        rows.add(add);
        assetReportTableResponse.setRows(rows);
        return assetReportTableResponse;
    }

    /**
     * 导出报表数据
     * @param reportQueryRequest
     * @returnK
     */
    @Override
    public ReportForm exportAreaTable(ReportQueryRequest reportQueryRequest) {
        ReportForm reportForm = new ReportForm();
        AssetReportResponse assetReportResponse = this.getAssetWithArea(reportQueryRequest);
        // 表格标题
        reportForm.setTitle("资产区域报表数据");
        // 表格行头
        reportForm.setHeaderList(assetReportResponse.getDate());
        // 表格列头
        List columnList = assetReportResponse.getList().stream().map(ReportData::getClassify)
            .collect(Collectors.toList());
        columnList.add("总数");
        columnList.add("新增数量");
        List<List<Integer>> dataList = Lists.newArrayList();
        reportForm.setColumnList(columnList);
        List<ReportData> list = assetReportResponse.getList();
        list.stream().forEach(reportData -> {
            dataList.add(reportData.getData());
        });
        dataList.add(assetReportResponse.getAlldata());
        dataList.add(assetReportResponse.getAllAdd());
        String[][] datas = new String[columnList.size()][assetReportResponse.getDate().size()];
        for (int i = 0; i < dataList.size(); i++) {
            List<Integer> data = dataList.get(i);
            for (int j = 0; j < data.size(); j++) {
                datas[i][j] = String.valueOf(data.get(j));
            }
        }
        reportForm.setData(datas);
        return reportForm;
    }

    private String getAreaNameById(String id, List<AssetAreaReportRequest> assetAreaIds) {
        for (AssetAreaReportRequest assetAreaReportRequest : assetAreaIds) {
            if (id.equals(assetAreaReportRequest.getParentAreaId())) {
                return assetAreaReportRequest.getParentAreaName();
            }
        }
        return "";
    }

    private AssetReportResponse getAreaReportData(String timeType) {
        AssetReportResponse assetReportResponse = new AssetReportResponse();

        // 本周
        if ("1".equals(timeType)) {
            // assetReportResponse.setDate(ReportDateUtils.getDayOfWeek().values());
        }
        return null;

    }

    private List<String> getTopFive(ReportQueryRequest reportRequest) {

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
        List<String> areaId = new ArrayList<>();
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
