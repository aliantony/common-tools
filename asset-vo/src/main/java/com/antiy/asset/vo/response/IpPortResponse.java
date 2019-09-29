package com.antiy.asset.vo.response;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: lvliang
 * @Date: 2019/9/29 14:02
 */
public class IpPortResponse {
    @ApiModelProperty("IP")
    private String  ip;
    @ApiModelProperty("网口")
    private Integer net;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getNet() {
        return net;
    }

    public void setNet(Integer net) {
        this.net = net;
    }
}
