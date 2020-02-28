package com.antiy.asset.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author zhangyajun
 * @create 2020-02-28 11:19
 **/
@ApiModel("统计纳入管理响应对象")
public class AssetCountIncludeResponse {
    @ApiModelProperty("纳入管理资产数")
    private Integer includeAmount;
    @ApiModelProperty("未纳入管理资产数")
    private Integer unIncludeAmount;

    public Integer getIncludeAmount() {
        return includeAmount;
    }

    public void setIncludeAmount(Integer includeAmount) {
        this.includeAmount = includeAmount;
    }

    public Integer getUnIncludeAmount() {
        return unIncludeAmount;
    }

    public void setUnIncludeAmount(Integer unIncludeAmount) {
        this.unIncludeAmount = unIncludeAmount;
    }
}
