package com.antiy.asset.vo.query;

import com.antiy.common.encoder.Encode;
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
    List<String> supplier;
    @ApiModelProperty("资产组id 加密")
    @Encode
    List<String> assetGroup;
    @ApiModelProperty("重要程度")
    List<String> importance;
    @ApiModelProperty(value = "资产类型ids",hidden = true)
    List<String> categoryIds;

    public List<String> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<String> categoryIds) {
        this.categoryIds = categoryIds;
    }

    public List<String> getSupplier() {
        return supplier;
    }

    public void setSupplier(List<String> supplier) {
        this.supplier = supplier;
    }

    public List<String> getAssetGroup() {
        return assetGroup;
    }

    public void setAssetGroup(List<String> assetGroup) {
        this.assetGroup = assetGroup;
    }

    public List<String> getImportance() {
        return importance;
    }

    public void setImportance(List<String> importance) {
        this.importance = importance;
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