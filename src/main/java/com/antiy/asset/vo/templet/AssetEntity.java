package com.antiy.asset.vo.templet;


import com.antiy.asset.excel.annotation.ExcelField;

public class AssetEntity {


    private static final long serialVersionUID = 1L;


    /**
     * 1台式办公机,2便携式办公机,3服务器虚拟终,4移动设备,4ATM机,5工控上位机,6路由器,7交换机,8防火墙,9IDS,10IPS,
     */
    @ExcelField(value = "type", align = 1, title = "资产类型", type = 0, dictType = "hardware_type")
    private Integer type;


    /**
     * 资产名称
     */
    @ExcelField(value = "name", align = 1, title = "资产名称", type = 0)
    private String name;


    /**
     * 序列号
     */
    @ExcelField(value = "serial", align = 1, title = "序列号", type = 0)
    private String serial;


    /**
     * 品类
     */
    @ExcelField(value = "category", align = 1, title = "资产品类", type = 0)
    private Integer category;


    /**
     * 资产型号
     */
    @ExcelField(value = "model", align = 1, title = "资产型号", type = 0)
    private Integer model;


    /**
     * 制造商
     */
    @ExcelField(value = "manufacturer", align = 1, title = "制造商", type = 0)
    private String manufacturer;


    /**
     * 1-待登记，2-不予登记，3-待配置，4-待验证，5-待入网，6-已入网，7-待退役，8-已退役
     */
    @ExcelField(value = "assetStatus", align = 1, title = "资产状态", type = 0, dictType = "hardware_status")
    private Integer assetStatus;


    /**
     * 操作系统,如果type为IDS或者IPS则此字段存放软件版本信息
     */
    @ExcelField(value = "operationSystem", align = 1, title = "操作系统", type = 0)
    private String operationSystem;


    /**
     * 系统位数
     */
    @ExcelField(value = "systemBit", align = 1, title = "系统位数", type = 0)
    private Integer systemBit;


    /**
     * 物理位置
     */
    @ExcelField(value = "location", align = 1, title = "物理位置", type = 0)
    private String location;


    /**
     * 固件版本
     */
    @ExcelField(value = "firmwareVersion", align = 1, title = "固件版本", type = 0)
    private String firmwareVersion;


    /**
     * 责任人主键
     */
    @ExcelField(value = "responsible_user_id", align = 1, title = "责任人编号", type = 0)
    private Integer responsibleUserId;


    /**
     * 硬盘
     */
    @ExcelField(value = "hardDisk", align = 1, title = "硬盘", type = 1)
    private String hardDisk;


    /**
     * 内存JSON数据{ID:1,name:Kingston,rom:8GB}
     */
    @ExcelField(value = "memory", align = 1, title = "内存", type = 1)
    private String memory;


    /**
     * 设备来源,1-自动上报，2-人工上报
     */
    @ExcelField(value = "assetSource", align = 1, title = "设备来源", type = 0, dictType = "report_source")
    private Integer assetSource;


    /**
     * 0-不重要(not_major),1- 一般(general),3-重要(major),
     */
    @ExcelField(value = "importanceDegree", align = 1, title = "重要程度", type = 0, dictType = "major_type")
    private Integer importanceDegree;

    /**
     * CPUJSON数据{ID:1,name:intel,coresize:8}
     */
    @ExcelField(value = "cpu", align = 1, title = "CPU", type = 1)
    private String cpu;


    /**
     * 网卡JSON数据{ID:1,name:intel,speed:1900M}
     */
    @ExcelField(value = "networkCard", align = 1, title = "网卡", type = 1)
    private String networkCard;

    /**
     * 描述
     */
    @ExcelField(value = "describle", align = 1, title = "描述", type = 0)
    private String describle;


    /**
     * 所属标签ID和名称列表JSON串
     */
    @ExcelField(value = "tags", align = 1, title = "所属标签", type = 1)
    private String tags;


    /**
     * 是否入网,0表示未入网,1表示入网
     */
    @ExcelField(value = "isInnet", align = 1, title = "是否入网", type = 1, dictType = "yesorno")
    private Integer isInnet;


    /**
     * 使用到期时间
     */
    @ExcelField(value = "serviceLife", align = 1, title = "使用到期时间", type = 0, isDate = true)
    private Long serviceLife;


    /**
     * 制造日期
     */
    @ExcelField(value = "buyDate", align = 1, title = "制造日期", type = 0, isDate = true)
    private Long buyDate;


    /**
     * 保修期
     */
    @ExcelField(value = "warranty", align = 1, title = "保修期", type = 0, isDate = true)
    private Long warranty;


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


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
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

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
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

    public Integer getImportanceDegree() {
        return importanceDegree;
    }

    public void setImportanceDegree(Integer importanceDegree) {
        this.importanceDegree = importanceDegree;
    }

    public Integer getIsInnet() {
        return isInnet;
    }

    public void setIsInnet(Integer isInnet) {
        this.isInnet = isInnet;
    }

    public Integer getResponsibleUserId() {
        return responsibleUserId;
    }

    public void setResponsibleUserId(Integer responsibleUserId) {
        this.responsibleUserId = responsibleUserId;
    }

    @Override
    public String toString() {
        return "AssetEntity{" +
                "type=" + type +
                ", name='" + name + '\'' +
                ", serial='" + serial + '\'' +
                ", category=" + category +
                ", model=" + model +
                ", manufacturer='" + manufacturer + '\'' +
                ", assetStatus=" + assetStatus +
                ", operationSystem='" + operationSystem + '\'' +
                ", systemBit=" + systemBit +
                ", location='" + location + '\'' +
                ", firmwareVersion='" + firmwareVersion + '\'' +
                ", responsibleUserId=" + responsibleUserId +
                ", hardDisk='" + hardDisk + '\'' +
                ", memory='" + memory + '\'' +
                ", assetSource=" + assetSource +
                ", importanceDegree=" + importanceDegree +
                ", cpu='" + cpu + '\'' +
                ", networkCard='" + networkCard + '\'' +
                ", describle='" + describle + '\'' +
                ", tags='" + tags + '\'' +
                ", isInnet=" + isInnet +
                ", serviceLife=" + serviceLife +
                ", buyDate=" + buyDate +
                ", warranty=" + warranty +
                '}';
    }

    public AssetEntity(Integer type, String name, String serial, Integer category, Integer model, String manufacturer, Integer assetStatus, String operationSystem, Integer systemBit, String location, String firmwareVersion, Integer responsibleUserId, String hardDisk, String memory, Integer assetSource, Integer importanceDegree, String cpu, String networkCard, String describle, String tags, Integer isInnet, Long serviceLife, Long buyDate, Long warranty) {
        this.type = type;
        this.name = name;
        this.serial = serial;
        this.category = category;
        this.model = model;
        this.manufacturer = manufacturer;
        this.assetStatus = assetStatus;
        this.operationSystem = operationSystem;
        this.systemBit = systemBit;
        this.location = location;
        this.firmwareVersion = firmwareVersion;
        this.responsibleUserId = responsibleUserId;
        this.hardDisk = hardDisk;
        this.memory = memory;
        this.assetSource = assetSource;
        this.importanceDegree = importanceDegree;
        this.cpu = cpu;
        this.networkCard = networkCard;
        this.describle = describle;
        this.tags = tags;
        this.isInnet = isInnet;
        this.serviceLife = serviceLife;
        this.buyDate = buyDate;
        this.warranty = warranty;
    }

    public AssetEntity() {
    }

}
