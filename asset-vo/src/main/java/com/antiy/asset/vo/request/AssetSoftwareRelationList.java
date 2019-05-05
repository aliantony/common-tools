package com.antiy.asset.vo.request;

import com.antiy.common.base.BaseRequest;
import com.antiy.common.encoder.Encode;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

/**
 * 软件安装
 * @author lvliang
 */
public class AssetSoftwareRelationList extends BaseRequest {
    /**
     * 软件id
     */
    @Encode(message = "软件Id解密失败")
    @NotBlank(message = "软件Id不能为空")
    @ApiModelProperty("软件id")
    private String                    softwareId;
    /**
     * 安装方式
     */
    @ApiModelProperty("安装方式：1人工，2自动")
    @NotNull(message = "安装方式不能为空")
    @Max(value = 2, message = "安装方式不能大于2")
    @Min(value = 1, message = "安装方式不能小于1")
    private Integer                   installType;
    /**
     * 安装结果
     */
    @ApiModelProperty("安装结果")
    @Valid
    private List<AssetInstallRequest> assetInstallRequestList;
    /**
     * 安装状态
     */
    @ApiModelProperty("安装状态")
    private Integer                   installStatus;
    /**
     * 安装时间
     */
    @ApiModelProperty("安装时间")
    @Max(message = "时间超出范围",value = 9999999999999L)
    private Long                      installTime;

    public String getSoftwareId() {
        return softwareId;
    }

    public void setSoftwareId(String softwareId) {
        this.softwareId = softwareId;
    }

    public Integer getInstallType() {
        return installType;
    }

    public void setInstallType(Integer installType) {
        this.installType = installType;
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

    public List<AssetInstallRequest> getAssetInstallRequestList() {
        return assetInstallRequestList;
    }

    public void setAssetInstallRequestList(List<AssetInstallRequest> assetInstallRequestList) {
        this.assetInstallRequestList = assetInstallRequestList;
    }

    @Override
    public String toString() {
        return "AssetSoftwareRelationList{" + "softwareId='" + softwareId + '\'' + ", installType=" + installType
               + ", assetInstallRequestList=" + assetInstallRequestList + ", installStatus=" + installStatus
               + ", installTime=" + installTime + '}';
    }
}
