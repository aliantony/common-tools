package com.antiy.asset.vo.request;

import com.antiy.common.base.BaseRequest;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetRunRecordRequest 请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetRunRecordRequest extends BaseRequest implements ObjectValidator {

    /**
     * 唯一键
     */
    @ApiModelProperty("唯一键")
    private Long    uniqueId;
    /**
     * 启动时间
     */
    @ApiModelProperty("启动时间")
    private Long    startTime;
    /**
     * 停止时间
     */
    @ApiModelProperty("停止时间")
    private Long    stopTime;
    /**
     * 差值
     */
    @ApiModelProperty("差值")
    private Integer differenceValue;

    public Long getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(Long uniqueId) {
        this.uniqueId = uniqueId;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getStopTime() {
        return stopTime;
    }

    public void setStopTime(Long stopTime) {
        this.stopTime = stopTime;
    }

    public Integer getDifferenceValue() {
        return differenceValue;
    }

    public void setDifferenceValue(Integer differenceValue) {
        this.differenceValue = differenceValue;
    }

    @Override
    public void validate() throws RequestParamValidateException {

    }

}