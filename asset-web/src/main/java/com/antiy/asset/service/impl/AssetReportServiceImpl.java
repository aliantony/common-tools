package com.antiy.asset.service.impl;

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
import com.antiy.common.utils.ParamterExceptionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

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
        List<AssetReportResponse.ReportData> columnarList = new ArrayList<>();
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

        AssetReportResponse.ReportData computeDeviceColumnar = reportResponse.new ReportData();
        computeDeviceColumnar.setClassify(AssetSecondCategoryEnum.COMPUTE_DEVICE.getMsg());
        computeDeviceColumnar.setData(computerDataList);
        columnarList.add(computeDeviceColumnar);

        AssetReportResponse.ReportData networkDeviceColumnar = reportResponse.new ReportData();
        networkDeviceColumnar.setClassify(AssetSecondCategoryEnum.NETWORK_DEVICE.getMsg());
        networkDeviceColumnar.setData(networkDataList);
        columnarList.add(networkDeviceColumnar);

        AssetReportResponse.ReportData storageDeviceColumnar = reportResponse.new ReportData();
        storageDeviceColumnar.setClassify(AssetSecondCategoryEnum.STORAGE_DEVICE.getMsg());
        storageDeviceColumnar.setData(storageDataList);
        columnarList.add(storageDeviceColumnar);

        AssetReportResponse.ReportData safetyDeviceColumnar = reportResponse.new ReportData();
        safetyDeviceColumnar.setClassify(AssetSecondCategoryEnum.SAFETY_DEVICE.getMsg());
        safetyDeviceColumnar.setData(safetyDataList);
        columnarList.add(safetyDeviceColumnar);

        AssetReportResponse.ReportData otherDeviceColumnar = reportResponse.new ReportData();
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
    public AssetReportResponse getNewAssetWithCategoryInWeek(ReportQueryRequest reportQueryRequest) throws Exception {
        List<AssetCategoryModel> secondCategoryModelList = assetCategoryModelDao.getNextLevelCategoryByName("硬件");
        List<AssetCategoryModel> categoryModelAll = assetCategoryModelDao.getAll();
        // 初始化品类二级品类和子节点的映射
        Map<Integer, List<Integer>> categoryMap = new HashMap<>();
        for (AssetCategoryModel assetCategoryModel : secondCategoryModelList) {
            List<AssetCategoryModel> categoryModelList = iAssetCategoryModelService.recursionSearch(categoryModelAll,
                assetCategoryModel.getId());
            categoryMap.put(assetCategoryModel.getId(), getCategoryIdList(categoryModelList));
        }
        // 查询数据
        List<AssetCategoryEntity> assetCategoryEntityList = assetReportDao
            .getNewAssetWithCategoryByDay(reportQueryRequest);
        Map<String, Integer> result = new HashMap<>();
        // 组装数据，将子品类的数量组装进二级品类中
        for (AssetCategoryEntity assetCategoryEntity : assetCategoryEntityList) {
            for (Map.Entry<Integer, List<Integer>> entry : categoryMap.entrySet()) {
                if (entry.getKey().equals(assetCategoryEntity.getCategoryModel())) {
                    String categoryDate = new StringBuffer().append(assetCategoryEntity.getCategoryModel()).append(" ")
                        .append(assetCategoryEntity.getDate()).toString();
                    if (!result.containsKey(categoryDate)) {
                        result.put(categoryDate, assetCategoryEntity.getCategoryCount());
                    } else {
                        result.put(categoryDate, assetCategoryEntity.getCategoryCount() + result.get(categoryDate));
                    }
                } else if (entry.getValue().contains(assetCategoryEntity.getCategoryModel())) {
                    StringBuffer categoryDate = new StringBuffer().append(entry.getKey()).append(" ")
                        .append(assetCategoryEntity.getDate());
                    if (!result.containsKey(categoryDate.toString())) {
                        result.put(categoryDate.toString(), assetCategoryEntity.getCategoryCount());
                    } else {
                        result.put(categoryDate.toString(),
                            assetCategoryEntity.getCategoryCount() + result.get(categoryDate.toString()));
                    }
                }
            }
        }
        // 初始化返回类
        AssetReportResponse assetReportResponse = new AssetReportResponse();
        Map<String, String> weekMap = ReportDateUtils.getDayOfWeek();
        // 初始化横坐标
        String[] weeks = new String[weekMap.size()];
        for (Map.Entry<String, String> entry : weekMap.entrySet()) {
            weeks[Integer.parseInt(entry.getKey()) - 1] = entry.getValue();
        }
        assetReportResponse.setDate(Arrays.asList(weeks));
        // 将结果数据组装到Response中
        List<AssetReportResponse.ReportData> reportDataList = new ArrayList<>();
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
            AssetReportResponse.ReportData reportData = assetReportResponse.new ReportData();
            reportData.setClassify(assetCategoryModel.getName());
            reportData.setAdd(Arrays.asList(data));
            reportDataList.add(reportData);
        }

        assetReportResponse.setList(reportDataList);
        return assetReportResponse;
    }

    /**
     * 获取品类型号中的列表id
     * @param search
     * @return
     */
    public List<Integer> getCategoryIdList(List<AssetCategoryModel> search) {
        List<Integer> list = new ArrayList<>();
        for (AssetCategoryModel assetCategoryModel : search) {
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
                                                      Map<String, String> weekMap) {
        List<AssetGroupEntity> assetConutWithGroup = assetReportDao.getAssetConutWithGroup(reportQueryRequest);
        AssetReportResponse reportResponse = new AssetReportResponse();
        Iterator<Map.Entry<String, String>> iterator = weekMap.entrySet().iterator();
        List<String> dateList = new ArrayList<>();

        // 将结果数据组装到Response中
        List<AssetReportResponse.ReportData> reportDataList = new ArrayList<>();
        List<Integer> countDate = new ArrayList<>();
        while (iterator.hasNext()) {
            int count = 0;

            Map.Entry<String, String> entry = iterator.next();
            String key = entry.getKey();
            String time = entry.getValue();
            for (AssetGroupEntity entity : assetConutWithGroup) {
                if (key.equals(entity.getDate())) {
                    count =  entity.getGroupCount();
                }
            }

            countDate.add(count);
            dateList.add(time);

        }


        for (AssetGroupEntity groupEntity : assetConutWithGroup) {
            AssetReportResponse.ReportData reportData = reportResponse.new ReportData();
            reportData.setClassify(groupEntity.getName ());
            reportData.setData (countDate);
            reportDataList.add (reportData);
        }

        reportResponse.setList (reportDataList);
        reportResponse.setDate (dateList);

        return reportResponse;
    }


}