package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetCompositionReportDao;
import com.antiy.asset.entity.AssetCompositionReport;
import com.antiy.asset.service.IAssetCompositionReportService;
import com.antiy.asset.vo.query.AssetCompositionReportQuery;
import com.antiy.asset.vo.request.AssetCompositionReportRequest;
import com.antiy.asset.vo.response.AssetCompositionReportResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import com.antiy.common.utils.BusinessExceptionUtils;
import com.antiy.common.utils.LogUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
}
