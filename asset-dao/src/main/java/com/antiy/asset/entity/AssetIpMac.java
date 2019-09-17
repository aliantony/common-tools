package com.antiy.asset.entity;

import com.antiy.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>资产-IP-MAC表</p>
 *
 * @author zhangyajun
 * @since 2019-09-16
 */

public class AssetIpMac extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 资产主表
     */
    private Integer           assetId;
    /**
     * IP
     */
    private String            ip;
    /**
     * MAC
     */
    private String            mac;

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
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

    @Override
    public String toString() {
        return "AssetIpMac{" + ", assetId=" + assetId + ", ip=" + ip + ", mac=" + mac + "}";
    }
}