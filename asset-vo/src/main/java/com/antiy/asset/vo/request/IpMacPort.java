package com.antiy.asset.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author zhangyajun
 * @create 2019-10-10 17:23
 **/
@ApiModel
public class IpMacPort {
    @ApiModelProperty("IP")
    private String ip;
    @ApiModelProperty("MAC")
    private String mac;
    @ApiModelProperty("端口")
    private List<Integer> portList;

    public List<Integer> getPortList() {
        return portList;
    }

    public void setPortList(List<Integer> portList) {
        this.portList = portList;
    }

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
