package com.antiy.asset.vo.query;

import com.antiy.common.encoder.Encode;

public class NoRegisterRequest {
    @Encode
    private String assetId;
    private String taskId;

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
