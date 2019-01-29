package com.antiy.asset.vo.response;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 待办任务的bean
 * @author chenbin
 * @create 2019-01-04 9:38
 */
@ApiModel(value = "待办任务")
public class WaitingTaskReponse {
    @ApiModelProperty(value = "处理人", name = "assignee", example = "处理人")
    private String assignee;
    @ApiModelProperty(value = "任务名", name = "name", example = "任务名")
    private String name;
    @ApiModelProperty(value = "优先级", name = "priority", example = "优先级")
    private int    priority;
    @ApiModelProperty(value = "创建时间", name = "createTime", example = "创建时间")
    private Date   createTime;
    @ApiModelProperty(value = "执行id", name = "executionId", example = "执行id")
    private String executionId;
    @ApiModelProperty(value = "流程实例id", name = "processInstanceId", example = "流程实例id")
    private String processInstanceId;
    @ApiModelProperty(value = "流程定义id", name = "processDefinitionId", example = "流程定义id")
    private String processDefinitionId;
    @ApiModelProperty(value = "流程定义key", name = "taskDefinitionKey", example = "流程定义key")
    private String taskDefinitionKey;
    @ApiModelProperty(value = "表单页面", name = "formKey", example = "表单页面")
    private String formKey;
    @ApiModelProperty(value = "任务id", name = "taskId", example = "任务id")
    private String taskId;
    @ApiModelProperty(value = "业务主键", name = "businessId", example = "业务主键")
    private String businessId;

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getExecutionId() {
        return executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    public String getTaskDefinitionKey() {
        return taskDefinitionKey;
    }

    public void setTaskDefinitionKey(String taskDefinitionKey) {
        this.taskDefinitionKey = taskDefinitionKey;
    }

    public String getFormKey() {
        return formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }
}
