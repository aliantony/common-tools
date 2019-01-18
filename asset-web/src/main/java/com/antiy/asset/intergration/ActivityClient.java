package com.antiy.asset.intergration;

import com.antiy.asset.vo.request.ActivityHandleRequest;
import com.antiy.asset.vo.request.ManualStartActivityRequest;
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
}
