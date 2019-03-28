package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetCategoryModelDao;
import com.antiy.asset.dao.AssetReportDao;
import com.antiy.asset.entity.AssetCategoryEntity;
import com.antiy.asset.entity.AssetCategoryModel;
import com.antiy.asset.entity.AssetGroupEntity;
import com.antiy.asset.service.IAssetCategoryModelService;
import com.antiy.asset.service.IAssetReportService;
import com.antiy.asset.templet.ReportForm;
import com.antiy.asset.util.ArrayTypeUtil;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.util.ExcelUtils;
import com.antiy.asset.util.ReportDateUtils;
import com.antiy.asset.vo.enums.AssetSecondCategoryEnum;
import com.antiy.asset.vo.enums.ShowCycleType;
import com.antiy.asset.vo.query.AssetReportCategoryCountQuery;
import com.antiy.asset.vo.request.ReportQueryRequest;
import com.antiy.asset.vo.response.AssetReportResponse;
import com.antiy.asset.vo.response.AssetReportTableResponse;
import com.antiy.asset.vo.response.ReportData;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.ParamterExceptionUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 资产报表实现类
 *
 * @author zhangyajun
 * @create 2019-03-26 13:41
 **/
@Service
public class AssetReportServiceImpl implements IAssetReportService {
    private final static String DAY    = "%w";
    private final static String WEEK   = "%u";
    private final static String MONTH  = "%Y-%m";

    @Resource
    IAssetCategoryModelService  iAssetCategoryModelService;
    @Resource
    AssetCategoryModelDao       assetCategoryModelDao;
    @Resource
    AssetReportDao              assetReportDao;
    @Resource
    AssetCategoryModelDao       categoryModelDao;
    private static Logger       logger = LogUtils.get(AssetReportServiceImpl.class);

    @Override
    public AssetReportResponse queryCategoryCountByTime(AssetReportCategoryCountQuery query) throws Exception {

        ShowCycleType showCycleType = query.getShowCycleType();
        checkParameter(query, showCycleType);

        AssetReportResponse reportResponse = new AssetReportResponse();

        Map<String, Object> map;
        if (ShowCycleType.THIS_WEEK.getCode().equals(showCycleType.getCode())) {
            query.setFormat(DAY);
            map = buildCategoryCountByTime(query, ReportDateUtils.getDayOfWeek());
            reportResponse.setDate((List) map.get("dateList"));
            reportResponse.setList((List) map.get("columnarList"));
            return reportResponse;
        } else if (ShowCycleType.THIS_MONTH.getCode().equals(showCycleType.getCode())) {
            query.setFormat(WEEK);
            map = buildCategoryCountByTime(query, ReportDateUtils.getWeekOfMonth());
            reportResponse.setDate((List) map.get("dateList"));
            reportResponse.setList((List) map.get("columnarList"));
            return reportResponse;
        } else if (ShowCycleType.THIS_QUARTER.getCode().equals(showCycleType.getCode())) {
            query.setFormat(MONTH);
            map = buildCategoryCountByTime(query, ReportDateUtils.getSeason());
            reportResponse.setDate((List) map.get("dateList"));
            reportResponse.setList((List) map.get("columnarList"));
            return reportResponse;
        } else if (ShowCycleType.THIS_YEAR.getCode().equals(showCycleType.getCode())) {
            query.setFormat(MONTH);
            map = buildCategoryCountByTime(query, ReportDateUtils.getCurrentMonthOfYear());
            reportResponse.setDate((List) map.get("dateList"));
            reportResponse.setList((List) map.get("columnarList"));
            return reportResponse;
        } else if (ShowCycleType.ASSIGN_TIME.getCode().equals(showCycleType.getCode())) {
            map = buildCategoryCountByTime(query,
                ReportDateUtils.getMonthWithDate(query.getBeginTime(), query.getEndTime()));
            reportResponse.setDate((List) map.get("dateList"));
            reportResponse.setList((List) map.get("columnarList"));
            return reportResponse;
        } else {
            throw new BusinessException("非法参数");
        }
    }

    /**
     * 构建按时间分类统计资产数据
     * @param query
     * @param weekMap
     * @return
     */
    private Map<String, Object> buildCategoryCountByTime(AssetReportCategoryCountQuery query,
                                                         Map<String, String> weekMap) {
        Map<String, Object> map = new HashMap<>();
        List<AssetCategoryModel> categoryModels = categoryModelDao.findAllCategory();
        // 构造柱状图所需的source
        List<Integer> computerDataList = new ArrayList<>();
        List<Integer> networkDataList = new ArrayList<>();
        List<Integer> storageDataList = new ArrayList<>();
        List<Integer> safetyDataList = new ArrayList<>();
        List<Integer> otherDataList = new ArrayList<>();
        Iterator<Map.Entry<String, String>> iterator = weekMap.entrySet().iterator();
        List<String> dateList = new ArrayList<>();
        List<ReportData> columnarList = new ArrayList<>();
        AssetCategoryModel assetCategoryModel = new AssetCategoryModel();

        while (iterator.hasNext()) {
            int computeDevice = 0;
            int networkDevice = 0;
            int storageDevice = 0;
            int safetyDevice = 0;
            int otherDevice = 0;

            Map.Entry<String, String> entry = iterator.next();
            String key = entry.getKey();
            String time = entry.getValue();
            List<AssetCategoryEntity> categoryEntityList = assetReportDao.findCategoryCountByTime(query);
            for (AssetCategoryEntity categoryEntity : categoryEntityList) {
                if (key.equals(categoryEntity.getDate())) {
                    assetCategoryModel.setId(categoryEntity.getCategoryModel());
                    assetCategoryModel.setParentId(categoryEntity.getParentId());
                    assetCategoryModel.setName(categoryEntity.getCategoryName());
                    String secondCategoryName = this.getParentCategory(assetCategoryModel, categoryModels).getName();
                    if (AssetSecondCategoryEnum.COMPUTE_DEVICE.getMsg().equals(secondCategoryName)
                        && key.equals(categoryEntity.getDate())) {
                        computeDevice = computeDevice + categoryEntity.getCategoryCount();
                    } else if (AssetSecondCategoryEnum.NETWORK_DEVICE.getMsg().equals(secondCategoryName)
                               && key.equals(categoryEntity.getDate())) {
                        networkDevice = networkDevice + categoryEntity.getCategoryCount();
                    } else if (AssetSecondCategoryEnum.STORAGE_DEVICE.getMsg().equals(secondCategoryName)
                               && key.equals(categoryEntity.getDate())) {
                        storageDevice = storageDevice + categoryEntity.getCategoryCount();
                    } else if (AssetSecondCategoryEnum.SAFETY_DEVICE.getMsg().equals(secondCategoryName)
                               && key.equals(categoryEntity.getDate())) {
                        safetyDevice = safetyDevice + categoryEntity.getCategoryCount();
                    } else if (AssetSecondCategoryEnum.OTHER_DEVICE.getMsg().equals(secondCategoryName)
                               && key.equals(categoryEntity.getDate())) {
                        otherDevice = otherDevice + categoryEntity.getCategoryCount();
                    }
                }
            }
            dateList.add(time);
            computerDataList.add(computeDevice);
            networkDataList.add(networkDevice);
            storageDataList.add(storageDevice);
            safetyDataList.add(safetyDevice);
            otherDataList.add(otherDevice);
        }

        // 构建柱状数据

        ReportData computeDeviceColumnar = new ReportData();
        computeDeviceColumnar.setClassify(AssetSecondCategoryEnum.COMPUTE_DEVICE.getMsg());
        computeDeviceColumnar.setData(computerDataList);
        columnarList.add(computeDeviceColumnar);

        ReportData networkDeviceColumnar = new ReportData();
        networkDeviceColumnar.setClassify(AssetSecondCategoryEnum.NETWORK_DEVICE.getMsg());
        networkDeviceColumnar.setData(networkDataList);
        columnarList.add(networkDeviceColumnar);

        ReportData storageDeviceColumnar = new ReportData();
        storageDeviceColumnar.setClassify(AssetSecondCategoryEnum.STORAGE_DEVICE.getMsg());
        storageDeviceColumnar.setData(storageDataList);
        columnarList.add(storageDeviceColumnar);

        ReportData safetyDeviceColumnar = new ReportData();
        safetyDeviceColumnar.setClassify(AssetSecondCategoryEnum.SAFETY_DEVICE.getMsg());
        safetyDeviceColumnar.setData(safetyDataList);
        columnarList.add(safetyDeviceColumnar);

        ReportData otherDeviceColumnar = new ReportData();
        otherDeviceColumnar.setClassify(AssetSecondCategoryEnum.OTHER_DEVICE.getMsg());
        otherDeviceColumnar.setData(otherDataList);
        columnarList.add(otherDeviceColumnar);

        map.put("dateList", dateList);
        map.put("columnarList", columnarList);
        return map;
    }

    /**
     * 时间参数校验
     *
     * @param query
     * @param showCycleType
     */
    private void checkParameter(AssetReportCategoryCountQuery query, ShowCycleType showCycleType) {
        if (ShowCycleType.THIS_WEEK.getCode().equals(showCycleType.getCode())
            || ShowCycleType.THIS_MONTH.getCode().equals(showCycleType.getCode())
            || ShowCycleType.THIS_QUARTER.getCode().equals(showCycleType.getCode())
            || ShowCycleType.THIS_YEAR.getCode().equals(showCycleType.getCode())) {
            ParamterExceptionUtils.isNull(query.getBeginTime(), "开始时间不能为空");
            ParamterExceptionUtils.isNull(query.getEndTime(), "开始时间不能为空");
        } else if (ShowCycleType.ASSIGN_TIME.getCode().equals(showCycleType.getCode())) {
            ParamterExceptionUtils.isNull(query.getBeginTime(), "指定开始时间不能为空");
        }
    }

    /**
     * 获取二级品类型号信息
     *
     * @param categoryModel
     * @return
     */
    private AssetCategoryModel getParentCategory(AssetCategoryModel categoryModel,
                                                 List<AssetCategoryModel> allCategory) {

        if (DataTypeUtils.stringToInteger(categoryModel.getParentId()) == 2) {
            return categoryModel;
        }

        Optional<AssetCategoryModel> categoryModelOptional = allCategory.stream()
            .filter(x -> Objects.equals(x.getId(), DataTypeUtils.stringToInteger(categoryModel.getParentId())))
            .findFirst();
        if (categoryModelOptional.isPresent()) {
            AssetCategoryModel tblCategory = categoryModelOptional.get();
            return getParentCategory(tblCategory, allCategory);
        } else {
            throw new BusinessException("获取二级品类型号失败");
        }
    }

    @Override
    public AssetReportResponse getNewAssetWithCategory(AssetReportCategoryCountQuery assetReportCategoryCountQuery) throws Exception {
        List<AssetCategoryModel> secondCategoryModelList = assetCategoryModelDao.getNextLevelCategoryByName("硬件");
        List<AssetCategoryModel> categoryModelAll = assetCategoryModelDao.getAll();
        // 初始化品类二级品类和子节点的映射
        Map<Integer, List<Integer>> categoryMap = getIntegerListMap(secondCategoryModelList, categoryModelAll);
        setFormat(assetReportCategoryCountQuery);
        // 查询数据
        List<AssetCategoryEntity> assetCategoryEntityList = assetReportDao
            .getNewAssetWithCategory(assetReportCategoryCountQuery);
        Map<String, Integer> result = new HashMap<>();
        // 组装数据，将子品类的数量组装进二级品类中
        installNodeData(categoryMap, assetCategoryEntityList, result);
        switch (assetReportCategoryCountQuery.getShowCycleType()) {
            case THIS_WEEK:
                return getAssetReportResponseInWeek(secondCategoryModelList, result);
            case THIS_MONTH:
                return getAssetReportResponseInMonth(secondCategoryModelList, result);
            case THIS_QUARTER:
                return getAssetReportResponseInQuarterOrMonth(assetReportCategoryCountQuery, secondCategoryModelList,
                    result);
            case THIS_YEAR:
                return getAssetReportResponseInQuarterOrMonth(assetReportCategoryCountQuery, secondCategoryModelList,
                    result);
            case ASSIGN_TIME:
                return getAssetReportResponseInTime(assetReportCategoryCountQuery, secondCategoryModelList, result);
            default:
                return null;
        }
    }

    private void setFormat(AssetReportCategoryCountQuery assetReportCategoryCountQuery) {
        switch (assetReportCategoryCountQuery.getShowCycleType()) {
            case THIS_WEEK:
                assetReportCategoryCountQuery.setFormat("%w");
                break;
            case THIS_MONTH:
                assetReportCategoryCountQuery.setFormat("%u");
                break;

            case THIS_QUARTER:
                assetReportCategoryCountQuery.setFormat("%Y-%m");
                break;

            case THIS_YEAR:
                assetReportCategoryCountQuery.setFormat("%Y-%m");
                break;

            case ASSIGN_TIME:
                assetReportCategoryCountQuery.setFormat("%Y-%m");
                break;
        }
    }

    @Override
    public void exportCategoryCount(AssetReportCategoryCountQuery assetReportCategoryCountQuery) throws Exception {
        ReportForm reportForm = new ReportForm();
        String titleStr;
        switch (assetReportCategoryCountQuery.getShowCycleType()) {
            case THIS_WEEK:
                titleStr = assetReportCategoryCountQuery.getShowCycleType().getMessage();
                break;
            case THIS_MONTH:
                titleStr = assetReportCategoryCountQuery.getShowCycleType().getMessage();
                break;
            case THIS_QUARTER:
                titleStr = assetReportCategoryCountQuery.getShowCycleType().getMessage();
                break;
            case THIS_YEAR:
                titleStr = assetReportCategoryCountQuery.getShowCycleType().getMessage();
                break;
            case ASSIGN_TIME:
                titleStr = getTitleStr(assetReportCategoryCountQuery);
                break;
            default:
                throw new BusinessException("timeType参数异常");
        }
        reportForm.setTitle("资产" + titleStr + "品类型号总数");
        AssetReportResponse assetReportResponse = queryCategoryCountByTime(assetReportCategoryCountQuery);
        List<String> headerList = assetReportResponse.getDate();
        headerList.add("总数");
        int[] total = new int[headerList.size()];
        headerList.add("新增");
        int[] add = new int[headerList.size()];
        List<ReportData> reportDataList = assetReportResponse.getList();
        List<String> columnList = new ArrayList<>();
        String[][] data = new String[reportDataList.size()][headerList.size()];
        for (int i = 0; i < reportDataList.size(); i++) {
            ReportData reportData = reportDataList.get(i);
            String classify = reportData.getClassify();
            columnList.add(classify);
            List dataList = reportData.getData();
            data[i] = ArrayTypeUtil.objectArrayToStringArray(dataList.toArray());
        }
        reportForm.setHeaderList(headerList);
        reportForm.setData(data);
        reportForm.setColumnList(columnList);
        ExcelUtils.exportFormToClient(reportForm, "报表导出.xlsx");
    }

    private String getTitleStr(AssetReportCategoryCountQuery assetReportCategoryCountQuery) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        String beginTime = simpleDateFormat.format(new Date(assetReportCategoryCountQuery.getBeginTime()));
        String endTime = simpleDateFormat.format(new Date(assetReportCategoryCountQuery.getEndTime()));
        return new StringBuffer(beginTime).append(" ").append(endTime).toString();
    }

    private AssetReportResponse getAssetReportResponseInWeek(List<AssetCategoryModel> secondCategoryModelList,
                                                             Map<String, Integer> result) {
        // 初始化返回类
        Map<String, String> weekMap = ReportDateUtils.getDayOfWeek();
        AssetReportResponse assetReportResponse = new AssetReportResponse();
        // 初始化横坐标
        String[] weeks = new String[weekMap.size()];
        for (Map.Entry<String, String> entry : weekMap.entrySet()) {
            weeks[Integer.parseInt(entry.getKey()) - 1] = entry.getValue();
        }
        assetReportResponse.setDate(Arrays.asList(weeks));
        // 将结果数据组装到Response中
        List<ReportData> reportDataList = new ArrayList<>();
        for (AssetCategoryModel assetCategoryModel : secondCategoryModelList) {
            Integer[] data = new Integer[weekMap.size()];
            for (Map.Entry<String, Integer> entry : result.entrySet()) {
                int week = Integer.parseInt(entry.getKey().substring(entry.getKey().indexOf(" ") + 1));
                String category = entry.getKey().substring(0, entry.getKey().indexOf(" "));
                if (Objects.equals(assetCategoryModel.getStringId(), category)) {
                    if (week <= weeks.length) {
                        data[week - 1] = entry.getValue();
                    }
                }
            }
            addReportData(assetReportResponse, reportDataList, assetCategoryModel, data);
        }
        assetReportResponse.setList(reportDataList);
        return assetReportResponse;
    }

    private void installNodeData(Map<Integer, List<Integer>> categoryMap,
                                 List<AssetCategoryEntity> assetCategoryEntityList, Map<String, Integer> result) {
        for (AssetCategoryEntity assetCategoryEntity : assetCategoryEntityList) {
            for (Map.Entry<Integer, List<Integer>> entry : categoryMap.entrySet()) {
                if (entry.getKey().equals(assetCategoryEntity.getCategoryModel())) {
                    String categoryDate = new StringBuffer().append(assetCategoryEntity.getCategoryModel()).append(" ")
                        .append(assetCategoryEntity.getDate()).toString();
                    putResult(result, assetCategoryEntity, categoryDate);
                } else if (entry.getValue().contains(assetCategoryEntity.getCategoryModel())) {
                    String categoryDate = new StringBuffer().append(entry.getKey()).append(" ")
                        .append(assetCategoryEntity.getDate()).toString();
                    putResult(result, assetCategoryEntity, categoryDate);
                }

            }
        }
    }

    private void putResult(Map<String, Integer> result, AssetCategoryEntity assetCategoryEntity, String categoryDate) {
        if (!result.containsKey(categoryDate)) {
            result.put(categoryDate, assetCategoryEntity.getCategoryCount());
        } else {
            result.put(categoryDate, assetCategoryEntity.getCategoryCount() + result.get(categoryDate));
        }
    }

    private AssetReportResponse getAssetReportResponseInMonth(List<AssetCategoryModel> secondCategoryModelList,
                                                              Map<String, Integer> result) {
        // 初始化返回类
        Map<String, String> weekMap = ReportDateUtils.getWeekOfMonth();
        int minWeek = Integer.MAX_VALUE;
        for (Map.Entry<String, String> entry : weekMap.entrySet()) {
            if (minWeek > Integer.parseInt(entry.getKey())) {
                minWeek = Integer.parseInt(entry.getKey());
            }
        }
        AssetReportResponse assetReportResponse = new AssetReportResponse();
        // 初始化横坐标
        String[] weeks = new String[weekMap.size()];
        for (Map.Entry<String, String> entry : weekMap.entrySet()) {
            weeks[Integer.parseInt(entry.getKey()) - minWeek] = entry.getValue();
        }
        assetReportResponse.setDate(Arrays.asList(weeks));
        // 将结果数据组装到Response中
        List<ReportData> reportDataList = new ArrayList<>();
        for (AssetCategoryModel assetCategoryModel : secondCategoryModelList) {
            Integer[] data = new Integer[weekMap.size()];
            for (Map.Entry<String, Integer> entry : result.entrySet()) {
                int week = Integer.parseInt(entry.getKey().substring(entry.getKey().indexOf(" ") + 1));
                setDataArray(minWeek, weeks, assetCategoryModel, data, entry, week);
            }
            addReportData(assetReportResponse, reportDataList, assetCategoryModel, data);
        }
        assetReportResponse.setList(reportDataList);
        return assetReportResponse;
    }

    private AssetReportResponse getAssetReportResponseInQuarterOrMonth(AssetReportCategoryCountQuery assetReportCategoryCountQuery,
                                                                       List<AssetCategoryModel> secondCategoryModelList,
                                                                       Map<String, Integer> result) {
        // 初始化返回类
        Map<String, String> monthMap = null;
        if (assetReportCategoryCountQuery.getShowCycleType().equals(ShowCycleType.THIS_QUARTER)) {
            monthMap = ReportDateUtils.getSeason();
        } else if (assetReportCategoryCountQuery.getShowCycleType().equals(ShowCycleType.THIS_YEAR)) {
            monthMap = ReportDateUtils.getCurrentMonthOfYear();
        }
        int minMonth = Integer.MAX_VALUE;
        for (Map.Entry<String, String> entry : monthMap.entrySet()) {
            int i = Integer.parseInt(entry.getKey().substring(entry.getKey().indexOf("-") + 1));
            if (minMonth > i) {
                minMonth = i;
            }
        }
        AssetReportResponse assetReportResponse = new AssetReportResponse();
        // 初始化横坐标
        String[] weeks = new String[monthMap.size()];
        for (Map.Entry<String, String> entry : monthMap.entrySet()) {
            int i = Integer.parseInt(entry.getKey().substring(entry.getKey().indexOf("-") + 1)) - minMonth;
            weeks[i] = entry.getValue();
        }
        assetReportResponse.setDate(Arrays.asList(weeks));
        // 将结果数据组装到Response中
        List<ReportData> reportDataList = new ArrayList<>();
        for (AssetCategoryModel assetCategoryModel : secondCategoryModelList) {
            Integer[] data = new Integer[monthMap.size()];
            for (Map.Entry<String, Integer> entry : result.entrySet()) {
                int week = Integer.parseInt(entry.getKey().substring(entry.getKey().indexOf("-") + 1));
                setDataArray(minMonth, weeks, assetCategoryModel, data, entry, week);
            }
            addReportData(assetReportResponse, reportDataList, assetCategoryModel, data);
        }
        assetReportResponse.setList(reportDataList);
        return assetReportResponse;
    }

    private AssetReportResponse getAssetReportResponseInTime(AssetReportCategoryCountQuery assetReportCategoryCountQuery,
                                                             List<AssetCategoryModel> secondCategoryModelList,
                                                             Map<String, Integer> result) {
        // 初始化返回类
        Map<String, Integer> map = getWeeks(assetReportCategoryCountQuery.getBeginTime(),
            assetReportCategoryCountQuery.getEndTime());
        AssetReportResponse assetReportResponse = new AssetReportResponse();
        // 初始化横坐标
        String[] weeks = new String[map.size()];
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            weeks[entry.getValue()] = entry.getKey();
        }
        assetReportResponse.setDate(Arrays.asList(weeks));
        // 将结果数据组装到Response中
        List<ReportData> reportDataList = new ArrayList<>();
        for (AssetCategoryModel assetCategoryModel : secondCategoryModelList) {
            Integer[] data = new Integer[weeks.length];
            for (Map.Entry<String, Integer> entry : result.entrySet()) {
                int index = entry.getKey().indexOf(" ");
                int month = map.get(entry.getKey().substring(index + 1));
                String category = entry.getKey().substring(0, index);
                if (Objects.equals(assetCategoryModel.getStringId(), category)) {
                    data[month] = entry.getValue();
                }
            }
            addReportData(assetReportResponse, reportDataList, assetCategoryModel, data);
        }
        assetReportResponse.setList(reportDataList);
        return assetReportResponse;
    }

    private Map<String, Integer> getWeeks(Long startTime, Long endTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        String startTimeStr = simpleDateFormat.format(new Date(startTime));
        String endTimeStr = simpleDateFormat.format(new Date(endTime));
        int index = startTimeStr.indexOf("-");
        String startYear = startTimeStr.substring(0, index);
        String startMonth = startTimeStr.substring(index + 1);
        String endYear = endTimeStr.substring(0, index);
        String endMonth = endTimeStr.substring(index + 1);
        int start = Integer.parseInt(startMonth);
        int end = Integer.parseInt(endMonth);
        Map<String, Integer> result = new LinkedHashMap<>();
        if (startYear.equals(endYear)) {
            for (int i = start; i <= end; i++) {
                StringBuffer sb = new StringBuffer();
                if (i >= 10) {
                    result.put(sb.append(startYear).append("-").append(i).toString(), i - start);
                } else {
                    result.put(sb.append(startYear).append("-0").append(i).toString(), i - start);
                }
            }
        } else {
            int m = 0;
            for (int i = start; i <= 12; i++) {
                StringBuffer sb = new StringBuffer();
                if (i >= 10) {
                    result.put(sb.append(startYear).append("-").append(i).toString(), m++);
                } else {
                    result.put(sb.append(startYear).append("-0").append(i).toString(), m++);
                }
            }
            for (int i = 1; i <= end; i++) {
                StringBuffer sb = new StringBuffer();
                if (i >= 10) {
                    result.put(sb.append(Integer.parseInt(startYear) + 1).append("-").append(i).toString(), m++);
                } else {
                    result.put(sb.append(Integer.parseInt(startYear) + 1).append("-0").append(i).toString(), m++);
                }
            }
        }
        return result;
    }

    private void setDataArray(int minMonth, String[] weeks, AssetCategoryModel assetCategoryModel, Integer[] data,
                              Map.Entry<String, Integer> entry, int week) {
        String category = entry.getKey().substring(0, entry.getKey().indexOf(" "));
        if (Objects.equals(assetCategoryModel.getStringId(), category)) {
            if (week - minMonth <= weeks.length) {
                try {
                    data[week - minMonth] = entry.getValue();
                } catch (Exception e) {
                    logger.error(e.toString());
                    throw new BusinessException("时间传入参数错误");
                }
            }
        }
    }

    private void addReportData(AssetReportResponse assetReportResponse, List<ReportData> reportDataList,
                               AssetCategoryModel assetCategoryModel, Integer[] data) {
        ReportData reportData = new ReportData();
        reportData.setClassify(assetCategoryModel.getName());
        reportData.setAdd(Arrays.asList(data));
        reportDataList.add(reportData);
    }

    private Map<Integer, List<Integer>> getIntegerListMap(List<AssetCategoryModel> secondCategoryModelList,
                                                          List<AssetCategoryModel> categoryModelAll) throws Exception {
        // 初始化品类二级品类和子节点的映射
        Map<Integer, List<Integer>> categoryMap = new HashMap<>();
        for (AssetCategoryModel assetCategoryModel : secondCategoryModelList) {
            List<AssetCategoryModel> categoryModelList = iAssetCategoryModelService.recursionSearch(categoryModelAll,
                assetCategoryModel.getId());
            categoryMap.put(assetCategoryModel.getId(), getCategoryIdList(categoryModelList));
        }
        return categoryMap;
    }

    private List<Integer> getCategoryIdList(List<AssetCategoryModel> categoryModelList) {
        List<Integer> list = new ArrayList<>();
        for (AssetCategoryModel assetCategoryModel : categoryModelList) {
            list.add(assetCategoryModel.getId());
        }
        return list;
    }

    @Override
    public AssetReportResponse getAssetConutWithGroup(ReportQueryRequest reportQueryRequest) throws Exception {

        reportQueryRequest.setTopFive("查询top5");
        // @ApiModelProperty(value = "时间类型,1-本周,2-本月,3-本季度,4-本年,5-时间范围", required = true)
        switch (reportQueryRequest.getTimeType()) {
            case "1":
                reportQueryRequest.setSqlTime("%w");
                return buildGroupCountByTime(reportQueryRequest, ReportDateUtils.getDayOfWeek());

            case "2":
                reportQueryRequest.setSqlTime("%U");
                return buildGroupCountByTime(reportQueryRequest, ReportDateUtils.getWeekOfMonth());

            case "3":
                reportQueryRequest.setSqlTime("%Y-%m");
                return buildGroupCountByTime(reportQueryRequest, ReportDateUtils.getSeason());

            case "4":
                reportQueryRequest.setSqlTime("%Y-%m");
                return buildGroupCountByTime(reportQueryRequest, ReportDateUtils.getCurrentMonthOfYear());

            case "5":
                reportQueryRequest.setSqlTime("%Y-%m");
                return buildGroupCountByTime(reportQueryRequest, ReportDateUtils
                    .getMonthWithDate(reportQueryRequest.getStartTime(), reportQueryRequest.getEndTime()));
            default:
                reportQueryRequest.setSqlTime("%Y-%m");
                return buildGroupCountByTime(reportQueryRequest, ReportDateUtils.getCurrentMonthOfYear());
        }

    }

    private AssetReportResponse buildGroupCountByTime(ReportQueryRequest reportQueryRequest,
                                                      Map<String, String> timeMap) {
        List<AssetGroupEntity> groupReportEntityList = assetReportDao.getAssetConutWithGroup(reportQueryRequest);

        // 1.初始化返回对象
        AssetReportResponse assetReportResponse = new AssetReportResponse();
        List<ReportData> reportDataList = new ArrayList<>();

        // 2.获取本月的周数信息map,重新按周数key排序
        Map<String, String> treeWeekMap = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Integer.valueOf(o1.replace("-", "")).compareTo(Integer.valueOf(o2.replace("-", "")));
            }
        });
        treeWeekMap.putAll(timeMap);
        // 3.添加排序后的日期信息到dateList
        List<String> dateKeyList = new ArrayList<>(treeWeekMap.size());
        List<String> dateValueList = new ArrayList<>(treeWeekMap.size());
        for (Map.Entry<String, String> entry : treeWeekMap.entrySet()) {
            dateKeyList.add(entry.getKey());
            dateValueList.add(entry.getValue());
        }
        // 4.资产组名字信息
        List<String> groupNameList = new ArrayList<>();
        groupReportEntityList.forEach(groupReportEntity -> {
            if (!groupNameList.contains(groupReportEntity.getName())) {
                groupNameList.add(groupReportEntity.getName());
            }
        });
        // 5.根据资产组信息封装
        groupNameList.forEach(groupName -> {
            ReportData reportData = new ReportData();
            reportData.setClassify(groupName);
            List<Integer> addNumList = new ArrayList<>();

            dateKeyList.forEach(date -> {
                Integer num = 0;
                for (AssetGroupEntity groupReportEntity : groupReportEntityList) {
                    // 去掉数据库返回的数据中开头为0的部分
                    String groupReportEntityDate = groupReportEntity.getDate().startsWith("0")
                            ? groupReportEntity.getDate().substring(1)
                            : groupReportEntity.getDate();
                    // 资产组别且对应周数匹配
                    if (groupReportEntity.getName().equals(groupName) && groupReportEntityDate.equals(date)) {
                        num = groupReportEntity.getGroupCount();
                    }
                }
                addNumList.add(num);
            });
            reportData.setData(addNumList);
            reportDataList.add(reportData);
        });
        assetReportResponse.setDate(dateValueList);
        assetReportResponse.setList(reportDataList);
        return assetReportResponse;


//        AssetReportResponse reportResponse = new AssetReportResponse();
//        Iterator<Map.Entry<String, String>> iterator = weekMap.entrySet().iterator();
//        // 横坐标
//        List<String> dateList = new ArrayList<>();
//
//        // 将结果数据组装到Response中
//        List<ReportData> reportDataList = new ArrayList<>();
//        List<Integer> countDate = new ArrayList<>();
//        while (iterator.hasNext()) {
//            int count = 0;
//
//            Map.Entry<String, String> entry = iterator.next();
//            String key = entry.getKey();
//            String time = entry.getValue();
//            for (AssetGroupEntity entity : assetConutWithGroup) {
//                if (key.equals(entity.getDate())) {
//                    count = entity.getGroupCount();
//                }
//            }
//
//            countDate.add(count);
//            dateList.add(time);
//
//        }
//
//        for (AssetGroupEntity groupEntity : assetConutWithGroup) {
//            ReportData reportData = new ReportData();
//            reportData.setClassify(groupEntity.getName());
//            reportData.setData(countDate);
//            reportDataList.add(reportData);
//        }
//
//        reportResponse.setList(reportDataList);
//        reportResponse.setDate(dateList);
//
//        return reportResponse;
    }


    /**
     * 根据资产组查询资产新增数量
     *
     * @param reportQueryRequest
     * @return
     */
    @Override
    public AssetReportResponse getNewAssetWithGroup(ReportQueryRequest reportQueryRequest) throws Exception {
        // 1-本周,2-本月,3-本季度,4-本年,5-时间范围
        switch (reportQueryRequest.getTimeType()) {
            case "1":
                reportQueryRequest.setSqlTime("%w");
                return queryNewAssetWithGroup(reportQueryRequest, ReportDateUtils.getDayOfWeek());
            case "2":
                reportQueryRequest.setSqlTime("%U");
                return queryNewAssetWithGroup(reportQueryRequest, ReportDateUtils.getWeekOfMonth());
            case "3":
                reportQueryRequest.setSqlTime("%Y-%m");
                return queryNewAssetWithGroup(reportQueryRequest, ReportDateUtils.getSeason());
            case "4":
                reportQueryRequest.setSqlTime("%Y-%m");
                return queryNewAssetWithGroup(reportQueryRequest, ReportDateUtils.getCurrentMonthOfYear());
            case "5":
                reportQueryRequest.setSqlTime("%Y-%m");
                return queryNewAssetWithGroup(reportQueryRequest, ReportDateUtils
                    .getMonthWithDate(reportQueryRequest.getStartTime(), reportQueryRequest.getEndTime()));
            default:
                reportQueryRequest.setSqlTime("%Y-%m");
                return queryNewAssetWithGroup(reportQueryRequest, ReportDateUtils.getCurrentMonthOfYear());
        }
    }

    @Override
    public AssetReportTableResponse queryCategoryCountByTimeToTable(AssetReportCategoryCountQuery query) throws Exception {
        ShowCycleType showCycleType = query.getShowCycleType();
        checkParameter(query, showCycleType);

        // if (ShowCycleType.THIS_WEEK.getCode().equals(showCycleType.getCode())) {
        // query.setFormat(DAY);
        // return buildCategoryCountByTime(query, ReportDateUtils.getDayOfWeek());
        // } else if (ShowCycleType.THIS_MONTH.getCode().equals(showCycleType.getCode())) {
        // query.setFormat(WEEK);
        // return buildCategoryCountByTime(query, ReportDateUtils.getWeekOfMonth());
        // } else if (ShowCycleType.THIS_QUARTER.getCode().equals(showCycleType.getCode())) {
        // query.setFormat(MONTH);
        // return buildCategoryCountByTime(query, ReportDateUtils.getSeason());
        // } else if (ShowCycleType.THIS_YEAR.getCode().equals(showCycleType.getCode())) {
        // query.setFormat(MONTH);
        // return buildCategoryCountByTime(query, ReportDateUtils.getCurrentMonthOfYear());
        // } else if (ShowCycleType.ASSIGN_TIME.getCode().equals(showCycleType.getCode())) {
        // return buildCategoryCountByTime(query,
        // ReportDateUtils.getMonthWithDate(query.getBeginTime(), query.getEndTime()));
        // } else {
        // throw new BusinessException("非法参数");
        // }
        return null;
    }

    /**
     * 根据资产组类别查本周新增资产情况
     * @param reportQueryRequest
     * @return
     */
    public AssetReportResponse getNewAssetWithGroupInWeek(ReportQueryRequest reportQueryRequest) {
        Map<String, String> weekMap = ReportDateUtils.getDayOfWeek();
        AssetReportResponse assetReportResponse = new AssetReportResponse();
        // 初始化横坐标
        String[] weeks = new String[weekMap.size()];
        for (Map.Entry<String, String> entry : weekMap.entrySet()) {
            weeks[Integer.parseInt(entry.getKey()) - 1] = entry.getValue();
        }
        assetReportResponse.setDate(Arrays.asList(weeks));
        List<AssetGroupEntity> assetGroupEntities = assetReportDao.myTest(reportQueryRequest);
        // 获取资产组名字（不重复）
        List<String> groupNameList = new ArrayList<>();
        for (AssetGroupEntity assetGroupEntity : assetGroupEntities) {
            if (!groupNameList.contains(assetGroupEntity.getName())) {
                groupNameList.add(assetGroupEntity.getName());
            }
        }
        List<ReportData> dataList = new ArrayList<>();
        for (int i = 0; i < groupNameList.size(); i++) {
            ReportData reportData = new ReportData();
            String groupName = groupNameList.get(i);
            reportData.setClassify(groupName);
            Integer[] addList = new Integer[weeks.length];
            Arrays.fill(addList, 0);
            for (int day = 0; day < weeks.length; day++) {
                for (AssetGroupEntity groupReportEntity : assetGroupEntities) {
                    if (groupReportEntity.getName().equals(groupName)) {
                        addList[DataTypeUtils.stringToInteger(groupReportEntity.getDate()) - 1] = groupReportEntity
                            .getGroupCount();
                    }
                }
            }
            reportData.setAdd(Arrays.asList(addList));
            dataList.add(reportData);
        }
        assetReportResponse.setList(dataList);
        return assetReportResponse;
    }

    /**
     * 根据资产组类别查本月新增资产情况
     * @param reportQueryRequest
     * @return
     */
    public AssetReportResponse queryNewAssetWithGroup(ReportQueryRequest reportQueryRequest,
                                                      Map<String, String> timeMap) {
        List<AssetGroupEntity> groupReportEntityList = assetReportDao.getNewAssetWithGroup(reportQueryRequest);
        // 1.初始化返回对象
        AssetReportResponse assetReportResponse = new AssetReportResponse();
        List<ReportData> reportDataList = new ArrayList<>();

        // 2.获取本月的周数信息map,重新按周数key排序
        Map<String, String> treeWeekMap = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Integer.valueOf(o1.replace("-", "")).compareTo(Integer.valueOf(o2.replace("-", "")));
            }
        });
        treeWeekMap.putAll(timeMap);
        // 3.添加排序后的日期信息到dateList
        List<String> dateKeyList = new ArrayList<>(treeWeekMap.size());
        List<String> dateValueList = new ArrayList<>(treeWeekMap.size());
        for (Map.Entry<String, String> entry : treeWeekMap.entrySet()) {
            dateKeyList.add(entry.getKey());
            dateValueList.add(entry.getValue());
        }
        // 4.资产组名字信息
        List<String> groupNameList = new ArrayList<>();
        groupReportEntityList.forEach(groupReportEntity -> {
            if (!groupNameList.contains(groupReportEntity.getName())) {
                groupNameList.add(groupReportEntity.getName());
            }
        });
        // 5.根据资产组信息封装
        groupNameList.forEach(groupName -> {
            ReportData reportData = new ReportData();
            reportData.setClassify(groupName);
            List<Integer> addNumList = new ArrayList<>();

            dateKeyList.forEach(date -> {
                Integer num = 0;
                for (AssetGroupEntity groupReportEntity : groupReportEntityList) {
                    // 去掉数据库返回的数据中开头为0的部分
                    String groupReportEntityDate = groupReportEntity.getDate().startsWith("0")
                        ? groupReportEntity.getDate().substring(1)
                        : groupReportEntity.getDate();
                    // 资产组别且对应周数匹配
                    if (groupReportEntity.getName().equals(groupName) && groupReportEntityDate.equals(date)) {
                        num = groupReportEntity.getGroupCount();
                    }
                }
                addNumList.add(num);
            });
            reportData.setData(addNumList);
            reportDataList.add(reportData);
        });
        assetReportResponse.setDate(dateValueList);
        assetReportResponse.setList(reportDataList);
        return assetReportResponse;
    }

    /**
     * 根据资产组类别查本季度新增资产情况
     * @param reportQueryRequest
     * @return
     */
    public AssetReportResponse getNewAssetWithGroupInSeason(ReportQueryRequest reportQueryRequest) {
        return null;
    }

    /**
     * 根据资产组类别查本年新增资产情况
     * @param reportQueryRequest
     * @return
     */
    public AssetReportResponse getNewAssetWithGroupInYear(ReportQueryRequest reportQueryRequest) {
        return null;
    }

    /**
     * 根据资产组类别查某个时间范围新增资产情况
     * @param reportQueryRequest
     * @return
     */
    public AssetReportResponse getNewAssetWithGroupInRange(ReportQueryRequest reportQueryRequest) {
        return null;
    }
}