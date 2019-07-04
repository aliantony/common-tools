package com.antiy.asset.entity;


import io.swagger.annotations.ApiModelProperty;

/**
 * 关联软件信息
 */
public class RelateSoftware {
    private String softwareId;
    @ApiModelProperty("名称")
    private String softName;
    @ApiModelProperty("许可秘钥")
    private String licenseSecretKey;
    @ApiModelProperty("端口")
    private String mulPort;
    @ApiModelProperty("描述")
    private String description;

    public String getSoftwareId() {
        return softwareId;
    }

    public void setSoftwareId(String softwareId) {
        this.softwareId = softwareId;
    }

    public String getSoftName() {
        return softName;
    }

    public void setSoftName(String softName) {
        this.softName = softName;
    }

    public String getLicenseSecretKey() {
        return licenseSecretKey;
    }

    public void setLicenseSecretKey(String licenseSecretKey) {
        this.licenseSecretKey = licenseSecretKey;
    }

    public String getMulPort() {
        return mulPort;
    }

    public void setMulPort(String mulPort) {
        this.mulPort = mulPort;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
