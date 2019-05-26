package com.antiy.asset.vo.request;

import com.antiy.asset.vo.response.CategoryType;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.encoder.Encode;

public class UseableIpRequest extends BaseRequest {
    /**
     * 资产id
     */
    @Encode
    private String       assetId;
    /**
     * 设备类型
     */
    private CategoryType categoryType;

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public CategoryType getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(CategoryType categoryType) {
        this.categoryType = categoryType;
    }
}
