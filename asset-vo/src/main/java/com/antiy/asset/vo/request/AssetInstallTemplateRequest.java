package com.antiy.asset.vo.request;

import com.antiy.common.base.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import com.antiy.common.validation.ObjectValidator;
import com.antiy.common.exception.RequestParamValidateException;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * <p> AssetInstallTemplateRequest 请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetInstallTemplateRequest extends BaseRequest implements ObjectValidator {

    /**
     * 模板名称
     */
    @ApiModelProperty("模板名称")
    private String  name;
    /**
     * 模板编号
     */
    @ApiModelProperty("模板编号")
    private String  numberCode;
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
    private Long    operationSystem;
    /**
     * 操作系统名称
     */
    @ApiModelProperty("操作系统名称")
    private String  operationSystemName;
    /**
     * 是否启用：0-禁用，1-启用
     */
    @ApiModelProperty("是否启用：0-禁用，1-启用")
    @Min(value = 0, message = "是否启用只能为0，1")
    @Max(value = 1, message = "是否启用只能为0，1")
    private Integer enable;
    /**
     * 描述
     */
    @ApiModelProperty("描述")
    private String  description;

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
    public void validate() throws RequestParamValidateException {
    }

}