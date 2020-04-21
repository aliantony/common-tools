package com.antiy.asset.vo.request;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.*;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetRequest 请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetRequest extends BasicRequest implements ObjectValidator, Serializable {

    @ApiModelProperty("id")
    @Encode
    private String                  id;

    @ApiModelProperty("businessId")
    private String                  businessId;

    @ApiModelProperty("基准模板id")
    private String                  baselineTemplateId;

    @ApiModelProperty("装机模板id")
    @Encode
    private String                  installTemplateId;

    @ApiModelProperty("是否孤岛设备：1、是 2、否")
    private Integer                 isOrphan;

    @ApiModelProperty("资产组")
    private List<AssetGroupRequest> assetGroups;

    @ApiModelProperty("机房位置")
    @Size(message = "机房位置不能超过30位", max = 30)
    private String                  houseLocation;

    @ApiModelProperty("资产编号")
    @NotBlank(message = "资产编号不能为空")
    @Size(message = "资产编号应在1-30之间", max = 30, min = 1)
    private String                  number;

    @ApiModelProperty("资产名称")
    @NotBlank(message = "资产名称不能为空")
    @Size(message = "资产名字应该在1-128之间", max = 128, min = 1)
    private String                  name;

    @ApiModelProperty("资产版本")
    @NotBlank(message = "资产版本不能为空")
    private String                  version;

    @ApiModelProperty("序列号")
    @Size(message = "资产序列号不能超过30位", max = 30)
    private String                  serial;

    @ApiModelProperty("品类型号")
    private Integer                 categoryModel;
    @ApiModelProperty("品类型号:1计算设备,2网络设备3安全设备4存储设备5其它设备")
    private Integer                 categoryModelType;
    @ApiModelProperty("行政区id")
    @Encode
    @NotBlank(message = "行政区域信息不能为空")
    private String                  areaId;

    @ApiModelProperty("厂商")
    @Size(message = "资产厂商不能超过80位", max = 80)
    private String                  manufacturer;

    @ApiModelProperty("1-待登记，2-不予登记，3-模板待实施，4-待验证，5-待入网，6-已入网,7-待检查，8-待整改，9-变更中, 10-待退役，11-已退役")
    private Integer                 assetStatus;
    /**
     * 操作系统,如果type为IDS或者IPS则此字段存放软件版本信息
     */
    @ApiModelProperty("操作系统")
    private Long                    operationSystem;

    @ApiModelProperty("操作系统名称")
    private String                  operationSystemName;

    @ApiModelProperty("系统位数")
    @Max(message = "系统位数不能超过64", value = 64)
    private Integer                 systemBit;

    @ApiModelProperty("维护方式1人工2自动")
    @Max(message = "维护方式最大为2", value = 2)
    @Min(message = "维护方式最小为1", value = 1)
    private Integer                 installType;

    @ApiModelProperty("stepNode")
    private String                  stepNode;

    @ApiModelProperty("设备uuid")
    @Size(message = "设备uuid不能超过64位", max = 64)
    private String                  uuid;

    @ApiModelProperty("责任人主键")
    @Encode
    @NotBlank(message = "责任人不能为空")
    private String                  responsibleUserId;

    @ApiModelProperty("上报来源,1-自动上报，2-人工上报")
    // @NotNull(message = "上报来源不能为空")
    @Max(message = "上报来源不能大于3", value = 3)
    @Min(message = "上报来源不能小于1", value = 1)
    private Integer                 assetSource;

    @ApiModelProperty("1核心2重要3一般")
    @NotNull(message = "重要程度不能为空")
    @Max(message = "重要程度不能大于3", value = 3)
    @Min(message = "重要程度不能小于1", value = 1)
    private Integer                 importanceDegree;

    @ApiModelProperty("使用到期时间")
    @NotNull(message = "使用到期时间不能为空")
    @Max(value = 9999999999999L, message = "时间超出范围")
    private Long                    serviceLife;

    @ApiModelProperty("购买日期")
    @Max(value = 9999999999999L, message = "时间超出范围")
    private Long                    buyDate;

    @ApiModelProperty("保修期")
    @Size(message = "保修期大于0位不能超过30位", max = 30)
    private String                  warranty;

    @ApiModelProperty("资产准入状态:1已允许，2已禁止")
    @Max(message = "资产准入状态不能大于2", value = 2)
    @Min(message = "资产准入状态不能小于1", value = 1)
    private Integer                 admittanceStatus;

    @ApiModelProperty("首次入网时间")
    @Max(value = 9999999999999L, message = "时间超出范围")
    private Long                    firstEnterNett;

    @ApiModelProperty("描述")
    @Size(message = "描述不能超过300个字符", max = 300)
    private String                  describle;

    @ApiModelProperty("网络连接：1在线，2离线，3未知")
    private Integer                 netStatus;

    @ApiModelProperty("物理位置")
    private String                  location;
    @ApiModelProperty("机器名")
    private String                  machineName;
    @ApiModelProperty("是否涉密：1涉密，0-不涉密")
    private Integer                 isSecrecy;
    @ApiModelProperty("国资码")
    private String                  code;
    /**
     * 网络类型:1红网，2篮网
     */
    @ApiModelProperty("网络类型:1红网，2篮网")
    @Encode
    private String                  netType;
    /**
     * 是否借用：1-借用，2-未借用
     */
    @ApiModelProperty("是否可借用：1-可借用，2-不可借用")
    private Integer                 isBorrow;
    /**
     * key
     */
    @ApiModelProperty("key")
    private String                  key;
    /**
     * 装机时间
     */
    @ApiModelProperty("装机时间")
    private Long                    installDate;
    /**
     * 启用时间
     */
    @ApiModelProperty("启用时间")
    private Long                    activiateDate;
    /**
     * 到期提醒
     */
    @ApiModelProperty("到期提醒")
    private Long                    expirationReminder;

    public Integer getCategoryModelType() {
        return categoryModelType;
    }

    public void setCategoryModelType(Integer categoryModelType) {
        this.categoryModelType = categoryModelType;
    }

    public String getNetType() {
        return netType;
    }

    public void setNetType(String netType) {
        this.netType = netType;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getNetStatus() {
        return netStatus;
    }

    public void setNetStatus(Integer netStatus) {
        this.netStatus = netStatus;
    }

    public Long getFirstEnterNett() {
        return firstEnterNett;
    }

    public void setFirstEnterNett(Long firstEnterNett) {
        this.firstEnterNett = firstEnterNett;
    }

    public String getNumber() {
        return number;
    }

    public Integer getAdmittanceStatus() {
        return admittanceStatus;
    }

    public void setAdmittanceStatus(Integer admittanceStatus) {
        this.admittanceStatus = admittanceStatus;
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

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
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

    public Long getOperationSystem() {
        return operationSystem;
    }

    public void setOperationSystem(Long operationSystem) {
        this.operationSystem = operationSystem;
    }

    public Integer getSystemBit() {
        return systemBit;
    }

    public void setSystemBit(Integer systemBit) {
        this.systemBit = systemBit;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getResponsibleUserId() {
        return responsibleUserId;
    }

    public void setResponsibleUserId(String responsibleUserId) {
        this.responsibleUserId = responsibleUserId;
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

    public Integer getCategoryModel() {
        return categoryModel;
    }

    public void setCategoryModel(Integer categoryModel) {
        this.categoryModel = categoryModel;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHouseLocation() {
        return houseLocation;
    }

    public void setHouseLocation(String houseLocation) {
        this.houseLocation = houseLocation;
    }

    public List<AssetGroupRequest> getAssetGroups() {
        return assetGroups;
    }

    public void setAssetGroups(List<AssetGroupRequest> assetGroups) {
        this.assetGroups = assetGroups;
    }

    @Override
    public void validate() throws RequestParamValidateException {
    }

    public Integer getInstallType() {
        return installType;
    }

    public void setInstallType(Integer installType) {
        this.installType = installType;
    }

    public String getDescrible() {
        return describle;
    }

    public void setDescrible(String describle) {
        this.describle = describle;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getInstallTemplateId() {
        return installTemplateId;
    }

    public void setInstallTemplateId(String installTemplateId) {
        this.installTemplateId = installTemplateId;
    }

    public String getBaselineTemplateId() {
        return baselineTemplateId;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public void setBaselineTemplateId(String baselineTemplateId) {
        this.baselineTemplateId = baselineTemplateId;
    }

    @Override
    public String toString() {
        return "AssetRequest{" + "id='" + id + '\'' + ", businessId='" + businessId + '\'' + ", baselineTemplateId='"
               + baselineTemplateId + '\'' + ", installTemplateId='" + installTemplateId + '\'' + ", assetGroups="
               + assetGroups + ", houseLocation='" + houseLocation + '\'' + ", number='" + number + '\'' + ", name='"
               + name + '\'' + ", version='" + version + '\'' + ", serial='" + serial + '\'' + ", categoryModel='"
               + categoryModel + '\'' + ", areaId='" + areaId + '\'' + ", manufacturer='" + manufacturer + '\''
               + ", assetStatus=" + assetStatus + ", operationSystem='" + operationSystem + '\'' + ", systemBit="
               + systemBit + ", installType=" + installType + ", uuid='" + uuid + '\'' + ", responsibleUserId='"
               + responsibleUserId + '\'' + ", assetSource=" + assetSource + ", importanceDegree=" + importanceDegree
               + ", serviceLife=" + serviceLife + ", buyDate=" + buyDate + ", warranty='" + warranty + '\''
               + ", admittanceStatus=" + admittanceStatus + ", firstEnterNett=" + firstEnterNett + ", describle='"
               + describle + '\'' + '}';
    }

    public String getStepNode() {
        return stepNode;
    }

    public void setStepNode(String stepNode) {
        this.stepNode = stepNode;
    }

    public String getOperationSystemName() {
        return operationSystemName;
    }

    public void setOperationSystemName(String operationSystemName) {
        this.operationSystemName = operationSystemName;
    }

    public Integer getIsOrphan() {
        return isOrphan;
    }

    public void setIsOrphan(Integer isOrphan) {
        this.isOrphan = isOrphan;
    }
}