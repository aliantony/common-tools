package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetCompositionReportDao;
import com.antiy.asset.entity.AssetCompositionReport;
import com.antiy.asset.service.IAssetCompositionReportService;
import com.antiy.asset.util.ArrayTypeUtil;
import com.antiy.asset.util.Constants;
import com.antiy.asset.vo.query.AssetCompositionReportQuery;
import com.antiy.asset.vo.request.AssetCompositionReportRequest;
import com.antiy.asset.vo.response.AssetCompositionReportResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.BusinessData;
import com.antiy.common.base.PageResult;
import com.antiy.common.download.DownloadVO;
import com.antiy.common.download.ExcelDownloadUtil;
import com.antiy.common.enums.BusinessModuleEnum;
import com.antiy.common.enums.BusinessPhaseEnum;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.utils.BusinessExceptionUtils;
import com.antiy.common.utils.DateUtils;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * <p> 服务实现类 </p>
 *
 * @author why
 * @since 2020-02-24
 */
@Service
public class AssetCompositionReportServiceImpl extends BaseServiceImpl<AssetCompositionReport>
                                               implements IAssetCompositionReportService {

    private static final Logger                                                   logger = LogUtils.get();

    @Resource
    private AssetCompositionReportDao                                             assetCompositionReportDao;
    @Resource
    private BaseConverter<AssetCompositionReportRequest, AssetCompositionReport>  requestConverter;
    @Resource
    private BaseConverter<AssetCompositionReport, AssetCompositionReportResponse> responseConverter;
    @Resource
    private ExcelDownloadUtil                                                     excelDownloadUtil;

    @Override
    public Integer saveAssetCompositionReport(AssetCompositionReportRequest request) throws Exception {
        // 名称去重
        String name = request.getName();
        AssetCompositionReportQuery assetCompositionReportQuery = new AssetCompositionReportQuery();
        assetCompositionReportQuery.setName(name);
        assetCompositionReportQuery.setStatus(1);
        Integer count = assetCompositionReportDao.findCount(assetCompositionReportQuery);
        BusinessExceptionUtils.isTrue(count < 1, "当前模板名称已经存在!");
        AssetCompositionReport assetCompositionReport = requestConverter.convert(request, AssetCompositionReport.class);
        assetCompositionReportDao.insert(assetCompositionReport);
        return assetCompositionReport.getId();
    }

    @Override
    public Integer updateAssetCompositionReport(AssetCompositionReportRequest request) throws Exception {
        AssetCompositionReport assetCompositionReport = requestConverter.convert(request, AssetCompositionReport.class);
        return assetCompositionReportDao.update(assetCompositionReport);
    }

    @Override
    public List<AssetCompositionReportResponse> findListAssetCompositionReport(AssetCompositionReportQuery query) throws Exception {
        List<AssetCompositionReport> assetCompositionReportList = assetCompositionReportDao.findQuery(query);
        // TODO
        List<AssetCompositionReportResponse> assetCompositionReportResponse = responseConverter
            .convert(assetCompositionReportList, AssetCompositionReportResponse.class);
        return assetCompositionReportResponse;
    }

    @Override
    public PageResult<AssetCompositionReportResponse> findPageAssetCompositionReport(AssetCompositionReportQuery query) throws Exception {
        return new PageResult<>(query.getPageSize(), this.findCount(query), query.getCurrentPage(),
            this.findListAssetCompositionReport(query));
    }

    @Override
    public void exportData(AssetCompositionReportQuery assetQuery, HttpServletResponse response,
                           HttpServletRequest request) throws Exception {
        if ((assetQuery.getStart() != null && assetQuery.getEnd() != null)) {
            assetQuery.setStart(assetQuery.getStart() - 1);
            assetQuery.setEnd(assetQuery.getEnd() - assetQuery.getStart());
        }
        assetQuery.setPageSize(Constants.ALL_PAGE);
        assetQuery.setAreaIds(
            ArrayTypeUtil.objectArrayToStringArray(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser().toArray()));
        DownloadVO downloadVO = new DownloadVO();
        // List<AssetResponse> list = this.findPageAsset(assetQuery).getItems();
        List<AssetCompositionReportResponse> items = findPageAssetCompositionReport(assetQuery).getItems();
        // List<AssetEntity> assetEntities = assetEntityConvert.convert(list, AssetEntity.class);
        // downloadVO.setDownloadList(assetEntities);

        downloadVO.setSheetName("资产综合报表");
        // 3种导方式 1 excel 2 cvs 3 xml
        if (CollectionUtils.isNotEmpty(downloadVO.getDownloadList())) {

            if (assetQuery.getExportType() == 1) {
                excelDownloadUtil.excelDownload(request, response,
                    "资产" + DateUtils.getDataString(new Date(), DateUtils.NO_TIME_FORMAT), downloadVO);
            } else if (assetQuery.getExportType() == 2) {

            } else {

            }

            LogUtils.recordOperLog(
                new BusinessData("导出《资产" + DateUtils.getDataString(new Date(), DateUtils.NO_TIME_FORMAT) + "》", 0, "",
                    assetQuery, BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.NONE));
        } else {
            throw new BusinessException("导出数据为空");
        }
    }
}
