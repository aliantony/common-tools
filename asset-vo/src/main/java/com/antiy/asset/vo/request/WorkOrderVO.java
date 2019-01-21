package com.antiy.asset.vo.request;

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

    /**
     * 工单名称
     */
    @ApiModelProperty("工单名称")
    private String            name;

    /**
     * 1.紧急，2.重要，3.次要，4.提示
     */
    @ApiModelProperty("1.紧急，2.重要，3.次要，4.提示")
    private String            workLevel;

    /**
     * 1巡检，2预警，3重保，4应急，5清查,6基准配置，7基准验证，8准入实施，9其他
     */
    @ApiModelProperty("1巡检，2预警，3重保，4应急，5清查,6基准配置，7基准验证，8准入实施，9其他")
    private String            orderType;

    /**
     * 1资产管理，2配置管理，3漏洞管理，4.补丁管理，5日志管理6.告警管理，7日常安全管理，8安全设备管理，9系统管理，10其他
     */
    @ApiModelProperty("1资产管理，2配置管理，3漏洞管理，4.补丁管理，5日志管理6.告警管理，7日常安全管理，8安全设备管理，9系统管理，10其他")
    private String            orderSource;

    /**
     * 工单内容
     */
    @ApiModelProperty("工单内容")
    private String            content;

    /**
     * 执行人id
     */
    @ApiModelProperty("执行人id")
    private String            executeUserId;
    /**
     * 执行人姓名
     */
    @ApiModelProperty("执行人姓名")
    private String            executeUserName;

    /**
     * 工单开始时间
     */
    @ApiModelProperty("工单开始时间")
    /** 正则表达式范围2001-09-09 09:46:40 12:00:00 ----> 2128-06-11 16:53:19 1000000000000-4999999999999 */
    private String            startTime;

    /**
     * 工单结束时间
     */
    @ApiModelProperty("工单结束时间")
    /** 正则表达式范围2001-09-09 09:46:40 12:00:00 ----> 2128-06-11 16:53:19 1000000000000-4999999999999 */
    private String            endTime;

    /**
     * 工单附件id
     */
    @ApiModelProperty("多个工单附件id,Json数组")
    private List<Integer>     workOrderAttachments;

    /**
     * 与工单绑定的相关告警来源id
     */
    @ApiModelProperty("与工单绑定的相关告警来源id")
    private String            relatedSourceId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWorkLevel() {

        return Integer.valueOf(workLevel);
    }

    public void setWorkLevel(String workLevel) {
        this.workLevel = workLevel;
    }

    public Integer getOrderType() {
        return Integer.valueOf(orderType);
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Integer getOrderSource() {
        return Integer.valueOf(orderSource);
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getStartTime() {
        return Long.valueOf(startTime);
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return Long.valueOf(endTime);
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

    public Integer getExecuteUserId() {
        // 如果解密失败返回null处理
        return null != executeUserId ? Integer.valueOf(executeUserId) : null;
    }

    public void setExecuteUserId(String executeUserId) {
        this.executeUserId = executeUserId;
    }

    public Integer getRelatedSourceId() {
        return Integer.valueOf(relatedSourceId);
    }

    public void setRelatedSourceId(String relatedSourceId) {
        this.relatedSourceId = relatedSourceId;
    }

    public String getExecuteUserName() {
        return executeUserName;
    }

    public void setExecuteUserName(String executeUserName) {
        this.executeUserName = executeUserName;
    }

    @Override
    public void validate() throws RequestParamValidateException {
        if (null != startTime && null != endTime) {
            ParamterExceptionUtils.isTrue(getStartTime() < getEndTime() ? true : false, "任务预计开始时间不能晚于或等于任务预计结束时间");
        }
    }

    @Override
    public String toString() {
        return super.toString() + "WorkOrderVO{" + "name='" + name + '\'' + ", workLevel=" + workLevel + ", orderType="
               + orderType + ", orderSource=" + orderSource + ", content='" + content + '\'' + ", executeUserId="
               + executeUserId + ", executeUserName='" + executeUserName + '\'' + ", startTime=" + startTime
               + ", endTime=" + endTime + ", workOrderAttachments=" + workOrderAttachments + ", relatedSourceId="
               + relatedSourceId + '}';
    }
}
