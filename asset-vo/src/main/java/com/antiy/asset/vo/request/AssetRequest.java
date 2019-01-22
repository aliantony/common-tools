package com.antiy.asset.vo.request;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p> AssetRequest 请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetRequest extends BasicRequest implements ObjectValidator {

    /**
     * id
     */
    @ApiModelProperty("id")
    @Encode
    private String                  id;
    /**
     * 资产zu
     */
    @ApiModelProperty("资产组")
    private List<AssetGroupRequest> assetGroup;
    /**
     * 机房位置
     */
    @ApiModelProperty("机房位置")
    private String                  houseLocation;
    /**
     * 联系电话
     */
    @ApiModelProperty("联系电话")
    private String                  contactTel;
    /**
     * 邮箱
     */
    @ApiModelProperty("邮箱")
    @Email
    private String                  email;
    /**
     * 资产编号
     */
    @ApiModelProperty("资产编号")
    private String                  number;
    /**
     * 资产类型:1台式办公机,2便携式办公机,3服务器虚拟终,4移动设备,4ATM机,5工控上位机,6路由器,7交换机,8防火墙,9IDS,10IPS,
     */
    @ApiModelProperty("资产类型:1台式办公机,2便携式办公机,3服务器虚拟终,4移动设备,4ATM机,5工控上位机,6路由器,7交换机,8防火墙,9IDS,10IPS,")
    private Integer                 type;
    /**
     * 资产名称
     */
    @ApiModelProperty("资产名称")
    @NotNull
    private String                  name;
    /**
     * 序列号
     */
    @ApiModelProperty("序列号")
    @NotNull
    private String                  serial;
    /**
     * 品类型号
     */
    @ApiModelProperty("品类型号")
    private Integer                 categoryModel;

    /**
     * 行政区划主键ID
     */
    @ApiModelProperty("行政区id")
    private Integer                 areaId;
    /**
     * 厂商
     */
    @ApiModelProperty("厂商")
    private String                  manufacturer;
    /**
     * 资产状态：1-待登记，2-不予登记，3-待配置，4-待验证，5-待入网，6-已入网，7-待退役，8-已退役
     */
    @ApiModelProperty("资产状态：1-待登记，2-不予登记，3-待配置，4-待验证，5-待入网，6-已入网，7-待退役，8-已退役")
    private Integer                 assetStatus;
    /**
     * 操作系统,如果type为IDS或者IPS则此字段存放软件版本信息
     */
    @ApiModelProperty("操作系统,如果type为IDS或者IPS则此字段存放软件版本信息")
    private String                  operationSystem;
    /**
     * 系统位数
     */
    @ApiModelProperty("系统位数")
    private Integer                 systemBit;
    /**
     * 物理位置
     */
    @ApiModelProperty("物理位置")
    private String                  location;

    /**
     * 固件版本
     */
    @ApiModelProperty("固件版本")
    private String                  firmwareVersion;
    /**
     * 设备uuid
     */
    @ApiModelProperty("设备uuid")
    private String                  uuid;
    /**
     * 责任人主键
     */
    @ApiModelProperty("责任人主键")
    @NotNull
    private Integer                 responsibleUserId;

    /**
     * 上报来源,1-自动上报，2-人工上报
     */
    @ApiModelProperty("上报来源,1-自动上报，2-人工上报")
    @NotNull
    private Integer                 assetSource;
    /**
     * 1核心2重要3一般
     */
    @ApiModelProperty("1核心2重要3一般")
    @NotNull
    private Integer                 importanceDegree;

    /**
     * 父类资源Id
     */
    @ApiModelProperty("父类资源Id")
    private Integer                 parentId;
    /**
     * 所属标签ID和名称列表JSON串
     */
    @ApiModelProperty("所属标签ID和名称列表JSON串")
    private String                  tags;
    /**
     * 是否入网,0表示未入网,1表示入网
     */
    @ApiModelProperty("是否入网,0表示未入网,1表示入网")
    private Boolean                 isInnet;
    /**
     * 使用到期时间
     */
    @ApiModelProperty("使用到期时间")
    private Long                    serviceLife;
    /**
     * 制造日期
     */
    @ApiModelProperty("制造日期")
    private Long                    buyDate;
    /**
     * 保修期
     */
    @ApiModelProperty("保修期")
    private Long                    warranty;
    /**
     * 资产准入状态
     */
    @ApiModelProperty("资产准入状态")
    private Integer                 admittanceStatus;

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

    public Integer getCategoryModel() {
        return categoryModel;
    }

    public void setCategoryModel(Integer categoryModel) {
        this.categoryModel = categoryModel;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void validate() throws RequestParamValidateException {

    }

    public List<AssetGroupRequest> getAssetGroup() {
        return assetGroup;
    }

    public void setAssetGroup(List<AssetGroupRequest> assetGroup) {
        this.assetGroup = assetGroup;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }

    public String getHouseLocation() {
        return houseLocation;
    }

    public void setHouseLocation(String houseLocation) {
        this.houseLocation = houseLocation;
    }
}