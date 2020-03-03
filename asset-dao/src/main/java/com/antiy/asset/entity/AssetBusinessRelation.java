package com.antiy.asset.entity;


import com.antiy.common.base.BaseEntity;
import com.antiy.common.encoder.Encode;

/**
 * <p></p>
 *
 * @author zhangyajun
 * @since 2020-02-17
 */

public class AssetBusinessRelation extends BaseEntity {


private static final long serialVersionUID = 1L;

    /**
    *  业务主键id
    */
    private Integer assetBusinessId;
    /**
    *  资产主键id
    */
    @Encode
    private String assetId;
    /**
    *  业务影响：1-高，2-中，3-低
    */
    private Integer businessInfluence;
    /**
    *  创建时间
    */
    private Long gmtCreate;
    /**
    *  更新时间
    */
    private Long gmtModified;
    /**
    *  创建人
    */
    private Integer createUser;
    /**
    *  修改人
    */
    private Integer modifyUser;
    /**
    *  状态：1-未删除,0-已删除
    */
    private Integer status;

    /**
     * 来源 来源： 1--业务关联  2--资产登记
     *
     */
    private Integer source;

    private String uniqueId;

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public Integer getAssetBusinessId() {
        return assetBusinessId;
    }

    public void setAssetBusinessId(Integer assetBusinessId) {
        this.assetBusinessId = assetBusinessId;
    }


    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public Integer getBusinessInfluence() {
        return businessInfluence;
    }

    public void setBusinessInfluence(Integer businessInfluence) {
        this.businessInfluence = businessInfluence;
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

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }
}