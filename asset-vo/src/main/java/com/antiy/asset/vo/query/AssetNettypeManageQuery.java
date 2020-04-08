package com.antiy.asset.vo.query;

import com.antiy.common.base.ObjectQuery;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * AssetNettypeManage 查询条件
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetNettypeManageQuery extends ObjectQuery {

    @ApiModelProperty("网络类型名称")
    private String netTypeName;

    public String getNetTypeName() {
        return netTypeName;
    }

    public void setNetTypeName(String netTypeName) {
        this.netTypeName = netTypeName;
    }
}