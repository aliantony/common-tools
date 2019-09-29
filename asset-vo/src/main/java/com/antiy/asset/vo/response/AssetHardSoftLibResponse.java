package com.antiy.asset.vo.response;

import com.antiy.common.base.BaseResponse;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetHardSoftLibResponse 响应对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */
@ApiModel
public class AssetHardSoftLibResponse extends BaseResponse {
    /**
     * 业务主键
     */
    @ApiModelProperty("业务主键")
    private String  businessId;
    /**
     * 类型编号
     */
    @ApiModelProperty("类型编号")
    private Integer number;
    /**
     * 类型：a：应用软件 h:硬件 o:操作系统
     */
    @ApiModelProperty("类型：a：应用软件 h:硬件 o:操作系统")
    private String  type;
    /**
     * 类型名称
     */
    @ApiModelProperty("类型名称")
    private String  typeName;
    /**
     * 供应商
     */
    @ApiModelProperty("厂商")
    private String  supplier;
    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String  productName;
    /**
     * 版本号
     */
    @ApiModelProperty("版本号")
    private String  version;
    /**
     * 更新信息
     */
    @ApiModelProperty("更新信息")
    private String  upgradeMsg;
    /**
     * 系统版本
     */
    @ApiModelProperty("系统版本")
    private String  sysVersion;
    /**
     * 语言
     */
    @ApiModelProperty("语言")
    private String  language;
    /**
     * 软件版本
     */
    @ApiModelProperty("软件版本")
    private String  softVersion;
    /**
     * 软件平台
     */
    @ApiModelProperty("软件平台")
    private String  softPlatform;
    /**
     * 硬件平台
     */
    @ApiModelProperty("硬件平台")
    private String  hardPlatform;
    /**
     * 其他
     */
    @ApiModelProperty("其他")
    private String  other;
    /**
     * 是否入库：1已入库、2未入库
     */
    @ApiModelProperty("是否入库：1已入库、2未入库")
    private Integer isStorage;
    /**
     * cpe路径
     */
    @ApiModelProperty("cpe路径")
    private String  cpeUri;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String  memo;
    /**
     * 收录时间
     */
    @ApiModelProperty("收录时间")
    private Long    gmtCreate;

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUpgradeMsg() {
        return upgradeMsg;
    }

    public void setUpgradeMsg(String upgradeMsg) {
        this.upgradeMsg = upgradeMsg;
    }

    public String getSysVersion() {
        return sysVersion;
    }

    public void setSysVersion(String sysVersion) {
        this.sysVersion = sysVersion;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSoftVersion() {
        return softVersion;
    }

    public void setSoftVersion(String softVersion) {
        this.softVersion = softVersion;
    }

    public String getSoftPlatform() {
        return softPlatform;
    }

    public void setSoftPlatform(String softPlatform) {
        this.softPlatform = softPlatform;
    }

    public String getHardPlatform() {
        return hardPlatform;
    }

    public void setHardPlatform(String hardPlatform) {
        this.hardPlatform = hardPlatform;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public Integer getIsStorage() {
        return isStorage;
    }

    public void setIsStorage(Integer isStorage) {
        this.isStorage = isStorage;
    }

    public String getCpeUri() {
        return cpeUri;
    }

    public void setCpeUri(String cpeUri) {
        this.cpeUri = cpeUri;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}