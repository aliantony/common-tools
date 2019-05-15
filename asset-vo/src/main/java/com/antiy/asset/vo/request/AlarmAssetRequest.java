package com.antiy.asset.vo.request;

import javax.validation.constraints.NotBlank;

import com.antiy.common.base.ObjectQuery;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author: zhangbing
 * @date: 2019/5/15 10:43
 * @description: 告警资产查询条件
 */
public class AlarmAssetRequest extends ObjectQuery {

    @ApiModelProperty(value = "IP地址", required = true)
    @NotBlank(message = "IP地址不能为空")
    private String ip;

    @ApiModelProperty(value = "MAC 地址", required = true)
    @NotBlank(message = "MAC地址不能为空")
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
