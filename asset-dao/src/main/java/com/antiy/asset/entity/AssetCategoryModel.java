package com.antiy.asset.entity;

import com.antiy.common.base.BaseEntity;
import com.antiy.common.encoder.Encode;

/**
 * <p> 品类型号表 </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */

public class AssetCategoryModel extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    private String            name;
    /**
     * 类型:1-品类，2-型号
     */
    private Integer           type;
    /**
     * 资产类型:1软件，2硬件
     */
    private Integer           assetType;
    /**
     * 父ID
     */
    private String           parentId;
    /**
     * 描述
     */
    private String            description;
    /**
     * 是否系统默认：0系统1自定义
     */
    private Integer           isDefault;
    /**
     * 创建时间
     */
    private Long              gmtCreate;
    /**
     * 更新时间
     */
    private Long              gmtModified;
    /**
     * 备注
     */
    private String            memo;
    /**
     * 创建人
     */
    private String           createUser;
    /**
     * 修改人
     */
    private String           modifyUser;
    /**
     * 状态,1未删除,0已删除
     */
    private Integer           status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getAssetType() {
        return assetType;
    }

    public void setAssetType(Integer assetType) {
        this.assetType = assetType;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }

    @Override
    public String toString() {
        return "AssetCategoryModel{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", assetType=" + assetType +
                ", parentId='" + parentId + '\'' +
                ", description='" + description + '\'' +
                ", isDefault=" + isDefault +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", memo='" + memo + '\'' +
                ", createUser='" + createUser + '\'' +
                ", modifyUser='" + modifyUser + '\'' +
                ", status=" + status +
                '}';
    }
}