package com.antiy.asset.vo.query;

import com.antiy.common.base.ObjectQuery;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * <p> AssetAssembly 查询条件 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetAssemblyQuery extends ObjectQuery {

    @ApiModelProperty("厂商")
    private String            supplier;

    @ApiModelProperty("名称")
    private String            productName;

    @ApiModelProperty("排除的组件id集合")
    private List<String> excludeAssemblyIds;

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public List<String> getExcludeAssemblyIds() {
        return excludeAssemblyIds;
    }

    public void setExcludeAssemblyIds(List<String> excludeAssemblyIds) {
        this.excludeAssemblyIds = excludeAssemblyIds;
    }
}