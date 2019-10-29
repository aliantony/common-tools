package com.antiy.asset.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.powermock.api.mockito.PowerMockito.when;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.compress.utils.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.antiy.asset.convert.AccessExportConvert;
import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.entity.Asset;
import com.antiy.asset.service.AssetAdmittanceService;
import com.antiy.asset.service.IAssetService;
import com.antiy.asset.util.Constants;
import com.antiy.asset.util.LogHandle;
import com.antiy.asset.vo.query.AssetQuery;
import com.antiy.asset.vo.request.AdmittanceRequest;
import com.antiy.asset.vo.response.AssetResponse;
import com.antiy.biz.util.RedisKeyUtil;
import com.antiy.common.download.ExcelDownloadUtil;
import com.antiy.common.utils.DateUtils;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;

/**
 * @Author: lvliang
 * @Date: 2019/10/22 13:26
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ LoginUserUtil.class, LogUtils.class, LogHandle.class, RedisKeyUtil.class, DateUtils.class })
public class AssetAdmittanceControllerTest {
    @Mock
    public IAssetService             assetService;
    @Mock
    public AssetAdmittanceService    assetAdmittanceService;
    @Mock
    public ExcelDownloadUtil         excelDownloadUtil;
    @Mock
    public AccessExportConvert       accessExportConvert;
    @Mock
    private AssetDao                 assetDao;
    @InjectMocks
    public AssetAdmittanceController assetAdmittanceController;

    @Test
    public void queryList() throws Exception {
        AssetQuery asset = new AssetQuery();
        assetAdmittanceController.queryList(asset);
    }

    @Test
    public void anagement() throws Exception {
        AdmittanceRequest admittance = new AdmittanceRequest();
        admittance.setStringId("1");
        admittance.setAdmittanceStatus(2);
        Asset asset = new Asset();
        asset.setId(1);
        asset.setName("1");
        asset.setNumber("1");
        asset.setAssetStatus(1);
        Mockito.when(assetService.getById(admittance.getStringId())).thenReturn(asset);
        assetAdmittanceController.anagement(admittance);

        admittance.setAdmittanceStatus(3);
        assetAdmittanceController.anagement(admittance);
    }

    @Test
    public void export() throws Exception {
        Integer status = 6;
        Integer start = 1;
        Integer end = 10;
        List<AssetResponse> assetList = Lists.newArrayList();
        AssetResponse assetResponse = new AssetResponse();
        assetList.add(assetResponse);
        AssetQuery assetQuery = new AssetQuery();
        assetQuery.setAdmittanceStatus(status);
        assetQuery.setPageSize(Constants.ALL_PAGE);
        if (start != null) {
            assetQuery.setStart(start - 1);
            assetQuery.setEnd(end - start + 1);
        }
        assetQuery.setAssetStatusList(Arrays.asList(3, 4, 5, 6, 7, 8, 9, 10, 11));
        when(assetService.findListAsset(any(), any())).thenReturn(assetList);
        assetAdmittanceController.export(status, start, end, null, null);
        when(assetService.findListAsset(any(), any())).thenReturn(Lists.newArrayList());
        assetAdmittanceController.export(status, start, end, null, null);
    }
}