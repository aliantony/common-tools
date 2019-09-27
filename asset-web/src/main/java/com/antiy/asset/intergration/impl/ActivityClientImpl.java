package com.antiy.asset.intergration.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import com.antiy.asset.aop.AssetLog;
import com.antiy.asset.intergration.ActivityClient;
import com.antiy.asset.util.BaseClient;
import com.antiy.asset.vo.enums.AssetLogOperationType;
import com.antiy.asset.vo.query.ActivityWaitingQuery;
import com.antiy.asset.vo.request.ActivityHandleRequest;
import com.antiy.asset.vo.request.ManualStartActivityRequest;
import com.antiy.asset.vo.response.WaitingTaskReponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.utils.ParamterExceptionUtils;

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

    @Value("${batchCompleteTaskUrl}")
    private String     batchCompleteTaskUrl;

    @Value("${completeTaskUrl}")
    private String     completeTaskUrl;

    @Value("${waitingTaskUrl}")
    private String     waitingTaskUrl;

    @Value("${startProcessWithoutFormBatchUrl}")
    private String     startProcessWithoutFormBatchUrl;

    @Override
    @AssetLog(description = "人工登记启动工作流", operationType = AssetLogOperationType.CREATE)
    public ActionResponse manualStartProcess(ManualStartActivityRequest request) {
        ParamterExceptionUtils.isNull(request, "启动流程参数不能为空");
        return (ActionResponse) baseClient.post(request, new ParameterizedTypeReference<ActionResponse>() {
        }, manualStartProcessUrl);
    }

    @Override
    @AssetLog(description = "处理工作流", operationType = AssetLogOperationType.ADD)
    public ActionResponse completeTask(ActivityHandleRequest request) {
        ParamterExceptionUtils.isNull(request, "处理流程参数不能为空");
        return (ActionResponse) baseClient.post(request, new ParameterizedTypeReference<ActionResponse>() {
        }, completeTaskUrl);
    }

    @Override
    @AssetLog(description = "批量处理工作流", operationType = AssetLogOperationType.ADD)
    public ActionResponse completeTaskBatch(List<ActivityHandleRequest> request) {
        ParamterExceptionUtils.isNull(request, "处理流程参数不能为空");
        return (ActionResponse) baseClient.post(request, new ParameterizedTypeReference<ActionResponse>() {
        }, batchCompleteTaskUrl);
    }

    @Override
    @AssetLog(description = "获取当前用户的待办任务")
    public ActionResponse<List<WaitingTaskReponse>> queryAllWaitingTask(ActivityWaitingQuery activityWaitingQuery) {
        return (ActionResponse) baseClient.post(activityWaitingQuery,
            new ParameterizedTypeReference<ActionResponse<List<WaitingTaskReponse>>>() {
            }, waitingTaskUrl);
    }

    @Override
    @AssetLog(description = "批量启动任务")
    public ActionResponse startProcessWithoutFormBatch(List<ManualStartActivityRequest> startProcessRequests) {
        return (ActionResponse) baseClient.post(startProcessRequests,
            new ParameterizedTypeReference<ActionResponse<List<WaitingTaskReponse>>>() {
            }, startProcessWithoutFormBatchUrl);
    }
}
