package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetSafetyEquipmentDao;
import com.antiy.asset.entity.AssetSafetyEquipment;
import com.antiy.asset.util.LogHandle;
import com.antiy.asset.vo.query.AssetSafetyEquipmentQuery;
import com.antiy.asset.vo.request.AssetSafetyEquipmentRequest;
import com.antiy.asset.vo.response.AssetSafetyEquipmentResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.LoginUser;
import com.antiy.common.encoder.AesEncoder;
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
@PrepareForTest({LoginUserUtil.class, LogHandle.class, AssetSafetyEquipmentServiceImpl.class})
public class AssetSafetyEquipmentServiceImplTest {

    @Mock
    private BaseConverter<AssetSafetyEquipmentRequest, AssetSafetyEquipment> requestConverter;
    @Mock
    private BaseConverter<AssetSafetyEquipment, AssetSafetyEquipmentResponse> responseConverter;
    @Mock
    private AesEncoder aesEncoder;
    @Mock
    private AssetSafetyEquipmentDao assetSafetyEquipmentDao;
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
        PowerMockito.mockStatic(LogHandle.class);
        PowerMockito.doNothing().when(LogHandle.class, "log", Mockito.any(), Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt());
    }

    @Test
    public void saveAssetSafetyEquipmentTest() throws Exception {
        AssetSafetyEquipmentRequest request = new AssetSafetyEquipmentRequest();
        AssetSafetyEquipment assetSafetyEquipment = new AssetSafetyEquipment();
        assetSafetyEquipment.setId(1);
        String expect = "测试";
        Mockito.when(requestConverter.convert(request, AssetSafetyEquipment.class)).thenReturn(assetSafetyEquipment);
        Mockito.when(assetSafetyEquipmentDao.insert(assetSafetyEquipment)).thenReturn(1);
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
