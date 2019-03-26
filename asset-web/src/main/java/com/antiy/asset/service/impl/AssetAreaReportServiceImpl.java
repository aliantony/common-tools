package com.antiy.asset.service.impl;

import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetReportDao;
import com.antiy.asset.service.IAssetAreaReportService;
import com.antiy.asset.vo.request.ReportQueryRequest;
import com.antiy.asset.vo.response.AssetReportResponse;

import javax.annotation.Resource;

/**
 * @author: zhangbing
 * @date: 2019/3/26 14:09
 * @description:
 */
@Service
public class AssetAreaReportServiceImpl implements IAssetAreaReportService {

    @Resource
    private AssetReportDao assetReportDao;

    @Override
    public AssetReportResponse getAssetWithArea(ReportQueryRequest reportRequest) {
        assetReportDao.getAllAssetWithArea(reportRequest);
        return null;
    }
}
