package com.antiy.asset.entity;

import com.antiy.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

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
    private String            name;
    /**
     * 模板编号
     */
    private String            numberCode;
    /**
     * 品类型号
     */
    private Integer           categoryModel;
    /**
     * 审核状态
     */
    private Integer           checkStatus;
    /**
     * 适用操作系统
     */
    private Long              operationSystem;
    /**
     * 操作系统名称
     */
    private String            operationSystemName;
    /**
     * 是否启用：0-禁用，1-启用
     */
    private Integer           enable;
    /**
     * 描述
     */
    private String            description;

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

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
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

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "AssetInstallTemplate{" + ", name=" + name + ", numberCode=" + numberCode + ", categoryModel="
               + categoryModel + ", checkStatus=" + checkStatus + ", operationSystem=" + operationSystem
               + ", operationSystemName=" + operationSystemName + ", enable=" + enable + ", description=" + description
               + "}";
    }
}