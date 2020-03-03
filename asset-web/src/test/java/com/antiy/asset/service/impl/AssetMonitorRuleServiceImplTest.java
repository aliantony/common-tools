package com.antiy.asset.service.impl;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
        request.setAlarmLevel(1);
        request.setUniqueId(100000000111L);
        request.setAreaId("001001001001");
        request.setCpuThreshold(20);
        request.setMemoryThreshold(30);
        request.setDiskThreshold(40);
        request.setName("资产监控规则一");
        request.setRuleStatus(1);
        AssetRuntimeExceptionThreshold runtimeExceptionThreshold = new AssetRuntimeExceptionThreshold();
        runtimeExceptionThreshold.setRuntimeExceptionThreshold(90);
        runtimeExceptionThreshold.setUnit(TimeEnum.HOUR);
        request.setRuntimeExceptionThreshold(runtimeExceptionThreshold);
        request.setRelatedAsset(Lists.newArrayList("1", "2", "3"));
        monitorRuleService.saveAssetMonitorRule(request);
    }

    @Test
    public void updateAssetMonitorRule() {
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
}