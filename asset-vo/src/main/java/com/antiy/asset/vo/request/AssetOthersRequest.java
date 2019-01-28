package com.antiy.asset.vo.request;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

/**
 * <p> AssetRequest 请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetOthersRequest extends BasicRequest implements ObjectValidator {

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
    @Valid
    private List<AssetGroupRequest> assetGroups;
    /**
     * 联系电话
     */
    @ApiModelProperty("联系电话")
    @Size(message = "联系电话必须为11位", max = 11, min = 11)
    @Pattern(regexp = "^1(3|4|5|7|8|9)\\d{9}$")
    private String                  contactTel;
    /**
     * 邮箱
     */
    @ApiModelProperty("邮箱")
    @Email(message = "邮箱不正确")
    private String                  email;
    /**
     * 资产编号
     */
    @ApiModelProperty("资产编号")
    @Size(message = "资产编号不能超过32位", max = 32)
    private String                  number;

    /**
     * 资产名称
     */
    @ApiModelProperty("资产名称")
    @NotNull(message = "资产名称不能为空")
    @Size(message = "资产名字不能超过32位", max = 32)
    private String                  name;
    /**
     * 序列号
     */
    @ApiModelProperty("序列号")
    @Size(message = "资产序列号不能超过32位", max = 32)
    private String                  serial;
    /**
     * 品类型号
     */
    @ApiModelProperty("品类型号")
    @Encode
    @NotBlank(message = "品类型号不能为空")
    private String                  categoryModel;

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
    @Size(message = "资产厂商不能超过32位", max = 32)
    private String                  manufacturer;



    /**
     * 责任人主键
     */
    @ApiModelProperty("责任人主键")
    @Encode
    private String                  responsibleUserId;

    /**
     * 父类资源Id
     */
    @ApiModelProperty("父类资源Id")
    @Encode
    private String                  parentId;

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
    @ApiModelProperty("资产准入状态:待设置，2已允许，3已禁止")
    private Integer                 admittanceStatus;
    /**
     * 首次入网时间
     */
    @ApiModelProperty("首次入网时间")
    private Long                    firstEnterNett;
    /**
     * 描述
     */
    @ApiModelProperty("描述")
    @Size(message = "描述不能超过128位", max = 128)
    private String            describle;

    @Override
    public String toString() {
        return "AssetOthersRequest{" +
                "id='" + id + '\'' +
                ", assetGroups=" + assetGroups +
                ", contactTel='" + contactTel + '\'' +
                ", email='" + email + '\'' +
                ", number='" + number + '\'' +
                ", name='" + name + '\'' +
                ", serial='" + serial + '\'' +
                ", categoryModel='" + categoryModel + '\'' +
                ", areaId='" + areaId + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", responsibleUserId='" + responsibleUserId + '\'' +
                ", parentId='" + parentId + '\'' +
                ", serviceLife=" + serviceLife +
                ", buyDate=" + buyDate +
                ", warranty=" + warranty +
                ", admittanceStatus=" + admittanceStatus +
                ", firstEnterNett=" + firstEnterNett +
                ", describle='" + describle + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<AssetGroupRequest> getAssetGroups() {
        return assetGroups;
    }

    public void setAssetGroups(List<AssetGroupRequest> assetGroups) {
        this.assetGroups = assetGroups;
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

    public String getCategoryModel() {
        return categoryModel;
    }

    public void setCategoryModel(String categoryModel) {
        this.categoryModel = categoryModel;
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

    public String getResponsibleUserId() {
        return responsibleUserId;
    }

    public void setResponsibleUserId(String responsibleUserId) {
        this.responsibleUserId = responsibleUserId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
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

    public Integer getAdmittanceStatus() {
        return admittanceStatus;
    }

    public void setAdmittanceStatus(Integer admittanceStatus) {
        this.admittanceStatus = admittanceStatus;
    }

    public Long getFirstEnterNett() {
        return firstEnterNett;
    }

    public void setFirstEnterNett(Long firstEnterNett) {
        this.firstEnterNett = firstEnterNett;
    }

    public String getDescrible() {
        return describle;
    }

    public void setDescrible(String describle) {
        this.describle = describle;
    }

    @Override
    public void validate() throws RequestParamValidateException {

    }
}