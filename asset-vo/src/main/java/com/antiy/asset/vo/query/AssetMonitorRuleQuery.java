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
    @ApiModelProperty("是否关联资产数排序")
    private boolean relatedAssetSort;
    @ApiModelProperty("唯一键")
    private String uniqueId;

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public Boolean getRelatedAssetSort() {
        return relatedAssetSort;
    }

    public void setRelatedAssetSort(Boolean relatedAssetSort) {
        this.relatedAssetSort = relatedAssetSort;
    }

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