package com.antiy.asset.vo.response;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Author xiaoqianyong
 * @DATE 2020/4/8 10:28
 * @Description
 */
public class AssetAreaAndIpResponse {

    @ApiModelProperty(value = "资产ip")
    private String ip;

    @ApiModelProperty(value = "资产区域id")
    private String areaId;


    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }
}
