package com.antiy.asset.vo.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.antiy.common.base.BaseRequest;
import com.antiy.common.encoder.Encode;

import io.swagger.annotations.ApiModelProperty;

/**
 * 安装结果
 * @author lvliang
 */
public class AssetInstallRequest extends BaseRequest {
    /**
     * 资产id
     */
    @Encode(message = "资产Id解密失败")
    @NotBlank(message = "资产Id不能为空")
    @ApiModelProperty("资产id")
    private String  assetId;
    /**
     * 配置状态
     */
    @NotNull(message = "配置状态不能为空")
    @ApiModelProperty("配置状态")
    private Integer configureStatus;

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

    @Override
    public String toString() {
        return "AssetInstallRequest{" + "assetId='" + assetId + '\'' + ", configureStatus=" + configureStatus + '}';
    }
}
