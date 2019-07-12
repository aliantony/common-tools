package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetDao;
import com.antiy.common.base.LicenseContent;
import com.antiy.common.utils.LicenseUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LicenseUtil.class)
public class IAssetLicenseServiceImplTest {
    @InjectMocks
    IAssetLicenseServiceImpl iAssetLicenseService;

    @Mock
    private AssetDao         assetDao;

    @Test
    public void validateAuthNum() throws Exception {
        PowerMockito.mockStatic(LicenseUtil.class);
        Mockito.when(assetDao.countAsset()).thenReturn(10);
        LicenseContent licenseContent = new LicenseContent();
        licenseContent.setAssetNum(null);
        Mockito.when(LicenseUtil.getLicense()).thenReturn(licenseContent);
        Assert.assertEquals("license异常，请联系客服人员！", iAssetLicenseService.validateAuthNum().getMsg());


        licenseContent.setAssetNum(11);
        Mockito.when(LicenseUtil.getLicense()).thenReturn(licenseContent);
        Assert.assertEquals(true, iAssetLicenseService.validateAuthNum().getResult());

        licenseContent.setAssetNum(5);
        Mockito.when(LicenseUtil.getLicense()).thenReturn(licenseContent);
        Assert.assertEquals("资产数量已超过授权数量，请联系客服人员！", iAssetLicenseService.validateAuthNum().getMsg());

    }

}
