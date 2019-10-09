package com.antiy.asset.vo.request;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;
/**
 * <p> AssetRequest 请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetRequest extends BasicRequest implements ObjectValidator, Serializable {

    /**
     * id
     */
    @ApiModelProperty("id")
    @Encode
    private String                  id;
    /**
     * businessId
     */
    @ApiModelProperty("businessId")
    private String                  businessId;
    /**
     * id
     */
    @ApiModelProperty("基准模板id")
    @Encode
    private String                  baselineTemplateId;
    /**
     * id
     */
    @ApiModelProperty("装机模板id")
    @Encode
    private String                  installTemplateId;

    /**
     * 资产zu
     */
    @ApiModelProperty("资产组")
    private List<AssetGroupRequest> assetGroups;
    /**
     * 机房位置
     */
    @ApiModelProperty("机房位置")
    @Size(message = "机房位置不能超过30位", max = 30)
    private String                  houseLocation;

    /**
     * 资产编号
     */
    @ApiModelProperty("资产编号")
    @NotBlank(message = "资产编号不能为空")
    @Size(message = "资产编号应在1-30之间", max = 30, min = 1)
    private String                  number;

    /**
     * 资产名称
     */
    @ApiModelProperty("资产名称")
    @NotBlank(message = "资产名称不能为空")
    @Size(message = "资产名字应该在1-30之间", max = 30, min = 1)
    private String                  name;
    /**
     * 资产版本
     */
    @ApiModelProperty("资产版本")
    @NotBlank(message = "资产版本不能为空")
    private String                  version;
    /**
     * 序列号
     */
    @ApiModelProperty("序列号")
    @Size(message = "资产序列号不能超过30位", max = 30)
    private String                  serial;
    /**
     * NETWORK(2, "网络设备"), SAFETY(3, "安全设备"), STORAGE(4, "存储设备"), OTHER(5, "其它设备");
     */
    @ApiModelProperty("品类型号:1计算设备,2网络设备3安全设备4存储设备5其它设备")
    private Integer                 categoryModel;

    /**
     * 行政区划主键ID
     */
    @ApiModelProperty("行政区id")
    @Encode
    @NotBlank(message = "行政区域信息不能为空")
    private String                  areaId;
    /**
     * 厂商
     */
    @ApiModelProperty("厂商")
    @Size(message = "资产厂商不能超过80位", max = 80)
    private String                  manufacturer;
    /**
     * 资产状态：1-待登记，2-不予登记，3-待配置，4-待验证，5-待入网，6待检查，7-已入网，8-待退役，9-已退役
     */
    @ApiModelProperty("1-待登记，2-不予登记，3-模板待实施，4-待验证，5-待入网，6-已入网,7-待检查，8-待整改，9-变更中, 10-待退役，11-已退役")
    private Integer                 assetStatus;
    /**
     * 操作系统,如果type为IDS或者IPS则此字段存放软件版本信息
     */
    @ApiModelProperty("操作系统")
    private Long                  operationSystem;
    /**
     * 系统位数
     */
    @ApiModelProperty("系统位数")
    @Max(message = "系统位数不能超过64", value = 64)
    private Integer                 systemBit;

    /**
     * 安装方式
     */
    @ApiModelProperty("维护方式1人工2自动")
    @Max(message = "维护方式最大为2", value = 2)
    @Min(message = "维护方式最小为1", value = 1)
    private Integer                 installType;

    /**
     * 固件版本
     */
    @ApiModelProperty("固件版本")
    @Size(message = "固件版本不能超过30位", max = 30)
    private String                  firmwareVersion;
    /**
     * 软件版本
     */
    @ApiModelProperty("软件版本")
    @Size(message = "软件版本不能超过16位", max = 16)
    private String                  softwareVersion;
    /**
     * 设备uuid
     */
    @ApiModelProperty("设备uuid")
    @Size(message = "设备uuid不能超过64位", max = 64)
    private String                  uuid;
    /**
     * 责任人主键
     */
    @ApiModelProperty("责任人主键")
    @Encode
    @NotBlank(message = "责任人不能为空")
    private String                  responsibleUserId;

    /**
     * 上报来源,1-自动上报，2-人工上报
     */
    @ApiModelProperty("上报来源,1-自动上报，2-人工上报")
    // @NotNull(message = "上报来源不能为空")
    @Max(message = "上报来源不能大于2", value = 2)
    @Min(message = "上报来源不能小于1", value = 1)
    private Integer                 assetSource;
    /**
     * 1核心2重要3一般
     */
    @ApiModelProperty("1核心2重要3一般")
    @NotNull(message = "重要程度不能为空")
    @Max(message = "重要程度不能大于3", value = 3)
    @Min(message = "重要程度不能小于1", value = 1)
    private Integer                 importanceDegree;

    /**
     * 使用到期时间
     */
    @ApiModelProperty("使用到期时间")
    @NotNull(message = "使用到期时间不能为空")
    @Max(value = 9999999999999L, message = "时间超出范围")
    private Long                    serviceLife;
    /**
     * 购买日期
     */
    @ApiModelProperty("购买日期")
    @Max(value = 9999999999999L, message = "时间超出范围")
    private Long                    buyDate;
    /**
     * 保修期
     */
    @ApiModelProperty("保修期")
    @Size(message = "保修期大于0位不能超过30位", max = 30)
    private String                  warranty;
    /**
     * 资产准入状态
     */
    @ApiModelProperty("资产准入状态:待设置，2已允许，3已禁止")
    @Max(message = "资产准入状态不能大于3", value = 3)
    @Min(message = "资产准入状态不能小于1", value = 1)
    private Integer                 admittanceStatus;
    /**
     * 首次入网时间
     */
    @ApiModelProperty("首次入网时间")
    @Max(value = 9999999999999L, message = "时间超出范围")
    private Long                    firstEnterNett;
    /**
     * 描述
     */
    @ApiModelProperty("描述")
    @Size(message = "描述不能超过300个字符", max = 300)
    private String                  describle;

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

    public String getSoftwareVersion() {
        return softwareVersion;
    }

    public void setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion;
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
               + systemBit + ", installType=" + installType + ", firmwareVersion='" + firmwareVersion + '\''
               + ", softwareVersion='" + softwareVersion + '\'' + ", uuid='" + uuid + '\'' + ", responsibleUserId='"
               + responsibleUserId + '\'' + ", assetSource=" + assetSource + ", importanceDegree=" + importanceDegree
               + ", serviceLife=" + serviceLife + ", buyDate=" + buyDate + ", warranty='" + warranty + '\''
               + ", admittanceStatus=" + admittanceStatus + ", firstEnterNett=" + firstEnterNett + ", describle='"
               + describle + '\'' + '}';
    }

}