package com.antiy.asset.service.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.antiy.asset.AssetApplication;
import com.antiy.asset.service.IAssetMonitorRuleService;
import com.antiy.asset.vo.enums.TimeEnum;
import com.antiy.asset.vo.request.AssetMonitorRuleRequest;
import com.antiy.asset.vo.request.AssetRuntimeExceptionThreshold;
import com.google.common.collect.Lists;

/**
 * @author zhangyajun
 * @create 2020-03-03 11:27
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AssetApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AssetMonitorRuleServiceImplTest {

    @Resource
    private IAssetMonitorRuleService monitorRuleService;

    @Test
    public void saveAssetMonitorRule() throws Exception {
        AssetMonitorRuleRequest request = new AssetMonitorRuleRequest();
        request.setAlarmLevel("1");
        request.setUniqueId("100000000111");
        request.setAreaId("001001001001");
        request.setCpuThreshold(20);
        request.setMemoryThreshold(30);
        request.setDiskThreshold(40);
        request.setName("资产监控规则一");
        request.setRuleStatus("1");
        AssetRuntimeExceptionThreshold runtimeExceptionThreshold = new AssetRuntimeExceptionThreshold();
        runtimeExceptionThreshold.setRuntimeExceptionThreshold(90);
        runtimeExceptionThreshold.setUnit(TimeEnum.HOUR);
        request.setRuntimeExceptionThreshold(runtimeExceptionThreshold);
        request.setRelatedAsset(Lists.newArrayList("1", "2", "3"));
        System.out.println(JSON.toJSONString(request));
        // monitorRuleService.saveAssetMonitorRule(request);
    }

    @Test
    public void updateAssetMonitorRule() throws Exception {
        AssetMonitorRuleRequest request = new AssetMonitorRuleRequest();
        request.setUniqueId("684768284255453184");
        request.setAlarmLevel("1");
        request.setAreaId("001001001001");
        request.setCpuThreshold(10);
        request.setMemoryThreshold(10);
        request.setDiskThreshold(10);
        request.setName("资产监控规则yi一");
        request.setRuleStatus("1");
        AssetRuntimeExceptionThreshold runtimeExceptionThreshold = new AssetRuntimeExceptionThreshold();
        runtimeExceptionThreshold.setRuntimeExceptionThreshold(80);
        runtimeExceptionThreshold.setUnit(TimeEnum.HOUR);
        request.setRuntimeExceptionThreshold(runtimeExceptionThreshold);
        request.setRelatedAsset(Lists.newArrayList("4", "5", "6"));
        System.out.println(JSON.toJSONString(request));
        monitorRuleService.updateAssetMonitorRule(request);
    }

    @Test
    public void queryListAssetMonitorRule() {
    }

    @Test
    public void queryPageAssetMonitorRule() {
    }

    @Test
    public void queryAssetMonitorRuleById() {
    }

    @Test
    public void getAppPropertis() throws FileNotFoundException {
        String path = AssetMonitorRuleServiceImplTest.class.getClassLoader()
            .getResource("config/application-common.properties").getPath();
        System.out.println(path);
        FileInputStream inputStream = new FileInputStream("path");
    }
}