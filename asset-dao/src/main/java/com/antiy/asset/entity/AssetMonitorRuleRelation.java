package com.antiy.asset.entity;

import com.antiy.common.base.BaseEntity;

/**
 * <p>资产监控规则与资产关系表</p>
 *
 * @author zhangyajun
 * @since 2020-03-02
 */

public class AssetMonitorRuleRelation extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一键
     */
    private String            uniqueId;
    /**
     * 资产主键
     */
    private String            assetId;
    /**
     * 规则唯一键
     */
    private String            ruleUniqueId;
    /**
     * 修改时间
     */
    private Long              gmtModified;
    /**
     * 创建时间
     */
    private Long              gmtCreate;

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getRuleUniqueId() {
        return ruleUniqueId;
    }

    public void setRuleUniqueId(String ruleUniqueId) {
        this.ruleUniqueId = ruleUniqueId;
    }

    public Long getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    @Override
    public String toString() {
        return "AssetMonitorRuleRelation{" + "uniqueId='" + uniqueId + '\'' + ", assetId='" + assetId + '\''
               + ", ruleUniqueId='" + ruleUniqueId + '\'' + ", gmtModified=" + gmtModified + ", gmtCreate=" + gmtCreate
               + '}';
    }
}