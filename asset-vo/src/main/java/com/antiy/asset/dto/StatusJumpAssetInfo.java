package com.antiy.asset.dto;

import com.antiy.common.encoder.Encode;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author zhangxin
 * @date 2019/9/27
 */
public class StatusJumpAssetInfo {
    @ApiModelProperty("资产Id")
    @NotNull(message = "资产数据id不能为空")
    @NotBlank(message = "资产数据id不正确")
    @Encode(message = "资产数据id格式不正确")
    private String assetId;

    @ApiModelProperty(value = "任务Id")
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

    @Override
    public String toString() {
        return "StatusJumpAssetInfo{" +
                "assetId='" + assetId + '\'' +
                ", taskId='" + taskId + '\'' +
                '}';
    }
}
