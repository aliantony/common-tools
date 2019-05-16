package com.antiy.asset.entity;

import com.antiy.common.base.BaseEntity;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p> 软件信息表 </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */

public class AssetSoftware extends BaseEntity {

    /**
     * 软件关联资产的端口
     */
    private String  port;
    /**
     * MD5/SHA
     */
    private String  md5Code;

    private String  checkType;
    /**
     * 软件大小(KB)
     */
    private Long    size;
    /**
     * 操作系统(WINDOWS7-32-64,WINDOWS8-64)
     */
    private String  operationSystem;
    /**
     * 软件品类
     */
    private String  categoryModel;
    /**
     * 软件品类名字
     */
    @ApiModelProperty(value = "软件品类名字")
    private String  categoryModelName;
    /**
     * 软件名称
     */
    private String  name;
    /**
     * 序列号
     */
    private String  serial;

    /**
     * 上传的软件名称
     */
    private String  uploadSoftwareName;
    /**
     * 安装包路径
     */
    private String  path;
    /**
     * 版本
     */
    private String  version;
    /**
     * 厂商
     */
    private String  manufacturer;
    /**
     * 软件描述
     */
    private String  description;
    /**
     * 资产组
     */
    private String  assetGroup;
    /**
     * 软件标签
     */
    private Integer softwareLabel;
    /**
     * 1-待登记，2-不予登记，3-待配置，4-待验证，5-待入网，6-已入网，7-待退役，8-已退役
     */
    private Integer softwareStatus;
    /**
     * 0-免费软件，1-商业软件
     */
    private Integer authorization;
    /**
     * 上报来源:1-自动上报，2-人工上报
     */
    private Integer reportSource;
    /**
     * 协议
     */
    private String  protocol;
    /**
     * 语言
     */
    private String  language;
    /**
     * 发布时间
     */
    private Long    releaseTime;
    /**
     * 发布者
     */
    private String  publisher;
    /**
     * 创建时间
     */
    private Long    gmtCreate;
    /**
     * 备注
     */
    private String  memo;
    /**
     * 更新时间
     */
    private Long    gmtModified;
    /**
     * 创建人
     */
    private Integer createUser;
    /**
     * 修改人
     */
    private Integer modifyUser;
    /**
     * 状态,0 未删除,1已删除
     */
    private Integer status;

    private Long    buyDate;
    /**
     * 到期时间
     */
    private Long    serviceLife;
    /**
     * 资产数量
     */
    private Integer assetCount;
    /**
     * 软件许可密钥
     */
    private String  licenseSecretKey;

    /**
     * 安装说明书url地址
     */
    private String  manualDocUrl;

    /**
     * 安装说明书名字
     */
    private String  manualDocName;

    public String getManualDocUrl() {
        return manualDocUrl;
    }

    public void setManualDocUrl(String manualDocUrl) {
        this.manualDocUrl = manualDocUrl;
    }

    public String getManualDocName() {
        return manualDocName;
    }

    public void setManualDocName(String manualDocName) {
        this.manualDocName = manualDocName;
    }

    public String getLicenseSecretKey() {
        return licenseSecretKey;
    }

    public void setLicenseSecretKey(String licenseSecretKey) {
        this.licenseSecretKey = licenseSecretKey;
    }

    public Integer getAssetCount() {
        return assetCount;
    }

    public void setAssetCount(Integer assetCount) {
        this.assetCount = assetCount;
    }

    public Long getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Long buyDate) {
        this.buyDate = buyDate;
    }

    public Long getServiceLife() {
        return serviceLife;
    }

    public void setServiceLife(Long serviceLife) {
        this.serviceLife = serviceLife;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getOperationSystem() {
        return operationSystem;
    }

    public void setOperationSystem(String operationSystem) {
        this.operationSystem = operationSystem;
    }

    public String getCategoryModel() {
        return categoryModel;
    }

    public void setCategoryModel(String categoryModel) {
        this.categoryModel = categoryModel;
    }

    public String getCategoryModelName() {
        return categoryModelName;
    }

    public void setCategoryModelName(String categoryModelName) {
        this.categoryModelName = categoryModelName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUploadSoftwareName() {
        return uploadSoftwareName;
    }

    public void setUploadSoftwareName(String uploadSoftwareName) {
        this.uploadSoftwareName = uploadSoftwareName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public String getAssetGroup() {
        return assetGroup;
    }

    public void setAssetGroup(String assetGroup) {
        this.assetGroup = assetGroup;
    }

    public Integer getSoftwareLabel() {
        return softwareLabel;
    }

    public void setSoftwareLabel(Integer softwareLabel) {
        this.softwareLabel = softwareLabel;
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

    public Integer getReportSource() {
        return reportSource;
    }

    public void setReportSource(Integer reportSource) {
        this.reportSource = reportSource;
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

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Long getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Integer getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(Integer modifyUser) {
        this.modifyUser = modifyUser;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getMd5Code() {
        return md5Code;
    }

    public void setMd5Code(String md5Code) {
        this.md5Code = md5Code;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "AssetSoftware{" + "port='" + port + '\'' + ", md5Code='" + md5Code + '\'' + ", size=" + size
               + ", operationSystem='" + operationSystem + '\'' + ", categoryModel='" + categoryModel + '\''
               + ", categoryModelName='" + categoryModelName + '\'' + ", name='" + name + '\'' + ", serial='" + serial
               + '\'' + ", uploadSoftwareName='" + uploadSoftwareName + '\'' + ", path='" + path + '\'' + ", version='"
               + version + '\'' + ", manufacturer='" + manufacturer + '\'' + ", description='" + description + '\''
               + ", assetGroup='" + assetGroup + '\'' + ", softwareLabel=" + softwareLabel + ", softwareStatus="
               + softwareStatus + ", authorization=" + authorization + ", reportSource=" + reportSource + ", protocol='"
               + protocol + '\'' + ", language='" + language + '\'' + ", releaseTime=" + releaseTime + ", publisher='"
               + publisher + '\'' + ", gmtCreate=" + gmtCreate + ", memo='" + memo + '\'' + ", gmtModified="
               + gmtModified + ", createUser=" + createUser + ", modifyUser=" + modifyUser + ", status=" + status
               + ", buyDate=" + buyDate + ", serviceLife=" + serviceLife + ", assetCount=" + assetCount
               + ", licenseSecretKey='" + licenseSecretKey + '\'' + '}';
    }

    public String getCheckType() {
        return checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }
}