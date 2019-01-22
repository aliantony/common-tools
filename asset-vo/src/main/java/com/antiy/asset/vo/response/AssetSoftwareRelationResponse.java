package com.antiy.asset.vo.response;

import com.antiy.common.encoder.Encode;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetSoftwareRelationResponse 响应对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */
public class AssetSoftwareRelationResponse extends BaseResponse {

    /**
     * 资产主键
     */
    @ApiModelProperty("资产主键")
    @Encode
    private String  assetId;
    /**
     * 软件主键
     */
    @ApiModelProperty("软件主键")
    @Encode
    private String  softwareId;
    /**
     * 软件资产状态：1待登记2待分析3可安装4已退役5不予登记
     */
    @ApiModelProperty("软件资产状态：1待登记2待分析3可安装4已退役5不予登记")
    private Integer softwareStatus;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String  memo;
    /**
     * 协议
     */
    @ApiModelProperty("协议")
    private String  protocol;
    /**
     * 端口
     */
    @ApiModelProperty("端口")
    private String  port;
    /**
     * 许可密钥
     */
    @ApiModelProperty("许可密钥")
    private String  licenseSecretKey;
    /**
     * 安装方式
     */
    @ApiModelProperty("安装方式")
    private String  installType;
    /**
     * 安装状态0失败、1成功，2安装中
     */
    @ApiModelProperty("安装方式")
    private Integer installStatus;
    /**
     * 安装时间
     */
    @ApiModelProperty("安装时间")
    private Integer installTime;

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getSoftwareId() {
        return softwareId;
    }

    public void setSoftwareId(String softwareId) {
        this.softwareId = softwareId;
    }

    public Integer getSoftwareStatus() {
        return softwareStatus;
    }

    public void setSoftwareStatus(Integer softwareStatus) {
        this.softwareStatus = softwareStatus;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getInstallType() {
        return installType;
    }

    public void setInstallType(String installType) {
        this.installType = installType;
    }

    public String getLicenseSecretKey() {
        return licenseSecretKey;
    }

    public void setLicenseSecretKey(String licenseSecretKey) {
        this.licenseSecretKey = licenseSecretKey;
    }

    public Integer getInstallStatus() {
        return installStatus;
    }

    public void setInstallStatus(Integer installStatus) {
        this.installStatus = installStatus;
    }

    public Integer getInstallTime() {
        return installTime;
    }

    public void setInstallTime(Integer installTime) {
        this.installTime = installTime;
    }
}