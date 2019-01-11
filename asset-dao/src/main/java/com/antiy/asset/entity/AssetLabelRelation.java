package com.antiy.asset.entity;

import com.antiy.common.base.BaseEntity;

/**
 * <p> 资产标签关系表 </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */

public class AssetLabelRelation extends BaseEntity {

    /**
     * 资产主键
     */
    private Integer assetId;
    /**
     * 标签主键
     */
    private Integer assetLabelId;
    /**
     * 创建时间
     */
    private Long    gmtCreate;
    /**
     * 更新时间
     */
    private Long    gmtModified;
    /**
     * 备注
     */
    private String  memo;
    /**
     * 创建人
     */
    private Integer createUser;
    /**
     * 修改人
     */
    private Integer modifyUser;
    /**
     * 状态,1未删除,0已删除
     */
    private Integer status;

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }

    public Integer getAssetLabelId() {
        return assetLabelId;
    }

    public void setAssetLabelId(Integer assetLabelId) {
        this.assetLabelId = assetLabelId;
    }

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Long getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
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
        return "AssetLabelRelation{" + ", assetId=" + assetId + ", assetLabelId=" + assetLabelId + ", gmtCreate="
               + gmtCreate + ", gmtModified=" + gmtModified + ", memo=" + memo + ", createUser=" + createUser
               + ", modifyUser=" + modifyUser + ", status=" + status + "}";
    }
}