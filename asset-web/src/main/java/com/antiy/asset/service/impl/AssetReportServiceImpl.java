package com.antiy.asset.service.impl;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

import com.antiy.asset.vo.enums.ReportFormType;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

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
            query.setFormat(MONTH);
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

        if (query.getReportFormType().equals(ReportFormType.ALL)) {
            List<AssetCategoryEntity> assetCategoryEntities = assetReportDao.findCategoryCountPrevious(query);
            int computeDevice = 0;
            int networkDevice = 0;
            int storageDevice = 0;
            int safetyDevice = 0;
            int otherDevice = 0;
            for (AssetCategoryEntity assetCategoryEntity : assetCategoryEntities) {
                AssetCategoryModel assetCategory = new AssetCategoryModel();
                assetCategoryModel.setId(assetCategoryEntity.getCategoryModel());
                assetCategoryModel.setParentId(assetCategoryEntity.getParentId());
                assetCategoryModel.setName(assetCategoryEntity.getCategoryName());
                AssetCategoryModel parentAssetCategory = getParentCategory(assetCategory, categoryModels);
                String secondCategoryName = parentAssetCategory.getName();
                if (AssetSecondCategoryEnum.COMPUTE_DEVICE.getMsg().equals(secondCategoryName)) {
                    computeDevice = computeDevice + assetCategoryEntity.getCategoryCount();
                } else if (AssetSecondCategoryEnum.NETWORK_DEVICE.getMsg().equals(secondCategoryName)) {
                    networkDevice = networkDevice + assetCategoryEntity.getCategoryCount();
                } else if (AssetSecondCategoryEnum.STORAGE_DEVICE.getMsg().equals(secondCategoryName)) {
                    storageDevice = storageDevice + assetCategoryEntity.getCategoryCount();
                } else if (AssetSecondCategoryEnum.SAFETY_DEVICE.getMsg().equals(secondCategoryName)) {
                    safetyDevice = safetyDevice + assetCategoryEntity.getCategoryCount();
                } else if (AssetSecondCategoryEnum.OTHER_DEVICE.getMsg().equals(secondCategoryName)) {
                    otherDevice = otherDevice + assetCategoryEntity.getCategoryCount();
                }
            }
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                String key = entry.getKey();
                String time = entry.getValue();
                List<AssetCategoryEntity> categoryEntityList = assetReportDao.findCategoryCountByTime(query);
                for (AssetCategoryEntity categoryEntity : categoryEntityList) {
                    if (key.equals(categoryEntity.getDate())) {
                        assetCategoryModel.setId(categoryEntity.getCategoryModel());
                        assetCategoryModel.setParentId(categoryEntity.getParentId());
                        assetCategoryModel.setName(categoryEntity.getCategoryName());
                        String secondCategoryName = this.getParentCategory(assetCategoryModel, categoryModels)
                            .getName();
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
            addColumnarList(computerDataList, columnarList, AssetSecondCategoryEnum.COMPUTE_DEVICE);
            addColumnarList(networkDataList, columnarList, AssetSecondCategoryEnum.NETWORK_DEVICE);
            addColumnarList(storageDataList, columnarList, AssetSecondCategoryEnum.STORAGE_DEVICE);
            addColumnarList(safetyDataList, columnarList, AssetSecondCategoryEnum.SAFETY_DEVICE);
            addColumnarList(otherDataList, columnarList, AssetSecondCategoryEnum.OTHER_DEVICE);
        } else {
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
                        String secondCategoryName = this.getParentCategory(assetCategoryModel, categoryModels)
                            .getName();
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
            addColumnarNewList(computerDataList, columnarList, AssetSecondCategoryEnum.COMPUTE_DEVICE);
            addColumnarNewList(networkDataList, columnarList, AssetSecondCategoryEnum.NETWORK_DEVICE);
            addColumnarNewList(storageDataList, columnarList, AssetSecondCategoryEnum.STORAGE_DEVICE);
            addColumnarNewList(safetyDataList, columnarList, AssetSecondCategoryEnum.SAFETY_DEVICE);
            addColumnarNewList(otherDataList, columnarList, AssetSecondCategoryEnum.OTHER_DEVICE);

        }

        map.put("dateList", dateList);
        map.put("columnarList", columnarList);
        return map;
    }

    private void addColumnarNewList(List<Integer> safetyDataList, List<ReportData> columnarList,
                                    AssetSecondCategoryEnum assetSecondCategoryEnum) {
        ReportData reportData = new ReportData();
        reportData.setClassify(assetSecondCategoryEnum.getMsg());
        reportData.setAdd(safetyDataList);
        columnarList.add(reportData);
    }

    private void addColumnarList(List<Integer> safetyDataList, List<ReportData> columnarList,
                                 AssetSecondCategoryEnum assetSecondCategoryEnum) {
        ReportData reportData = new ReportData();
        reportData.setClassify(assetSecondCategoryEnum.getMsg());
        reportData.setData(safetyDataList);
        columnarList.add(reportData);
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
        assetReportCategoryCountQuery.setReportFormType(ReportFormType.ALL);
        AssetReportResponse assetReportResponse = this.queryCategoryCountByTime(assetReportCategoryCountQuery);
        List<String> headerList = assetReportResponse.getDate();
        List<ReportData> reportDataList = assetReportResponse.getList();
        List<String> columnList = new ArrayList<>();
        String[][] data = new String[reportDataList.size() + 2][headerList.size()];
        for (int i = 0; i < reportDataList.size(); i++) {
            ReportData reportData = reportDataList.get(i);
            String classify = reportData.getClassify();
            columnList.add(classify);
            List dataList = reportData.getData();
            data[i] = ArrayTypeUtil.objectArrayToStringArray(dataList.toArray());
        }
        columnList.add("总数");
        columnList.add("新增");
        int[] total = new int[headerList.size()];
        assetReportCategoryCountQuery.setReportFormType(ReportFormType.NEW);
        AssetReportResponse addReport = this.queryCategoryCountByTime(assetReportCategoryCountQuery);
        List<ReportData> reportList = addReport.getList();
        int[] add = new int[headerList.size()];
        for (int i = 0; i < headerList.size(); i++) {
            total[i] = 0;
            add[i] = 0;
            for (int j = 0; j < reportDataList.size(); j++) {
                ReportData reportData = reportList.get(j);
                List<Integer> addList = reportData.getAdd();
                add[i] += addList.get(i);
                total[i] += Integer.parseInt(data[j][i]);
            }
        }
        data[reportDataList.size()] = ArrayTypeUtil.integerArrayToStringArray(total);
        data[reportDataList.size() + 1] = ArrayTypeUtil.integerArrayToStringArray(add);
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