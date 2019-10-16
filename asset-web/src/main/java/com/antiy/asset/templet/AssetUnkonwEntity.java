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
    private String assetSource;
    /**
     * 首次发现时间
     */
    @ApiModelProperty("首次发现时间")

    private String gmtCreate;

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

    public String getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getAssetSource() {
        return assetSource;
    }

    public void setAssetSource(String assetSource) {
        this.assetSource = assetSource;
    }
}
