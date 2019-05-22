package com.antiy.asset.entity;

import com.antiy.common.base.BaseEntity;

/**
 * <p>资产状态任务表</p>
 *
 * @author zhangyajun
 * @since 2019-05-22
 */

public class AssetStatusTask extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 流程任务主键
     */
    private String            taskId;
    /**
     * 资产主键
     */
    private Integer           assetId;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }

    @Override
    public String toString() {
        return "AssetStatusTask{" + ", taskId=" + taskId + ", assetId=" + assetId + "}";
    }
}