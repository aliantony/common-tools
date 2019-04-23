package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetCpuDao;
import com.antiy.asset.entity.AssetCpu;
import com.antiy.asset.vo.query.AssetCpuQuery;
import com.antiy.asset.vo.request.AssetCpuRequest;
import com.antiy.asset.vo.response.AssetCpuResponse;
import com.antiy.common.base.BaseConverter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class AssetCpuServiceImplTest {

    @Mock
    private AssetCpuDao assetCpuDao;
    @Mock
    private BaseConverter<AssetCpuRequest, AssetCpu> requestConverter;
    @Mock
    private BaseConverter<AssetCpu, AssetCpuResponse> responseConverter;
    @InjectMocks
    private AssetCpuServiceImpl assetCpuServiceImpl;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveAssetCpuTest() throws Exception {
        AssetCpuRequest request = new AssetCpuRequest();
        AssetCpu assetCpu = new AssetCpu();
        Integer expect = 101;
        assetCpu.setId(expect);
        Integer notZero = 1;
        Mockito.when(requestConverter.convert(request, AssetCpu.class)).thenReturn(assetCpu);
        Mockito.when(assetCpuDao.insert(assetCpu)).thenReturn(notZero);
        Assert.assertEquals(expect, assetCpuServiceImpl.saveAssetCpu(request));

    }

    @Test
    public void updateAssetCpuTest() throws Exception {
        AssetCpuRequest request = new AssetCpuRequest();
        AssetCpu assetCpu = new AssetCpu();
        Integer expect = 0;
        Mockito.when(requestConverter.convert(request, AssetCpu.class)).thenReturn(assetCpu);
        Mockito.when(assetCpuDao.update(assetCpu)).thenReturn(expect);
        Assert.assertEquals(expect, assetCpuServiceImpl.updateAssetCpu(request));

    }

    @Test
    public void findListAssetCpuTest() throws Exception {
        List<AssetCpu> assetCpu = new ArrayList<>();
        List<AssetCpuResponse> expect = new ArrayList<>();
        AssetCpuResponse assetCpuResponse = new AssetCpuResponse();
        assetCpuResponse.setBrand("Intel");
        expect.add(assetCpuResponse);
        Mockito.when(assetCpuDao.findListAssetCpu(Mockito.any())).thenReturn(assetCpu);
        Mockito.when(responseConverter.convert(assetCpu, AssetCpuResponse.class)).thenReturn(expect);
        Assert.assertEquals(assetCpuResponse.getBrand(), assetCpuServiceImpl.findListAssetCpu(Mockito.any()).get(0).getBrand());

    }

    @Test
    public void findCountAssetCpuTest() throws Exception {
        Integer expect = 1;
        Mockito.when(assetCpuDao.findCount(Mockito.any())).thenReturn(expect);
        Assert.assertEquals(expect, assetCpuServiceImpl.findCountAssetCpu(Mockito.any()));

    }

    @Test
    public void findPageAssetCpuTest() throws Exception {
        AssetCpuQuery assetCpuQuery = new AssetCpuQuery();
        assetCpuQuery.setCurrentPage(1);
        assetCpuQuery.setPageSize(10);
        List<AssetCpuResponse> expect = new ArrayList<>();
        AssetCpuResponse assetCpuResponse = new AssetCpuResponse();
        assetCpuResponse.setBrand("Intel");
        expect.add(assetCpuResponse);
        Integer totalRecord = 1;
        Mockito.when(assetCpuServiceImpl.findCountAssetCpu(Mockito.any())).thenReturn(totalRecord);
        Mockito.when(assetCpuServiceImpl.findListAssetCpu(Mockito.any())).thenReturn(expect);
        Assert.assertEquals(expect, assetCpuServiceImpl.findPageAssetCpu(assetCpuQuery).getItems());

    }
}
