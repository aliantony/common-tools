package com.antiy.asset.vo.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

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
    @ApiModelProperty("移除的资产id集合，未加密")
    private List<String> removedAssetIds;
    @ApiModelProperty("添加或者删除后的所有资产id集合，未加密")
    private List<String> assetIds;
    @ApiModelProperty("厂商名称")
    List<String> suppliers;
    @ApiModelProperty("资产组名称")
    List<String> groupName;
    @ApiModelProperty("重要程度")
    List<String> importanceDegree;

    public List<String> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<String> suppliers) {
        this.suppliers = suppliers;
    }

    public List<String> getGroupName() {
        return groupName;
    }

    public void setGroupName(List<String> groupName) {
        this.groupName = groupName;
    }

    public List<String> getImportanceDegree() {
        return importanceDegree;
    }

    public void setImportanceDegree(List<String> importanceDegree) {
        this.importanceDegree = importanceDegree;
    }

    public List<String> getAssetIds() {
        return assetIds;
    }

    public void setAssetIds(List<String> assetIds) {
        this.assetIds = assetIds;
    }

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

    public List<String> getRemovedAssetIds() {
        return removedAssetIds;
    }

    public void setRemovedAssetIds(List<String> removedAssetIds) {
        this.removedAssetIds = removedAssetIds;
    }
}