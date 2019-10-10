package com.antiy.asset.vo.request;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author zhangyajun
 * @create 2019-10-10 17:23
 **/
@ApiModel
public class IpMac {
    @ApiModelProperty("IP")
    private String ip;
    @ApiModelProperty("MAC")
    private String mac;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
}
