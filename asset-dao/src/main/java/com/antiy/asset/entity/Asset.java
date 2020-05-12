package com.antiy.asset.entity;

import com.antiy.common.base.BaseEntity;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p> 资产主表 </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */

public class Asset extends BaseEntity {
    /**
     * 厂商名称版本
     */
    private String  mnv;

    private Long    businessId;
    /**
     * 配置模板idbaselineTemplateId
     */
    private String  baselineTemplateId;
    /**
     * 网络状态：1、在线 2、离线 3、未知
     */
    private Integer netStatus;
    /**
     * 是否孤岛设备：1、是 2、否
     */
    private Integer isOrphan;
    /**
     * 自定义字段
     */
    private String  customField;
    /**
     * 配置模板名称
     */
    private String  baselineTemplateName;
    /**
     * 装机模板id
     */
    private String  installTemplateId;
    /**
     * stepNode
     */
    private String  stepNode;
    /**
     * 品类名称
     */
    private String  version;

    /**
     * 是否入网
     */
    private Integer isInnet;
    /**
     * 是否入网
     */
    private String  latitude;
    /**
     * 是否入网
     */
    private String  longitude;

    /**
     * json数据
     */
    @ApiModelProperty("资产组")
    private String  assetGroup;
    /**
     * 资产编号
     */
    @ApiModelProperty("资产编号")
    private String  number;
    /**
     * 资产名称
     */
    private String  name;

    /**
     * 安装方式1人工2自动
     */
    @ApiModelProperty("安装方式")
    private Integer installType;
    @ApiModelProperty("安装方式")
    private String  installTypeName;
    /**
     * 序列号
     */
    private String  serial;
    /**
     * 行政区划主键
     */
    @ApiModelProperty("归属区域")
    private String  areaId;
    /**
     * 行政区划名称
     */
    @ApiModelProperty("行政区划名称")
    private String  areaName;
    /**
     * 品类型号
     */
    private String  categoryModel;
    /**
     * 品类名称
     */
    private String  categoryModelName;
    private String  categoryType;
    /**
     * 厂商
     */
    private String  manufacturer;
    /**
     * 资产状态：1-待登记，2-不予登记，3-待配置，4-待验证，5-待入网，6待检查，7-已入网，8-待退役，9-已退役
     */
    private Integer assetStatus;
    /**
     * 准入状态，1已允许，2已禁止
     */
    private Integer admittanceStatus;
    /**
     * 操作系统,如果type为IDS或者IPS则此字段存放软件版本信息
     */
    @ApiModelProperty("操作系统")
    private Long    operationSystem;
    /**
     * 操作系统名
     */
    @ApiModelProperty("操作系统名")
    private String  operationSystemName;

    /**
     * 使用者主键
     */
    @ApiModelProperty("使用者")
    private String  responsibleUserId;

    /**
     * 使用者名称
     */
    @ApiModelProperty("使用者")
    private String  responsibleUserName;

    /**
     * 机房位置
     */
    @ApiModelProperty("机房位置")
    private String  houseLocation;
    @ApiModelProperty("物理位置")
    private String  location;
    /**
     * 固件版本
     */
    @ApiModelProperty("固件版本")
    private String  firmwareVersion;
    /**
     * 软件版本
     */
    @ApiModelProperty("软件版本")
    private String  softwareVersion;

    /**
     * 设备uuid
     */
    private String  uuid;

    private String  ips;
    /**
     * mac
     */
    private String  macs;
    /**
     * 上报来源,1-自动上报，2-人工上报
     */
    private Integer assetSource;
    /**
     * 上报来源名称
     */
    private String  assetSourceName;
    /**
     * 资产重要程度：1核心2重要3一般
     */
    @ApiModelProperty("重要程度")
    private Integer importanceDegree;
    /**
     * 资产重要程度：1核心2重要3一般
     */
    @ApiModelProperty("重要程度")
    private String  importanceDegreeName;
    /**
     * 描述
     */
    @ApiModelProperty("描述")
    private String  describle;
    /**
     * 机器名
     */
    private String  machineName;
    /**
     * 是否涉密：1涉密，2-不涉密
     */
    private Integer isSecrecy;
    /**
     * 国资码
     */
    private String  code;
    /**
     * 首次入网时间
     */
    private Long    firstEnterNett;
    /**
     * 首次发现时间
     */
    private Long    firstDiscoverTime;

    /**
     * 使用到期时间
     */
    @ApiModelProperty("到期时间")
    private Long    serviceLife;
    /**
     * 购买日期
     */
    @ApiModelProperty("购买日期")
    private Long    buyDate;
    /**
     * 保修期
     */
    @ApiModelProperty("保修期")
    private String  warranty;
    /**
     * 装机时间
     */
    private Long    installDate;
    /**
     * 启用时间
     */
    private Long    activiateDate;
    /**
     * 到期提醒
     */
    private Long    expirationReminder;
    /**
     * 是否借用：1-借用，2-未借用
     */
    private Integer isBorrow;
    /**
     * key
     */
    private String  key;
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
    @ApiModelProperty("描述")
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
    @ApiModelProperty("未修复漏洞数量")
    private String  vulCount;
    @ApiModelProperty("未安装补丁数量")
    private String  patchCount;
    @ApiModelProperty("部门名")
    private String  departmentName;
    @ApiModelProperty("告警数量")
    private String  alarmCount;

    private Long    baselineTemplateCorrelationGmt;

    private Long    installTemplateCorrelationGmt;

    @ApiModelProperty("从属业务,详情展示")
    private String  assetBusiness;
    @ApiModelProperty("网络类型")
    private String  netType;
    /**
     * 网络类型名称
     */
    private String  netTypeName;
    /**
     * 行颜色
     */
    private String  rowColor;
    /**
     * 软件版本
     */
    private String  softVersion;
    /**
     * 是否被整改过：1,已整改,2未整改
     */
    private Integer rectification;

    public Integer getRectification() {
        return rectification;
    }

    public void setRectification(Integer rectification) {
        this.rectification = rectification;
    }

    public String getSoftVersion() {
        return softVersion;
    }

    public void setSoftVersion(String softVersion) {
        this.softVersion = softVersion;
    }

    public String getRowColor() {
        return rowColor;
    }

    public void setRowColor(String rowColor) {
        this.rowColor = rowColor;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public String getNetTypeName() {
        return netTypeName;
    }

    public void setNetTypeName(String netTypeName) {
        this.netTypeName = netTypeName;
    }

    public String getNetType() {
        return netType;
    }

    public void setNetType(String netType) {
        this.netType = netType;
    }

    public String getMnv() {
        return mnv;
    }

    public void setMnv(String mnv) {
        this.mnv = mnv;
    }

    public Long getInstallDate() {
        return installDate;
    }

    public void setInstallDate(Long installDate) {
        this.installDate = installDate;
    }

    public Long getActiviateDate() {
        return activiateDate;
    }

    public void setActiviateDate(Long activiateDate) {
        this.activiateDate = activiateDate;
    }

    public Long getExpirationReminder() {
        return expirationReminder;
    }

    public void setExpirationReminder(Long expirationReminder) {
        this.expirationReminder = expirationReminder;
    }

    public Integer getIsBorrow() {
        return isBorrow;
    }

    public void setIsBorrow(Integer isBorrow) {
        this.isBorrow = isBorrow;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getAssetBusiness() {
        return assetBusiness;
    }

    public void setAssetBusiness(String assetBusiness) {
        this.assetBusiness = assetBusiness;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public Integer getIsSecrecy() {
        return isSecrecy;
    }

    public void setIsSecrecy(Integer isSecrecy) {
        this.isSecrecy = isSecrecy;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBaselineTemplateName() {
        return baselineTemplateName;
    }

    public void setBaselineTemplateName(String baselineTemplateName) {
        this.baselineTemplateName = baselineTemplateName;
    }

    public String getAssetSourceName() {
        return assetSourceName;
    }

    public void setAssetSourceName(String assetSourceName) {
        this.assetSourceName = assetSourceName;
    }

    public String getCategoryModelName() {
        return categoryModelName;
    }

    public void setCategoryModelName(String categoryModelName) {
        this.categoryModelName = categoryModelName;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public String getBaselineTemplateId() {
        return baselineTemplateId;
    }

    public void setBaselineTemplateId(String baselineTemplateId) {
        this.baselineTemplateId = baselineTemplateId;
    }

    public String getInstallTemplateId() {
        return installTemplateId;
    }

    public void setInstallTemplateId(String installTemplateId) {
        this.installTemplateId = installTemplateId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getIsInnet() {
        return isInnet;
    }

    public void setIsInnet(Integer isInnet) {
        this.isInnet = isInnet;
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

    public Integer getInstallType() {
        return installType;
    }

    public void setInstallType(Integer installType) {
        this.installType = installType;
    }

    public String getInstallTypeName() {
        return installTypeName;
    }

    public void setInstallTypeName(String installTypeName) {
        this.installTypeName = installTypeName;
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

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getCategoryModel() {
        return categoryModel;
    }

    public void setCategoryModel(String categoryModel) {
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

    public Integer getAdmittanceStatus() {
        return admittanceStatus;
    }

    public void setAdmittanceStatus(Integer admittanceStatus) {
        this.admittanceStatus = admittanceStatus;
    }

    public Long getOperationSystem() {
        return operationSystem;
    }

    public void setOperationSystem(Long operationSystem) {
        this.operationSystem = operationSystem;
    }

    public String getOperationSystemName() {
        return operationSystemName;
    }

    public void setOperationSystemName(String operationSystemName) {
        this.operationSystemName = operationSystemName;
    }

    public String getResponsibleUserId() {
        return responsibleUserId;
    }

    public void setResponsibleUserId(String responsibleUserId) {
        this.responsibleUserId = responsibleUserId;
    }

    public String getResponsibleUserName() {
        return responsibleUserName;
    }

    public void setResponsibleUserName(String responsibleUserName) {
        this.responsibleUserName = responsibleUserName;
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

    public String getSoftwareVersion() {
        return softwareVersion;
    }

    public void setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getIps() {
        return ips;
    }

    public void setIps(String ips) {
        this.ips = ips;
    }

    public String getMacs() {
        return macs;
    }

    public String getCustomField() {
        return customField;
    }

    public void setCustomField(String customField) {
        this.customField = customField;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public void setMacs(String macs) {
        this.macs = macs;
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

    public String getImportanceDegreeName() {
        return importanceDegreeName;
    }

    public void setImportanceDegreeName(String importanceDegreeName) {
        this.importanceDegreeName = importanceDegreeName;
    }

    public String getDescrible() {
        return describle;
    }

    public void setDescrible(String describle) {
        this.describle = describle;
    }

    public Long getFirstEnterNett() {
        return firstEnterNett;
    }

    public void setFirstEnterNett(Long firstEnterNett) {
        this.firstEnterNett = firstEnterNett;
    }

    public Long getFirstDiscoverTime() {
        return firstDiscoverTime;
    }

    public void setFirstDiscoverTime(Long firstDiscoverTime) {
        this.firstDiscoverTime = firstDiscoverTime;
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

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
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

    public String getVulCount() {
        return vulCount;
    }

    public void setVulCount(String vulCount) {
        this.vulCount = vulCount;
    }

    public String getPatchCount() {
        return patchCount;
    }

    public void setPatchCount(String patchCount) {
        this.patchCount = patchCount;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getAlarmCount() {
        return alarmCount;
    }

    public void setAlarmCount(String alarmCount) {
        this.alarmCount = alarmCount;
    }

    public Long getBaselineTemplateCorrelationGmt() {
        return baselineTemplateCorrelationGmt;
    }

    public void setBaselineTemplateCorrelationGmt(Long baselineTemplateCorrelationGmt) {
        this.baselineTemplateCorrelationGmt = baselineTemplateCorrelationGmt;
    }

    public Long getInstallTemplateCorrelationGmt() {
        return installTemplateCorrelationGmt;
    }

    public void setInstallTemplateCorrelationGmt(Long installTemplateCorrelationGmt) {
        this.installTemplateCorrelationGmt = installTemplateCorrelationGmt;
    }

    public String getStepNode() {
        return stepNode;
    }

    public void setStepNode(String stepNode) {
        this.stepNode = stepNode;
    }

    public Integer getNetStatus() {
        return netStatus;
    }

    public void setNetStatus(Integer netStatus) {
        this.netStatus = netStatus;
    }

    public Integer getIsOrphan() {
        return isOrphan;
    }

    public void setIsOrphan(Integer isOrphan) {
        this.isOrphan = isOrphan;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}