package com.antiy.asset.vo.request;

import com.antiy.asset.vo.request.AssetPortProtocolRequest;
import com.antiy.asset.vo.request.AssetSoftwareLicenseRequest;
import com.antiy.common.base.BasicRequest;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Arrays;

/**
 * <p> AssetSoftwareRequest 请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */
@ApiModel(value = "软件请求")
public class AssetSoftwareReportRequest extends BasicRequest implements ObjectValidator {
    /**
     * 安装时间
     */
    @ApiModelProperty("安装时间")
    private Long installTime;
    /**
     * 购买日期
     */
    @ApiModelProperty("购买日期")
    private Long    buyDate;
    /**
     * protocol
     */
    @ApiModelProperty("协议")
    private String  protocol;

    /**
     * MD5/SHA
     */
    @ApiModelProperty("MD5/SHA")
    @Size(message = "MD5/SHA不超过64位", max = 64)
    private String  md5Code;
    /**
     * 软件大小(KB)
     */
    @ApiModelProperty(value = "软件大小")
    private Integer size;
    /**
     * 操作系统(WINDOWS7-32-64,WINDOWS8-64)
     */
    @ApiModelProperty(value = "操作系统")
    @Size(message = "操作系统不能超过32位", max = 32)
    @NotBlank(message = "操作系统不能为空")
    private String  operationSystem;
    /**
     * 软件品类
     */
    @ApiModelProperty(value = "软件品类")
    @Encode
    @NotBlank(message = "软件品类不能为空")
    private String  categoryModel;
    /**
     * 软件名称
     */
    @NotBlank(message = "软件名称不能为空")
    @Size(message = "软件名称字段长度不能超过20位", max = 20)
    @ApiModelProperty(value = "软件名称")
    private String  name;

    /**
     * 版本
     */
    @ApiModelProperty(value = "软件版本")
    @NotBlank(message = "软件版本不能为空")
    private String  version;
    /**
     * 厂商
     */
    @ApiModelProperty(value = "厂商")
    @Size(message = "资产厂商不能超过32位", max = 32)
    private String  manufacturer;
    /**
     * 软件描述
     */
    @ApiModelProperty(value = "软件描述")
    @Size(message = "描述不能超过128位", max = 128)
    private String  description;

    /**
     * 序列号
     */
    @ApiModelProperty(value = "序列号")
    @Size(message = "资产序列号不能超过32位", max = 32)
    private String  serial;

    /**
     * 到期时间
     */
    @ApiModelProperty(value = "到期时间")
    private Long    serviceLife;

    /**
     * 软件状态：1待登记2待分析3可安装4已退役5不予登记
     */
    @ApiModelProperty(value = "软件状态：1待登记2待分析3可安装4已退役5不予登记")
    private Integer softwareStatus;
    /**
     * 0-免费软件，1-商业软件
     */
    @ApiModelProperty(value = "软件是否免费")
    private Integer authorization;

    /**
     * 语言
     */
    @ApiModelProperty(value = "语言")
    private String  language;
    /**
     * 发布时间
     */
    @ApiModelProperty(value = "发布时间")
    private Long    releaseTime;

    /**
     * 发布者
     */
    @ApiModelProperty(value = "发布者")
    private String  publisher;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String  memo;

    @ApiModelProperty(value = "硬件资产和软件资产关联表Id")
    @Encode
    private String  assetSoftwareRelationId;

    public Long getInstallTime() {
        return installTime;
    }

    public void setInstallTime(Long installTime) {
        this.installTime = installTime;
    }

    public Long getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Long buyDate) {
        this.buyDate = buyDate;
    }

    public String getAssetSoftwareRelationId() {
        return assetSoftwareRelationId;
    }

    public void setAssetSoftwareRelationId(String assetSoftwareRelationId) {
        this.assetSoftwareRelationId = assetSoftwareRelationId;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getOperationSystem() {
        return operationSystem;
    }

    public void setOperationSystem(String operationSystem) {
        this.operationSystem = operationSystem;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSoftwareStatus() {
        return softwareStatus;
    }

    public void setSoftwareStatus(Integer softwareStatus) {
        this.softwareStatus = softwareStatus;
    }

    public Integer getAuthorization() {
        return authorization;
    }

    public void setAuthorization(Integer authorization) {
        this.authorization = authorization;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Long getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Long releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getCategoryModel() {
        return categoryModel;
    }

    public void setCategoryModel(String categoryModel) {
        this.categoryModel = categoryModel;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public void validate() throws RequestParamValidateException {
    }

    public String getMd5Code() {
        return md5Code;
    }

    public void setMd5Code(String md5Code) {
        this.md5Code = md5Code;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    @Override
    public String toString() {
        return "AssetSoftwareRequest{" + "buyDate=" + buyDate + ", protocol='" + protocol + '\'' + ", md5Code='"
                + md5Code + '\'' + ", size=" + size + ", operationSystem='" + operationSystem + '\''
                + ", categoryModel='" + categoryModel + '\'' + ", name='" + name + '\'' + ", version='" + version + '\''
                + ", manufacturer='" + manufacturer + '\'' + ", description='" + description + '\'' + ", serial='"
                + serial + '\'' + ", serviceLife=" + serviceLife + ", softwareStatus=" + softwareStatus
                + ", authorization=" + authorization + ", language='" + language + '\'' + ", releaseTime=" + releaseTime
                + ", publisher='" + publisher + '\'' + ", memo='" + memo + '\'' + ", assetSoftwareRelationId='"
                + assetSoftwareRelationId + '\'' + '}';
    }

    public Long getServiceLife() {
        return serviceLife;
    }

    public void setServiceLife(Long serviceLife) {
        this.serviceLife = serviceLife;
    }
}