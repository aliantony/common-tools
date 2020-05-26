package com.antiy.asset.vo.query;

import com.antiy.common.base.ObjectQuery;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * <p> AssetInstallTemplate 查询条件 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetInstallTemplateQuery extends ObjectQuery {
    @ApiModelProperty("基准模板主键id")
    private String baselineId;
    @ApiModelProperty("模板名称")
    private String name;
    @ApiModelProperty("模板编号")
    private String numberCode;
    @ApiModelProperty("适用资产类型")
    private Integer categoryModel;
    @ApiModelProperty("模板状态")
    private Integer currentStatus;
    @ApiModelProperty("适用操作系统编号")
    private Long operationSystem;
    @ApiModelProperty("适用操作系统集合")
    private List<Long> operationSystems;
    @ApiModelProperty("适用操作系统名称")
    private String operationSystemName;
    @ApiModelProperty("描述")
    private String description;
    @ApiModelProperty("创建时间")
    private Long gmtCreate;
    @ApiModelProperty("修改时间")
    private Long gmtModified;
    @ApiModelProperty("创建人")
    private String createUser;
    @ApiModelProperty("修改人")
    private String modifiedUser;
    @ApiModelProperty("模板状态")
    private Integer status;
    @ApiModelProperty("模板id")
    private String stringId;

    public List<Long> getOperationSystems() {
        return operationSystems;
    }

    public void setOperationSystems(List<Long> operationSystems) {
        this.operationSystems = operationSystems;
    }

    public String getBaselineId() {
        return baselineId;
    }

    public void setBaselineId(String baselineId) {
        this.baselineId = baselineId;
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

    public String getStringId() {
        return stringId;
    }

    public void setStringId(String stringId) {
        this.stringId = stringId;
    }

    @Override
    public String toString() {
        return "AssetInstallTemplateQuery{" +
                "baselineId='" + baselineId + '\'' +
                ", name='" + name + '\'' +
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