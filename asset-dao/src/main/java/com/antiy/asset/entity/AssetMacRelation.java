package com.antiy.asset.entity;


import com.antiy.common.base.BaseEntity;
/**
 * <p>资产-MAC关系表</p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */

public class AssetMacRelation extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 资产主表
     */
    private Integer           assetId;
    /**
     * MAC
     */
    private String            mac;
    /**
     * 状态：1-未删除,0-已删除
     */
    private Integer           status;


    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }


    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "AssetMacRelation{" + ", assetId=" + assetId + ", mac=" + mac + ", status=" + status + "}";
    }
}