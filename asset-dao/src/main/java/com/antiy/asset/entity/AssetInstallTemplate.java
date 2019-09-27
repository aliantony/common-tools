package com.antiy.asset.entity;

import com.antiy.common.base.BaseEntity;

/**
 * <p>装机模板</p>
 *
 * @author zhangyajun
 * @since 2019-09-16
 */

public class AssetInstallTemplate extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 模板名称
     */
    private String name;
    /**
     * 模板编号
     */
    private String numberCode;
    /**
     * 品类型号
     */
    private Integer categoryModel;
    /**
     * 模板状态
     */
    private Integer currentStatus;
    /**
     * 适用操作系统
     */
    private Long operationSystem;
    /**
     * 操作系统名称
     */
    private String operationSystemName;

    /**
     * 描述
     */
    private String description;
    /**
     * 创建时间
     */
    private Long              gmtCreate;
    /**
     * 修改时间
     */
    private Long              gmtModified;
    /**
     * 创建人
     */
    private String            createUser;
    /**
     * 修改人
     */
    private String            modifiedUser;

    /**
     * 状态：1-正常，0-删除
     */
    private Integer           status;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumberCode() {
        return numberCode;
    }

    public void setNumberCode(String numberCode) {
        this.numberCode = numberCode;
    }

    public Integer getCategoryModel() {
        return categoryModel;
    }

    public void setCategoryModel(Integer categoryModel) {
        this.categoryModel = categoryModel;
    }

    public Integer getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(Integer currentStatus) {
        this.currentStatus = currentStatus;
    }

    public Long getOperationSystem() {
        return operationSystem;
    }

    public void setOperationSystem(Long operationSystem) {
        this.operationSystem = operationSystem;
    }

    public String getOperationSystemName() {
        return operationSystemName;
    }

    public void setOperationSystemName(String operationSystemName) {
        this.operationSystemName = operationSystemName;
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
        return gmtModified;
    }

    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getModifiedUser() {
        return modifiedUser;
    }

    public void setModifiedUser(String modifiedUser) {
        this.modifiedUser = modifiedUser;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AssetInstallTemplate{" +
                "name='" + name + '\'' +
                ", numberCode='" + numberCode + '\'' +
                ", categoryModel=" + categoryModel +
                ", currentStatus=" + currentStatus +
                ", operationSystem=" + operationSystem +
                ", operationSystemName='" + operationSystemName + '\'' +
                ", description='" + description + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", createUser='" + createUser + '\'' +
                ", modifiedUser='" + modifiedUser + '\'' +
                ", status=" + status +
                '}';
    }
}