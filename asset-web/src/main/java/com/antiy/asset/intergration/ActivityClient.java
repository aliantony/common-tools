package com.antiy.asset.intergration;

import java.util.List;

import com.antiy.asset.vo.query.ActivityWaitingQuery;
import com.antiy.asset.vo.request.ActivityHandleRequest;
import com.antiy.asset.vo.request.ManualStartActivityRequest;
import com.antiy.asset.vo.response.WaitingTaskReponse;
import com.antiy.common.base.ActionResponse;

/**
 * @auther: zhangbing
 * @date: 2019/1/18 09:56
 * @description: 流程引擎远程调用client
 */
public interface ActivityClient {

    /**
     * 手动登记硬件资产启动流程
     * @param request
     * @return
     */
    ActionResponse manualStartProcess(ManualStartActivityRequest request);

    /**
     * 处理流程
     * @param request
     * @return
     */
    ActionResponse completeTask(ActivityHandleRequest request);

    /**
     * 查询当前用户的代办任务
     * @return
     */
    ActionResponse<List<WaitingTaskReponse>> queryAllWaitingTask(ActivityWaitingQuery activityWaitingQuery);

    /**
     * 批量启动任务
     * @return
     */
    ActionResponse startProcessWithoutFormBatch(List<ManualStartActivityRequest> startProcessRequests);
}
