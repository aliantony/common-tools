package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetCategoryModelDao;
import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.AssetLinkRelationDao;
import com.antiy.asset.dao.AssetTopologyDao;
import com.antiy.asset.entity.Asset;
import com.antiy.asset.intergration.EmergencyClient;
import com.antiy.asset.intergration.OperatingSystemClient;
import com.antiy.asset.service.IAssetCategoryModelService;
import com.antiy.asset.service.IAssetService;
import com.antiy.asset.vo.response.AssetOuterResponse;
import com.antiy.asset.vo.response.AssetResponse;
import com.antiy.biz.util.RedisUtil;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.encoder.AesEncoder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AssetTopologyServiceImplTest {
    @Mock
    private AssetLinkRelationDao                assetLinkRelationDao;
    @Mock
    private AssetDao                            assetDao;
    @Mock
    private IAssetCategoryModelService          iAssetCategoryModelService;
    @Mock
    private AssetTopologyDao                    assetTopologyDao;
    @Mock
    private EmergencyClient                     emergencyClient;
    @Mock
    private BaseConverter<Asset, AssetResponse> converter;
    @Mock
    private RedisUtil                           redisUtil;
    @Mock
    private AssetCategoryModelDao               assetCategoryModelDao;
    @Mock
    private AesEncoder                          aesEncoder;
    @Mock
    private OperatingSystemClient               operatingSystemClient;
    @Mock
    private IAssetService                       iAssetService;
    @InjectMocks
    private AssetTopologyServiceImpl            assetTopologyService;

    private List<Double>                        middlePoint        = Arrays.asList(0d, 0d, 0d);
    private List<Double>                        cameraPos          = Arrays.asList(-3000d, 1200d, 4800d);
    private List<Double>                        targetPos          = Arrays.asList(-1000d, 800d, 600d);
    private Double                              firstLevelSpacing  = 200d;
    private Double                              firstLevelHeight   = 1800d;
    private Double                              secondLevelSpacing = 1000d;
    private Double                              secondLevelHeight  = 1300d;
    private Double                              thirdLevelSpacing  = 100d;
    private Double                              thirdLevelHeight   = 675d;

    @Test
    public void queryCategoryModelsTest() {
        when(assetLinkRelationDao.queryCategoryModes()).thenReturn(new ArrayList());
        Assert.assertEquals(1, assetTopologyService.queryCategoryModels().size());
    }

    @Test
    public void queryAssetNodeInfoTest() throws Exception {
        AssetOuterResponse assetOuterResponse = new AssetOuterResponse();
        AssetResponse assetResponse = new AssetResponse();
        assetResponse.setName("abc");
        assetOuterResponse.setAsset(assetResponse);
        when(iAssetService.getByAssetId(any())).thenReturn(assetOuterResponse);
        Assert.assertEquals("abc", assetTopologyService.queryAssetNodeInfo("1").getData().get(0).getAsset_name());
    }
}
