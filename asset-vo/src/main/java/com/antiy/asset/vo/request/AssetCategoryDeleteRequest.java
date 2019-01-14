package com.antiy.asset.vo.request;

import com.antiy.common.base.QueryCondition;

public class AssetCategoryDeleteRequest extends QueryCondition {
    private Boolean confrim;

    public Boolean getConfrim() {
        return confrim;
    }

    public void setConfrim(Boolean confrim) {
        this.confrim = confrim;
    }
}
