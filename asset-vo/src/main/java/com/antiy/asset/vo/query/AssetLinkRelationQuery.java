package com.antiy.asset.vo.query;

import com.antiy.common.base.ObjectQuery;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetLinkRelation 查询条件 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetLinkRelationQuery extends ObjectQuery {
    /**
     * 资产id
     */
    @ApiModelProperty("资产id")
    private String assetId;
    /**
     * 关联资产id
     */
    @ApiModelProperty("关联资产id")
    private String parentAssetId;
    /**
     * 资产品类型号
     */
    @ApiModelProperty("资产品类型号")
    private String categoryModel;
    /**
     * 资产综合查询
     */
    @ApiModelProperty("资产综合查询")
    private String multipleQuery;
    /**
     * 关联资产综合查询
     */
    @ApiModelProperty("关联资产综合查询")
    private String parentMultipleQuery;

    public String getCategoryModel() {
        return categoryModel;
    }

    public void setCategoryModel(String categoryModel) {
        this.categoryModel = categoryModel;
    }

    public String getMultipleQuery() {
        return multipleQuery;
    }

    public void setMultipleQuery(String multipleQuery) {
        this.multipleQuery = multipleQuery;
    }

    public String getParentMultipleQuery() {
        return parentMultipleQuery;
    }

    public void setParentMultipleQuery(String parentMultipleQuery) {
        this.parentMultipleQuery = parentMultipleQuery;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getParentAssetId() {
        return parentAssetId;
    }

    public void setParentAssetId(String parentAssetId) {
        this.parentAssetId = parentAssetId;
    }
}