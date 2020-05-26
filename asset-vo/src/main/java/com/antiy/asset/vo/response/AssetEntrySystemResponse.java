package com.antiy.asset.vo.response;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author liulusheng
 * @since 2020/5/26
 */
public class AssetEntrySystemResponse {
    @ApiModelProperty("mac地址")
    private String mac;
    @ApiModelProperty("准入状态：1允许2禁止")
    private Integer admittanceStatus;

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public Integer getAdmittanceStatus() {
        return admittanceStatus;
    }

    public void setAdmittanceStatus(Integer admittanceStatus) {
        this.admittanceStatus = admittanceStatus;
    }

    @Override
    public String toString() {
        return "AssetEntrySystemResponse{" +
                "mac='" + mac + '\'' +
                ", admittanceStatus=" + admittanceStatus +
                '}';
    }
}
