package com.antiy.asset.entity;


import com.antiy.common.base.BaseEntity;
/**
 * <p>资产-IP关系表</p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */

public class AssetIpRelation extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 资产主表
     */
    private Integer           assetId;
    /**
     * IP
     */
    private String            ip;
    /**
     * 网口
     */
    private Integer           net;
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


    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }


    public Integer getNet() {
        return net;
    }

    public void setNet(Integer net) {
        this.net = net;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "AssetIpRelation{" + ", assetId=" + assetId + ", ip=" + ip + ", net=" + net + ", status=" + status + "}";
    }
}