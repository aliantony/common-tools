package com.antiy.asset.vo.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetMonitorRuleRelation 查询条件 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */
@ApiModel(description = "监控规则关联的资产查询条件")
public class AssetMonitorRuleRelationQuery extends AssetBaseQuery {
    @ApiModelProperty("综合查询条件")
    private String multipleQuery;
    @ApiModelProperty("监控规则唯一键，未加密")
    private String ruleUniqueId;
    @ApiModelProperty(hidden = true)
    private Boolean relatedAssetSort;

    public Boolean getRelatedAssetSort() {
        return relatedAssetSort;
    }

    public void setRelatedAssetSort(Boolean relatedAssetSort) {
        this.relatedAssetSort = relatedAssetSort;
    }

    public String getRuleUniqueId() {
        return ruleUniqueId;
    }

    public void setRuleUniqueId(String ruleUniqueId) {
        this.ruleUniqueId = ruleUniqueId;
    }

    public String getMultipleQuery() {
        return multipleQuery;
    }

    public void setMultipleQuery(String multipleQuery) {
        this.multipleQuery = multipleQuery;
    }
}