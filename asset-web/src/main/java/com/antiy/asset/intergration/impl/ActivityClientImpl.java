package com.antiy.asset.intergration.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import com.antiy.asset.aop.AssetLog;
import com.antiy.asset.intergration.ActivityClient;
import com.antiy.asset.util.BaseClient;
import com.antiy.asset.vo.request.ActivityHandleRequest;
import com.antiy.asset.vo.request.ManualStartActivityRequest;
import com.antiy.common.base.ActionResponse;

/**
 * @auther: zhangbing
 * @date: 2019/1/18 10:03
 * @description: 流程引起远程调用
 */
@Component
public class ActivityClientImpl implements ActivityClient {

    @Resource
    private BaseClient baseClient;

    @Value("${manualStartProcessUrl}")
    private String     manualStartProcessUrl;

    @Value("${completeTaskUrl}")
    private String     completeTaskUrl;

    @Override
    @AssetLog(description = "人工登记启动工作流")
    public ActionResponse manualStartProcess(ManualStartActivityRequest request) {
        return (ActionResponse) baseClient.post(request, new ParameterizedTypeReference<ActionResponse>() {
        }, manualStartProcessUrl);
    }

    @Override
    @AssetLog(description = "处理工作流")
    public ActionResponse completeTask(ActivityHandleRequest request) {
        return (ActionResponse) baseClient.post(request, new ParameterizedTypeReference<ActionResponse>() {
        }, completeTaskUrl);
    }
}
