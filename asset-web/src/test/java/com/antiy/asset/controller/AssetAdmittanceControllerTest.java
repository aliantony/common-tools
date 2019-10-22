package com.antiy.asset.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import com.antiy.asset.convert.AccessExportConvert;
import com.antiy.asset.entity.Asset;
import com.antiy.asset.service.AssetAdmittanceService;
import com.antiy.asset.service.IAssetService;
import com.antiy.asset.vo.query.AssetQuery;
import com.antiy.asset.vo.request.AdmittanceRequest;
import com.antiy.common.download.ExcelDownloadUtil;

/**
 * @Author: lvliang
 * @Date: 2019/10/22 13:26
 */
@RunWith(PowerMockRunner.class)
public class AssetAdmittanceControllerTest {
    @Mock
    public IAssetService             assetService;
    @Mock
    public AssetAdmittanceService    assetAdmittanceService;
    @Mock
    public ExcelDownloadUtil         excelDownloadUtil;
    @Mock
    public AccessExportConvert       accessExportConvert;

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
    public void export() {
    }
}