package com.antiy.asset.vo.response;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetResponse 响应对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetResponse extends BaseResponse {
    /**
     * 资产编号
     */
    @ApiModelProperty("资产编号")
    private String                   number;
    /**
     * 资产名称
     */
    @ApiModelProperty("资产名称")
    private String                   name;
    /**
     * 资产组
     */
    @ApiModelProperty("资产组")
    private String                   assetGroup;
    /**
     * 资产组列表
     */
    @ApiModelProperty("资产组列表")
    private List<AssetGroupResponse> assetGroups;
    /**
     * ip
     */
    @ApiModelProperty("ip")
    private String                   ip;
    /**
     * mac
     */
    @ApiModelProperty("mac")
    private String                   mac;
    /**
     * 序列号
     */
    @ApiModelProperty("序列号")
    private String                   serial;
    /**
     * 品类
     */
    @ApiModelProperty("品类")
    private Integer                  categoryModel;
    /**
     * 品类名称
     */
    @ApiModelProperty("品类名称")
    private String                   categoryModelName;
    /**
     * 厂商
     */
    @ApiModelProperty("厂商")
    private String                   manufacturer;
    /**
     * 资产状态：1-待登记，2-不予登记，3-待配置，4-待验证，5-待入网，6待检查，7-已入网，8-待退役，9-已退役
     */
    @ApiModelProperty("资产状态")
    private Integer                  assetStatus;
    /**
     * 操作系统,如果type为IDS或者IPS则此字段存放软件版本信息
     */
    @ApiModelProperty("操作系统")
    private String                   operationSystem;
    /**
     * 系统位数
     */
    @ApiModelProperty("系统位数")
    private Integer                  systemBit;

    /**
     * 固件版本
     */
    @ApiModelProperty("固件版本")
    private String                   firmwareVersion;
    /**
     * 设备uuid
     */
    @ApiModelProperty("设备uuid")
    private String                   uuid;
    /**
     * 责任人主键
     */
    @ApiModelProperty("责任人主键")
    private Integer                  responsibleUserId;
    /**
     * 联系电话
     */
    @ApiModelProperty("联系电话")
    private String                   contactTel;
    /**
     * 邮箱
     */
    @ApiModelProperty("邮箱")
    private String                   email;

    /**
     * 上报来源,1-自动上报，2-人工上报
     */
    @ApiModelProperty("上报来源")
    private Integer                  assetSource;
    /**
     * 1核心2重要3一般
     */
    @ApiModelProperty("1核心2重要3一般")
    private Integer                  importanceDegree;

    /**
     * 父类资源Id
     */
    @ApiModelProperty("父类资源Id")
    private Integer                  parentId;
    /**
     * 所属标签
     */
    @ApiModelProperty("")
    private String                   tags;
    /**
     * 是否入网,false表示未入网,true表示入网
     */
    @ApiModelProperty("是否入网,false表示未入网,true已入网")
    private Boolean                  innet;
    /**
     * 使用到期时间
     */
    @ApiModelProperty("使用到期时间")
    private Long                     serviceLife;
    /**
     * 制造日期
     */
    @ApiModelProperty("制造日期")
    private Long                     buyDate;
    /**
     * 保修期
     */
    @ApiModelProperty("保修期")
    private Long                     warranty;
    /**
     * 资产准入状态
     */
    @ApiModelProperty("资产准入状态:待设置，2已允许，3已禁止")
    private Integer                  admittanceStatus;
    /**
     * 创建时间
     */
    @ApiModelProperty("首次发现时间")
    private Long                     gmtCreate;

    /**
     * 首次入网时间
     */
    @ApiModelProperty("首次入网时间")
    private Long                     firstEnterNett;

    public Integer getAdmittanceStatus() {
        return admittanceStatus;
    }

    public void setAdmittanceStatus(Integer admittanceStatus) {
        this.admittanceStatus = admittanceStatus;
    }

    public String getCategoryModelName() {
        return categoryModelName;
    }

    public void setCategoryModelName(String categoryModelName) {
        this.categoryModelName = categoryModelName;
    }

    public String getAssetGroup() {
        return assetGroup;
    }

    public void setAssetGroup(String assetGroup) {
        this.assetGroup = assetGroup;
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

    public Integer getCategoryModel() {
        return categoryModel;
    }

    public void setCategoryModel(Integer categoryModel) {
        this.categoryModel = categoryModel;
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
        return innet;
    }

    public void setInnet(Boolean innet) {
        this.innet = innet;
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

    public List<AssetGroupResponse> getAssetGroups() {
        return assetGroups;
    }

    public void setAssetGroups(List<AssetGroupResponse> assetGroups) {
        this.assetGroups = assetGroups;
    }

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Long getFirstEnterNett() {
        return firstEnterNett;
    }

    public void setFirstEnterNett(Long firstEnterNett) {
        this.firstEnterNett = firstEnterNett;
    }
}