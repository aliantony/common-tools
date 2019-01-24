package com.antiy.asset.entity;

import com.antiy.common.base.BaseEntity;
import com.antiy.common.encoder.Encode;

import java.util.Date;

/**
 * <p> 资产主表 </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */

public class Asset extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 品类名称
     */
    private String            categoryModelName;

    /**
     * 硬盘
     */
    private String            hardDisk;
    /**
     * 内存JSON数据{ID:1,name:Kingston,rom:8GB}
     */
    private String            memory;

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
    @Encode
    private String            parentId;


    /**
     * ip
     */
    private String            ip;
    /**
     * mac
     */
    private String            mac;

    /**
     * json数据
     */
    private String            assetGroup;
    /**
     * 资产编号
     */
    private String            number;
    /**
     * 资产名称
     */
    private String            name;
    /**
     * 网口
     */
    private Integer           ethernetPort;
    /**
     * 串口
     */
    private Integer           serialPort;
    /**
     * 安装方式1人工2自动
     */
    private Integer           installType;
    /**
     * 序列号
     */
    private String            serial;
    /**
     * 行政区划主键
     */
    private String           areaId;
    /**
     * 品类型号
     */
    private String           categoryModel;
    /**
     * 厂商
     */
    private String            manufacturer;
    /**
     * 资产状态：1-待登记，2-不予登记，3-待配置，4-待验证，5-待入网，6待检查，7-已入网，8-待退役，9-已退役
     */
    private Integer           assetStatus;
    /**
     * 准入状态，1待设置，2已允许，3已禁止
     */
    private Integer           admittanceStatus;
    /**
     * 操作系统,如果type为IDS或者IPS则此字段存放软件版本信息
     */
    private String            operationSystem;
    /**
     * 系统位数
     */
    private Integer           systemBit;
    /**
     * 责任人主键
     */
    private String           responsibleUserId;
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
     * 机房位置
     */
    private String            houseLocation;
    /**
     * 固件版本
     */
    private String            firmwareVersion;
    /**
     * 设备uuid
     */
    private String            uuid;
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
     * 资产重要程度：1核心2重要3一般
     */
    private Integer           importanceDegree;
    /**
     * 描述
     */
    private String            describle;

    /**
     * 所属标签ID和名称列表JSON串
     */
    private String            tags;
    /**
     * 首次入网时间
     */
    private Date              firstEnterNett;
    /**
     * 是否入网,0表示未入网,1表示入网
     */
    private Integer           isInnet;
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

    @Override
    public String toString() {
        return "Asset{" +
                "categoryModelName='" + categoryModelName + '\'' +
                ", hardDisk='" + hardDisk + '\'' +
                ", memory='" + memory + '\'' +
                ", cpu='" + cpu + '\'' +
                ", networkCard='" + networkCard + '\'' +
                ", parentId='" + parentId + '\'' +
                ", ip='" + ip + '\'' +
                ", mac='" + mac + '\'' +
                ", assetGroup='" + assetGroup + '\'' +
                ", number='" + number + '\'' +
                ", name='" + name + '\'' +
                ", ethernetPort=" + ethernetPort +
                ", serialPort=" + serialPort +
                ", installType=" + installType +
                ", serial='" + serial + '\'' +
                ", areaId=" + areaId +
                ", categoryModel=" + categoryModel +
                ", manufacturer='" + manufacturer + '\'' +
                ", assetStatus=" + assetStatus +
                ", admittanceStatus=" + admittanceStatus +
                ", operationSystem='" + operationSystem + '\'' +
                ", systemBit=" + systemBit +
                ", responsibleUserId=" + responsibleUserId +
                ", location='" + location + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", houseLocation='" + houseLocation + '\'' +
                ", firmwareVersion='" + firmwareVersion + '\'' +
                ", uuid='" + uuid + '\'' +
                ", contactTel='" + contactTel + '\'' +
                ", email='" + email + '\'' +
                ", assetSource=" + assetSource +
                ", importanceDegree=" + importanceDegree +
                ", describle='" + describle + '\'' +
                ", tags='" + tags + '\'' +
                ", firstEnterNett=" + firstEnterNett +
                ", isInnet=" + isInnet +
                ", serviceLife=" + serviceLife +
                ", buyDate=" + buyDate +
                ", warranty=" + warranty +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", memo='" + memo + '\'' +
                ", createUser=" + createUser +
                ", modifyUser=" + modifyUser +
                ", status=" + status +
                '}';
    }

    public String getCategoryModelName() {
        return categoryModelName;
    }

    public void setCategoryModelName(String categoryModelName) {
        this.categoryModelName = categoryModelName;
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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
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

    public String getAssetGroup() {
        return assetGroup;
    }

    public void setAssetGroup(String assetGroup) {
        this.assetGroup = assetGroup;
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

    public Integer getEthernetPort() {
        return ethernetPort;
    }

    public void setEthernetPort(Integer ethernetPort) {
        this.ethernetPort = ethernetPort;
    }

    public Integer getSerialPort() {
        return serialPort;
    }

    public void setSerialPort(Integer serialPort) {
        this.serialPort = serialPort;
    }

    public Integer getInstallType() {
        return installType;
    }

    public void setInstallType(Integer installType) {
        this.installType = installType;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getCategoryModel() {
        return categoryModel;
    }

    public void setCategoryModel(String categoryModel) {
        this.categoryModel = categoryModel;
    }

    public String getResponsibleUserId() {
        return responsibleUserId;
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

    public Integer getAdmittanceStatus() {
        return admittanceStatus;
    }

    public void setAdmittanceStatus(Integer admittanceStatus) {
        this.admittanceStatus = admittanceStatus;
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

    public String getHouseLocation() {
        return houseLocation;
    }

    public void setHouseLocation(String houseLocation) {
        this.houseLocation = houseLocation;
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

    public String getDescrible() {
        return describle;
    }

    public void setDescrible(String describle) {
        this.describle = describle;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Date getFirstEnterNett() {
        return firstEnterNett;
    }

    public void setFirstEnterNett(Date firstEnterNett) {
        this.firstEnterNett = firstEnterNett;
    }

    public Integer getIsInnet() {
        return isInnet;
    }

    public void setIsInnet(Integer isInnet) {
        this.isInnet = isInnet;
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

    public void setResponsibleUserId(String responsibleUserId) {
        this.responsibleUserId = responsibleUserId;
    }
}