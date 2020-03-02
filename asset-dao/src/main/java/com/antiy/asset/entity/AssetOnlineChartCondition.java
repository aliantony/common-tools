package com.antiy.asset.entity;

import com.antiy.asset.base.AreaBase;

/**
 * @author zhangyajun
 * @create 2020-03-02 12:39
 **/
public class AssetOnlineChartCondition extends AreaBase {
    private Long startTime;
    private Long endTime;

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
}
