package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetNetworkEquipmentDao;
import com.antiy.asset.entity.AssetNetworkEquipment;
import com.antiy.asset.util.LogHandle;
import com.antiy.asset.vo.query.AssetNetworkEquipmentQuery;
import com.antiy.asset.vo.request.AssetNetworkEquipmentRequest;
import com.antiy.asset.vo.response.AssetNetworkEquipmentResponse;
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
@PrepareForTest({LoginUserUtil.class, LogHandle.class, AssetNetworkEquipmentServiceImpl.class})
public class AssetNetworkEquipmentServiceImplTest {

    @Mock
    private BaseConverter<AssetNetworkEquipmentRequest, AssetNetworkEquipment> requestConverter;
    @Mock
    private AssetNetworkEquipmentDao assetNetworkEquipmentDao;
    @Mock
    private BaseConverter<AssetNetworkEquipment, AssetNetworkEquipmentResponse> responseConverter;
    @InjectMocks
    private AssetNetworkEquipmentServiceImpl assetNetworkEquipmentService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(LoginUserUtil.class);
        LoginUser loginUser = new LoginUser();
        loginUser.setId(1);
        PowerMockito.when(LoginUserUtil.getLoginUser()).thenReturn(loginUser);
        PowerMockito.mockStatic(LogHandle.class);
        PowerMockito.doNothing().when(LogHandle.class, "log", Mockito.any(), Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt());
    }

    @Test
    public void saveAssetNetworkEquipmentTest() throws Exception {
        AssetNetworkEquipmentRequest request = new AssetNetworkEquipmentRequest();
        AssetNetworkEquipment assetNetworkEquipment = new AssetNetworkEquipment();
        Integer expect = 1;
        assetNetworkEquipment.setId(expect);
        Mockito.when(requestConverter.convert(request, AssetNetworkEquipment.class)).thenReturn(assetNetworkEquipment);
        Mockito.when(assetNetworkEquipmentDao.insert(assetNetworkEquipment)).thenReturn(1);
        Assert.assertEquals(expect, assetNetworkEquipmentService.saveAssetNetworkEquipment(request));
    }

    @Test
    public void updateAssetNetworkEquipmentTest() throws Exception {
        AssetNetworkEquipmentRequest request = new AssetNetworkEquipmentRequest();
        AssetNetworkEquipment assetNetworkEquipment = new AssetNetworkEquipment();
        Integer expect = 1;
        assetNetworkEquipment.setId(expect);
        Mockito.when(requestConverter.convert(request, AssetNetworkEquipment.class)).thenReturn(assetNetworkEquipment);
        Mockito.when(assetNetworkEquipmentDao.update(assetNetworkEquipment)).thenReturn(expect);
        Assert.assertEquals(expect, assetNetworkEquipmentService.updateAssetNetworkEquipment(request));
    }

    @Test
    public void findListAssetNetworkEquipmentTest() throws Exception {
        AssetNetworkEquipmentQuery query = new AssetNetworkEquipmentQuery();
        List<AssetNetworkEquipment> list = new ArrayList<>();
        List<AssetNetworkEquipmentResponse> expect = new ArrayList<>();
        Mockito.when(assetNetworkEquipmentDao.findListAssetNetworkEquipment(query)).thenReturn(list);
        Mockito.when(responseConverter.convert(list, AssetNetworkEquipmentResponse.class)).thenReturn(expect);
        Assert.assertEquals(expect, assetNetworkEquipmentService.findListAssetNetworkEquipment(query));
    }

    @Test
    public void findCountAssetNetworkEquipmentTest() throws Exception {
        AssetNetworkEquipmentQuery query = new AssetNetworkEquipmentQuery();
        Integer expect = 1;
        Mockito.when(assetNetworkEquipmentDao.findCount(query)).thenReturn(expect);
        Assert.assertEquals(expect, assetNetworkEquipmentService.findCountAssetNetworkEquipment(query));
    }

    @Test
    public void findPageAssetNetworkEquipmentTest() throws Exception {
        assetNetworkEquipmentService = PowerMockito.spy(assetNetworkEquipmentService);
        Integer expect = 1;
        AssetNetworkEquipmentQuery query = new AssetNetworkEquipmentQuery();
        List<AssetNetworkEquipmentResponse> expectList = new ArrayList<>();
        PowerMockito.doReturn(expect).when(assetNetworkEquipmentService).findCountAssetNetworkEquipment(query);
        PowerMockito.doReturn(expectList).when(assetNetworkEquipmentService).findListAssetNetworkEquipment(query);
        Assert.assertEquals(expect.intValue(), assetNetworkEquipmentService.findPageAssetNetworkEquipment(query).getTotalRecords());
        Assert.assertEquals(expectList, assetNetworkEquipmentService.findPageAssetNetworkEquipment(query).getItems());
    }

}
