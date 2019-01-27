package com.antiy.asset.vo.request;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.utils.ParamterExceptionUtils;
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
    private String  softwareId;
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
    private Integer installType;
    /**
     * 安装状态0失败、1成功，2安装中
     */
    @ApiModelProperty("安装状态")
    private Integer installStatus;
    /**
     * 安装时间
     */
    @ApiModelProperty("安装时间")
    private Integer installTime;

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

    public Integer getInstallType() {
        return installType;
    }

    public void setInstallType(Integer installType) {
        this.installType = installType;
    }

    public Integer getInstallTime() {
        return installTime;
    }

    public void setInstallTime(Integer installTime) {
        this.installTime = installTime;
    }

    @Override
    public void validate() throws RequestParamValidateException {
        if (installType == 1) {
            ParamterExceptionUtils.isNull(installTime, "人工安装，安装时间不能为空");
            ParamterExceptionUtils.isNull(installStatus, "人工安装，安装状态不能为空");
        }
    }

    @Override
    public String toString() {
        return "AssetSoftwareRelationRequest{" + "id='" + id + '\'' + ", assetId='" + assetId + '\'' + ", softwareId='"
               + softwareId + '\'' + ", softwareStatus=" + softwareStatus + ", memo='" + memo + '\'' + ", protocol='"
               + protocol + '\'' + ", licenseSecretKey='" + licenseSecretKey + '\'' + ", port='" + port + '\''
               + ", installType=" + installType + ", installStatus=" + installStatus + ", installTime=" + installTime
               + '}';
    }

    public String getLicenseSecretKey() {
        return licenseSecretKey;
    }

    public void setLicenseSecretKey(String licenseSecretKey) {
        this.licenseSecretKey = licenseSecretKey;
    }
}