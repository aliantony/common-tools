package com.antiy.asset.vo.request;

import com.antiy.common.base.QueryCondition;
import com.antiy.common.encoder.Encode;

public class AssetLockRequest extends QueryCondition {
    /**
     * 1 变更
     *
     *2  登记
     * 3  不予登记
     * 4 拟退役
     */
    Integer operation;
    @Encode
    private String assetId;
    private Integer userId;

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
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
