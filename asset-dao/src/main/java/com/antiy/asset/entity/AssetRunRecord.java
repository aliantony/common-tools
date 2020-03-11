package com.antiy.asset.entity;

import com.antiy.common.base.BaseEntity;

/**
 * <p>系统运行记录表</p>
 *
 * @author zhangyajun
 * @since 2020-03-10
 */

public class AssetRunRecord extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一键
     */
    private String            uniqueId;
    /**
     * 启动时间
     */
    private Long              startTime;
    /**
     * 停止时间
     */
    private Long              stopTime;
    /**
     * 差值
     */
    private Long              differenceValue;

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
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

    public Long getDifferenceValue() {
        return differenceValue;
    }

    public void setDifferenceValue(Long differenceValue) {
        this.differenceValue = differenceValue;
    }

    @Override
    public String toString() {
        return "AssetRunRecord{" + "uniqueId=" + uniqueId + ", startTime=" + startTime + ", stopTime=" + stopTime
               + ", differenceValue=" + differenceValue + '}';
    }
}