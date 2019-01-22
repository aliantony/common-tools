package com.antiy.asset.vo.request;

import javax.validation.constraints.NotNull;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetSoftwareRelationRequest 请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */
public class AssetSoftwareRelationRequest extends BasicRequest implements ObjectValidator {

    @ApiModelProperty(value = "主键")
    @Encode
    private String  id;
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
    private Integer softwareId;
    /**
     * 软件资产状态：1待登记2待分析3可安装4已退役5不予登记
     */
    @ApiModelProperty("软件资产状态：1待登记2待分析3可安装4已退役5不予登记")
    private Integer softwareStatus;
    /**
     * 端口描述
     */
    @ApiModelProperty("端口描述")
    private String  memo;
    /**
     * 协议
     */
    @ApiModelProperty("协议")
    private String  protocol;
    /**
     * 许可密钥
     */
    @ApiModelProperty("许可密钥")
    private String  licenseSecretKey;
    /**
     * 端口
     */
    @ApiModelProperty("端口")
    private String  port;
    /**
     * 安装方式1人工2自动
     */
    @ApiModelProperty("安装方式")
    @NotNull
    private Integer installType;
    /**
     * 安装状态0失败、1成功，2安装中
     */
    @ApiModelProperty("安装方式")
    @NotNull
    private Integer installStatus;

    public String getId() {
        return id;
    }

    public Integer getInstallStatus() {
        return installStatus;
    }

    public void setInstallStatus(Integer installStatus) {
        this.installStatus = installStatus;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public Integer getSoftwareId() {
        return softwareId;
    }

    public void setSoftwareId(Integer softwareId) {
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

    public Integer getInstallType() {
        return installType;
    }

    public void setInstallType(Integer installType) {
        this.installType = installType;
    }

    @Override
    public void validate() throws RequestParamValidateException {

    }

    public String getLicenseSecretKey() {
        return licenseSecretKey;
    }

    public void setLicenseSecretKey(String licenseSecretKey) {
        this.licenseSecretKey = licenseSecretKey;
    }
}