package com.antiy.asset.service.impl;

import com.antiy.asset.convert.ComReportConvert;
import com.antiy.asset.dao.AssetCompositionReportDao;
import com.antiy.asset.entity.AssetCompositionReport;
import com.antiy.asset.service.IAssetCompositionReportService;
import com.antiy.asset.templet.AssetComReportEntity;
import com.antiy.asset.util.ArrayTypeUtil;
import com.antiy.asset.util.CSVUtils;
import com.antiy.asset.util.Constants;
import com.antiy.asset.vo.query.AssetCompositionReportQuery;
import com.antiy.asset.vo.query.AssetCompositionReportTemplateQuery;
import com.antiy.asset.vo.request.AssetCompositionReportRequest;
import com.antiy.asset.vo.response.AssetCompositionReportResponse;
import com.antiy.biz.util.RedisKeyUtil;
import com.antiy.biz.util.RedisUtil;
import com.antiy.common.base.*;
import com.antiy.common.download.DownloadVO;
import com.antiy.common.download.ExcelDownloadUtil;
import com.antiy.common.enums.BusinessModuleEnum;
import com.antiy.common.enums.BusinessPhaseEnum;
import com.antiy.common.enums.ModuleEnum;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.utils.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang.ArrayUtils;
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
    @Resource
    private ComReportConvert                                                      comreportconvert;
    @Resource
    private RedisUtil                                                             redisUtil;

    @Override
    public Integer saveAssetCompositionReport(AssetCompositionReportRequest request) throws Exception {
        // 名称去重
        Integer count = assetCompositionReportDao.findCountByName(request.getName(), null);
        BusinessExceptionUtils.isTrue(count < 1, "当前模板名称已经存在!");
        AssetCompositionReport assetCompositionReport = requestConverter.convert(request, AssetCompositionReport.class);
        assetCompositionReport.setQueryCondition(JsonUtil.object2Json(request.getQuery()));
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        assetCompositionReport.setUserId(loginUser.getId());
        if (request.getIdentification() == 1) {
            AssetCompositionReportTemplateQuery assetCompositionReportTemplateQuery = new AssetCompositionReportTemplateQuery();
            List<AssetCompositionReport> report = this.findReport(assetCompositionReportTemplateQuery);
            if (CollectionUtils.isNotEmpty(report)) {
                report.forEach(assetCompositionReport1 -> assetCompositionReport1.setIdentification(0));
                assetCompositionReportDao.updateBatch(report);
            }
        }
        assetCompositionReport.setGmtCreate(System.currentTimeMillis());
        assetCompositionReportDao.insert(assetCompositionReport);
        return assetCompositionReport.getId();
    }

    @Override
    public Integer updateAssetCompositionReport(AssetCompositionReportRequest request) throws Exception {
        // 名称去重
        Integer count = assetCompositionReportDao.findCountByName(request.getName(), request.getId());
        BusinessExceptionUtils.isTrue(count < 1, "当前模板名称已经存在!");
        AssetCompositionReport assetCompositionReport = requestConverter.convert(request, AssetCompositionReport.class);
        assetCompositionReport.setQueryCondition(JsonUtil.object2Json(request.getQuery()));
        assetCompositionReport.setGmtCreate(System.currentTimeMillis());
        if (request.getIdentification() == 1) {
            AssetCompositionReportTemplateQuery assetCompositionReportTemplateQuery = new AssetCompositionReportTemplateQuery();
            List<AssetCompositionReport> report = this.findReport(assetCompositionReportTemplateQuery);
            report.forEach(assetCompositionReport1 -> assetCompositionReport1.setIdentification(0));
            assetCompositionReportDao.updateBatch(report);
        }

        return assetCompositionReportDao.update(assetCompositionReport);
    }

    @Override
    public List<AssetCompositionReportResponse> findListAssetCompositionReport(AssetCompositionReportQuery query) throws Exception {
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        if (loginUser == null) {
            return Lists.newArrayList();
        }
        if (ArrayUtils.isEmpty(query.getAreaIds())) {
            query.setAreaIds(DataTypeUtils.integerArrayToStringArray(loginUser.getAreaIdsOfCurrentUser()));
        }
        List<AssetCompositionReportResponse> assetCompositionReportList = assetCompositionReportDao.findAll(query);
        if (CollectionUtils.isNotEmpty(assetCompositionReportList)) {
            assetCompositionReportList.stream().forEach(a -> {
                try {
                    String key = RedisKeyUtil.getKeyWhenGetObject(ModuleEnum.SYSTEM.getType(), SysArea.class,
                        a.getAreaId());
                    SysArea sysArea = redisUtil.getObject(key, SysArea.class);
                    a.setAreaName(sysArea.getFullName());
                } catch (Exception e) {
                    logger.warn("获取资产区域名称失败", e);
                }
            });
        }
        return assetCompositionReportList;
    }

    @Override
    public PageResult<AssetCompositionReportResponse> findPageAssetCompositionReport(AssetCompositionReportQuery query) throws Exception {
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        if (loginUser == null) {
            return new PageResult<>(query.getPageSize(), 0, query.getCurrentPage(), Lists.newArrayList());
        }

        return new PageResult<>(query.getPageSize(), this.findCountAsset(query), query.getCurrentPage(),
            this.findListAssetCompositionReport(query));
    }

    public Integer findCountAsset(AssetCompositionReportQuery query) throws Exception {
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        if (loginUser == null) {
            return 0;
        }
        if (ArrayUtils.isEmpty(query.getAreaIds())) {
            query.setAreaIds(DataTypeUtils.integerArrayToStringArray(loginUser.getAreaIdsOfCurrentUser()));
        }
        return assetCompositionReportDao.findCount(query);
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
        List<AssetCompositionReportResponse> items = findPageAssetCompositionReport(assetQuery).getItems();
        List<AssetComReportEntity> assetEntities = comreportconvert.convert(items, AssetComReportEntity.class);
        downloadVO.setDownloadList(assetEntities);

        downloadVO.setSheetName("资产综合报表");
        // 3种导方式 1 excel 2 cvs 3 xml
        if (CollectionUtils.isNotEmpty(downloadVO.getDownloadList())) {

            if (assetQuery.getExportType() == 1) {
                excelDownloadUtil.excelDownload(request, response,
                    "综合资产报表" + DateUtils.getDataString(new Date(), DateUtils.NO_TIME_FORMAT), downloadVO);
            } else if (assetQuery.getExportType() == 2) {
                List<?> downloadList = downloadVO.getDownloadList();

                String[] files = new String[] { "编号", "区域", "首次入网时间", "重要程度", "ip", "使用者", "厂商", "基准模板", "已修复漏洞",
                                                "已修复漏洞", "已安装补丁", "未安装补丁" };

                CSVUtils.writeCSV(downloadList,
                    "综合资产报表" + DateUtils.getDataString(new Date(), DateUtils.NO_TIME_FORMAT), files, request, response);
            }

            LogUtils.recordOperLog(
                new BusinessData("导出《综合资产报表" + DateUtils.getDataString(new Date(), DateUtils.NO_TIME_FORMAT) + "》", 0,
                    "", assetQuery, BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.NONE));
        } else {
            throw new BusinessException("导出数据为空");
        }
    }

    @Override
    public List<AssetCompositionReport> findReport(AssetCompositionReportTemplateQuery assetCompositionReportQuery) throws Exception {
        assetCompositionReportQuery.setUserId(LoginUserUtil.getLoginUser().getId());
        assetCompositionReportQuery.setPageSize(-1);
        return assetCompositionReportDao.findQuery(assetCompositionReportQuery);
    }
}
