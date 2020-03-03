package com.antiy.asset.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetMonitorRuleResponse 响应对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */
@ApiModel("资产监控规则响应对象")
public class AssetMonitorRuleResponse {
    @ApiModelProperty("主键")
    private String  uniqueId;
    @ApiModelProperty("规则名称")
    private String  name;
    @ApiModelProperty("区域ID")
    private String  areaId;
    @ApiModelProperty("区域")
    private String  areaName;
    @ApiModelProperty("告警等级")
    private String  alarmLevel;
    @ApiModelProperty("告警等级")
    private String  alarmLevelName;
    @ApiModelProperty("关联资产数")
    private Integer relatedAssetAmount;
    @ApiModelProperty("状态")
    private String  ruleStatus;
    @ApiModelProperty("状态")
    private String  ruleStatusName;

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAlarmLevelName() {
        return alarmLevelName;
    }

    public void setAlarmLevelName(String alarmLevelName) {
        this.alarmLevelName = alarmLevelName;
    }

    public Integer getRelatedAssetAmount() {
        return relatedAssetAmount;
    }

    public void setRelatedAssetAmount(Integer relatedAssetAmount) {
        this.relatedAssetAmount = relatedAssetAmount;
    }

    public String getRuleStatusName() {
        return ruleStatusName;
    }

    public void setRuleStatusName(String ruleStatusName) {
        this.ruleStatusName = ruleStatusName;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAlarmLevel() {
        return alarmLevel;
    }

    public void setAlarmLevel(String alarmLevel) {
        this.alarmLevel = alarmLevel;
    }

    public String getRuleStatus() {
        return ruleStatus;
    }

    public void setRuleStatus(String ruleStatus) {
        this.ruleStatus = ruleStatus;
    }
}