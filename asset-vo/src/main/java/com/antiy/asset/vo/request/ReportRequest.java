package com.antiy.asset.vo.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.utils.ParamterExceptionUtils;
import com.antiy.common.validation.ObjectValidator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author: zhangbing
 * @date: 2019/3/25 15:47
 * @description: 报表统计查询sql
 */
@ApiModel(value = "报表统计查询条件")
public class ReportRequest extends BasicRequest implements ObjectValidator {

    /**
     * 资产状态
     */
    @ApiModelProperty(value = "资产状态")
    private Integer assetStatus;

    /**
     * 区域Id
     */
    @ApiModelProperty(value = "区域Id")
    private String  areaId;

    @ApiModelProperty(value = "开始时间(时间戳)", required = true)
    @NotNull(message = "开始时间不能为空")
    private Long    startTime;

    @ApiModelProperty(value = "结束时间(时间戳)", required = true)
    @NotNull(message = "结束时间不能为空")
    private Long    endTime;

    @ApiModelProperty(value = "时间类型,1-本周,2-本月,3-本季度,4-本年,5-时间范围", required = true)
    @NotBlank(message = "时间类型不能为空")
    private String  timeType;

    public Integer getAssetStatus() {
        return assetStatus;
    }

    public void setAssetStatus(Integer assetStatus) {
        this.assetStatus = assetStatus;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
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

    public String getTimeType() {
        return timeType;
    }

    public void setTimeType(String timeType) {
        this.timeType = timeType;
    }

    @Override
    public void validate() throws RequestParamValidateException {
        ParamterExceptionUtils.isTrue(endTime > startTime, "开始时间必须小于结束时间");
    }
}
