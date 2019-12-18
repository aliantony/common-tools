package com.antiy.asset.vo.request;

import com.antiy.common.base.QueryCondition;

public class AssetLockRequest extends QueryCondition {
    /**
     * 1 变更
     *
     *2  登记
     * 3  不予登记
     * 4 拟退役
     */
    Integer operation;
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

    public Integer getOperation() {
        return operation;
    }

    public void setOperation(Integer operation) {
        this.operation = operation;
    }
}
