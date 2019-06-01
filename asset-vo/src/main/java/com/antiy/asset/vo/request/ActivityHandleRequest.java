package com.antiy.asset.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Map;

/**
 * @auther: zhangbing
 * @date: 2019/1/17 15:51
 * @description: 流程处理请求
 */
@ApiModel(value = "流程处理请求")
public class ActivityHandleRequest {
    @ApiModelProperty(value = "流程表单数据,JSON串")
    private Map    formData;

    @ApiModelProperty(value = "任务Id")
    private String taskId;

    public Map getFormData() {
        return formData;
    }

    public void setFormData(Map formData) {
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
