package com.antiy.asset.vo.request;

import com.antiy.common.base.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Map;
import java.util.Objects;

/**
 * @auther: zhangbing
 * @date: 2019/1/17 15:51
 * @description: 流程处理请求
 */
@ApiModel(value = "流程处理请求")
public class ActivityHandleRequest extends BaseRequest {
    @ApiModelProperty(value = "流程表单数据,JSON串")
    private Map formData;

    @ApiModelProperty(value = "任务Id")
    private String taskId;
    @ApiModelProperty(value = "主键id,未加密")
    private String id;


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

    //先获取父类的加密id,如果为null，说明传的是未加密的id,
    @Override
    public Integer getId() {
        if (Objects.isNull(super.getId())) {
            return Integer.valueOf(id);
        }
        return super.getId();
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ActivityHandleRequest{" +
                "formData=" + formData +
                ", taskId='" + taskId + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
