package com.antiy.asset.vo.response;

import com.antiy.common.encoder.Encode;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author: zhangbing
 * @date: 2019/5/15 10:46
 * @description: 告警资产响应类
 */
public class AlarmAssetResponse {
    @ApiModelProperty(value = "资产IP地址")
    private String ip;

    @ApiModelProperty(value = "资产主键")
    @Encode(message = "资产主键加密失败")
    private String assetId;
    @ApiModelProperty(value = "资产编号")
    private String assetNumber;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getAssetNumber() {
        return assetNumber;
    }

    public void setAssetNumber(String assetNumber) {
        this.assetNumber = assetNumber;
    }
}
