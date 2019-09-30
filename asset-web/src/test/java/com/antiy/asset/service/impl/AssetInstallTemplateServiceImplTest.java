package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetInstallTemplateDao;
import com.antiy.asset.vo.response.AssetTemplateRelationResponse;
import com.antiy.common.base.QueryCondition;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AssetInstallTemplateServiceImplTest {
    @MockBean
    private AssetInstallTemplateDao assetInstallTemplateDao;
    @SpyBean
    private AssetInstallTemplateServiceImpl assetInstallTemplateServiceImpl;

    @Before
    @Test
    public void queryTemplateByAssetId() throws Exception {
        AssetTemplateRelationResponse assetTemplateRelationResponse=new AssetTemplateRelationResponse();
        assetTemplateRelationResponse.setDescription("csdf");
        assetTemplateRelationResponse.setName("che");
        assetTemplateRelationResponse.setPatchCount(100);
        QueryCondition queryCondition=new QueryCondition();
        queryCondition.setPrimaryKey("1");
        when(assetInstallTemplateDao.queryTemplateById(any())).thenReturn(assetTemplateRelationResponse);
        AssetTemplateRelationResponse assetTemplateRelationResponse1 = assetInstallTemplateServiceImpl.queryTemplateByAssetId(queryCondition);
        Assert.assertNotNull(assetTemplateRelationResponse);
    }
}