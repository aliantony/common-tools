package com.antiy.asset.entity;


import com.antiy.common.base.BaseEntity;

/**
 * <p>保存最新模板与资产关联关系</p>
 *
 * @author gemingrun
 * @since 2019-09-30
 */

public class BaselineAssetTemplate extends BaseEntity {


    private static final long serialVersionUID = 1L;

    /**
     * 资产id
     */
    private Integer assetId;
    /**
     * 模板id
     */
    private Integer templateHistId;
    /**
     * 模板名
     */
    private String templateHistName;
    /**
     * 关联时间
     */
    private Long gmtCreate;
    /**
     * 关联人
     */
    private Integer createUser;
    /**
     * 是否是资产关联的最新模板: 1是，0否
     */
    private Integer isNew;

    /**
     * 针对模板扫描处,进行变更按钮隐现判断: 1变更中，0否
     */
    private Integer isChange;


    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }


    public Integer getTemplateHistId() {
        return templateHistId;
    }

    public void setTemplateHistId(Integer templateHistId) {
        this.templateHistId = templateHistId;
    }


    public String getTemplateHistName() {
        return templateHistName;
    }

    public void setTemplateHistName(String templateHistName) {
        this.templateHistName = templateHistName;
    }


    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Integer getIsNew() {
        return isNew;
    }

    public void setIsNew(Integer isNew) {
        this.isNew = isNew;
    }

    @Override
    public String toString() {
        return "BaselineAssetTemplate{" +
                ", assetId=" + assetId +
                ", templateHistId=" + templateHistId +
                ", templateHistName=" + templateHistName +
                ", gmtCreate=" + gmtCreate +
                ", createUser=" + createUser +
                ", isNew=" + isNew +
                "}";
    }

    public Integer getIsChange() {
        return isChange;
    }

    public void setIsChange(Integer isChange) {
        this.isChange = isChange;
    }
}