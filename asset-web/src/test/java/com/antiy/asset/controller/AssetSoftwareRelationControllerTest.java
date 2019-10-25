package com.antiy.asset.controller;

import java.util.List;

import org.apache.commons.compress.utils.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.antiy.asset.service.IAssetSoftwareRelationService;
import com.antiy.asset.util.LogHandle;
import com.antiy.asset.vo.query.InstallQuery;
import com.antiy.asset.vo.request.AssetSoftwareReportRequest;
import com.antiy.biz.util.RedisKeyUtil;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;

/**
 * @Author: lvliang
 * @Date: 2019/10/22 15:54
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ LoginUserUtil.class, LogUtils.class, LogHandle.class, RedisKeyUtil.class })
public class AssetSoftwareRelationControllerTest {
    @InjectMocks
    private AssetSoftwareRelationController assetSoftwareRelationController;
    @Mock
    public IAssetSoftwareRelationService    iAssetSoftwareRelationService;

    @Test
    public void countAssetBySoftId() throws Exception {
        QueryCondition queryCondition = new QueryCondition();
        queryCondition.setPrimaryKey("1");
        assetSoftwareRelationController.countAssetBySoftId(queryCondition);
    }

    @Test
    public void queryOS() throws Exception {
        assetSoftwareRelationController.queryOS();

    }

    @Test
    public void queryInstalledList() throws Exception {
        QueryCondition query = new QueryCondition();
        query.setPrimaryKey("1");
        assetSoftwareRelationController.queryInstalledList(query);
    }

    @Test
    public void queryInstallableList() throws Exception {
        InstallQuery query = new InstallQuery();
        assetSoftwareRelationController.queryInstallableList(query);

        query.setAssetId("1");
        assetSoftwareRelationController.queryInstallableList(query);
    }

    @Test
    public void batchRelation() throws Exception {
        AssetSoftwareReportRequest softwareReportRequest = new AssetSoftwareReportRequest();
        List<String> assetId = Lists.newArrayList();
        assetId.add("1");

        List<Long> softId = Lists.newArrayList();
        softId.add(1L);
        softwareReportRequest.setAssetId(assetId);
        softwareReportRequest.setSoftId(softId);
        assetSoftwareRelationController.batchRelation(softwareReportRequest);
    }
}