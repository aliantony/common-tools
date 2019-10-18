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
    private String primaryKey;

    @Override
    public String getPrimaryKey() {
        return primaryKey;
    }

    @Override
    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }
}