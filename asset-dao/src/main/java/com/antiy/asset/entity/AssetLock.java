package com.antiy.asset.entity;

import com.antiy.common.base.BaseEntity;

public class AssetLock extends BaseEntity {
    private Integer assetId;
    private Integer userId;

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
