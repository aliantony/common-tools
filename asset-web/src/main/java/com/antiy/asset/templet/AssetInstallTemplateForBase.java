package com.antiy.asset.templet;

/**
 * @Filename: AssetInstallTemplateForBase Description:
 * @Version: 1.0
 * @Author: why
 * @Date: 2019/10/23
 */
public class AssetInstallTemplateForBase {
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
    private String categoryModel;
    /**
     * 模板状态
     */
    private String currentStatus;

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
    private String gmtCreate;
    /**
     * 修改时间
     */
    private String gmtModified;

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

    public String getCategoryModel() {
        return categoryModel;
    }

    public void setCategoryModel(String categoryModel) {
        this.categoryModel = categoryModel;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
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

    public String getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(String gmtModified) {
        this.gmtModified = gmtModified;
    }

}
