package com.antiy.asset.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "四库下拉框格式返回")
public class BusinessSelectResponse {
    @ApiModelProperty(value = "返回BusinessId")
    private String id;

    @ApiModelProperty(value = "返回的value值")
    private String value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
