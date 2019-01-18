package com.antiy.asset.vo.request;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.utils.ParamterExceptionUtils;
import com.antiy.common.validation.ObjectValidator;

import io.swagger.annotations.ApiModelProperty;

/**
 * WorkOrderVO 请求对象
 *
 * @author zhangyajun
 * @since 2018-12-27
 */
public class WorkOrderVO extends BasicRequest implements ObjectValidator {

    /** 工单名称 */
    @ApiModelProperty("工单名称")
    @NotBlank(message = "用户名不能为空")
    private String        name;

    /** 1.紧急，2.重要，3.次要，4.提示 */
    @ApiModelProperty("1.紧急，2.重要，3.次要，4.提示")
    @NotNull(message = "工单等级不能为空")
    private Integer       workLevel;

    /** 1巡检，2预警，3重保，4应急，5清查,6基准配置，7基准验证，8准入实施，9其他 */
    @ApiModelProperty("1巡检，2预警，3重保，4应急，5清查,6基准配置，7基准验证，8准入实施，9其他")
    @NotNull(message = "工单类型不能为空")
    private Integer       orderType;

    /** 1资产管理，2配置管理，3漏洞管理，4.补丁管理，5日志管理6.告警管理，7日常安全管理，8安全设备管理，9系统管理，10其他 */
    @ApiModelProperty("1资产管理，2配置管理，3漏洞管理，4.补丁管理，5日志管理6.告警管理，7日常安全管理，8安全设备管理，9系统管理，10其他")
    @NotNull(message = "工单来源不能为空")
    private Integer       orderSource;

    /** 工单内容 */
    @ApiModelProperty("工单内容")
    @NotEmpty(message = "工单内容不能为空")
    private String        content;

    /** 执行人id */
    @ApiModelProperty("执行人id")
    @NotNull(message = "工单执行人不能为空")
    private Integer       executeUserId;

    /** 执行人姓名 */
    @ApiModelProperty("执行人姓名")
    @NotNull(message = "执行人姓名不能为空")
    private String        executeUserName;
    /** 创建人 */
    @ApiModelProperty("创建人")
    private Integer       createUser;

    /** 工单开始时间 */
    @ApiModelProperty("工单开始时间")
    @NotNull(message = "工单开始时间不能为空")
    private Long          startTime;

    /** 工单结束时间 */
    @ApiModelProperty("工单结束时间")
    @NotNull(message = "工单结束时间不能为空")
    private Long          endTime;

    /** 工单附件id */
    @ApiModelProperty("多个工单附件id,Json数组,上传调用工单接口")
    @Encode
    private List<Integer> workOrderAttachments;

    public Integer getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(Integer orderSource) {
        this.orderSource = orderSource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWorkLevel() {
        return workLevel;
    }

    public void setWorkLevel(Integer workLevel) {
        this.workLevel = workLevel;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public List<Integer> getWorkOrderAttachments() {
        return workOrderAttachments;
    }

    public void setWorkOrderAttachments(List<Integer> workOrderAttachments) {
        this.workOrderAttachments = workOrderAttachments;
    }

    public Integer getExecuteUserId() {
        return executeUserId;
    }

    public void setExecuteUserId(Integer executeUserId) {
        this.executeUserId = executeUserId;
    }

    public String getExecuteUserName() {
        return executeUserName;
    }

    public void setExecuteUserName(String executeUserName) {
        this.executeUserName = executeUserName;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    @Override
    public void validate() throws RequestParamValidateException {
        if (null != startTime && null != endTime) {
            ParamterExceptionUtils.isTrue(startTime <= endTime ? true : false, "结束时间不能早于开始时间");
        }
    }
}
