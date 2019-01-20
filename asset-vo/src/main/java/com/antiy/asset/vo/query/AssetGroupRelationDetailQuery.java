package com.antiy.asset.vo.query;

import com.antiy.common.base.ObjectQuery;

/**
 * <p> AssetGroupRelationDetailQuery 查询条件 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetGroupRelationDetailQuery extends ObjectQuery {

    /**
     * 资产组ID
     */
    private Integer assetGroupId;

    public Integer getAssetGroupId() {
        return assetGroupId;
    }

    public void setAssetGroupId(Integer assetGroupId) {
        this.assetGroupId = assetGroupId;
    }
}