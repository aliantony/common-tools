package com.antiy.asset.vo.query;

import com.antiy.common.base.ObjectQuery;
import com.antiy.common.encoder.Encode;

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
    @Encode
    private String assetGroupId;

    public String getAssetGroupId() {
        return assetGroupId;
    }

    public void setAssetGroupId(String assetGroupId) {
        this.assetGroupId = assetGroupId;
    }

    @Override
    public String toString() {
        return "AssetGroupRelationDetailQuery{" +
                "assetGroupId='" + assetGroupId + '\'' +
                '}';
    }
}