package com.antiy.asset.entity;

import com.antiy.common.base.BaseEntity;

/**
 * <p> 资产与资产组关系表 </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */

public class AssetGroupRelation extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 资产组主键
     */
    private Integer           assetGroupId;
    /**
     * 资产主键
     */
    private Integer           assetId;
    /**
     * 创建时间
     */
    private Long              gmtCreate;
    /**
     * 修改时间
     */
    private Long              gmtModified;
    /**
     * 备注
     */
    private String            memo;
    /**
     * 创建人
     */
    private Integer           createUser;
    /**
     * 修改人
     */
    private Integer           modifyUser;
    /**
     * 状态,1未删除,0已删除
     */
    private Integer           status;

    public Integer getAssetGroupId() {
        return assetGroupId;
    }

    public void setAssetGroupId(Integer assetGroupId) {
        this.assetGroupId = assetGroupId;
    }

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Long getGmtModified() {
        return System.currentTimeMillis();
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Integer getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(Integer modifyUser) {
        this.modifyUser = modifyUser;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AssetGroupRelation{" + ", assetGroupId=" + assetGroupId + ", assetId=" + assetId + ", gmtCreate="
               + gmtCreate + ", gmtModified=" + gmtModified + ", memo=" + memo + ", createUser=" + createUser
               + ", modifyUser=" + modifyUser + ", status=" + status + "}";
    }

    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }
}