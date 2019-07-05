package com.antiy.asset.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.antiy.asset.dao.AssetMemoryDao;
import com.antiy.asset.entity.AssetMemory;
import com.antiy.asset.util.BeanConvert;
import com.antiy.asset.util.LogHandle;
import com.antiy.asset.vo.query.AssetMemoryQuery;
import com.antiy.asset.vo.request.AssetMemoryRequest;
import com.antiy.asset.vo.response.AssetMemoryResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BusinessData;
import com.antiy.common.base.LoginUser;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ LoginUserUtil.class, LogHandle.class, BeanConvert.class, AssetMemoryServiceImpl.class,
                  LogUtils.class })
public class AssetMemoryServiceImplTest {
    @InjectMocks
    private AssetMemoryServiceImpl                         assetMemoryService;
    @Mock
    private BaseConverter<AssetMemoryRequest, AssetMemory> requestConverter;
    @Mock
    private AssetMemoryDao assetMemoryDao;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(LoginUserUtil.class);
        LoginUser loginUser = new LoginUser();
        loginUser.setId(1);
        PowerMockito.when(LoginUserUtil.getLoginUser()).thenReturn(loginUser);

        // assetMemoryService = PowerMockito.spy(assetMemoryService);

        PowerMockito.mockStatic(LogUtils.class);
        PowerMockito.doNothing().when(LogUtils.class, "recordOperLog", Mockito.any(BusinessData.class));
    }

    @Test
    @Ignore
    public void saveAssetMemoryTest() throws Exception {
        AssetMemoryRequest request = new AssetMemoryRequest();
        AssetMemory assetMemory = new AssetMemory();
        Integer expect = 1;
        Mockito.when(requestConverter.convert(request, AssetMemory.class)).thenReturn(assetMemory);
        Mockito.when(assetMemoryDao.insert(Mockito.any())).thenReturn(expect);
        Assert.assertEquals(expect, assetMemoryService.saveAssetMemory(request));
    }

    @Test
    @Ignore
    public void updateAssetMemoryTest() throws Exception {
        AssetMemoryRequest request = new AssetMemoryRequest();
        AssetMemory assetMemory = new AssetMemory();
        Integer expect = 1;
        Mockito.when(requestConverter.convert(request, AssetMemory.class)).thenReturn(assetMemory);
        Mockito.when(assetMemoryDao.update(Mockito.any())).thenReturn(expect);
        Assert.assertEquals(expect, assetMemoryService.updateAssetMemory(request));
    }

    @Test
    @Ignore
    public void findListAssetMemoryTest() throws Exception {
        AssetMemoryQuery query = new AssetMemoryQuery();
        List<AssetMemory> list = new ArrayList<>();
        List<AssetMemoryResponse> expect = new ArrayList<>();
        PowerMockito.mockStatic(BeanConvert.class);
        PowerMockito.when(BeanConvert.convert(list, AssetMemoryResponse.class)).thenReturn(expect);
        Mockito.when(assetMemoryDao.findListAssetMemory(Mockito.any())).thenReturn(list);
        Assert.assertEquals(expect, assetMemoryService.findListAssetMemory(query));
    }

    @Test
    @Ignore
    public void findCountAssetMemoryTest() throws Exception {
        AssetMemoryQuery query = new AssetMemoryQuery();
        Integer expect = 1;
        Mockito.when(assetMemoryDao.findCount(Mockito.any())).thenReturn(expect);
        Assert.assertEquals(expect, assetMemoryService.findCountAssetMemory(query));
    }

    @Test
    @Ignore
    public void findPageAssetMemoryTest() throws Exception {
        AssetMemoryQuery query = new AssetMemoryQuery();
        List<AssetMemoryResponse> expectList = new ArrayList<>();
        Integer expect = 1;
        PowerMockito.doReturn(expect).when(assetMemoryService, "findCountAssetMemory", Mockito.any());
        PowerMockito.doReturn(expectList).when(assetMemoryService, "findListAssetMemory", Mockito.any());
        Assert.assertEquals(expect.intValue(), assetMemoryService.findPageAssetMemory(query).getTotalRecords());
        Assert.assertEquals(expectList, assetMemoryService.findPageAssetMemory(query).getItems());

    }

    @Test
    @Ignore
    public void deleteByIdTest() throws Exception {
        Integer expect = 1;
        Mockito.when(assetMemoryDao.deleteById(Mockito.anyInt())).thenReturn(expect);
        Assert.assertEquals(expect, assetMemoryService.deleteById(2));
    }

}
