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