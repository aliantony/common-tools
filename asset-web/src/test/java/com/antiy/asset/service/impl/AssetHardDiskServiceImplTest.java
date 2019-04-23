package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetHardDiskDao;
import com.antiy.asset.entity.AssetHardDisk;
import com.antiy.asset.util.BeanConvert;
import com.antiy.asset.util.LogHandle;
import com.antiy.asset.vo.query.AssetHardDiskQuery;
import com.antiy.asset.vo.request.AssetHardDiskRequest;
import com.antiy.asset.vo.response.AssetHardDiskResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
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
@PrepareForTest({LogHandle.class, BeanConvert.class, AssetHardDiskServiceImpl.class})
public class AssetHardDiskServiceImplTest {
    @Mock
    private AssetHardDiskDao assetHardDiskDao;
    @Mock
    private BaseServiceImpl<AssetHardDisk> baseService;
    @Mock
    private BaseConverter<AssetHardDiskRequest, AssetHardDisk> requestConverter;
    @InjectMocks
    private AssetHardDiskServiceImpl assetHardDiskService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(LogHandle.class);
        PowerMockito.doNothing().when(LogHandle.class, "log", Mockito.any(), Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt());
        assetHardDiskService = PowerMockito.spy(assetHardDiskService);
    }

    @Test
    public void saveAssetHardDiskTest() throws Exception {
        AssetHardDiskRequest request = new AssetHardDiskRequest();
        AssetHardDisk assetHardDisk = new AssetHardDisk();
        Integer expect = 1;
        Mockito.when(requestConverter.convert(request, AssetHardDisk.class)).thenReturn(assetHardDisk);
        Mockito.when(assetHardDiskDao.insert(Mockito.any())).thenReturn(expect);
        Assert.assertEquals(expect, assetHardDiskService.saveAssetHardDisk(request));
    }

    @Test
    public void updateAssetHardDiskTest() throws Exception {
        AssetHardDiskRequest request = new AssetHardDiskRequest();
        AssetHardDisk assetHardDisk = new AssetHardDisk();
        Integer expect = 1;
        Mockito.when(requestConverter.convert(request, AssetHardDisk.class)).thenReturn(assetHardDisk);
        Mockito.when(assetHardDiskDao.update(Mockito.any())).thenReturn(expect);
        Assert.assertEquals(expect, assetHardDiskService.updateAssetHardDisk(request));
    }

    @Test
    public void findListAssetHardDiskTest() throws Exception {
        AssetHardDiskQuery query = new AssetHardDiskQuery();
        AssetHardDisk assetHardDisk = new AssetHardDisk();
        List<AssetHardDisk> list = new ArrayList<>();
        list.add(assetHardDisk);
        List<AssetHardDiskResponse> expect = getAssetHardDiskResponseList();
        Mockito.when(assetHardDiskDao.findListAssetHardDisk(Mockito.any())).thenReturn(list);
        PowerMockito.mockStatic(BeanConvert.class);
        PowerMockito.doReturn(expect).when(BeanConvert.class, "convert", Mockito.any(), Mockito.any());
        Assert.assertEquals(expect, assetHardDiskService.findListAssetHardDisk(query));
    }

    @Test
    public void findCountAssetHardDiskTest() throws Exception {
        AssetHardDiskQuery query = new AssetHardDiskQuery();
        Integer expect = 1;
        Mockito.when(assetHardDiskDao.findCount(Mockito.any())).thenReturn(expect);
        Assert.assertEquals(expect, assetHardDiskService.findCountAssetHardDisk(query));
    }

    @Test
    public void findPageAssetHardDiskTest() throws Exception {
        AssetHardDiskQuery query = new AssetHardDiskQuery();
        List<AssetHardDiskResponse> expectlist = getAssetHardDiskResponseList();
        Integer expect = 1;
        PowerMockito.doReturn(expect).when(assetHardDiskService, "findCountAssetHardDisk", Mockito.any());
        PowerMockito.doReturn(expectlist).when(assetHardDiskService).findListAssetHardDisk(query);
        Assert.assertEquals(expectlist, assetHardDiskService.findPageAssetHardDisk(query).getItems());
        Assert.assertEquals(expect.intValue(), assetHardDiskService.findPageAssetHardDisk(query).getTotalRecords());
    }

    @Test
    public void deleteByIdTest() throws Exception {
        Integer expect = 1;
        Mockito.when(assetHardDiskDao.deleteById(Mockito.anyInt())).thenReturn(expect);
        Assert.assertEquals(expect, assetHardDiskService.deleteById(1));
    }

    private List<AssetHardDiskResponse> getAssetHardDiskResponseList() {
        AssetHardDiskResponse assetHardDiskResponse = new AssetHardDiskResponse();
        List<AssetHardDiskResponse> list = new ArrayList<>();
        list.add(assetHardDiskResponse);
        return list;
    }
}
