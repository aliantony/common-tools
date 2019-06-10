package com.antiy.asset.vo.query;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.antiy.common.base.ObjectQuery;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;

import io.swagger.annotations.ApiModelProperty;

public class InstallQuery extends ObjectQuery implements ObjectValidator {

    @ApiModelProperty("综合查询")
    @Size(max = 30, message = "综合查询长度不能超过30个字符")
    private String        multipleQuery;

    @ApiModelProperty(value = "软件id", required = true)
    @NotNull
    @Encode
    private String        softwareId;
    /**
     * 安装方式
     */
    @ApiModelProperty("安装方式 1人工 2自动")
    private Integer       installType;
    /**
     * 安装方式
     */
    @ApiModelProperty("配置方式 1未配置，2配置中，3已配置")
    private Integer       configureStatus;

    @ApiModelProperty("区域id列表 不传")
    @Encode
    private String[]      areaIds;

    /**
     * 安装状态
     */
    @ApiModelProperty("安装状态 1失败、2成功，3安装中4未安装")
    private Integer       installStatus;
    /**
     * 资产状态
     */
    @ApiModelProperty("资产状态[列表] 不传")
    private List<Integer> assetStatusList;

    @ApiModelProperty("品类型号")
    @Encode
    private List<String>  categoryModels;

    private List<String> operationSystems;

    public List<String> getOperationSystems() {
        return operationSystems;
    }

    public void setOperationSystems(List<String> operationSystems) {
        this.operationSystems = operationSystems;
    }

    public String getMultipleQuery() {
        return multipleQuery;
    }

    public void setMultipleQuery(String multipleQuery) {
        this.multipleQuery = multipleQuery;
    }

    public Integer getInstallType() {
        return installType;
    }

    public void setInstallType(Integer installType) {
        this.installType = installType;
    }

    public Integer getConfigureStatus() {
        return configureStatus;
    }

    public void setConfigureStatus(Integer configureStatus) {
        this.configureStatus = configureStatus;
    }

    public String[] getAreaIds() {
        return areaIds;
    }

    public void setAreaIds(String[] areaIds) {
        this.areaIds = areaIds;
    }

    public Integer getInstallStatus() {
        return installStatus;
    }

    public void setInstallStatus(Integer installStatus) {
        this.installStatus = installStatus;
    }

    public List<Integer> getAssetStatusList() {
        return assetStatusList;
    }

    public void setAssetStatusList(List<Integer> assetStatusList) {
        this.assetStatusList = assetStatusList;
    }

    public String getSoftwareId() {
        return softwareId;
    }

    public void setSoftwareId(String softwareId) {
        this.softwareId = softwareId;
    }

    public List<String> getCategoryModels() {
        return categoryModels;
    }

    public void setCategoryModels(List<String> categoryModels) {
        this.categoryModels = categoryModels;
    }

    @Override
    public void validate() throws RequestParamValidateException {
    }
}
