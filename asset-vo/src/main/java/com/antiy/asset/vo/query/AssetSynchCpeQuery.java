package com.antiy.asset.vo.query;

import javax.validation.constraints.NotNull;

import com.antiy.common.base.ObjectQuery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * cpe信息查询条件
 * @author zhouye
 */
@ApiModel
public class AssetSynchCpeQuery extends ObjectQuery {
    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间")
    private Long startStamp;
    /**
     * 结束时间
     */
    @NotNull(message = "结束时间不以为空")
    @ApiModelProperty(value = "结束时间")
    private Long endStamp;

    public Long getStartStamp() {
        return startStamp;
    }

    public void setStartStamp(Long startStamp) {
        this.startStamp = startStamp;
    }

    public Long getEndStamp() {
        return endStamp;
    }

    public void setEndStamp(Long endStamp) {
        this.endStamp = endStamp;
    }
}
