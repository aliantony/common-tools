package com.antiy.asset.vo.query;

import com.antiy.asset.vo.enums.AssetTypeEnum;
import com.antiy.common.base.ObjectQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p> HardSoftLib 查询条件 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */
@ApiModel
public class AssetHardSoftLibQuery extends ObjectQuery {
    /**
     * 资产类型
     */
    @ApiModelProperty("资产类型，HARD硬件，SOFT软件,OS操作系统")
    @NotNull(message = "资产类型不能为空")
    private AssetTypeEnum assetType;
    /**
     * 资产类型
     */
    @ApiModelProperty("资产类型:a,o,h")
    private String        type;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String        productName;

    /**
     * 厂商
     */
    @ApiModelProperty("厂商")
    private String        supplier;
    /**
     * 是否入库：1已入库、2未入库
     */
    @ApiModelProperty("是否入库：1已入库、2未入库")
    private Integer       isStorage;
    /**
     * 业务主键(组件，协议，服务),组件，服务，协议详情查询关联的硬件时才需传
     */
    @ApiModelProperty("业务主键(组件，协议，服务),组件，服务，协议详情查询关联的硬件/软件时才需传")
    private String        businessId;
    /**
     * 类型（组件assembly,协议protocol,依赖服务service）
     */
    @ApiModelProperty("类型（组件assembly,协议protocol,依赖服务dependService,提供服务supplyService）查询关联的硬件/软件时才需传")
    private String        sourceType;

    @ApiModelProperty("去重id")
    private List<String> removeBusinessIds;

    @ApiModelProperty("版本")
    private String version;

    /**
     * 系统版本
     */
    @ApiModelProperty("系统版本")
    private String        sysVersion;
    /**
     * 软件版本
     */
    @ApiModelProperty("软件版本")
    private String        softVersion;
    /**
     * 软件平台
     */
    @ApiModelProperty("软件平台")
    private String        softPlatform;
    /**
     * 硬件平台
     */
    @ApiModelProperty("硬件平台")
    private String        hardPlatform;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<String> getRemoveBusinessIds() {
        return removeBusinessIds;
    }

    public void setRemoveBusinessIds(List<String> removeBusinessIds) {
        this.removeBusinessIds = removeBusinessIds;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private List<Long> exceptIds;

    public List<Long> getExceptIds() {
        return exceptIds;
    }

    public void setExceptIds(List<Long> exceptIds) {
        this.exceptIds = exceptIds;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public Integer getIsStorage() {
        return isStorage;
    }

    public void setIsStorage(Integer isStorage) {
        this.isStorage = isStorage;
    }

    public AssetTypeEnum getAssetType() {
        return assetType;
    }

    public void setAssetType(AssetTypeEnum assetType) {
        this.assetType = assetType;
    }

    public String getSysVersion() {
        return sysVersion;
    }

    public void setSysVersion(String sysVersion) {
        this.sysVersion = sysVersion;
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
}