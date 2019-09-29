package com.antiy.asset.vo.query;

import java.util.List;

import com.antiy.asset.vo.enums.AssetTypeEnum;
import com.antiy.common.base.ObjectQuery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

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
    @ApiModelProperty("资产类型，hard硬件，soft软件")
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
}