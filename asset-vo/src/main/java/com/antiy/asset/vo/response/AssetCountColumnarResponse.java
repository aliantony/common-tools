package com.antiy.asset.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "资产柱状图统计数据")
public class AssetCountColumnarResponse {

    @ApiModelProperty("柱状图的key")
    private String[] keys;

    @ApiModelProperty("柱状图的数据")
    private Long[]   values;

    public String[] getKeys() {
        return keys;
    }

    public void setKeys(String[] keys) {
        this.keys = keys;
    }

    public Long[] getValues() {
        return values;
    }

    public void setValues(Long[] values) {
        this.values = values;
    }
}
