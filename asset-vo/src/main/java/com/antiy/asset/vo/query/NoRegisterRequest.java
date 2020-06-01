package com.antiy.asset.vo.query;

import com.antiy.common.encoder.Encode;

public class NoRegisterRequest {
    @Encode
    private String assetId;
    private String taskId;
    /**
     * 来源
     * 1 漏洞不予登记
     * 2 配置不予登记
     */
    private String source;
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
