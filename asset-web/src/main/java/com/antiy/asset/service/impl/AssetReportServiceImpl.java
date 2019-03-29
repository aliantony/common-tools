package com.antiy.asset.service.impl;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

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
import com.antiy.asset.vo.enums.ReportFormType;
import com.antiy.asset.vo.enums.ShowCycleType;
import com.antiy.asset.vo.query.AssetReportCategoryCountQuery;
import com.antiy.asset.vo.request.ReportQueryRequest;
import com.antiy.asset.vo.response.AssetReportResponse;
import com.antiy.asset.vo.response.AssetReportTableResponse;
import com.antiy.asset.vo.response.ReportData;
import com.antiy.asset.vo.response.ReportTableHead;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.exception.RequestParamValidateException;
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
        Map<String, Object> timeValueMap = new HashMap<>();
        Map<String, String> computerTimeValueMap = new HashMap<>();
        Map<String, String> networkTimeValueMap = new HashMap<>();
        Map<String, String> storageTimeValueMap = new HashMap<>();
        Map<String, String> safetyTimeValueMap = new HashMap<>();
        Map<String, String> otherTimeValueMap = new HashMap<>();
        Map<String, String> amountTimeValueMap = new HashMap<>();
        Map<String, String> newAddTimeValueMap = new HashMap<>();
        computerTimeValueMap.put("classifyName", AssetSecondCategoryEnum.COMPUTE_DEVICE.getMsg());
        networkTimeValueMap.put("classifyName", AssetSecondCategoryEnum.NETWORK_DEVICE.getMsg());
        storageTimeValueMap.put("classifyName", AssetSecondCategoryEnum.STORAGE_DEVICE.getMsg());
        safetyTimeValueMap.put("classifyName", AssetSecondCategoryEnum.SAFETY_DEVICE.getMsg());
        otherTimeValueMap.put("classifyName", AssetSecondCategoryEnum.OTHER_DEVICE.getMsg());
        amountTimeValueMap.put("classifyName", AssetSecondCategoryEnum.AMOUNT.getMsg());
        newAddTimeValueMap.put("classifyName", AssetSecondCategoryEnum.NEW_ADD.getMsg());

        if (query.getReportFormType().equals(ReportFormType.ALL)) {
            List<AssetCategoryEntity> assetCategoryEntities = assetReportDao.findCategoryCountPrevious(query);
            int computeDevice = 0;
            int networkDevice = 0;
            int storageDevice = 0;
            int safetyDevice = 0;
            int otherDevice = 0;
            for (AssetCategoryEntity assetCategoryEntity : assetCategoryEntities) {
                AssetCategoryModel assetCategory = new AssetCategoryModel();
                assetCategory.setId(assetCategoryEntity.getCategoryModel());
                assetCategory.setParentId(assetCategoryEntity.getParentId());
                assetCategory.setName(assetCategoryEntity.getCategoryName());
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
        } else if (query.getReportFormType().equals(ReportFormType.NEW)) {
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

        } else if (query.getReportFormType().equals(ReportFormType.TABLE)) {
            // 表格数据
            while (iterator.hasNext()) {
                int computeDevice = 0;
                int networkDevice = 0;
                int storageDevice = 0;
                int safetyDevice = 0;
                int otherDevice = 0;

                Map.Entry<String, String> entry = iterator.next();
                String key = entry.getKey();
                String time = entry.getValue();

                List<AssetCategoryEntity> categoryEntityList = assetReportDao.findCategoryCountPrevious(query);
                for (AssetCategoryEntity categoryEntity : categoryEntityList) {
                    if (key.equals(categoryEntity.getDate())) {
                        assetCategoryModel.setId(categoryEntity.getCategoryModel());
                        assetCategoryModel.setParentId(categoryEntity.getParentId());
                        assetCategoryModel.setName(categoryEntity.getCategoryName());
                        String secondCategoryName = this.getParentCategory(assetCategoryModel, categoryModels)
                            .getName();
                        if (AssetSecondCategoryEnum.COMPUTE_DEVICE.getMsg().equals(secondCategoryName)
                            && key.equals(categoryEntity.getDate())) {
                            computerTimeValueMap.put("classifyName", AssetSecondCategoryEnum.COMPUTE_DEVICE.getMsg());
                            computeDevice = computeDevice + categoryEntity.getCategoryCount();
                        } else if (AssetSecondCategoryEnum.NETWORK_DEVICE.getMsg().equals(secondCategoryName)
                                   && key.equals(categoryEntity.getDate())) {
                            networkTimeValueMap.put("classifyName", AssetSecondCategoryEnum.COMPUTE_DEVICE.getMsg());
                            networkDevice = networkDevice + categoryEntity.getCategoryCount();
                        } else if (AssetSecondCategoryEnum.STORAGE_DEVICE.getMsg().equals(secondCategoryName)
                                   && key.equals(categoryEntity.getDate())) {
                            storageTimeValueMap.put("classifyName", AssetSecondCategoryEnum.STORAGE_DEVICE.getMsg());
                            storageDevice = storageDevice + categoryEntity.getCategoryCount();
                        } else if (AssetSecondCategoryEnum.SAFETY_DEVICE.getMsg().equals(secondCategoryName)
                                   && key.equals(categoryEntity.getDate())) {
                            safetyTimeValueMap.put("classifyName", AssetSecondCategoryEnum.SAFETY_DEVICE.getMsg());
                            safetyDevice = safetyDevice + categoryEntity.getCategoryCount();
                        } else if (AssetSecondCategoryEnum.OTHER_DEVICE.getMsg().equals(secondCategoryName)
                                   && key.equals(categoryEntity.getDate())) {
                            otherTimeValueMap.put("classifyName", AssetSecondCategoryEnum.OTHER_DEVICE.getMsg());
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

                computerTimeValueMap.put(key, String.valueOf(computeDevice));
                networkTimeValueMap.put(key, String.valueOf(networkDevice));
                storageTimeValueMap.put(key, String.valueOf(storageDevice));
                safetyTimeValueMap.put(key, String.valueOf(safetyDevice));
                otherTimeValueMap.put(key, String.valueOf(otherDevice));
                amountTimeValueMap.put(key,
                    String.valueOf(computeDevice + networkDevice + storageDevice + safetyDevice + otherDevice));
                // 计算新增数量
                int computeNewAdd = 0;
                int networkNewAdd = 0;
                int storageNewAdd = 0;
                int safetyNewAdd = 0;
                int otherNewAdd = 0;
                List<AssetCategoryEntity> amountCategoryEntityList = assetReportDao.findCategoryCountByTime(query);
                for (AssetCategoryEntity categoryEntity : amountCategoryEntityList) {
                    if (key.equals(categoryEntity.getDate())) {
                        assetCategoryModel.setId(categoryEntity.getCategoryModel());
                        assetCategoryModel.setParentId(categoryEntity.getParentId());
                        assetCategoryModel.setName(categoryEntity.getCategoryName());
                        String secondCategoryName = this.getParentCategory(assetCategoryModel, categoryModels)
                            .getName();
                        if (AssetSecondCategoryEnum.COMPUTE_DEVICE.getMsg().equals(secondCategoryName)
                            && key.equals(categoryEntity.getDate())) {
                            computeNewAdd = computeNewAdd + categoryEntity.getCategoryCount();
                        } else if (AssetSecondCategoryEnum.NETWORK_DEVICE.getMsg().equals(secondCategoryName)
                                   && key.equals(categoryEntity.getDate())) {
                            networkNewAdd = networkNewAdd + categoryEntity.getCategoryCount();
                        } else if (AssetSecondCategoryEnum.STORAGE_DEVICE.getMsg().equals(secondCategoryName)
                                   && key.equals(categoryEntity.getDate())) {
                            storageNewAdd = storageNewAdd + categoryEntity.getCategoryCount();
                        } else if (AssetSecondCategoryEnum.SAFETY_DEVICE.getMsg().equals(secondCategoryName)
                                   && key.equals(categoryEntity.getDate())) {
                            safetyNewAdd = safetyNewAdd + categoryEntity.getCategoryCount();
                        } else if (AssetSecondCategoryEnum.OTHER_DEVICE.getMsg().equals(secondCategoryName)
                                   && key.equals(categoryEntity.getDate())) {
                            otherNewAdd = otherNewAdd + categoryEntity.getCategoryCount();
                        }
                    }
                }

                newAddTimeValueMap.put(key,
                    String.valueOf(computeNewAdd + networkNewAdd + storageNewAdd + safetyNewAdd + otherNewAdd));
            }

            timeValueMap.put(AssetSecondCategoryEnum.COMPUTE_DEVICE.getMsg(), computerTimeValueMap);
            timeValueMap.put(AssetSecondCategoryEnum.NETWORK_DEVICE.getMsg(), networkTimeValueMap);
            timeValueMap.put(AssetSecondCategoryEnum.STORAGE_DEVICE.getMsg(), storageTimeValueMap);
            timeValueMap.put(AssetSecondCategoryEnum.SAFETY_DEVICE.getMsg(), safetyTimeValueMap);
            timeValueMap.put(AssetSecondCategoryEnum.OTHER_DEVICE.getMsg(), otherTimeValueMap);
            timeValueMap.put(AssetSecondCategoryEnum.AMOUNT.getMsg(), amountTimeValueMap);
            timeValueMap.put(AssetSecondCategoryEnum.NEW_ADD.getMsg(), newAddTimeValueMap);

            map.put("timeValueMap", timeValueMap);

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
            ParamterExceptionUtils.isNull(query.getReportFormType(), "报表类型不能为空");
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
                                                      Map<String, String> timeMap) {
        // 初始总量
        List<AssetGroupEntity> groupReportEntityList = assetReportDao.getAssetConutWithGroup(reportQueryRequest);
        if (Objects.isNull(reportQueryRequest.getTopFive())) {
            // 传入endTime 查询前5资产组
            List<AssetGroupEntity> groupReportEntityListTop = assetReportDao.getAssetConutWithGroup(reportQueryRequest);
            List<Integer> groupIds = new ArrayList<>();
            groupReportEntityListTop.stream().forEach(assetGroupEntity -> groupIds.add(assetGroupEntity.getGroupId()));
            reportQueryRequest.setGroupIds(groupIds);
        }

        // 新增的资产痛
        List<AssetGroupEntity> assetGroupEntities = assetReportDao.getNewAssetWithGroup(reportQueryRequest);

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
                Integer num2 = 0;
                for (int i = 0; i < assetGroupEntities.size(); i++) {
                    if (i == 0) {
                        for (int j = 0; j < groupReportEntityList.size(); j++) {
                            // 资产组别且对应周数匹配
                            if (groupReportEntityList.get(i).getName().equals(groupName)) {
                                num2 = groupReportEntityList.get(i).getGroupCount();
                            }
                        }
                    }

                    // 去掉数据库返回的数据中开头为0的部分
                    String groupReportEntityDate = assetGroupEntities.get(i).getDate().startsWith("0")
                        ? assetGroupEntities.get(i).getDate().substring(1)
                        : assetGroupEntities.get(i).getDate();
                    // 资产组别且对应周数匹配
                    if (assetGroupEntities.get(i).getName().equals(groupName) && groupReportEntityDate.equals(date)) {
                        num += assetGroupEntities.get(i).getGroupCount() + num2;
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
                throw new RequestParamValidateException("查询时间类型不正确");
        }
    }

    @Override
    public AssetReportTableResponse queryCategoryCountByTimeToTable(AssetReportCategoryCountQuery query) throws Exception {
        ShowCycleType showCycleType = query.getShowCycleType();
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
            return getAssetReportTableResponse(query,
                ReportDateUtils.getMonthWithDate(query.getBeginTime(), query.getEndTime()));
        } else {
            throw new BusinessException("非法参数");
        }
    }

    private AssetReportTableResponse getAssetReportTableResponse(AssetReportCategoryCountQuery query,
                                                                 Map<String, String> dateMap) {
        AssetReportTableResponse reportTableResponse = new AssetReportTableResponse();
        List<Map<String, String>> rows = new ArrayList<>();
        Map<String, String> computerMap = new HashMap<>();
        Map<String, String> networkMap = new HashMap<>();
        Map<String, String> storageMap = new HashMap<>();
        Map<String, String> safetyMap = new HashMap<>();
        Map<String, String> otherMap = new HashMap<>();
        Map<String, String> amountMap = new HashMap<>();
        Map<String, String> newAddMap = new HashMap<>();
        computerMap.put("classifyName", AssetSecondCategoryEnum.COMPUTE_DEVICE.getMsg());
        networkMap.put("classifyName", AssetSecondCategoryEnum.NETWORK_DEVICE.getMsg());
        storageMap.put("classifyName", AssetSecondCategoryEnum.STORAGE_DEVICE.getMsg());
        safetyMap.put("classifyName", AssetSecondCategoryEnum.SAFETY_DEVICE.getMsg());
        otherMap.put("classifyName", AssetSecondCategoryEnum.OTHER_DEVICE.getMsg());
        amountMap.put("classifyName", AssetSecondCategoryEnum.AMOUNT.getMsg());
        newAddMap.put("classifyName", AssetSecondCategoryEnum.NEW_ADD.getMsg());

        List<ReportTableHead> children = new ArrayList<>();
        ReportTableHead initTableHead = new ReportTableHead();
        initTableHead.setName("");
        initTableHead.setKey("classifyName");
        children.add(initTableHead);
        Map<String, Object> map;
        Iterator<Map.Entry<String, String>> iterator = dateMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            ReportTableHead reportTableHead = new ReportTableHead();
            reportTableHead.setKey(entry.getKey());
            reportTableHead.setName(entry.getValue());
            children.add(reportTableHead);

            computerMap.put(entry.getKey(), "");
            networkMap.put(entry.getKey(), "");
            storageMap.put(entry.getKey(), "");
            safetyMap.put(entry.getKey(), "");
            otherMap.put(entry.getKey(), "");
            amountMap.put(entry.getKey(), "");
            newAddMap.put(entry.getKey(), "");
        }
        map = buildCategoryCountByTime(query, dateMap);
        Map<String, Object> ssMap = (Map<String, Object>) map.get("timeValueMap");
        rows.add((Map<String, String>) ssMap.get(AssetSecondCategoryEnum.COMPUTE_DEVICE.getMsg()));
        rows.add((Map<String, String>) ssMap.get(AssetSecondCategoryEnum.NETWORK_DEVICE.getMsg()));
        rows.add((Map<String, String>) ssMap.get(AssetSecondCategoryEnum.STORAGE_DEVICE.getMsg()));
        rows.add((Map<String, String>) ssMap.get(AssetSecondCategoryEnum.SAFETY_DEVICE.getMsg()));
        rows.add((Map<String, String>) ssMap.get(AssetSecondCategoryEnum.OTHER_DEVICE.getMsg()));
        rows.add((Map<String, String>) ssMap.get(AssetSecondCategoryEnum.AMOUNT.getMsg()));
        rows.add((Map<String, String>) ssMap.get(AssetSecondCategoryEnum.NEW_ADD.getMsg()));

        reportTableResponse.setRows(rows);
        reportTableResponse.setChildren(children);
        return reportTableResponse;
    }

    /**
     * 根据资产组类别查本周新增资产情况
     * @param reportQueryRequest
     * @return
     */
    // public AssetReportResponse getNewAssetWithGroupInWeek(ReportQueryRequest reportQueryRequest) {
    // Map<String, String> weekMap = ReportDateUtils.getDayOfWeek();
    // AssetReportResponse assetReportResponse = new AssetReportResponse();
    // // 初始化横坐标
    // String[] weeks = new String[weekMap.size()];
    // for (Map.Entry<String, String> entry : weekMap.entrySet()) {
    // weeks[Integer.parseInt(entry.getKey()) - 1] = entry.getValue();
    // }
    // assetReportResponse.setDate(Arrays.asList(weeks));
    // List<AssetGroupEntity> assetGroupEntities = assetReportDao.myTest(reportQueryRequest);
    // // 获取资产组名字（不重复）
    // List<String> groupNameList = new ArrayList<>();
    // for (AssetGroupEntity assetGroupEntity : assetGroupEntities) {
    // if (!groupNameList.contains(assetGroupEntity.getName())) {
    // groupNameList.add(assetGroupEntity.getName());
    // }
    // }
    // List<ReportData> dataList = new ArrayList<>();
    // for (int i = 0; i < groupNameList.size(); i++) {
    // ReportData reportData = new ReportData();
    // String groupName = groupNameList.get(i);
    // reportData.setClassify(groupName);
    // Integer[] addList = new Integer[weeks.length];
    // Arrays.fill(addList, 0);
    // for (int day = 0; day < weeks.length; day++) {
    // for (AssetGroupEntity groupReportEntity : assetGroupEntities) {
    // if (groupReportEntity.getName().equals(groupName)) {
    // addList[DataTypeUtils.stringToInteger(groupReportEntity.getDate()) - 1] = groupReportEntity
    // .getGroupCount();
    // }
    // }
    // }
    // reportData.setAdd(Arrays.asList(addList));
    // dataList.add(reportData);
    // }
    // assetReportResponse.setList(dataList);
    // return assetReportResponse;
    // }

    /**
     * 根据资产组类别查新增资产情况-封装数据
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
            List<Integer> addNumList = new ArrayList<>(treeWeekMap.size());

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

    @Override
    public AssetReportTableResponse getAssetGroupReportTable(ReportQueryRequest reportQueryRequest) throws Exception {
        // 1-本周,2-本月,3-本季度,4-本年,5-时间范围
        switch (reportQueryRequest.getTimeType()) {
            case "1":
                reportQueryRequest.setSqlTime("%w");
                return buildAssetReportTable(reportQueryRequest, ReportDateUtils.getDayOfWeek(), "本周");
            case "2":
                reportQueryRequest.setSqlTime("%U");
                return buildAssetReportTable(reportQueryRequest, ReportDateUtils.getWeekOfMonth(), "本月");
            case "3":
                reportQueryRequest.setSqlTime("%Y-%m");
                return buildAssetReportTable(reportQueryRequest, ReportDateUtils.getSeason(), "本季度");
            case "4":
                reportQueryRequest.setSqlTime("%Y-%m");
                return buildAssetReportTable(reportQueryRequest, ReportDateUtils.getCurrentMonthOfYear(), "本年");
            case "5":
                reportQueryRequest.setSqlTime("%Y-%m");
                return buildAssetReportTable(reportQueryRequest,
                    ReportDateUtils.getMonthWithDate(reportQueryRequest.getStartTime(),
                        reportQueryRequest.getEndTime()),
                    reportQueryRequest.getStartTime() + "~" + reportQueryRequest.getEndTime());
            default:
                reportQueryRequest.setSqlTime("%Y-%m");
                return buildAssetReportTable(reportQueryRequest, ReportDateUtils.getCurrentMonthOfYear(), "本年");
        }
    }

    private AssetReportTableResponse buildAssetReportTable(ReportQueryRequest reportQueryRequest,
                                                           Map<String, String> timeMap, String title) {
        // 获取初始化数据
        ReportQueryRequest initReportQueryRequest = new ReportQueryRequest();
        initReportQueryRequest.setStartTime(reportQueryRequest.getStartTime());
        List<AssetGroupEntity> initAssetGroupEntities = assetReportDao.getInitGroupData(initReportQueryRequest);
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
        assetReportTableResponse.setRows(rows);
        // --------------------------------初始化数据完毕--------------------------------
        // --------------------------------开始增加新数据--------------------------------
        // 获取时间段新增数据
        List<AssetGroupEntity> assetGroupEntities = assetReportDao.getNewAssetWithGroup(reportQueryRequest);
        return assetReportTableResponse;
    }
}