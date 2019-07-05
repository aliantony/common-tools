package com.antiy.asset.service.impl;

import java.util.ArrayList;
import java.util.List;

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

import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.AssetSafetyEquipmentDao;
import com.antiy.asset.entity.Asset;
import com.antiy.asset.entity.AssetSafetyEquipment;
import com.antiy.asset.vo.query.AssetSafetyEquipmentQuery;
import com.antiy.asset.vo.request.AssetSafetyEquipmentRequest;
import com.antiy.asset.vo.response.AssetSafetyEquipmentResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.LoginUser;
import com.antiy.common.encoder.AesEncoder;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ LoginUserUtil.class, LogUtils.class })
public class AssetSafetyEquipmentServiceImplTest {

    @Mock
    private BaseConverter<AssetSafetyEquipmentRequest, AssetSafetyEquipment> requestConverter;
    @Mock
    private BaseConverter<AssetSafetyEquipment, AssetSafetyEquipmentResponse> responseConverter;
    @Mock
    private AesEncoder aesEncoder;
    @Mock
    private AssetSafetyEquipmentDao assetSafetyEquipmentDao;
    @Mock
    private AssetDao                                                          assetDao;
    @InjectMocks
    private AssetSafetyEquipmentServiceImpl assetSafetyEquipmentService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(LoginUserUtil.class);
        LoginUser loginUser = new LoginUser();
        loginUser.setId(1);
        loginUser.setUsername("小李");
        PowerMockito.when(LoginUserUtil.getLoginUser()).thenReturn(loginUser);
        PowerMockito.mockStatic(LogUtils.class);
        PowerMockito.doNothing().when(LogUtils.class, "recordOperLog", Mockito.any());
        PowerMockito.doNothing().when(LogUtils.class, "recordOperLog", Mockito.any());
    }

    @Test
    public void saveAssetSafetyEquipmentTest() throws Exception {
        AssetSafetyEquipmentRequest request = new AssetSafetyEquipmentRequest();
        request.setAssetId("666");
        AssetSafetyEquipment assetSafetyEquipment = new AssetSafetyEquipment();
        assetSafetyEquipment.setId(1);
        String expect = "测试";
        Mockito.when(requestConverter.convert(request, AssetSafetyEquipment.class)).thenReturn(assetSafetyEquipment);
        Mockito.when(assetSafetyEquipmentDao.insert(assetSafetyEquipment)).thenReturn(1);
        Asset asset = new Asset();
        asset.setNumber("66");
        Mockito.when(assetDao.getById(request.getAssetId())).thenReturn(asset);
        Mockito.when(aesEncoder.decode(Mockito.anyString(), Mockito.anyString())).thenReturn(expect);
        Assert.assertEquals(expect, assetSafetyEquipmentService.saveAssetSafetyEquipment(request));

    }

    @Test
    public void updateAssetSafetyEquipmentTest() throws Exception {
        AssetSafetyEquipmentRequest request = new AssetSafetyEquipmentRequest();
        AssetSafetyEquipment assetSafetyEquipment = new AssetSafetyEquipment();
        Integer expect = 1;
        Mockito.when(requestConverter.convert(request, AssetSafetyEquipment.class)).thenReturn(assetSafetyEquipment);
        Mockito.when(assetSafetyEquipmentDao.update(assetSafetyEquipment)).thenReturn(expect);
        Assert.assertEquals(expect, assetSafetyEquipmentService.updateAssetSafetyEquipment(request));

    }

    @Test
    public void findListAssetSafetyEquipmentTest() throws Exception {
        AssetSafetyEquipmentQuery query = new AssetSafetyEquipmentQuery();
        List<AssetSafetyEquipment> list = new ArrayList<>();
        List<AssetSafetyEquipmentResponse> expect = new ArrayList<>();
        Mockito.when(responseConverter.convert(list, AssetSafetyEquipmentResponse.class)).thenReturn(expect);
        Assert.assertEquals(expect, assetSafetyEquipmentService.findListAssetSafetyEquipment(query));

    }

    @Test
    public void findPageAssetSafetyEquipmentTest() throws Exception {
        assetSafetyEquipmentService = PowerMockito.spy(assetSafetyEquipmentService);
        AssetSafetyEquipmentQuery query = new AssetSafetyEquipmentQuery();
        Integer expect = 1;
        List<AssetSafetyEquipmentResponse> expectList = new ArrayList<>();
        PowerMockito.doReturn(expect).when(assetSafetyEquipmentService).findCount(Mockito.any());
        PowerMockito.doReturn(expectList).when(assetSafetyEquipmentService).findListAssetSafetyEquipment(Mockito.any());
        Assert.assertEquals(expect.intValue(), assetSafetyEquipmentService.findPageAssetSafetyEquipment(query).getTotalRecords());
        Assert.assertEquals(expectList, assetSafetyEquipmentService.findPageAssetSafetyEquipment(query).getItems());

    }

    @Test
    public void findSafetyEquipmentByIdTest() throws Exception {
        AssetSafetyEquipment assetSafetyEquipment = new AssetSafetyEquipment();
        AssetSafetyEquipmentResponse expect = new AssetSafetyEquipmentResponse();
        Mockito.when(assetSafetyEquipmentDao.getById(Mockito.anyString())).thenReturn(assetSafetyEquipment);
        Mockito.when(responseConverter.convert(assetSafetyEquipment, AssetSafetyEquipmentResponse.class)).thenReturn(expect);
        Assert.assertEquals(expect, assetSafetyEquipmentService.findSafetyEquipmentById("1"));

    }
}
