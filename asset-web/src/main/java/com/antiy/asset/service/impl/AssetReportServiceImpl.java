package com.antiy.asset.service.impl;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

import com.antiy.asset.vo.response.ReportData;
import com.antiy.common.utils.LogUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetCategoryModelDao;
import com.antiy.asset.dao.AssetReportDao;
import com.antiy.asset.entity.AssetCategoryEntity;
import com.antiy.asset.entity.AssetCategoryModel;
import com.antiy.asset.entity.AssetGroupEntity;
import com.antiy.asset.service.IAssetCategoryModelService;
import com.antiy.asset.service.IAssetReportService;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.util.ReportDateUtils;
import com.antiy.asset.vo.enums.AssetSecondCategoryEnum;
import com.antiy.asset.vo.enums.ShowCycleType;
import com.antiy.asset.vo.query.AssetReportCategoryCountQuery;
import com.antiy.asset.vo.request.ReportQueryRequest;
import com.antiy.asset.vo.response.AssetReportResponse;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.ParamterExceptionUtils;

/**
 * 资产报表实现类
 *
 * @author zhangyajun
 * @create 2019-03-26 13:41
 **/
@Service
public class AssetReportServiceImpl implements IAssetReportService {
    @Resource
    IAssetCategoryModelService iAssetCategoryModelService;
    @Resource
    AssetCategoryModelDao      assetCategoryModelDao;
    @Resource
    AssetReportDao             assetReportDao;
    @Resource
    AssetCategoryModelDao      categoryModelDao;
    private static Logger      logger = LogUtils.get(AssetReportServiceImpl.class);

    @Override
    public AssetReportResponse queryCategoryCountByTime(AssetReportCategoryCountQuery query) {

        ShowCycleType showCycleType = query.getShowCycleType();
        checkParameter(query, showCycleType);

        if (ShowCycleType.THIS_WEEK.getCode().equals(showCycleType.getCode())) {
            query.setFormat("%w");
            return buildCategoryCountByTime(query, ReportDateUtils.getDayOfWeek());
        } else if (ShowCycleType.THIS_MONTH.getCode().equals(showCycleType.getCode())) {
            query.setFormat("%U");
            return buildCategoryCountByTime(query, ReportDateUtils.getWeekOfMonth());
        } else if (ShowCycleType.THIS_QUARTER.getCode().equals(showCycleType.getCode())) {
            query.setFormat("%Y-%m");
            return buildCategoryCountByTime(query, ReportDateUtils.getSeason());
        } else if (ShowCycleType.THIS_YEAR.getCode().equals(showCycleType.getCode())) {
            query.setFormat("%Y-%m");
            return buildCategoryCountByTime(query, ReportDateUtils.getCurrentMonthOfYear());
        } else if (ShowCycleType.ASSIGN_TIME.getCode().equals(showCycleType.getCode())) {
            // TODO 待测试
            return buildCategoryCountByTime(query, ReportDateUtils.getMonthWithDate(1L, 1L));
        }
        return null;
    }

    /**
     * 构建按时间分类统计资产数据
     * @param query
     * @param weekMap
     * @return
     */
    private AssetReportResponse buildCategoryCountByTime(AssetReportCategoryCountQuery query,
                                                         Map<String, String> weekMap) {
        AssetReportResponse reportResponse = new AssetReportResponse();
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

        reportResponse.setDate(dateList);
        reportResponse.setList(columnarList);
        return reportResponse;
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
    public AssetReportResponse getNewAssetWithCategory(ReportQueryRequest reportQueryRequest) throws Exception {
        List<AssetCategoryModel> secondCategoryModelList = assetCategoryModelDao.getNextLevelCategoryByName("硬件");
        List<AssetCategoryModel> categoryModelAll = assetCategoryModelDao.getAll();
        // 初始化品类二级品类和子节点的映射
        Map<Integer, List<Integer>> categoryMap = getIntegerListMap(secondCategoryModelList, categoryModelAll);
        // 查询数据
        List<AssetCategoryEntity> assetCategoryEntityList = assetReportDao.getNewAssetWithCategory(reportQueryRequest);
        Map<String, Integer> result = new HashMap<>();
        // 组装数据，将子品类的数量组装进二级品类中
        installNodeData(categoryMap, assetCategoryEntityList, result);
        switch (reportQueryRequest.getTimeType()) {
            case "1":
                return getAssetReportResponseInWeek(secondCategoryModelList, result);
            case "2":
                return getAssetReportResponseInMonth(secondCategoryModelList, result);
            case "3":
                return getAssetReportResponseInQuarterOrMonth(reportQueryRequest, secondCategoryModelList, result);
            case "4":
                return getAssetReportResponseInQuarterOrMonth(reportQueryRequest, secondCategoryModelList, result);
            case "5":
                return getAssetReportResponseInTime(reportQueryRequest, secondCategoryModelList, result);
            default:
                return null;
        }
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

    private AssetReportResponse getAssetReportResponseInQuarterOrMonth(ReportQueryRequest reportQueryRequest,
                                                                       List<AssetCategoryModel> secondCategoryModelList,
                                                                       Map<String, Integer> result) {
        // 初始化返回类
        Map<String, String> monthMap = null;
        if (reportQueryRequest.getTimeType().equals("3")) {
            monthMap = ReportDateUtils.getSeason();
        } else if (reportQueryRequest.getTimeType().equals("4")) {
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

    private AssetReportResponse getAssetReportResponseInTime(ReportQueryRequest reportQueryRequest,
                                                             List<AssetCategoryModel> secondCategoryModelList,
                                                             Map<String, Integer> result) {
        // 初始化返回类
        Map<String, Integer> map = getWeeks(reportQueryRequest.getStartTime(), reportQueryRequest.getEndTime());
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

    public static void main(String[] args) {
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
                                                      Map<String, String> weekMap) {
        List<AssetGroupEntity> assetConutWithGroup = assetReportDao.getAssetConutWithGroup(reportQueryRequest);
        AssetReportResponse reportResponse = new AssetReportResponse();
        Iterator<Map.Entry<String, String>> iterator = weekMap.entrySet().iterator();
        // 横坐标
        List<String> dateList = new ArrayList<>();

        // 将结果数据组装到Response中
        List<ReportData> reportDataList = new ArrayList<>();
        List<Integer> countDate = new ArrayList<>();
        while (iterator.hasNext()) {
            int count = 0;

            Map.Entry<String, String> entry = iterator.next();
            String key = entry.getKey();
            String time = entry.getValue();
            for (AssetGroupEntity entity : assetConutWithGroup) {
                if (key.equals(entity.getDate())) {
                    count = entity.getGroupCount();
                }
            }

            countDate.add(count);
            dateList.add(time);

        }

        for (AssetGroupEntity groupEntity : assetConutWithGroup) {
            ReportData reportData = new ReportData();
            reportData.setClassify(groupEntity.getName());
            reportData.setData(countDate);
            reportDataList.add(reportData);
        }

        reportResponse.setList(reportDataList);
        reportResponse.setDate(dateList);

        return reportResponse;
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
                return getNewAssetWithGroupInWeek(reportQueryRequest);
            case "2":
                return getNewAssetWithGroupInMonth(reportQueryRequest);
            case "3":
                return getNewAssetWithGroupInSeason(reportQueryRequest);
            case "4":
                return getNewAssetWithGroupInYear(reportQueryRequest);
            case "5":
                return getNewAssetWithGroupInRange(reportQueryRequest);
            default:
                throw new BusinessException("查询时间类型不正确");
        }
    }

    /**
     * 根据资产组类别查本周新增资产情况
     * @param reportQueryRequest
     * @return
     */
    public AssetReportResponse getNewAssetWithGroupInWeek(ReportQueryRequest reportQueryRequest) {
        return null;
    }

    /**
     * 根据资产组类别查本月新增资产情况
     * @param reportQueryRequest
     * @return
     */
    public AssetReportResponse getNewAssetWithGroupInMonth(ReportQueryRequest reportQueryRequest) {

        List<AssetGroupEntity> groupReportEntityList = assetReportDao.getNewAssetWithGroupByMonth(reportQueryRequest);
        // 1.初始化返回对象
        AssetReportResponse assetReportResponse = new AssetReportResponse();
        List<AssetReportResponse.ReportData> reportDataList = new ArrayList<>();

        // 2.获取本月的周数信息map,重新按周数key排序
        Map<String, String> treeWeekMap = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Integer.valueOf(o1).compareTo(Integer.valueOf(o2));
            }
        });
        treeWeekMap.putAll(ReportDateUtils.getWeekOfMonth());
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
            AssetReportResponse.ReportData reportData = assetReportResponse.new ReportData();
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