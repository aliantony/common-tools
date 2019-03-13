package com.antiy.asset.vo.request;

import java.util.ArrayList;
import java.util.List;

import com.antiy.common.base.BasicRequest;
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

    private static final long serialVersionUID = 1L;

    WorkOrderVO() {
        this.setWorkOrderAttachments(new ArrayList<>());
    }

    /**
     * 工单名称
     */
    @ApiModelProperty("工单名称")
    private String        name;

    /**
     * 1.紧急，2.重要，3.次要，4.提示
     */
    @ApiModelProperty("1.紧急，2.重要，3.次要，4.提示")
    private Integer       workLevel;

    /**
     * 1巡检，2预警，3重保，4应急，5清查,6基准配置，7基准验证，8准入实施，9其他
     */
    @ApiModelProperty("1巡检，2预警，3重保，4应急，5清查,6基准配置，7基准验证，8准入实施，9其他")
    private Integer       orderType;

    /**
     * 1资产管理，2配置管理，3漏洞管理，4.补丁管理，5日志管理6.告警管理，7日常安全管理，8安全设备管理，9系统管理，10其他
     */
    @ApiModelProperty("1资产管理，2配置管理，3漏洞管理，4.补丁管理，5日志管理6.告警管理，7日常安全管理，8安全设备管理，9系统管理，10其他")
    private Integer       orderSource;

    /**
     * 工单内容
     */
    @ApiModelProperty("工单内容")
    private String        content;

    /**
     * 执行人id
     */
    @ApiModelProperty("执行人id")
    private String        executeUserId;

    /**
     * 工单开始时间
     */
    @ApiModelProperty("工单开始时间")
    /** 正则表达式范围2001-09-09 09:46:40 12:00:00 ----> 2128-06-11 16:53:19 1000000000000-4999999999999 */
    private String        startTime;

    /**
     * 工单结束时间
     */
    @ApiModelProperty("工单结束时间")
    /** 正则表达式范围2001-09-09 09:46:40 12:00:00 ----> 2128-06-11 16:53:19 1000000000000-4999999999999 */
    private String        endTime;

    /**
     * 工单附件id
     */
    @ApiModelProperty("多个工单附件id,Json数组")
    private List<Integer> workOrderAttachments;

    /**
     * 与工单绑定的相关告警来源id
     */
    @ApiModelProperty("与工单绑定的相关告警来源id")
    private String        relatedSourceId;

    public void setWorkLevel(Integer workLevel) {
        this.workLevel = workLevel;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
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

        return this.workLevel;
    }

    public Integer getOrderSource() {
        return this.orderSource;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public List<Integer> getWorkOrderAttachments() {
        return workOrderAttachments;
    }

    public void setWorkOrderAttachments(List<Integer> workOrderAttachments) {
        this.workOrderAttachments = workOrderAttachments;
    }

    public String getExecuteUserId() {
        return this.executeUserId;
    }

    public void setExecuteUserId(String executeUserId) {
        this.executeUserId = executeUserId;
    }

    public String getRelatedSourceId() {
        return this.relatedSourceId;
    }

    public void setRelatedSourceId(String relatedSourceId) {
        this.relatedSourceId = relatedSourceId;
    }

    @Override
    public void validate() throws RequestParamValidateException {
        if (null != startTime && null != endTime) {
            ParamterExceptionUtils.isTrue(Long.valueOf(getStartTime()) < Long.valueOf(getEndTime()),
                "任务预计开始时间不能晚于或等于任务预计结束时间");
        }
    }

    @Override
    public String toString() {
        return super.toString() + "WorkOrderVO{" + "name='" + name + '\'' + ", workLevel=" + workLevel + ", orderType="
               + orderType + ", orderSource=" + orderSource + ", content='" + content + '\'' + ", executeUserId="
               + executeUserId + '\'' + ", startTime=" + startTime + ", endTime=" + endTime + ", workOrderAttachments="
               + workOrderAttachments + ", relatedSourceId=" + relatedSourceId + '}';
    }
}
