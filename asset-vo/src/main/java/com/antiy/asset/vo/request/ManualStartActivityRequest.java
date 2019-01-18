package com.antiy.asset.vo.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.antiy.asset.vo.enums.AssetActivityTypeEnum;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @auther: zhangbing
 * @date: 2019/1/17 15:38
 * @description:人工登记启动工作流请求参数
 */
@ApiModel(value = "人工登记启动工作流请求参数")
public class ManualStartActivityRequest {

    /**
     * 处理人
     */
    @ApiModelProperty(value = "启动人")
    private String                assignee;

    /**
     * 业务Id
     */
    @ApiModelProperty(value = "业务Id")
    @NotBlank(message = "业务Id不能为空")
    private String                businessId;

    /**
     * 表单数据
     */
    @ApiModelProperty(value = "流程表单数据,JSON串")
    @NotBlank(message = "流程处理数据不能为空")
    private String                formData;

    @ApiModelProperty(value = "流程定义Id")
    @NotNull(message = "流程定义Id不能为空")
    private AssetActivityTypeEnum processDefintionKey;

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getFormData() {
        return formData;
    }

    public void setFormData(String formData) {
        this.formData = formData;
    }

    public String getProcessDefintionKey() {
        return processDefintionKey.getCode();
    }

    public void setProcessDefintionKey(AssetActivityTypeEnum processDefintionKey) {
        this.processDefintionKey = processDefintionKey;
    }
}
