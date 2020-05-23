package com.antiy.asset.vo.response;

import com.antiy.asset.vo.enums.CommonTypeEnum;
import com.antiy.common.encoder.Encode;

import io.swagger.annotations.ApiModelProperty;

public class AssetSoftwareInstallResponse extends BaseResponse {
    /**
     * 主键
     */
    @ApiModelProperty("主键")
    @Encode
    private String id;
    /**
     * 资产ID
     */
    @ApiModelProperty("资产ID")
    @Encode
    private String assetId;
    /**
     * 软件类型
     */
    @ApiModelProperty("软件类型")
    private String type;
    /**
     * 软件类型名称
     */
    @ApiModelProperty("软件类型名称")
    private String typeName;

    /**
     * 软件ID
     */
    @ApiModelProperty("软件ID")
    private String softwareId;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String productName;

    /**
     * 厂商
     */
    @ApiModelProperty("厂商")
    private String supplier;
    /**
     * 版本
     */
    @ApiModelProperty("版本")
    private String version;
    /**
     * 软件平台
     */
    @ApiModelProperty("软件平台")
    private String softPlatform;

    /**
     * 创建时间 @
     */
    @ApiModelProperty("软件平台")
    private String gmtCreate;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
        this.typeName = CommonTypeEnum.getNameByCode(type);
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSoftPlatform() {
        return softPlatform;
    }

    public void setSoftPlatform(String softPlatform) {
        this.softPlatform = softPlatform;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getSoftwareId() {
        return softwareId;
    }

    public void setSoftwareId(String softwareId) {
        this.softwareId = softwareId;
    }
}
