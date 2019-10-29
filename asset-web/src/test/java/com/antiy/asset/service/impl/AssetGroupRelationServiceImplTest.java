package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetGroupRelationDao;
import com.antiy.asset.service.IAssetGroupRelationService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.when;

/**
 * @author zhangxin
 * @date 2019/10/22
 */
@RunWith(PowerMockRunner.class)
public class AssetGroupRelationServiceImplTest {

    @InjectMocks
    private AssetGroupRelationServiceImpl assetGroupRelationService = new AssetGroupRelationServiceImpl();
    @Mock
    private AssetGroupRelationDao assetGroupRelationDao;


    @Test
    public void hasRealtionAsset() {
        when(assetGroupRelationDao.hasRealtionAsset(Mockito.eq("2"))).thenReturn(1);
        Assert.assertEquals(Integer.valueOf(1), assetGroupRelationService.hasRealtionAsset("2"));

        when(assetGroupRelationDao.hasRealtionAsset(Mockito.eq("3"))).thenReturn(0);
        Assert.assertEquals(Integer.valueOf(0), assetGroupRelationService.hasRealtionAsset("3"));
    }
}
