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
    @ApiModelProperty("区域")
    private String  areaName;
    @ApiModelProperty("告警等级")
    private String  alarmLevel;
    @ApiModelProperty("告警等级")
    private String  alarmLevelName;
    @ApiModelProperty("关联资产数")
    private Integer relatedAssetAmount;
    @ApiModelProperty("状态")
    private String  status;
    @ApiModelProperty("状态")
    private String  statusName;

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

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}