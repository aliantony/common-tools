package com.antiy.asset.vo.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetSoftwareLicenseResponse 响应对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */
public class AssetSoftwareLicenseResponse {
    private int     id;

    /**
     * 许可名称
     */
    @ApiModelProperty("许可名称")
    private String  name;
    /**
     * 厂商名称
     */
    @ApiModelProperty("厂商名称")
    private String  manufacturer;
    /**
     * 软件主键
     */
    @ApiModelProperty("软件主键")
    private Integer softwareId;
    /**
     * 购买日期
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("购买日期")
    private Long    buyDate;
    /**
     * 有效期限
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("有效期限")
    private Long    expiryDate;
    /**
     * 许可密钥
     */
    @ApiModelProperty("许可密钥")
    private String  licenseSecretKey;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String  memo;

    // 软件信息
    /**
     * 软件名称
     */
    @ApiModelProperty("软件名称")
    private String  softwareName;

    /**
     * 许可有效期还剩多少天
     */
    @ApiModelProperty("许可有效期还剩多少天")
    private Integer remainDay;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public Integer getSoftwareId() {
        return softwareId;
    }

    public void setSoftwareId(Integer softwareId) {
        this.softwareId = softwareId;
    }

    public Long getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Long buyDate) {
        this.buyDate = buyDate;
    }

    public Long getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Long expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getLicenseSecretKey() {
        return licenseSecretKey;
    }

    public void setLicenseSecretKey(String licenseSecretKey) {
        this.licenseSecretKey = licenseSecretKey;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getSoftwareName() {
        return softwareName;
    }

    public void setSoftwareName(String softwareName) {
        this.softwareName = softwareName;
    }

    public Integer getRemainDay() {
        return remainDay;
    }

    public void setRemainDay(Integer remainDay) {
        this.remainDay = remainDay;
    }
}