package com.antiy.asset.vo.request;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Author chenhuasheng
 * @description
 * @create 2019/5/23 10:21
 */
public class UniformChangeInfoRequest {
    @ApiModelProperty("业务ID")
    private Integer businessId;

    @ApiModelProperty("品类型号ID")
    private Integer categoryModelId;

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public Integer getCategoryModelId() {
        return categoryModelId;
    }

    public void setCategoryModelId(Integer categoryModelId) {
        this.categoryModelId = categoryModelId;
    }
}
