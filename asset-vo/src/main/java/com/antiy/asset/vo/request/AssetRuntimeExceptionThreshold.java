package com.antiy.asset.vo.request;

import com.antiy.asset.vo.enums.TimeEnum;
import com.antiy.common.base.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p> 运行异常监控子对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */

@ApiModel
public class AssetRuntimeExceptionThreshold extends BaseEntity {

    @ApiModelProperty("运行异常监控阈值")
    private Integer  runtimeExceptionThreshold;
    @ApiModelProperty("时间单位:HOUR/DAY")
    private TimeEnum unit;

    public Integer getRuntimeExceptionThreshold() {
        return runtimeExceptionThreshold;
    }

    public void setRuntimeExceptionThreshold(Integer runtimeExceptionThreshold) {
        this.runtimeExceptionThreshold = runtimeExceptionThreshold;
    }

    public TimeEnum getUnit() {
        return unit;
    }

    public void setUnit(TimeEnum unit) {
        this.unit = unit;
    }
}