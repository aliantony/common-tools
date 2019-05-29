package com.antiy.asset.templet;

import com.antiy.asset.annotation.ExcelField;
import io.swagger.annotations.ApiModelProperty;

public class AccessExport {
    /**
     * 资产名称
     */
    @ExcelField(value = "name", align = 1, title = "名称", type = 0)
    @ApiModelProperty("名称")
    private String  name;
    /**
     * 资产组
     */
    @ExcelField(value = "assetGroup", align = 1, title = "资产组", type = 0)
    @ApiModelProperty("资产组")
    private String  assetGroup;
    /**
     * ip
     */
    @ExcelField(value = "ip", align = 1, title = "ip", type = 0)
    @ApiModelProperty("ip")
    private String  ip;
    /**
     * mac
     */
    @ExcelField(value = "mac", align = 1, title = "mac", type = 0)
    @ApiModelProperty("mac")
    private String  mac;
    /**
     * 资产准入状态
     */
    @ExcelField(value = "admittanceStatusString", align = 1, title = "状态", type = 0)
    @ApiModelProperty("状态")
    private String admittanceStatusString;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAssetGroup() {
        return assetGroup;
    }

    public void setAssetGroup(String assetGroup) {
        this.assetGroup = assetGroup;
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


    public String getAdmittanceStatusString() {
        return admittanceStatusString;
    }

    public void setAdmittanceStatusString(String admittanceStatusString) {
        this.admittanceStatusString = admittanceStatusString;
    }

    public AccessExport() {
    }
}
