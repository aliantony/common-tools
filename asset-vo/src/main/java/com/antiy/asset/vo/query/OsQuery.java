package com.antiy.asset.vo.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author zhangyajun
 * @create 2019-08-01 14:10
 **/
@ApiModel("适用系统/品类型号查询")
public class OsQuery {
    @ApiModelProperty("软件名称")
    private String productName;

    @ApiModelProperty(value = "类型", hidden = true)
    private String type;

    @ApiModelProperty(value = "类型", hidden = true)
    private Boolean isfilter;

    public Boolean getIsfilter() {
        return isfilter;
    }

    public void setIsfilter(Boolean isfilter) {
        this.isfilter = isfilter;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
