package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetCategoryModelDao;
import com.antiy.asset.dao.AssetReportDao;
import com.antiy.asset.entity.AssetCategoryEntity;
import com.antiy.asset.entity.AssetCategoryModel;
import com.antiy.asset.service.IAssetCategoryModelService;
import com.antiy.asset.util.ReportDateUtils;
import com.antiy.asset.vo.request.ReportQueryRequest;
import com.antiy.asset.vo.response.AssetReportResponse;
import com.antiy.common.base.ActionResponse;
import org.springframework.stereotype.Service;

import com.antiy.asset.service.IAssetReportService;
import com.antiy.asset.vo.response.AssetCategoryModelResponse;

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
public class IAssetReportServiceImpl implements IAssetReportService {
    @Resource
    IAssetCategoryModelService iAssetCategoryModelService;
    @Resource
    AssetCategoryModelDao      assetCategoryModelDao;
    @Resource
    AssetReportDao             assetReportDao;

    @Override
    public AssetCategoryModelResponse queryCategoryCountByTime() {

        return null;
    }

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

}
