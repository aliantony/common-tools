package com.antiy.asset.vo.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetMonitorRule 查询条件 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

@ApiModel("综合查询")
public class AssetMonitorRuleQuery extends AssetBaseQuery {
    @ApiModelProperty("规则名称")
    private String name;
    @ApiModelProperty("告警等级")
    private String alarmLevel;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlarmLevel() {
        return alarmLevel;
    }

    public void setAlarmLevel(String alarmLevel) {
        this.alarmLevel = alarmLevel;
    }
}