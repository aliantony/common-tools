package com.antiy.asset.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.request.RequestContextHolder;

import com.antiy.asset.dao.AssetInstallTemplateDao;
import com.antiy.asset.util.ExcelUtils;
import com.antiy.asset.util.LogHandle;
import com.antiy.asset.util.ZipUtil;
import com.antiy.asset.vo.query.PrimaryKeyQuery;
import com.antiy.asset.vo.response.AssetTemplateRelationResponse;
import com.antiy.biz.util.RedisKeyUtil;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.utils.LicenseUtil;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringRunner.class)
@PrepareForTest({ ExcelUtils.class, RequestContextHolder.class, LoginUserUtil.class, LicenseUtil.class, LogUtils.class,
                  LogHandle.class, ZipUtil.class, RedisKeyUtil.class })
@PowerMockIgnore({ "javax.*.*", "com.sun.*", "org.xml.*", "org.apache.*" })
public class AssetInstallTemplateServiceImplTest {
    @MockBean
    private AssetInstallTemplateDao         assetInstallTemplateDao;
    @InjectMocks
    private AssetInstallTemplateServiceImpl assetInstallTemplateServiceImpl;

    @Before
    @Test
    public void queryTemplateByAssetId() throws Exception {
        AssetTemplateRelationResponse assetTemplateRelationResponse = new AssetTemplateRelationResponse();
        assetTemplateRelationResponse.setDescription("csdf");
        assetTemplateRelationResponse.setName("che");
        assetTemplateRelationResponse.setPatchCount(100);
        QueryCondition queryCondition = new QueryCondition();
        queryCondition.setPrimaryKey("1");
        when(assetInstallTemplateDao.queryTemplateById(any())).thenReturn(assetTemplateRelationResponse);
        AssetTemplateRelationResponse assetTemplateRelationResponse1 = assetInstallTemplateServiceImpl
            .queryTemplateByAssetId(queryCondition);
        Assert.assertNotNull(assetTemplateRelationResponse);
    }

    @Test
    public void querySoftPage() {
        PrimaryKeyQuery query = new PrimaryKeyQuery();
        when(assetInstallTemplateDao.querySoftCount(query)).thenReturn(0);
        assetInstallTemplateServiceImpl.querySoftPage(query);

        when(assetInstallTemplateDao.querySoftCount(query)).thenReturn(1);
        assetInstallTemplateServiceImpl.querySoftPage(query);
    }

    @Test
    public void queryPatchPage() {
        PrimaryKeyQuery query = new PrimaryKeyQuery();
        when(assetInstallTemplateDao.queryPatchCount(query)).thenReturn(0);
        assetInstallTemplateServiceImpl.queryPatchPage(query);

        when(assetInstallTemplateDao.queryPatchCount(query)).thenReturn(1);
        assetInstallTemplateServiceImpl.queryPatchPage(query);
    }
}