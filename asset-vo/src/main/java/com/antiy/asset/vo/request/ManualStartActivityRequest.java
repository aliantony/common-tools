package com.antiy.asset.vo.request;

import com.antiy.common.encoder.Encode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

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
    private String       assignee;

    /**
     * 业务Id
     */
    @ApiModelProperty(value = "业务Id")
    // @Encode
    private String       businessId;

    /**
     * 表单数据
     */
    @ApiModelProperty(value = "流程表单数据,JSON串")
    // @NotBlank(message = "流程处理数据不能为空")
    private Map          formData;

    @ApiModelProperty(value = "配置人员")
    private List<String> configUserIds;

    /**
     * 配置建议(退役再登记时使用)
     */
    @ApiModelProperty(value = "配置建议(退役再登记时使用)")
    @NotBlank(message = "配置建议不能为空")
    private String       suggest;

    public String getSuggest() {
        return suggest;
    }

    public void setSuggest(String suggest) {
        this.suggest = suggest;
    }

    public List<String> getConfigUserIds() {
        return configUserIds;
    }

    public void setConfigUserIds(List<String> configUserIds) {
        this.configUserIds = configUserIds;
    }

    @ApiModelProperty(value = "流程定义Id")
    // @NotNull(message = "流程定义Id不能为空")
    private String processDefinitionKey;

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

    public Map getFormData() {
        return formData;
    }

    public void setFormData(Map formData) {
        this.formData = formData;
    }

    public String getProcessDefinitionKey() {
        return processDefinitionKey;
    }

    public void setProcessDefinitionKey(String processDefinitionKey) {
        this.processDefinitionKey = processDefinitionKey;
    }
}
