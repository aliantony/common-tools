package com.antiy.asset.vo.query;

import com.antiy.common.base.ObjectQuery;

public class AlarmAssetIdQuery extends ObjectQuery {
    private String[] assetIds;

    public String[] getAssetIds() {
        return assetIds;
    }

    public void setAssetIds(String[] assetIds) {
        this.assetIds = assetIds;
    }
}
