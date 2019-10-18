package com.antiy.asset.vo.query;

import com.antiy.common.base.ObjectQuery;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetAssemblyLib 查询条件 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetAssemblyLibQuery extends ObjectQuery {
    private String businessId;

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }
}