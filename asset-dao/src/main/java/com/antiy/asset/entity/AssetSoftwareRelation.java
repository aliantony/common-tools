package com.antiy.asset.entity;

import com.antiy.common.base.BaseEntity;

/**
 * <p> 资产软件关系信息 </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */

public class AssetSoftwareRelation extends BaseEntity {

    /**
     * 软件版本
     */
    private String  version;
    /**
     * 软件厂商
     */
    private String  manufacturer;
    /**
     * 软件兼容操作系统(WINDOWS7-32-64,WINDOWS8-64)
     */
    private String  operationSystem;
    /**
     * 软件名字
     */
    private String  name;

    /**
     * 资产主键
         */
        private String  assetId;

        /**
         * 软件主键
         */
        private String  softwareId;
    /**
     * 软件资产状态：1待登记2待分析3可安装4已退役5不予登记
     */
    private Integer softwareStatus;
    /**
     * 配置状态：1未配置，2配置中，3已配置
     */
    private Integer configureStatus;
    /**
     * 协议
     */
    private String  protocol;
    /**
     * 端口
     */
    private String  port;
    /**
     * 许可密钥
     */
    private String  licenseSecretKey;
    /**
     * 安装方式1人工2自动
     */
    private Integer installType;
    /**
     * 安装状态0失败、1成功，2安装中
     */
    private Integer installStatus;
    /**
     * 创建时间
     */
    private Long    gmtCreate;
    /**
     * 更新时间
     */
    private Long    gmtModified;
    /**
     * 备注
     */
    private String  memo;
    /**
     * 创建人
     */
    private Integer createUser;
    /**
     * 修改人
     */
    private Integer modifyUser;
    /**
     * 状态,1未删除,0已删除
     */
    private Integer status;
    /**
     * 安装时间
     */
    private Long    installTime;
    /**
     * 责任人主键
     */
    private String  userId;
    /**
     * 责任人
     */
    private String  userName;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getSoftwareStatus() {
        return softwareStatus;
    }

    public void setSoftwareStatus(Integer softwareStatus) {
        this.softwareStatus = softwareStatus;
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

    public Integer getInstallType() {
        return installType;
    }

    public void setInstallType(Integer installType) {
        this.installType = installType;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Long getGmtModified() {
        return System.currentTimeMillis();
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
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

    public Long getInstallTime() {
        return installTime;
    }

    public void setInstallTime(Long installTime) {
        this.installTime = installTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    @Override
    public String toString() {
        return "AssetSoftwareRelation{" + "version='" + version + '\'' + ", manufacturer='" + manufacturer + '\''
               + ", operationSystem='" + operationSystem + '\'' + ", name='" + name + '\'' + ", assetId='" + assetId
               + '\'' + ", softwareId='" + softwareId + '\'' + ", softwareStatus=" + softwareStatus
               + ", configureStatus=" + configureStatus + ", protocol='" + protocol + '\'' + ", port='" + port + '\''
               + ", licenseSecretKey='" + licenseSecretKey + '\'' + ", installType=" + installType + ", installStatus="
               + installStatus + ", gmtCreate=" + gmtCreate + ", gmtModified=" + gmtModified + ", memo='" + memo + '\''
               + ", createUser=" + createUser + ", modifyUser=" + modifyUser + ", status=" + status + ", installTime="
               + installTime + ", userId='" + userId + '\'' + ", userName='" + userName + '\'' + '}';
    }

    public Integer getConfigureStatus() {
        return configureStatus;
    }

    public void setConfigureStatus(Integer configureStatus) {
        this.configureStatus = configureStatus;
    }
}