package com.antiy.asset.vo.request;

/**
 * @Filename: CancelWaitingTask Description:
 * @Version: 1.0
 * @Author: why
 * @Date: 2020/1/3
 */
public class CancelWaitingTask {
    private String processInstanceId;

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }
}
