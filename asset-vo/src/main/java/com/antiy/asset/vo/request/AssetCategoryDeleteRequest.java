package com.antiy.asset.vo.request;

import com.antiy.common.base.QueryCondition;

public class AssetCategoryDeleteRequest extends QueryCondition {
    private Boolean confirm;

    public Boolean getConfirm() {
        return confirm;
    }

    public void setConfirm(Boolean confirm) {
        this.confirm = confirm;
    }
}
