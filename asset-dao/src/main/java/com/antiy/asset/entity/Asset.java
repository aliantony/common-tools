package com.antiy.asset.entity;

import com.antiy.common.base.BaseEntity;

/**
 * <p> 资产主表 </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */

public class Asset extends BaseEntity {

    private static final long serialVersionUID = 1L;


    /**
     * 资产编号
     */

    private String            number;
    /**
     * 资产zu
     */
    private String            assetGroup;
    /**
     * 区域ID
     */
    private String            areaId;
    /**
     * 机房位置
     */
    private String            houseLocation;
    /**
     * 资产类型:1台式办公机,2便携式办公机,3服务器虚拟终,4移动设备,4ATM机,5工控上位机,6路由器,7交换机,8防火墙,9IDS,10IPS,
     */
    private Integer           type;
    /**
     * 资产名称
     */
    private String            name;
    /**
     * 序列号
     */
    private String            serial;
    /**
     * 品类
     */
    private Integer           categoryModel;
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
     * 物理位置
     */
    private String            location;
    /**
     * 纬度
     */
    private String            latitude;
    /**
     * 经度
     */
    private String            longitude;
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
     * 硬盘
     */
    private String            hardDisk;
    /**
     * 内存JSON数据{ID:1,name:Kingston,rom:8GB}
     */
    private String            memory;
    /**
     * 上报来源,1-自动上报，2-人工上报
     */
    private Integer           assetSource;
    /**
     * 0-不重要(not_major),1- 一般(general),3-重要(major),
     */
    private Integer           importanceDegree;
    /**
     * 描述
     */
    private String            describle;
    /**
     * CPUJSON数据{ID:1,name:intel,coresize:8}
     */
    private String            cpu;
    /**
     * 网卡JSON数据{ID:1,name:intel,speed:1900M}
     */
    private String            networkCard;
    /**
     * 父类资源Id
     */
    private Integer           parentId;
    /**
     * 所属标签ID和名称列表JSON串
     */
    private String            tags;
    /**
     * 是否入网,0表示未入网,1表示入网
     */
    private Boolean           isInnet;
    /**
     * 首次入网时间
     */
    private Long              firstEnterNett;
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
    /**
     * 创建时间
     */
    private Long              gmtCreate;
    /**
     * 更新时间
     */
    private Long              gmtModified;
    /**
     * 备注
     */
    private String            memo;
    /**
     * 创建人
     */
    private Integer           createUser;
    /**
     * 修改人
     */
    private Integer           modifyUser;
    /**
     * 状态,1未删除,0已删除
     */
    private Integer           status;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
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

    public String getHardDisk() {
        return hardDisk;
    }

    public void setHardDisk(String hardDisk) {
        this.hardDisk = hardDisk;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
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

    public String getDescrible() {
        return describle;
    }

    public void setDescrible(String describle) {
        this.describle = describle;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getNetworkCard() {
        return networkCard;
    }

    public void setNetworkCard(String networkCard) {
        this.networkCard = networkCard;
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

    public void setInnet(Boolean isInnet) {
        this.isInnet = isInnet;
    }

    public Long getFirstEnterNett() {
        return firstEnterNett;
    }

    public void setFirstEnterNett(Long firstEnterNett) {
        this.firstEnterNett = firstEnterNett;
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

    @Override
    public String toString() {
        return "Asset{" + ", number=" + number + ", type=" + type + ", name=" + name + ", serial=" + serial
               + ", categoryModel=" + categoryModel + ", manufacturer=" + manufacturer + ", assetStatus="
               + assetStatus + ", operationSystem=" + operationSystem + ", systemBit=" + systemBit + ", location="
               + location + ", latitude=" + latitude + ", longitude=" + longitude + ", firmwareVersion="
               + firmwareVersion + ", uuid=" + uuid + ", responsibleUserId=" + responsibleUserId + ", contactTel="
               + contactTel + ", email=" + email + ", hardDisk=" + hardDisk + ", memory=" + memory + ", assetSource="
               + assetSource + ", importanceDegree=" + importanceDegree + ", describle=" + describle + ", cpu=" + cpu
               + ", networkCard=" + networkCard + ", parentId=" + parentId + ", tags=" + tags + ", isInnet=" + isInnet
               + ", serviceLife=" + serviceLife + ", buyDate=" + buyDate + ", warranty=" + warranty + ", gmtCreate="
               + gmtCreate + ", gmtModified=" + gmtModified + ", memo=" + memo + ", createUser=" + createUser
               + ", modifyUser=" + modifyUser + ", isDelete=" + status + "}";
    }

    public String getAssetGroup() {
        return assetGroup;
    }

    public void setAssetGroup(String assetGroup) {
        this.assetGroup = assetGroup;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getHouseLocation() {
        return houseLocation;
    }

    public void setHouseLocation(String houseLocation) {
        this.houseLocation = houseLocation;
    }

    public void setCategoryModel(Integer categoryModel) {
        this.categoryModel = categoryModel;
    }
}