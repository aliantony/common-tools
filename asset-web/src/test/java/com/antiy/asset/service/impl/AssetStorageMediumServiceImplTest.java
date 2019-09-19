package com.antiy.asset.service.impl;


import com.antiy.asset.dao.AssetStorageMediumDao;
import com.antiy.asset.entity.AssetStorageMedium;
import com.antiy.asset.service.IAssetStorageMediumService;
import com.antiy.asset.vo.query.AssetStorageMediumQuery;
import com.antiy.asset.vo.request.AssetStorageMediumRequest;
import com.antiy.asset.vo.response.AssetStorageMediumResponse;
import com.antiy.common.base.PageResult;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AssetStorageMediumServiceImplTest {
    @MockBean
    private AssetStorageMediumDao assetStorageMediumDao;

    @SpyBean
    private IAssetStorageMediumService iAssetStorageMediumService;
    @Test
    public void saveAssetStorageMedium() throws Exception {
        AssetStorageMediumRequest request=new AssetStorageMediumRequest();
        when(assetStorageMediumDao.insert(any())).thenReturn(1);
        Integer result = iAssetStorageMediumService.saveAssetStorageMedium(request);
        Assert.assertEquals(null,result);
    }
    @Test
    public void updateAssetStorageMedium() throws Exception {
        AssetStorageMediumRequest request=new AssetStorageMediumRequest();
        when(assetStorageMediumDao.update(any())).thenReturn(1);
        int result = iAssetStorageMediumService.updateAssetStorageMedium(request);
        Assert.assertEquals(1,result);
    }

    @Test
    public void findListAssetStorageMedium() throws Exception {
        AssetStorageMediumQuery query=new AssetStorageMediumQuery();
        List<AssetStorageMedium> assetStorageMediumList =new ArrayList<>();
        when( assetStorageMediumDao.findQuery(query)).thenReturn(assetStorageMediumList);
        List<AssetStorageMediumResponse> listAssetStorageMedium = iAssetStorageMediumService
            .queryListAssetStorageMedium(query);
        Assert.assertEquals(0,listAssetStorageMedium.size());

    }
    @Test
    public void findPageAssetStorageMedium() throws Exception {
        AssetStorageMediumQuery query=new AssetStorageMediumQuery();
        List<AssetStorageMedium> assetStorageMediumList =new ArrayList<>();
        when(assetStorageMediumDao.findCount(query)).thenReturn(100);
        when(assetStorageMediumDao.findQuery(query)).thenReturn(assetStorageMediumList);
        PageResult<AssetStorageMediumResponse> pageAssetStorageMedium = iAssetStorageMediumService
            .queryPageAssetStorageMedium(query);
        Assert.assertEquals(10,pageAssetStorageMedium.getTotalPages());
    }

}
