package com.antiy.asset.vo.request;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Author chenhuasheng
 * @description
 * @create 2019/5/23 10:08
 */
public class CategoryNodeRequest {
    @ApiModelProperty("1--软件 2--硬件")
    private Integer type;
    @ApiModelProperty("是否是网络设备")
    private Integer isNet;
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getIsNet() {
        return isNet;
    }

    public void setIsNet(Integer isNet) {
        this.isNet = isNet;
    }
}
