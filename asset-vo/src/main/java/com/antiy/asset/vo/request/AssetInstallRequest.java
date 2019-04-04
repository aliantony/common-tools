package com.antiy.asset.vo.request;

import com.antiy.common.base.BaseRequest;
import com.antiy.common.encoder.Encode;

import javax.validation.constraints.NotBlank;

/**
 * 安装结果
 * @author lvliang
 */
public class AssetInstallRequest extends BaseRequest {
    /**
     * 资产id
     */
    @Encode
    @NotBlank
    private String  assetId;
    /**
     * 配置状态
     */
    @NotBlank
    private Integer configureStatus;
    /**
     * 安装状态
     */
    private Integer installStatus;
    /**
     * 安装时间
     */
    private Long    installTime;

    public Integer getConfigureStatus() {
        return configureStatus;
    }

    public void setConfigureStatus(Integer configureStatus) {
        this.configureStatus = configureStatus;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public Integer getInstallStatus() {
        return installStatus;
    }

    public void setInstallStatus(Integer installStatus) {
        this.installStatus = installStatus;
    }

    public Long getInstallTime() {
        return installTime;
    }

    public void setInstallTime(Long installTime) {
        this.installTime = installTime;
    }

    @Override
    public String toString() {
        return "AssetInstallRequest{" + "assetId='" + assetId + '\'' + ", configureStatus='" + configureStatus + '\''
               + ", installStatus=" + installStatus + ", installTime=" + installTime + '}';
    }
}
