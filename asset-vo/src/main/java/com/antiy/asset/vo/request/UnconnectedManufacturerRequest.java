package com.antiy.asset.vo.request;

import com.antiy.common.encoder.Encode;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author chenhuasheng
 * @description
 * @create 2019/5/23 10:47
 */
public class UnconnectedManufacturerRequest {
    @ApiModelProperty("是否是网络设备")
    private Integer isNet;
    @ApiModelProperty("主键")
    @Encode
    private String  primaryKey;

    public Integer getIsNet() {
        return isNet;
    }

    public void setIsNet(Integer isNet) {
        this.isNet = isNet;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }
}
