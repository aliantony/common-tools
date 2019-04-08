package com.antiy.asset.vo.query;

import com.antiy.common.base.ObjectQuery;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class InstallQuery extends ObjectQuery implements ObjectValidator {

    @ApiModelProperty("综合查询")
    private String        multipleQuery;
    /**
     * 安装方式
     */
    @ApiModelProperty("安装方式")
    private Integer       installType;
    /**
     * 安装方式
     */
    @ApiModelProperty("配置方式")
    private Integer       configureStatus;

    @ApiModelProperty("区域id列表")
    @Encode
    private String[]      areaIds;

    /**
     * 安装状态
     */
    @ApiModelProperty("安装状态")
    private Integer       installStatus;
    /**
     * 资产状态
     */
    @ApiModelProperty("资产状态[列表]")
    private List<Integer> assetStatusList;

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

    @Override
    public void validate() throws RequestParamValidateException {

    }
}
