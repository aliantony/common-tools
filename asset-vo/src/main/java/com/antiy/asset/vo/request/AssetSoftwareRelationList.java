package com.antiy.asset.vo.request;

import com.antiy.common.base.BaseRequest;
import com.antiy.common.encoder.Encode;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 软件安装
 * @author lvliang
 */
public class AssetSoftwareRelationList extends BaseRequest {
    /**
     * 软件id
     */
    @Encode
    @NotBlank
    @ApiModelProperty("软件id")
    private String                    softwareId;
    /**
     * 安装方式
     */
    @ApiModelProperty("安装方式")
    @NotNull
    private Integer                    installType;
    /**
     * 安装结果
     */
    @ApiModelProperty("安装结果")
    private List<AssetInstallRequest> assetInstallRequestList;

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

    public List<AssetInstallRequest> getAssetInstallRequestList() {
        return assetInstallRequestList;
    }

    public void setAssetInstallRequestList(List<AssetInstallRequest> assetInstallRequestList) {
        this.assetInstallRequestList = assetInstallRequestList;
    }

    @Override
    public String toString() {
        return "AssetSoftwareRelationList{" + "softwareId='" + softwareId + '\'' + ", installType='" + installType
               + '\'' + ", assetInstallRequestList=" + assetInstallRequestList + '}';
    }
}
