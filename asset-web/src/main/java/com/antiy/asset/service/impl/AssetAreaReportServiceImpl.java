package com.antiy.asset.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.antiy.asset.dao.AssetReportDao;
import com.antiy.asset.service.IAssetAreaReportService;
import com.antiy.asset.templet.ReportForm;
import com.antiy.asset.util.ExcelUtils;
import com.antiy.asset.util.ReportDateUtils;
import com.antiy.asset.vo.enums.AssetEventEnum;
import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.asset.vo.request.AssetAreaReportRequest;
import com.antiy.asset.vo.request.ReportQueryRequest;
import com.antiy.asset.vo.response.AssetReportResponse;
import com.antiy.asset.vo.response.AssetReportTableResponse;
import com.antiy.asset.vo.response.ReportData;
import com.antiy.asset.vo.response.ReportTableHead;
import com.antiy.biz.util.RedisKeyUtil;
import com.antiy.biz.util.RedisUtil;
import com.antiy.common.base.BusinessData;
import com.antiy.common.base.SysArea;
import com.antiy.common.enums.BusinessModuleEnum;
import com.antiy.common.enums.BusinessPhaseEnum;
import com.antiy.common.enums.ModuleEnum;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.utils.DataTypeUtils;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.ParamterExceptionUtils;

/**
 * @author: zhangbing
 * @date: 2019/3/26 14:09
 * @description:
 */
@Service
public class AssetAreaReportServiceImpl implements IAssetAreaReportService {
    private Logger              logger = LogUtils.get(this.getClass());
    @Resource
    private AssetReportDao      assetReportDao;
    @Resource
    private RedisUtil           redisUtil;
    private static final String TRUE   = "true";

    @Override
    public AssetReportResponse getAssetWithArea(ReportQueryRequest reportRequest) {
        reportRequest.setAssetStatusList(getStatusList());
        dealArea(reportRequest);
        AssetReportResponse assetReportResponse = new AssetReportResponse();
        List<ReportData> reportDataList = Lists.newArrayList();
        // 总数
        List<Integer> allDataList = Lists.newArrayList();
        // 总增量
        List<Integer> allAddList = Lists.newArrayList();
        List<Integer> topAreaIds;
        // 是否需要top5
        if (BooleanUtils.isTrue(reportRequest.getTopFive())) {
            // 1.查询TOP5的区域信息
            topAreaIds = getTopFive(reportRequest);
            reportRequest.setAssetAreaIds(reportRequest.getAssetAreaIds().stream()
                .filter(report -> topAreaIds.contains(DataTypeUtils.stringToInteger(report.getParentAreaId())))
                .collect(Collectors.toList()));
        } else {
            topAreaIds = reportRequest.getAssetAreaIds().stream().map(AssetAreaReportRequest::getParentAreaIdInteger)
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
            reportRequest.getStartTime(), reportRequest.getAssetStatusList());
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
        String timeType = ReportDateUtils.getTimeType(DataTypeUtils.stringToInteger(reportRequest.getTimeType()));
        // 如果为当前月，则显示为周
        Long mouth = ReportDateUtils.monthDiff(reportRequest.getStartTime(), reportRequest.getEndTime());
        if (mouth == 0 && "5".equals(reportRequest.getTimeType())) {
            timeType = "%u";
        }

        List<Map<String, String>> addData = assetReportDao.queryAddAssetWithArea(reportRequest.getAssetAreaIds(),
            reportRequest.getStartTime(), reportRequest.getEndTime(), timeType, reportRequest.getAssetStatusList());
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
            if (CollectionUtils.isNotEmpty(assetAreaReportRequestList) && assetAreaReportRequestList.size() >= 1
                && assetAreaReportRequestList.stream()
                    .filter(a -> reportRequest.getTopAreaId().equals(a.getParentAreaId())).collect(Collectors.toList())
                    .size() == 0) {
                AssetAreaReportRequest assetAreaReportRequest = new AssetAreaReportRequest();
                assetAreaReportRequest.setChildrenAradIds(Lists.newArrayList());
                assetAreaReportRequest.setParentAreaId(reportRequest.getTopAreaId());
                if (StringUtils.isBlank(reportRequest.getTopAreaName())) {
                    String key = RedisKeyUtil.getKeyWhenGetObject(ModuleEnum.SYSTEM.getType(), SysArea.class,
                        DataTypeUtils.stringToInteger(reportRequest.getTopAreaId()));
                    try {
                        SysArea sysArea = redisUtil.getObject(key, SysArea.class);
                        if (sysArea != null) {
                            assetAreaReportRequest.setParentAreaName(sysArea.getFullName());
                        } else {
                            ParamterExceptionUtils.isTrue(false, "获取顶级区域名称失败");
                        }
                    } catch (Exception e) {
                        logger.error("获取顶级区域名称失败", e);
                        ParamterExceptionUtils.isTrue(!StringUtils.equals("获取顶级区域名称失败", e.getMessage()), "获取顶级区域名称失败");
                    }
                } else {
                    assetAreaReportRequest.setParentAreaName(reportRequest.getTopAreaName());
                }
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
        if (CollectionUtils.isNotEmpty(children)) {
            String key = children.get(children.size() - 1).getKey();
            if (CollectionUtils.isNotEmpty(rows)) {
                rows.sort(new Comparator<Map<String, String>>() {
                    @Override
                    public int compare(Map<String, String> o1, Map<String, String> o2) {
                        return DataTypeUtils.stringToInteger(o1.get(key)) <= DataTypeUtils.stringToInteger(o2.get(key))
                            ? 1
                            : -1;
                    }
                });
            }
        }
        // 数据
        Map add = new HashMap();
        add.put("classifyName", "新增数量");
        index = 1;
        for (Integer addData : assetReportResponse.getAllAdd()) {
            add.put(String.valueOf(index++), String.valueOf(addData));
        }
        rows.add(add);
        Map total = new HashMap();
        total.put("classifyName", "总数");
        index = 1;
        for (Integer allData : assetReportResponse.getAlldata()) {
            total.put(String.valueOf(index++), String.valueOf(allData));
        }
        rows.add(total);
        assetReportTableResponse.setRows(rows);
        return assetReportTableResponse;
    }

    /**
     * 导出报表数据
     * @param reportQueryRequest
     * @returnK
     */
    @Override
    public void exportAreaTable(ReportQueryRequest reportQueryRequest) throws Exception {
        String titleStr;
        switch (reportQueryRequest.getTimeType()) {
            case "1":
                titleStr = "本周";
                break;
            case "2":
                titleStr = "本月";
                break;
            case "3":
                titleStr = "本季度";
                break;
            case "4":
                titleStr = "本年";
                break;
            case "5":
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
                SimpleDateFormat simpleDateFormatTwo = new SimpleDateFormat("yyyyMM");
                Date startTime = new Date(reportQueryRequest.getStartTime());
                Date endTime = new Date(reportQueryRequest.getEndTime());
                titleStr = simpleDateFormat.format(startTime) + "~" + simpleDateFormat.format(endTime);
                break;
            default:
                throw new BusinessException("timeType参数异常");
        }
        ReportForm reportForm = new ReportForm();
        AssetReportResponse assetReportResponse = this.getAssetWithArea(reportQueryRequest);
        // 表格标题
        String title = titleStr + "资产区域总数";
        reportForm.setTitle(title);
        // 表格行头
        reportForm.setHeaderList(assetReportResponse.getDate());
        // 表格列头
        List columnList = assetReportResponse.getList().stream().map(ReportData::getClassify)
            .collect(Collectors.toList());
        List<List<Integer>> dataList = Lists.newArrayList();
        reportForm.setColumnList(columnList);
        List<ReportData> list = assetReportResponse.getList();
        list.stream().forEach(reportData -> {
            dataList.add(reportData.getData());
        });
        columnList.add("新增数量");
        dataList.add(assetReportResponse.getAllAdd());
        columnList.add("总数");
        dataList.add(assetReportResponse.getAlldata());
        String[][] datas = new String[columnList.size()][assetReportResponse.getDate().size()];
        for (int i = 0; i < dataList.size(); i++) {
            List<Integer> data = dataList.get(i);
            for (int j = 0; j < data.size(); j++) {
                datas[i][j] = String.valueOf(data.get(j));
            }
        }
        reportForm.setData(datas);
        String fileName = titleStr + ".xlsx";
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
            .getRequest();
        ExcelUtils.exportFormToClient(reportForm, encodeChineseDownloadFileName(request, fileName));
        // 记录操作日志和运行日志
        LogUtils.recordOperLog(new BusinessData("导出《" + titleStr + "》", 0, "", reportQueryRequest,
            BusinessModuleEnum.REPORT, BusinessPhaseEnum.NONE));
        LogUtils.info(LogUtils.get(AssetReportServiceImpl.class), AssetEventEnum.ASSET_REPORT_EXPORT.getName() + " {}",
            reportQueryRequest.toString());

    }

    private String getAreaNameById(Integer id, List<AssetAreaReportRequest> assetAreaIds) {
        for (AssetAreaReportRequest assetAreaReportRequest : assetAreaIds) {
            if (id.equals(DataTypeUtils.stringToInteger(assetAreaReportRequest.getParentAreaId()))) {
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
                    areaId.add(DataTypeUtils.stringToInteger(assetAreaReportRequest.getParentAreaId()));
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

    private List<Integer> getStatusList() {
        List<Integer> statusList = new ArrayList<>();
        for (AssetStatusEnum assetStatusEnum : AssetStatusEnum.values()) {
            if (!assetStatusEnum.equals(AssetStatusEnum.RETIRE) && !assetStatusEnum.equals(AssetStatusEnum.NOT_REGSIST)
                && !assetStatusEnum.equals(AssetStatusEnum.WATI_REGSIST)) {
                statusList.add(assetStatusEnum.getCode());
            }
        }
        return statusList;
    }

    /**
     * 对文件流输出下载的中文文件名进行编码 屏蔽各种浏览器版本的差异性
     * @throws UnsupportedEncodingException
     */
    public String encodeChineseDownloadFileName(HttpServletRequest request,
                                                String pFileName) throws UnsupportedEncodingException {
        String userAgent = request.getHeader("user-agent").toLowerCase();
        if (userAgent.contains("msie") || userAgent.contains("like gecko")) {
            // win10 ie edge 浏览器 和其他系统的ie
            pFileName = URLEncoder.encode(pFileName, "UTF-8");
        } else {
            // fe
            pFileName = new String(pFileName.getBytes("utf-8"), "iso-8859-1");
        }
        return pFileName;
    }

}
