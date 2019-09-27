package com.antiy.asset.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @auther: zhangbing
 * @date: 2019/1/12 10:55
 * @description: 统一下拉框格式返回
 */
@ApiModel(value = "下拉框格式返回")
public class SelectResponse {

    @ApiModelProperty(value = "返回Id")
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
