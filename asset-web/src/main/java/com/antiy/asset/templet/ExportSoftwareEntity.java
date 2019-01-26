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
     * 软件编号
     */
    @ApiModelProperty("软件编号")
    private Integer id;

    /**
     * 软件厂商
     */
    @ApiModelProperty("软件厂商")
    private String  manufacturer;

    /**
     * 软件品类名
     */
    @ApiModelProperty("软件品类名")
    private String  categoryName;

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
     * 发布时间
     */
    @ApiModelProperty("发布时间")
    private String    releaseTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    @Override
    public String toString() {
        return "ExportSoftwareEntity{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", manufacturer='" + manufacturer + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", version='" + version + '\'' +
                ", assetCount=" + assetCount +
                ", status='" + status + '\'' +
                ", releaseTime='" + releaseTime + '\'' +
                '}';
    }
}
