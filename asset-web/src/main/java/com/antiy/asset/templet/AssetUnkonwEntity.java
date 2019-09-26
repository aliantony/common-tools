package com.antiy.asset.templet;

import io.swagger.annotations.ApiModelProperty;

public class AssetUnkonwEntity {

    /**
     * ip
     */
    @ApiModelProperty("ip")
    private String ips;
    /**
     * mac
     */
    @ApiModelProperty("mac")
    private String macs;

    @ApiModelProperty("上报方式")
    private String assetSourceName;

    /**
     * 首次发现时间
     */
    @ApiModelProperty("首次发现时间")
    private Long   gmtCreate;

    public String getIps() {
        return ips;
    }

    public void setIps(String ips) {
        this.ips = ips;
    }

    public String getMacs() {
        return macs;
    }

    public void setMacs(String macs) {
        this.macs = macs;
    }

    public String getAssetSourceName() {
        return assetSourceName;
    }

    public void setAssetSourceName(String assetSourceName) {
        this.assetSourceName = assetSourceName;
    }

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}
