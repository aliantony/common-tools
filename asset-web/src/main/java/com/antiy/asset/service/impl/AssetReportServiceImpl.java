package com.antiy.asset.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.antiy.asset.vo.enums.*;
import com.antiy.common.exception.RequestParamValidateException;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.antiy.asset.dao.AssetReportDao;
import com.antiy.asset.entity.AssetCategoryEntity;
import com.antiy.asset.entity.AssetGroupEntity;
import com.antiy.asset.service.IAssetReportService;
import com.antiy.asset.templet.ReportForm;
import com.antiy.asset.util.ArrayTypeUtil;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.util.ExcelUtils;
import com.antiy.asset.util.ReportDateUtils;
import com.antiy.asset.vo.query.AssetReportCategoryCountQuery;
import com.antiy.asset.vo.request.ReportQueryRequest;
import com.antiy.asset.vo.response.AssetReportResponse;
import com.antiy.asset.vo.response.AssetReportTableResponse;
import com.antiy.asset.vo.response.ReportData;
import com.antiy.asset.vo.response.ReportTableHead;
import com.antiy.common.base.BusinessData;
import com.antiy.common.enums.BusinessModuleEnum;
import com.antiy.common.enums.BusinessPhaseEnum;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;
import com.antiy.common.utils.ParamterExceptionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 资产报表实现类
 *
 * @author zhangyajun
 * @create 2019-03-26 13:41
 **/
@Transactional(rollbackFor = Exception.class)
@Service
public class AssetReportServiceImpl implements IAssetReportService {
    private Logger                logger = LogUtils.get(this.getClass());

    private final static String   DAY    = "%w";
    private final static String   WEEK   = "%u";
    private final static String   MONTH  = "%Y-%m";

    @Resource
    private AssetReportDao        assetReportDao;

    @Override
    public AssetReportResponse queryCategoryCountByTime(AssetReportCategoryCountQuery query) throws Exception {

        ShowCycleType showCycleType = query.getShowCycleType();
        checkParameter(query, showCycleType);
        Map<String, Object> map;
        query.setAreaIds(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser());
        AssetReportResponse reportResponse = new AssetReportResponse();
        if (ShowCycleType.THIS_WEEK.getCode().equals(showCycleType.getCode())) {
            query.setFormat(DAY);
            map = buildCategoryCountByTime(query, ReportDateUtils.getDayOfWeek());
        } else if (ShowCycleType.THIS_MONTH.getCode().equals(showCycleType.getCode())) {
            query.setFormat(WEEK);
            map = buildCategoryCountByTime(query, ReportDateUtils.getWeekOfMonth());
        } else if (ShowCycleType.THIS_QUARTER.getCode().equals(showCycleType.getCode())) {
            query.setFormat(MONTH);
            map = buildCategoryCountByTime(query, ReportDateUtils.getSeason());
        } else if (ShowCycleType.THIS_YEAR.getCode().equals(showCycleType.getCode())) {
            query.setFormat(MONTH);
            map = buildCategoryCountByTime(query, ReportDateUtils.getCurrentMonthOfYear());
        } else if (ShowCycleType.ASSIGN_TIME.getCode().equals(showCycleType.getCode())) {
            query.setFormat(MONTH);
            // 如果为当前月，则显示为周
            Long mouth = ReportDateUtils.monthDiff(query.getBeginTime(), query.getEndTime());
            if (mouth == 0) {
                query.setFormat(WEEK);
            }
            map = buildCategoryCountByTime(query,
                ReportDateUtils.getMonthWithDate(query.getBeginTime(), query.getEndTime()));
        } else {
            throw new BusinessException("非法参数");
        }

        reportResponse.setDate(map.get("dateList") != null ? (List) map.get("dateList") : null);
        reportResponse.setList(map.get("columnarList") != null ? (List) map.get("columnarList") : null);

        return reportResponse;
    }

    /**
     * 构建按时间分类统计资产数据
     * @param query
     * @param weekMap
     * @return
     */
    private Map<String, Object> buildCategoryCountByTime(AssetReportCategoryCountQuery query,
                                                         Map<String, String> weekMap) throws Exception {
        Map<String, Object> map = new HashMap<>();
        // 限制查询状态条件
        List<Integer> statusList = getStatusList();

        query.setAssetStatusList(statusList);
        // 构造柱状图所需的source
        Map<String, Object> timeValueMap = new HashMap<>();
        Map<String, String> computerTimeValueMap = new TreeMap<>();
        Map<String, String> networkTimeValueMap = new TreeMap<>();
        Map<String, String> storageTimeValueMap = new TreeMap<>();
        Map<String, String> safetyTimeValueMap = new TreeMap<>();
        Map<String, String> otherTimeValueMap = new TreeMap<>();
        Map<String, String> amountTimeValueMap = new TreeMap<>();
        Map<String, String> newAddTimeValueMap = new TreeMap<>();
        computerTimeValueMap.put("classifyName", AssetCategoryEnum.COMPUTER.getName());
        networkTimeValueMap.put("classifyName", AssetCategoryEnum.NETWORK.getName());
        storageTimeValueMap.put("classifyName", AssetCategoryEnum.STORAGE.getName());
        safetyTimeValueMap.put("classifyName", AssetCategoryEnum.SAFETY.getName());
        otherTimeValueMap.put("classifyName", AssetCategoryEnum.OTHER.getName());
        amountTimeValueMap.put("classifyName", "总数");
        newAddTimeValueMap.put("classifyName", "新增数量");

        List<String> dateList = new ArrayList<>();
        List<Integer> computerDataList = new ArrayList<>();
        List<Integer> networkDataList = new ArrayList<>();
        List<Integer> storageDataList = new ArrayList<>();
        List<Integer> safetyDataList = new ArrayList<>();
        List<Integer> otherDataList = new ArrayList<>();

        List<Integer> computerAddList = new ArrayList<>();
        List<Integer> networkAddList = new ArrayList<>();
        List<Integer> storageAddList = new ArrayList<>();
        List<Integer> safetyAddList = new ArrayList<>();
        List<Integer> otherAddList = new ArrayList<>();

        List<ReportData> columnarList = new ArrayList<>();

        List<AssetCategoryEntity> amountCategoryEntityList = assetReportDao.findCategoryCountByTime(query);
        Map<String, Object> filterMap = new HashMap<>();
        filterMap.put("beginTime", query.getBeginTime());
        filterMap.put("areaIds", query.getAreaIds());
        filterMap.put("assetStatusList", statusList);
        // 开始时间以前的数据
        List<AssetCategoryEntity> previousCategoryEntity = assetReportDao.findCategoryCountPrevious(query);
        int computerAmountSum = 0;
        int networkAmountSum = 0;
        int storageAmountSum = 0;
        int safetyAmountSum = 0;
        int otherAmountSum = 0;

        for (AssetCategoryEntity assetCategoryEntity : previousCategoryEntity) {
            if (AssetCategoryEnum.COMPUTER.getCode().equals(assetCategoryEntity.getCategoryModel())) {
                computerAmountSum = assetCategoryEntity.getCategoryCount();
                break;
            }
            if (AssetCategoryEnum.OTHER.getCode().equals(assetCategoryEntity.getCategoryModel())) {
                otherAmountSum = assetCategoryEntity.getCategoryCount();
                break;
            }
            if (AssetCategoryEnum.SAFETY.getCode().equals(assetCategoryEntity.getCategoryModel())) {
                safetyAmountSum = assetCategoryEntity.getCategoryCount();
                break;
            }
            if (AssetCategoryEnum.STORAGE.getCode().equals(assetCategoryEntity.getCategoryModel())) {
                storageAmountSum = assetCategoryEntity.getCategoryCount();
                break;
            }
            if (AssetCategoryEnum.NETWORK.getCode().equals(assetCategoryEntity.getCategoryModel())) {
                networkAmountSum = assetCategoryEntity.getCategoryCount();
            }
        }
        // 通过时间map组装数据
        Iterator<Map.Entry<String, String>> iterator = weekMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            String key = entry.getKey();
            String time = entry.getValue();
            dateList.add(time);
            // 计算新增数量
            int computeNewAdd = 0;
            int networkNewAdd = 0;
            int storageNewAdd = 0;
            int safetyNewAdd = 0;
            int otherNewAdd = 0;

            for (AssetCategoryEntity categoryEntity : amountCategoryEntityList) {
                if (key.equals(categoryEntity.getDate())) {
                    if (AssetCategoryEnum.COMPUTER.getCode().equals(categoryEntity.getCategoryModel())
                        && key.equals(categoryEntity.getDate())) {
                        filterMap.put("categoryModel", categoryEntity.getCategoryModel());
                        computeNewAdd = computeNewAdd + categoryEntity.getCategoryCount();
                    }
                    if (AssetCategoryEnum.NETWORK.getCode().equals(categoryEntity.getCategoryModel())
                            && key.equals(categoryEntity.getDate())) {
                        filterMap.put("categoryModel", categoryEntity.getCategoryModel());
                        networkNewAdd = networkNewAdd + categoryEntity.getCategoryCount();
                    }
                    if (AssetCategoryEnum.STORAGE.getCode().equals(categoryEntity.getCategoryModel())
                            && key.equals(categoryEntity.getDate())) {
                        filterMap.put("categoryModel", categoryEntity.getCategoryModel());
                        storageNewAdd = storageNewAdd + categoryEntity.getCategoryCount();
                    }
                    if (AssetCategoryEnum.SAFETY.getCode().equals(categoryEntity.getCategoryModel())
                            && key.equals(categoryEntity.getDate())) {
                        filterMap.put("categoryModel", categoryEntity.getCategoryModel());
                        safetyNewAdd = safetyNewAdd + categoryEntity.getCategoryCount();
                    }
                    if (AssetCategoryEnum.OTHER.getCode().equals(categoryEntity.getCategoryModel())
                            && key.equals(categoryEntity.getDate())) {
                        filterMap.put("categoryModel", categoryEntity.getCategoryModel());
                        otherNewAdd = otherNewAdd + categoryEntity.getCategoryCount();
                    }
                }
            }

            computerAmountSum = computerAmountSum + computeNewAdd;
            networkAmountSum = networkAmountSum + networkNewAdd;
            storageAmountSum = storageAmountSum + storageNewAdd;
            safetyAmountSum = safetyAmountSum + safetyNewAdd;
            otherAmountSum = otherAmountSum + otherNewAdd;

            computerTimeValueMap.put(key, String.valueOf(computerAmountSum));
            networkTimeValueMap.put(key, String.valueOf(networkAmountSum));
            storageTimeValueMap.put(key, String.valueOf(storageAmountSum));
            safetyTimeValueMap.put(key, String.valueOf(safetyAmountSum));
            otherTimeValueMap.put(key, String.valueOf(otherAmountSum));
            newAddTimeValueMap.put(key,
                String.valueOf(computeNewAdd + networkNewAdd + storageNewAdd + safetyNewAdd + otherNewAdd));
            amountTimeValueMap.put(key, String
                .valueOf(computerAmountSum + networkAmountSum + storageAmountSum + safetyAmountSum + otherAmountSum));

            computerDataList.add(computerAmountSum);
            networkDataList.add(networkAmountSum);
            storageDataList.add(storageAmountSum);
            safetyDataList.add(safetyAmountSum);
            otherDataList.add(otherAmountSum);

            computerAddList.add(computeNewAdd);
            networkAddList.add(networkNewAdd);
            storageAddList.add(storageNewAdd);
            safetyAddList.add(safetyNewAdd);
            otherAddList.add(otherNewAdd);
        }

        timeValueMap.put(AssetCategoryEnum.COMPUTER.getName(), computerTimeValueMap);
        timeValueMap.put(AssetCategoryEnum.NETWORK.getName(), networkTimeValueMap);
        timeValueMap.put(AssetCategoryEnum.STORAGE.getName(), storageTimeValueMap);
        timeValueMap.put(AssetCategoryEnum.SAFETY.getName(), safetyTimeValueMap);
        timeValueMap.put(AssetCategoryEnum.OTHER.getName(), otherTimeValueMap);
        timeValueMap.put("总数", amountTimeValueMap);
        timeValueMap.put("新增", newAddTimeValueMap);

        map.put("timeValueMap", timeValueMap);

        if (query.getReportFormType() == null) {
            // 构建柱状数据
            ReportData computeDeviceColumnar = new ReportData();
            computeDeviceColumnar.setClassify(AssetCategoryEnum.COMPUTER.getName());
            computeDeviceColumnar.setData(computerDataList);
            computeDeviceColumnar.setAdd(computerAddList);
            columnarList.add(computeDeviceColumnar);

            ReportData networkDeviceColumnar = new ReportData();
            networkDeviceColumnar.setClassify(AssetCategoryEnum.NETWORK.getName());
            networkDeviceColumnar.setData(networkDataList);
            networkDeviceColumnar.setAdd(networkAddList);
            columnarList.add(networkDeviceColumnar);

            ReportData storageDeviceColumnar = new ReportData();
            storageDeviceColumnar.setClassify(AssetCategoryEnum.STORAGE.getName());
            storageDeviceColumnar.setData(storageDataList);
            storageDeviceColumnar.setAdd(storageAddList);
            columnarList.add(storageDeviceColumnar);

            ReportData safetyDeviceColumnar = new ReportData();
            safetyDeviceColumnar.setClassify(AssetCategoryEnum.SAFETY.getName());
            safetyDeviceColumnar.setData(safetyDataList);
            safetyDeviceColumnar.setAdd(safetyAddList);
            columnarList.add(safetyDeviceColumnar);

            ReportData otherDeviceColumnar = new ReportData();
            otherDeviceColumnar.setClassify(AssetCategoryEnum.OTHER.getName());
            otherDeviceColumnar.setData(otherDataList);
            otherDeviceColumnar.setAdd(otherAddList);
            columnarList.add(otherDeviceColumnar);

            map.put("dateList", dateList);
            map.put("columnarList", columnarList);
        }
        return map;
    }

    private List<Integer> getStatusList() {
        List<Integer> statusList = new ArrayList<>();
        for (AssetStatusEnum assetStatusEnum : AssetStatusEnum.values()) {
            if (!assetStatusEnum.equals(AssetStatusEnum.RETIRE) && !assetStatusEnum.equals(AssetStatusEnum.NOT_REGISTER)
                && !assetStatusEnum.equals(AssetStatusEnum.WAIT_REGISTER)) {
                statusList.add(assetStatusEnum.getCode());
            }
        }
        return statusList;
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

    private static String hardwareId = null;

    private String getHardwareCategoryId() throws Exception {
        if (hardwareId == null) {
            return null;
        }
        return hardwareId;
    }

    @Override
    public void exportCategoryCount(AssetReportCategoryCountQuery assetReportCategoryCountQuery,
                                    HttpServletRequest request) throws Exception {
        ReportForm reportForm = new ReportForm();
        String titleStr = assetReportCategoryCountQuery.getShowCycleType().getMessage();
        if (assetReportCategoryCountQuery.getShowCycleType().equals(ShowCycleType.ASSIGN_TIME)) {
            titleStr = getTitleStr(assetReportCategoryCountQuery);
        }
        String title = titleStr + "资产品类型号总数";
        reportForm.setTitle(title);
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
        int[] total = new int[headerList.size()];
        List<ReportData> reportList = assetReportResponse.getList();
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
        columnList.add("新增");
        data[reportDataList.size()] = ArrayTypeUtil.integerArrayToStringArray(add);
        columnList.add("总数");
        data[reportDataList.size() + 1] = ArrayTypeUtil.integerArrayToStringArray(total);
        reportForm.setHeaderList(headerList);
        reportForm.setData(data);
        reportForm.setColumnList(columnList);
        String fileName = title + ".xlsx";
        ExcelUtils.exportFormToClient(reportForm, this.encodeChineseDownloadFileName(request, fileName));
        // 记录操作日志和运行日志
        LogUtils.recordOperLog(new BusinessData("导出《" + title + "》", 0, "", assetReportCategoryCountQuery,
            BusinessModuleEnum.REPORT, BusinessPhaseEnum.NONE));
        LogUtils.info(LogUtils.get(AssetReportServiceImpl.class), AssetEventEnum.ASSET_REPORT_EXPORT.getName() + " {}",
            assetReportCategoryCountQuery.toString());
    }

    private String getTitleStr(AssetReportCategoryCountQuery assetReportCategoryCountQuery) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        String beginTime = simpleDateFormat.format(new Date(assetReportCategoryCountQuery.getBeginTime()));
        String endTime = simpleDateFormat.format(new Date(assetReportCategoryCountQuery.getEndTime()));
        return new StringBuffer(beginTime).append("~").append(endTime).toString();
    }

    // private String getFileName(AssetReportCategoryCountQuery assetReportCategoryCountQuery) {
    // SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
    // String beginTime = simpleDateFormat.format(new Date(assetReportCategoryCountQuery.getBeginTime()));
    // String endTime = simpleDateFormat.format(new Date(assetReportCategoryCountQuery.getEndTime()));
    // return new StringBuffer(beginTime).append("-").append(endTime).toString();
    // }

    @Override
    public AssetReportResponse getAssetCountWithGroup(ReportQueryRequest reportQueryRequest) throws Exception {
        // @ApiModelProperty(value = "时间类型,1-本周,2-本月,3-本季度,4-本年,5-时间范围", required = true)
        switch (reportQueryRequest.getTimeType()) {
            case "1":
                reportQueryRequest.setSqlTime(DAY);
                return buildGroupCountByTime(reportQueryRequest, ReportDateUtils.getDayOfWeek());

            case "2":
                reportQueryRequest.setSqlTime(WEEK);
                return buildGroupCountByTime(reportQueryRequest, ReportDateUtils.getWeekOfMonth());

            case "3":
                reportQueryRequest.setSqlTime(MONTH);
                return buildGroupCountByTime(reportQueryRequest, ReportDateUtils.getSeason());

            case "4":
                reportQueryRequest.setSqlTime(MONTH);
                return buildGroupCountByTime(reportQueryRequest, ReportDateUtils.getCurrentMonthOfYear());

            case "5":
                reportQueryRequest.setSqlTime(MONTH);

                // 如果为当前月，则显示为周
                Long mouth = ReportDateUtils.monthDiff(reportQueryRequest.getStartTime(),
                    reportQueryRequest.getEndTime());
                if (mouth == 0) {
                    reportQueryRequest.setSqlTime(WEEK);
                }
                return buildGroupCountByTime(reportQueryRequest, ReportDateUtils
                    .getMonthWithDate(reportQueryRequest.getStartTime(), reportQueryRequest.getEndTime()));
            default:
                throw new RequestParamValidateException("查询时间类型不正确");
        }

    }

    private AssetReportResponse buildGroupCountByTime(ReportQueryRequest reportQueryRequest,
                                                      Map<String, String> timeMap) {
        reportQueryRequest.setAssetStatusList(getStatusList());
        if (MapUtils.isEmpty(timeMap)) {
            // 如果没有时间参数，则返回即可。
            return null;
        }
        // 1.获取结束时间之前,Top5的资产组数量信息
        reportQueryRequest.setTopFive(true);
        List<AssetGroupEntity> topGroupReportEntityList = assetReportDao.getAssetConutWithGroup(reportQueryRequest);

        if (Objects.nonNull(reportQueryRequest.getTopFive())) {
            // 添加当前结束时间内TOP5的资产组Id列表,后续查询缩小范围
            List<Integer> groupIds = new ArrayList<>();
            topGroupReportEntityList.stream().forEach(assetGroupEntity -> groupIds.add(assetGroupEntity.getGroupId()));
            reportQueryRequest.setGroupIds(groupIds);
        }

        // 2.获取所选本时间段,TOP5各资产组新增数量信息
        List<AssetGroupEntity> addAssetGroupEntityList = assetReportDao.getNewAssetWithGroup(reportQueryRequest);

        // 3.获取当前时间段,截止开始日期前资产组的统计信息,用于和新增数量相加求每一天总数
        ReportQueryRequest reportQuery = new ReportQueryRequest();
        reportQuery.setSqlTime(reportQueryRequest.getSqlTime());
        reportQuery.setEndTime(reportQueryRequest.getStartTime());
        reportQuery.setAreaIds(reportQueryRequest.getAreaIds());
        reportQuery.setAssetStatusList(getStatusList());
        List<AssetGroupEntity> oldAssetGroupEntities = assetReportDao.getAssetConutWithGroup(reportQuery);

        // 4.初始化返回对象
        AssetReportResponse assetReportResponse = new AssetReportResponse();

        // 5.添加日期信息到keyList用于遍历
        List<String> dateKeyList = new ArrayList<>(timeMap.size());
        List<String> dateValueList = new ArrayList<>(timeMap.size());
        for (Map.Entry<String, String> entry : timeMap.entrySet()) {
            dateKeyList.add(entry.getKey());
            dateValueList.add(entry.getValue());
        }
        assetReportResponse.setDate(dateValueList);

        // 6.添加资产组名字信息,根据名字遍历(或根据id)
        List<String> groupNameList = new ArrayList<>(5);
        topGroupReportEntityList.forEach(groupReportEntity -> {
            groupNameList.add(groupReportEntity.getName());
        });

        // 4.根据资产组名字信息和日期进行封装数据
        List<ReportData> dataList = new ArrayList<>(5);
        groupNameList.forEach(groupName -> {
            ReportData reportData = new ReportData();
            reportData.setClassify(groupName);
            List<Integer> totalNumList = new ArrayList<>(dateKeyList.size());
            List<Integer> addNumList = new ArrayList<>(dateKeyList.size());

            for (int i = 0; i < dateKeyList.size(); i++) {
                Integer totalNum = 0;
                Integer addNum = 0;

                for (AssetGroupEntity addAssetGroupEntity : addAssetGroupEntityList) {
                    // 从新增列表获取名字和日期均匹配的新增数量
                    if (dateKeyList.get(i).equals(addAssetGroupEntity.getDate())
                        && groupName.equals(addAssetGroupEntity.getName())) {
                        addNum = addAssetGroupEntity.getGroupCount();
                    }
                }
                // 总数:第一个数据从截止日期前的总数量获取,第二个起直接取上一个数据+新增数
                if (i == 0) {
                    Optional<AssetGroupEntity> assetGroupOptional = oldAssetGroupEntities.stream()
                        .filter(e -> e.getName().equals(groupName) && groupName.equals(e.getName())).findFirst();
                    totalNum = assetGroupOptional.isPresent() ? assetGroupOptional.get().getGroupCount() + addNum
                        : addNum;
                } else {
                    totalNum = totalNumList.get(i - 1) + addNum;
                }
                totalNumList.add(totalNum);
                addNumList.add(addNum);
            }
            reportData.setData(totalNumList);
            reportData.setAdd(addNumList);
            dataList.add(reportData);
        });
        assetReportResponse.setDate(dateValueList);
        assetReportResponse.setList(dataList);
        return assetReportResponse;

    }

    /**
     * 按时间分类统计返回表格数据
     * @param query
     * @return
     * @throws Exception
     */
    @Override
    public AssetReportTableResponse queryCategoryCountByTimeToTable(AssetReportCategoryCountQuery query) throws Exception {
        ShowCycleType showCycleType = query.getShowCycleType();
        query.setAreaIds(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser());
        checkParameter(query, showCycleType);
        if (ShowCycleType.THIS_WEEK.getCode().equals(showCycleType.getCode())) {
            query.setFormat(DAY);
            return getAssetReportTableResponse(query, ReportDateUtils.getDayOfWeek());
        } else if (ShowCycleType.THIS_MONTH.getCode().equals(showCycleType.getCode())) {
            query.setFormat(WEEK);
            return getAssetReportTableResponse(query, ReportDateUtils.getWeekOfMonth());
        } else if (ShowCycleType.THIS_QUARTER.getCode().equals(showCycleType.getCode())) {
            query.setFormat(MONTH);
            return getAssetReportTableResponse(query, ReportDateUtils.getSeason());
        } else if (ShowCycleType.THIS_YEAR.getCode().equals(showCycleType.getCode())) {
            query.setFormat(MONTH);
            return getAssetReportTableResponse(query, ReportDateUtils.getCurrentMonthOfYear());
        } else if (ShowCycleType.ASSIGN_TIME.getCode().equals(showCycleType.getCode())) {
            query.setFormat(MONTH);

            // 如果为当前月，则显示为周
            Long mouth = ReportDateUtils.monthDiff(query.getBeginTime(), query.getEndTime());
            if (mouth == 0) {
                query.setFormat(WEEK);
            }

            return getAssetReportTableResponse(query,
                ReportDateUtils.getMonthWithDate(query.getBeginTime(), query.getEndTime()));
        } else {
            throw new BusinessException("非法参数");
        }
    }

    /**
     * 获取表格数据
     * @param query
     * @param dateMap
     * @return
     */
    private AssetReportTableResponse getAssetReportTableResponse(AssetReportCategoryCountQuery query,
                                                                 Map<String, String> dateMap) throws Exception {
        AssetReportTableResponse reportTableResponse = new AssetReportTableResponse();
        List<Map<String, String>> rows = new ArrayList<>();
        List<ReportTableHead> children = new ArrayList<>();
        ReportTableHead initTableHead = new ReportTableHead();
        initTableHead.setName("");
        initTableHead.setKey("classifyName");
        children.add(initTableHead);
        Iterator<Map.Entry<String, String>> iterator = dateMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            ReportTableHead reportTableHead = new ReportTableHead();
            reportTableHead.setKey(entry.getKey());
            reportTableHead.setName(entry.getValue());
            children.add(reportTableHead);
        }

        Map<String, Object> map = buildCategoryCountByTime(query, dateMap);
        Map<String, Object> ssMap = (Map<String, Object>) map.get("timeValueMap");
        rows.add((Map<String, String>) ssMap.get(AssetCategoryEnum.COMPUTER.getName()));
        rows.add((Map<String, String>) ssMap.get(AssetCategoryEnum.NETWORK.getName()));
        rows.add((Map<String, String>) ssMap.get(AssetCategoryEnum.STORAGE.getName()));
        rows.add((Map<String, String>) ssMap.get(AssetCategoryEnum.SAFETY.getName()));
        rows.add((Map<String, String>) ssMap.get(AssetCategoryEnum.OTHER.getName()));
        rows.add((Map<String, String>) ssMap.get("新增"));
        rows.add((Map<String, String>) ssMap.get("总数"));

        reportTableResponse.setRows(rows);
        reportTableResponse.setChildren(children);
        return reportTableResponse;
    }

    // /**
    // * 根据资产组类别查新增资产情况-封装数据
    // * @param reportQueryRequest
    // * @return
    // */
    // public AssetReportResponse queryNewAssetWithGroup(ReportQueryRequest reportQueryRequest,
    // Map<String, String> timeMap) {
    // if (MapUtils.isEmpty(timeMap)) {
    // return null;
    // }
    // List<AssetGroupEntity> groupReportEntityList = assetReportDao.getNewAssetWithGroup(reportQueryRequest);
    // // 1.初始化返回对象
    // AssetReportResponse assetReportResponse = new AssetReportResponse();
    // List<ReportData> reportDataList = new ArrayList<>();
    //
    // // 2.添加日期信息到dateList,
    // List<String> dateKeyList = new ArrayList<>(timeMap.size());
    // List<String> dateValueList = new ArrayList<>(timeMap.size());
    // for (Map.Entry<String, String> entry : timeMap.entrySet()) {
    // dateKeyList.add(entry.getKey());
    // dateValueList.add(entry.getValue());
    // }
    // // 3.TOP5资产组名字信息
    // reportQueryRequest.setTopFive(true);
    // List<AssetGroupEntity> topAssetGroupEntityList = assetReportDao.getAssetConutWithGroup(reportQueryRequest);
    // List<String> groupNameList = new ArrayList<>(5);
    // topAssetGroupEntityList.forEach(groupReportEntity -> {
    // if (!groupNameList.contains(groupReportEntity.getName())) {
    // groupNameList.add(groupReportEntity.getName());
    // }
    // });
    // // 4.根据资产组信息封装
    // groupNameList.forEach(groupName -> {
    // ReportData reportData = new ReportData();
    // reportData.setClassify(groupName);
    // List<Integer> addNumList = new ArrayList<>(timeMap.size());
    //
    // dateKeyList.forEach(date -> {
    // Integer num = 0;
    // for (AssetGroupEntity groupReportEntity : groupReportEntityList) {
    // // 资产组别且对应周数匹配
    // if (groupReportEntity.getName().equals(groupName) && groupReportEntity.getDate().equals(date)) {
    // num = groupReportEntity.getGroupCount();
    // }
    // }
    // addNumList.add(num);
    // });
    // reportData.setData(addNumList);
    // reportDataList.add(reportData);
    // });
    // assetReportResponse.setDate(dateValueList);
    // assetReportResponse.setList(reportDataList);
    // return assetReportResponse;
    // }

    @Override
    public AssetReportTableResponse getAssetGroupReportTable(ReportQueryRequest reportQueryRequest) throws Exception {
        // 1-本周,2-本月,3-本季度,4-本年,5-时间范围
        switch (reportQueryRequest.getTimeType()) {
            case "1":
                reportQueryRequest.setSqlTime(DAY);
                return buildAssetReportTable(reportQueryRequest, ReportDateUtils.getDayOfWeek(), "本周");
            case "2":
                reportQueryRequest.setSqlTime(WEEK);
                return buildAssetReportTable(reportQueryRequest, ReportDateUtils.getWeekOfMonth(), "本月");
            case "3":
                reportQueryRequest.setSqlTime(MONTH);
                return buildAssetReportTable(reportQueryRequest, ReportDateUtils.getSeason(), "本季度");
            case "4":
                reportQueryRequest.setSqlTime(MONTH);
                return buildAssetReportTable(reportQueryRequest, ReportDateUtils.getCurrentMonthOfYear(), "本年");
            case "5":
                reportQueryRequest.setSqlTime(MONTH);
                // 如果为当前月，则显示为周
                Long mouth = ReportDateUtils.monthDiff(reportQueryRequest.getStartTime(),
                    reportQueryRequest.getEndTime());
                if (mouth == 0) {
                    reportQueryRequest.setSqlTime(WEEK);
                }

                return buildAssetReportTable(reportQueryRequest,
                    ReportDateUtils.getMonthWithDate(reportQueryRequest.getStartTime(),
                        reportQueryRequest.getEndTime()),
                    reportQueryRequest.getStartTime() + "~" + reportQueryRequest.getEndTime());
            default:
                throw new RequestParamValidateException("查询时间类型不正确");
        }
    }

    @Override
    public void exportAssetGroupTable(ReportQueryRequest reportQueryRequest) throws Exception {
        ReportForm reportForm = new ReportForm();
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
        AssetReportTableResponse assetReportTableResponse = this.getAssetGroupReportTable(reportQueryRequest);
        // 第二行标题
        List<String> headerList = new ArrayList<>();
        List<ReportTableHead> reportTableHeadList = assetReportTableResponse.getChildren();
        for (ReportTableHead reportTableHead : reportTableHeadList) {
            if (reportTableHead.getName() != "") {
                headerList.add(reportTableHead.getName());
            }
        }
        // 第一列标题
        List<String> columnList = new ArrayList();
        List<Map<String, String>> rows = assetReportTableResponse.getRows();
        for (Map<String, String> map : rows) {
            columnList.add(map.get("classifyName"));
        }
        // 数据
        String[][] data = new String[columnList.size()][headerList.size()];

        int i = 0;
        for (Map<String, String> map : rows) {
            int j = 0;
            for (Map.Entry<String, String> entry : map.entrySet()) {
                if (!entry.getKey().equals("classifyName")) {
                    data[i][j] = entry.getValue();
                    j++;
                }
            }
            i++;
        }
        String title = titleStr + "资产组资产总数";
        reportForm.setTitle(title);
        reportForm.setHeaderList(headerList);
        reportForm.setColumnList(columnList);
        reportForm.setData(data);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
            .getRequest();
        ExcelUtils.exportFormToClient(reportForm, this.encodeChineseDownloadFileName(request, title + ".xlsx"));
        // 记录操作日志和运行日志
        LogUtils.recordOperLog(new BusinessData("导出《" + title + "》", 0, "", reportQueryRequest,
            BusinessModuleEnum.REPORT, BusinessPhaseEnum.NONE));
        LogUtils.info(LogUtils.get(AssetReportServiceImpl.class), AssetEventEnum.ASSET_REPORT_EXPORT.getName() + " {}",
            reportQueryRequest.toString());

    }

    private AssetReportTableResponse buildAssetReportTable(ReportQueryRequest reportQueryRequest,
                                                           Map<String, String> timeMap, String title) {
        // 1.获取截止本时间段,开始时间之前的资产组数量信息
        ReportQueryRequest initReportQueryRequest = new ReportQueryRequest();
        initReportQueryRequest.setEndTime(reportQueryRequest.getStartTime());
        initReportQueryRequest.setAreaIds(reportQueryRequest.getAreaIds());
        initReportQueryRequest.setAssetStatusList(getStatusList());
        List<AssetGroupEntity> initAssetGroupEntities = assetReportDao.getAssetConutWithGroup(initReportQueryRequest);
        // 2.本时间段新增的数据
        reportQueryRequest.setAssetStatusList(getStatusList());
        List<AssetGroupEntity> assetGroupEntities = assetReportDao.getNewAssetWithGroup(reportQueryRequest);
        // 3.添加资产组名字
        List<String> initNameList = new ArrayList<>();
        for (AssetGroupEntity assetGroupEntity : initAssetGroupEntities) {
            initNameList.add(assetGroupEntity.getName());
        }
        // 添加本时间段内,新增资产的资产组的名字
        for (AssetGroupEntity assetGroupEntity : assetGroupEntities) {
            if (!initNameList.contains(assetGroupEntity.getName())) {
                initNameList.add(assetGroupEntity.getName());
                AssetGroupEntity assetGroupEntity1 = new AssetGroupEntity();
                assetGroupEntity1.setName(assetGroupEntity.getName());
                assetGroupEntity1.setGroupCount(0);
                initAssetGroupEntities.add(assetGroupEntity1);
            }
        }
        // 组装数据
        AssetReportTableResponse assetReportTableResponse = new AssetReportTableResponse();
        // 设置标题
        assetReportTableResponse.setFormName("资产组" + title + "总数统计");
        // 设置表格头对象
        List<ReportTableHead> reportTableHeadList = new ArrayList<>();
        ReportTableHead reportTableHeadFirst = new ReportTableHead();
        reportTableHeadFirst.setName("");
        reportTableHeadFirst.setKey("classifyName");
        reportTableHeadList.add(reportTableHeadFirst);
        for (Map.Entry<String, String> entry : timeMap.entrySet()) {
            ReportTableHead reportTableHead = new ReportTableHead();
            reportTableHead.setKey(entry.getKey());
            reportTableHead.setName(entry.getValue());
            reportTableHeadList.add(reportTableHead);
        }
        assetReportTableResponse.setChildren(reportTableHeadList);
        // 设置每行的数据
        List<Map<String, String>> rows = new ArrayList<>();
        for (AssetGroupEntity assetGroupEntity : initAssetGroupEntities) {
            // 获取日期map的第一个key
            String firstDateKey = timeMap.keySet().iterator().next();
            Map<String, String> map = new LinkedHashMap<>();
            map.put("classifyName", assetGroupEntity.getName());
            map.put(firstDateKey, DataTypeUtils.integerToString(assetGroupEntity.getGroupCount()));
            rows.add(map);
        }
        // --------------------------------初始化数据完毕--------------------------------
        // --------------------------------开始增加新数据--------------------------------
        // 获取时间段新增数据

        // 初始化新增的数据
        List<Map<String, String>> addRowsResult = new ArrayList<>();
        for (AssetGroupEntity assetGroupEntity : initAssetGroupEntities) {
            for (Map.Entry<String, String> entry : timeMap.entrySet()) {
                Map<String, String> initaddMap = new LinkedHashMap<>();
                initaddMap.put(entry.getKey(), "0");
                initaddMap.put("classifyName", assetGroupEntity.getName());
                addRowsResult.add(initaddMap);
            }
        }

        // 获得新增的数据
        List<Map<String, String>> addRows = new ArrayList<>();
        for (AssetGroupEntity assetGroupEntity : assetGroupEntities) {
            Map<String, String> addMap = new HashMap<>();
            if (initNameList.contains(assetGroupEntity.getName())) {
                addMap.put("classifyName", assetGroupEntity.getName());
                addMap.put(assetGroupEntity.getDate(), DataTypeUtils.integerToString(assetGroupEntity.getGroupCount()));
                addRows.add(addMap);
            }
        }

        // 将新增数据与初始化的新增数据进行比较配对。将有变化的数据进行修改
        for (int i = 0; i < addRows.size(); i++) {
            for (int j = 0; j < addRowsResult.size(); j++) {
                if (addRows.get(i).get("classifyName").equals(addRowsResult.get(j).get("classifyName")) && addRows
                    .get(i).keySet().iterator().next().equals(addRowsResult.get(j).keySet().iterator().next())) {
                    addRowsResult.get(j).put(addRows.get(i).keySet().iterator().next(),
                        addRows.get(i).get(addRows.get(i).keySet().iterator().next()));
                }
            }
        }

        // 把旧数据和新数据加起来
        for (int i = 0; i < rows.size(); i++) {
            for (int j = 0; j < addRowsResult.size(); j++) {
                if (rows.get(i).get("classifyName").equals(addRowsResult.get(j).get("classifyName"))) {
                    String day = addRowsResult.get(j).keySet().iterator().next();
                    String addCount = addRowsResult.get(j).get(addRowsResult.get(j).keySet().iterator().next());
                    Iterator<String> iterator = rows.get(i).keySet().iterator();
                    String lastKey = null;
                    while (iterator.hasNext()) {
                        lastKey = iterator.next();
                    }
                    String oldCount = rows.get(i).get(lastKey);
                    int sum = DataTypeUtils.stringToInteger(oldCount) + DataTypeUtils.stringToInteger(addCount);
                    rows.get(i).put(day, DataTypeUtils.integerToString(sum));
                }
            }
        }
        // 按最后一项时间项时的总数排序
        rows.sort((o1, o2) -> {
            String timeMapLastKey = "";
            for (Map.Entry<String, String> entry : timeMap.entrySet()) {
                timeMapLastKey = entry.getKey();
            }
            return DataTypeUtils.stringToInteger(o2.get(timeMapLastKey))
                    .compareTo(DataTypeUtils.stringToInteger(o1.get(timeMapLastKey)));
        });

        assetReportTableResponse.setRows(rows);
        return assetReportTableResponse;
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