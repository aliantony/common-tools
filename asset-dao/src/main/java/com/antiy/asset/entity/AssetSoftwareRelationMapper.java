package com.antiy.asset.entity;

public class AssetSoftwareRelationMapper {

    /**
     * 资产名称
     */
    private String  assetName;

    /**
     * 资产编号
     */
    private String  assetNumber;

    /**
     * 资产品类型号名
     */
    private String  categoryModelName;
    /**
     * 资产厂商
     */
    private String  manufacturer;
    /**
     * 资产IP
     */
    private String  ip;

    /**
     * 资产mac
     */
    private String  mac;

    /**
     * 资产主键
     */
    private Integer assetId;

    /**
     * 软件主键
     */
    private Integer softwareId;
    /**
     * 软件资产状态：1待登记，2不予登记，3待配置，4待验证，5待入网，6已入网，7待退役，8已退役
     */
    private Integer softwareStatus;
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
     * 责任人姓名
     */
    private String  userName;

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

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getAssetNumber() {
        return assetNumber;
    }

    public void setAssetNumber(String assetNumber) {
        this.assetNumber = assetNumber;
    }

    public String getCategoryModelName() {
        return categoryModelName;
    }

    public void setCategoryModelName(String categoryModelName) {
        this.categoryModelName = categoryModelName;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
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

    public String getLicenseSecretKey() {
        return licenseSecretKey;
    }

    public void setLicenseSecretKey(String licenseSecretKey) {
        this.licenseSecretKey = licenseSecretKey;
    }

    public Integer getInstallType() {
        return installType;
    }

    public void setInstallType(Integer installType) {
        this.installType = installType;
    }

    public Integer getInstallStatus() {
        return installStatus;
    }

    public void setInstallStatus(Integer installStatus) {
        this.installStatus = installStatus;
    }

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Long getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "AssetSoftwareRelationMapper{" +
                "assetName='" + assetName + '\'' +
                ", assetNumber='" + assetNumber + '\'' +
                ", categoryModelName='" + categoryModelName + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", ip='" + ip + '\'' +
                ", mac='" + mac + '\'' +
                ", assetId=" + assetId +
                ", softwareId=" + softwareId +
                ", softwareStatus=" + softwareStatus +
                ", protocol='" + protocol + '\'' +
                ", port='" + port + '\'' +
                ", licenseSecretKey='" + licenseSecretKey + '\'' +
                ", installType=" + installType +
                ", installStatus=" + installStatus +
                ", userName='" + userName + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", memo='" + memo + '\'' +
                ", createUser=" + createUser +
                ", modifyUser=" + modifyUser +
                ", status=" + status +
                '}';
    }
}
