package com.antiy.asset.vo.request;

import com.antiy.common.base.BaseRequest;
import com.antiy.common.encoder.Encode;
import io.swagger.annotations.ApiModelProperty;
import com.antiy.common.validation.ObjectValidator;
import com.antiy.common.exception.RequestParamValidateException;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Size;

/**
 * <p> AssetInstallTemplateRequest 请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetInstallTemplateRequest implements ObjectValidator {
    @ApiModelProperty("主键Id")
    // @Encode
    private String stringId;

    /**
     * 模板名称
     */
    @ApiModelProperty("模板名称")
    private String name;
    /**
     * 模板编号
     */
    @ApiModelProperty("模板编号")
    private String numberCode;
    /**
     * 品类型号
     */
    @ApiModelProperty("品类型号")
    private Integer categoryModel;
    /**
     * 审核状态
     */
    @ApiModelProperty("审核状态")
    private Integer checkStatus;
    /**
     * 适用操作系统
     */
    @ApiModelProperty("适用操作系统")
    private Long operationSystem;
    /**
     * 操作系统名称
     */
    @ApiModelProperty("操作系统名称")
    private String operationSystemName;
    /**
     * 是否启用：0-禁用，1-启用
     */
    @ApiModelProperty("是否启用：0-禁用，1-启用")
    // @Min(value = 0, message = "是否启用只能为0，1")
    // @Max(value = 1, message = "是否启用只能为0，1")
    private Integer enable;
    @ApiModelProperty("要更新的状态")
    @Range(message = "状态不合法", max = 4, min = 1)
    private Integer updateStatus;
    /**
     * 描述
     */
    @ApiModelProperty("描述")
    private String description;

    public String getStringId() {
        return stringId;
    }

    public void setStringId(String stringId) {
        this.stringId = stringId;
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

    public Integer getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(Integer updateStatus) {
        this.updateStatus = updateStatus;
    }

    public Integer getCurrentStatus() {
        return updateStatus;
    }

    public void setCurrentStatus(Integer updateStatus) {
        this.updateStatus = updateStatus;
    }

    public Integer getId() {
        return Integer.valueOf(stringId);
    }

    @Override
    public void validate() throws RequestParamValidateException {
    }

}