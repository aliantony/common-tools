package com.antiy.asset.vo.query;

import com.antiy.common.base.ObjectQuery;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetCompositionReport 查询条件 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetCompositionReportQuery extends ObjectQuery {
    @ApiModelProperty("name")
    private String  name;
    @ApiModelProperty("status")
    private Integer status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}