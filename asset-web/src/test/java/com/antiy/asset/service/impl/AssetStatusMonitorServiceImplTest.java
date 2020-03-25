package com.antiy.asset.service.impl;

import com.alibaba.fastjson.JSON;
import com.antiy.asset.AssetApplication;
import com.antiy.asset.service.IAssetStatusMonitorService;
import com.antiy.asset.vo.query.AssetStatusMonitorQuery;
import com.antiy.asset.vo.response.AssetStatusMonitorResponse;
import com.antiy.common.base.PageResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest(classes = AssetApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class AssetStatusMonitorServiceImplTest {

    @Resource
    private IAssetStatusMonitorService iAssetStatusMonitorService;
    @Test
    public void saveAssetStatusMonitor() {
    }

    @Test
    public void updateAssetStatusMonitor() {
    }

    @Test
    public void queryListAssetStatusMonitor() {
    }

    @Test
    public void queryPageAssetStatusMonitor() {
    }

    @Test
    public void queryAssetStatusMonitorById() {
    }

    @Test
    public void deleteAssetStatusMonitorById() {
    }

    @Test
    public void queryBasePerformance() {
        AssetStatusMonitorResponse assetStatusMonitorResponse = iAssetStatusMonitorService.queryBasePerformance("9");
        System.out.println(JSON.toJSONString(assetStatusMonitorResponse));
    }

    @Test
    public void queryMonitor() {
        AssetStatusMonitorQuery assetStatusMonitorQuery=new AssetStatusMonitorQuery();
        String json="{\"type\":2,\"assetId\":\"9\",\"relations\":[0,1]}";
        AssetStatusMonitorQuery assetStatusMonitorQuery1 = JSON.parseObject(json, AssetStatusMonitorQuery.class);
        PageResult<AssetStatusMonitorResponse> pageResult = iAssetStatusMonitorService.queryMonitor(assetStatusMonitorQuery1);
        System.out.println(JSON.toJSONString(pageResult));
    }
}