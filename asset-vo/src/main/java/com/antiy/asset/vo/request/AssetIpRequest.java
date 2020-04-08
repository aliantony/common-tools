package com.antiy.asset.vo.request;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;

/**
 * @Author xiaoqianyong
 * @DATE 2020/4/8 10:13
 * @Description
 */
public class AssetIpRequest {

    @ApiModelProperty(value = "用户选择区域id", required = true)
    @NotEmpty(message = "用户选择区域的id不能为空")
    private String areaId;

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }
}
