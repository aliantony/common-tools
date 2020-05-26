package com.antiy.asset.vo.request;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author liulusheng
 * @since 2020/5/20
 */
public class EntrySystemRequest {
    @ApiModelProperty("token密钥")
    private String token;
    @ApiModelProperty("终端mac地址")
    private String mac;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    @Override
    public String toString() {
        return "EntrySystemAllowRequest{" +
                "token='" + token + '\'' +
                ", mac='" + mac + '\'' +
                '}';
    }
}
