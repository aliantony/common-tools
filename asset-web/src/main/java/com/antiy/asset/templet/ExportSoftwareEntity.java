package com.antiy.asset.templet;

import com.antiy.asset.annotation.ExcelField;
import io.swagger.annotations.ApiModelProperty;

public class ExportSoftwareEntity {

    /**
     * 软件名称
     */
    @ApiModelProperty("软件名称")
    private String  name;

    /**
     * 软件厂商
     */
    @ApiModelProperty("软件厂商")
    private String  manufacturer;

    /**
     * 软件版本
     */
    @ApiModelProperty("软件版本")
    private String  version;

    @ApiModelProperty("关联设备数")
    private Integer assetCount;

    @ApiModelProperty("状态")
    private String  status;

    /**
     * 上传时间
     */
    @ApiModelProperty("登记时间")
    private String  gmtCreate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getAssetCount() {
        return assetCount;
    }

    public void setAssetCount(Integer assetCount) {
        this.assetCount = assetCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    @Override
    public String toString() {
        return "ExportSoftwareEntity{" + "name='" + name + '\'' + ", manufacturer='" + manufacturer + '\''
               + ", version='" + version + '\'' + ", assetCount=" + assetCount + ", status='" + status + '\''
               + ", gmtCreate='" + gmtCreate + '\'' + '}';
    }
}
