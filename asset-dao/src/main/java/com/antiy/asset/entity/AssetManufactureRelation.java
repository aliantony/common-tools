package com.antiy.asset.entity;

import com.antiy.common.base.BaseEntity;

/**
 * <p>厂商与资产关系表</p>
 *
 * @author zhangyajun
 * @since 2020-03-05
 */

public class AssetManufactureRelation extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一键
     */
    private Long              uniqueId;
    /**
     * 业务主键
     */
    private Long              businessId;
    /**
     * 资产业务主键（来源asset_hard_soft_lib）
     */
    private Long              assetIdHardsoftLib;

    public Long getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(Long uniqueId) {
        this.uniqueId = uniqueId;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public Long getAssetIdHardsoftLib() {
        return assetIdHardsoftLib;
    }

    public void setAssetIdHardsoftLib(Long assetIdHardsoftLib) {
        this.assetIdHardsoftLib = assetIdHardsoftLib;
    }

    @Override
    public String toString() {
        return "AssetManufactureRelation{" + ", uniqueId=" + uniqueId + ", businessId=" + businessId
               + ", assetIdHardsoftLib=" + assetIdHardsoftLib + "}";
    }
}