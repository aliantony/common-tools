package com.antiy.asset.vo.response;

/**
 * <p> AssetResponse 响应对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer           id;
    /**
     * 资产编号
     */
    private String            number;
    /**
     * 资产名称
     */
    private String            name;
    /**
     * 资产组
     */
    private String            assetGroups;
    /**
     * ip
     */
    private String            ip;
    /**
     * mac
     */
    private String            mac;
    /**
     * 序列号
     */
    private String            serial;
    /**
     * 品类
     */
    private Integer           category;
    /**
     * 资产型号
     */
    private Integer           model;
    /**
     * 厂商
     */
    private String            manufacturer;
    /**
     * 资产状态：1-待登记，2-不予登记，3-待配置，4-待验证，5-待入网，6-已入网，7-待退役，8-已退役
     */
    private Integer           assetStatus;
    /**
     * 操作系统,如果type为IDS或者IPS则此字段存放软件版本信息
     */
    private String            operationSystem;
    /**
     * 系统位数
     */
    private Integer           systemBit;

    /**
     * 固件版本
     */
    private String            firmwareVersion;
    /**
     * 设备uuid
     */
    private String            uuid;
    /**
     * 责任人主键
     */
    private Integer           responsibleUserId;
    /**
     * 联系电话
     */
    private String            contactTel;
    /**
     * 邮箱
     */
    private String            email;

    /**
     * 上报来源,1-自动上报，2-人工上报
     */
    private Integer           assetSource;
    /**
     * 0-不重要(not_major),1- 一般(general),3-重要(major),
     */
    private Integer           importanceDegree;

    /**
     * 父类资源Id
     */
    private Integer           parentId;
    /**
     * 所属标签
     */
    private String            tags;
    /**
     * 是否入网,0表示未入网,1表示入网
     */
    private Boolean           isInnet;
    /**
     * 使用到期时间
     */
    private Long              serviceLife;
    /**
     * 制造日期
     */
    private Long              buyDate;
    /**
     * 保修期
     */
    private Long              warranty;

    public String getAssetGroups() {
        return assetGroups;
    }

    public void setAssetGroups(String assetGroups) {
        this.assetGroups = assetGroups;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Integer getModel() {
        return model;
    }

    public void setModel(Integer model) {
        this.model = model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Integer getAssetStatus() {
        return assetStatus;
    }

    public void setAssetStatus(Integer assetStatus) {
        this.assetStatus = assetStatus;
    }

    public String getOperationSystem() {
        return operationSystem;
    }

    public void setOperationSystem(String operationSystem) {
        this.operationSystem = operationSystem;
    }

    public Integer getSystemBit() {
        return systemBit;
    }

    public void setSystemBit(Integer systemBit) {
        this.systemBit = systemBit;
    }

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getResponsibleUserId() {
        return responsibleUserId;
    }

    public void setResponsibleUserId(Integer responsibleUserId) {
        this.responsibleUserId = responsibleUserId;
    }

    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAssetSource() {
        return assetSource;
    }

    public void setAssetSource(Integer assetSource) {
        this.assetSource = assetSource;
    }

    public Integer getImportanceDegree() {
        return importanceDegree;
    }

    public void setImportanceDegree(Integer importanceDegree) {
        this.importanceDegree = importanceDegree;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Boolean getInnet() {
        return isInnet;
    }

    public void setInnet(Boolean innet) {
        isInnet = innet;
    }

    public Long getServiceLife() {
        return serviceLife;
    }

    public void setServiceLife(Long serviceLife) {
        this.serviceLife = serviceLife;
    }

    public Long getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Long buyDate) {
        this.buyDate = buyDate;
    }

    public Long getWarranty() {
        return warranty;
    }

    public void setWarranty(Long warranty) {
        this.warranty = warranty;
    }
}