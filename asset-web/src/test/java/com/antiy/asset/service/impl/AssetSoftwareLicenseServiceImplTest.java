package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetSoftwareLicenseDao;
import com.antiy.asset.entity.AssetSoftwareLicense;
import com.antiy.asset.util.LogHandle;
import com.antiy.asset.vo.query.AssetSoftwareLicenseQuery;
import com.antiy.asset.vo.request.AssetSoftwareLicenseRequest;
import com.antiy.asset.vo.response.AssetSoftwareLicenseResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.LoginUser;
import com.antiy.common.utils.LoginUserUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LoginUserUtil.class, LogHandle.class, AssetSoftwareLicenseServiceImpl.class})
public class AssetSoftwareLicenseServiceImplTest {
    @Mock
    private AssetSoftwareLicenseDao assetSoftwareLicenseDao;
    @Mock
    private BaseConverter<AssetSoftwareLicense, AssetSoftwareLicenseResponse> responseConverter;
    @Mock
    private BaseConverter<AssetSoftwareLicenseRequest, AssetSoftwareLicense> requestConverter;
    @InjectMocks
    private AssetSoftwareLicenseServiceImpl assetSoftwareLicenseService;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(LoginUserUtil.class);
        LoginUser loginUser = new LoginUser();
        loginUser.setId(1);
        loginUser.setUsername("小李");
        PowerMockito.when(LoginUserUtil.getLoginUser()).thenReturn(loginUser);
        PowerMockito.mockStatic(LogHandle.class);
        PowerMockito.doNothing().when(LogHandle.class, "log", Mockito.any(), Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt());
    }

    @Test
    public void saveAssetSoftwareLicenseTest() throws Exception {
        AssetSoftwareLicenseRequest request = new AssetSoftwareLicenseRequest();
        AssetSoftwareLicense assetSoftwareLicense = new AssetSoftwareLicense();
        Integer expect = 1;
        assetSoftwareLicense.setId(expect);
        Mockito.when(requestConverter.convert(request, AssetSoftwareLicense.class)).thenReturn(assetSoftwareLicense);
        Mockito.when(assetSoftwareLicenseDao.insert(assetSoftwareLicense)).thenReturn(expect);
        Assert.assertEquals(expect, assetSoftwareLicenseService.saveAssetSoftwareLicense(request));
    }

    @Test
    public void updateAssetSoftwareLicenseTest() throws Exception {
        AssetSoftwareLicenseRequest request = new AssetSoftwareLicenseRequest();
        AssetSoftwareLicense assetSoftwareLicense = new AssetSoftwareLicense();
        Integer expect = 1;
        Mockito.when(requestConverter.convert(request, AssetSoftwareLicense.class)).thenReturn(assetSoftwareLicense);
        Mockito.when(assetSoftwareLicenseDao.update(Mockito.any())).thenReturn(expect);
        Assert.assertEquals(expect, assetSoftwareLicenseService.updateAssetSoftwareLicense(request));
    }

    @Test
    public void findListAssetSoftwareLicenseTest() throws Exception {
        AssetSoftwareLicenseQuery query = new AssetSoftwareLicenseQuery();
        List<AssetSoftwareLicense> assetSoftwareLicenseList = new ArrayList<>();
        List<AssetSoftwareLicenseResponse> expect = new ArrayList<>();

        Mockito.when(assetSoftwareLicenseDao.findQuery(Mockito.any())).thenReturn(assetSoftwareLicenseList);
        Mockito.when(responseConverter.convert(assetSoftwareLicenseList, AssetSoftwareLicenseResponse.class)).thenReturn(expect);
        Assert.assertEquals(expect, assetSoftwareLicenseService.findListAssetSoftwareLicense(query));
    }

    @Test
    public void findPageAssetSoftwareLicenseTest() throws Exception {
        AssetSoftwareLicenseQuery query = new AssetSoftwareLicenseQuery();
        assetSoftwareLicenseService = PowerMockito.spy(assetSoftwareLicenseService);
        Integer expect = 1;
        List<AssetSoftwareLicenseResponse> expectList = new ArrayList<>();
        PowerMockito.doReturn(expect).when(assetSoftwareLicenseService).findCount(Mockito.any());
        PowerMockito.doReturn(expectList).when(assetSoftwareLicenseService).findListAssetSoftwareLicense(Mockito.any());
        Assert.assertEquals(expect.intValue(), assetSoftwareLicenseService.findPageAssetSoftwareLicense(query).getTotalRecords());
        Assert.assertEquals(expectList, assetSoftwareLicenseService.findPageAssetSoftwareLicense(query).getItems());
    }
}
