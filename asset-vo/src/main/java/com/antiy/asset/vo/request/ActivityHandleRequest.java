package com.antiy.asset.vo.request;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @auther: zhangbing
 * @date: 2019/1/17 15:51
 * @description: 流程处理请求
 */
@ApiModel(value = "流程处理请求")
public class ActivityHandleRequest {
    @ApiModelProperty(value = "流程表单数据,JSON串")
    @NotBlank(message = "流程表单数据不能为空")
    private String formData;

    @ApiModelProperty(value = "任务Id")
    private String taskId;

    public String getFormData() {
        return formData;
    }

    public void setFormData(String formData) {
        this.formData = formData;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @Override
    public String toString() {
        return "ActivityHandleRequest{" + "formData='" + formData + '\'' + ", taskId='" + taskId + '\'' + '}';
    }
}
