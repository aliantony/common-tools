package com.antiy.asset.vo.response;

import java.util.List;

public class SoftwareInstallResponse {
    /**
     * 软件信息
     */
    private AssetSoftwareResponse              assetSoftware;
    /**
     * 软件关联的资产
     */
    private List<AssetSoftwareInstallResponse> assetSoftwareInstallResponseList;

    public AssetSoftwareResponse getAssetSoftware() {
        return assetSoftware;
    }

    public void setAssetSoftware(AssetSoftwareResponse assetSoftware) {
        this.assetSoftware = assetSoftware;
    }

    public List<AssetSoftwareInstallResponse> getAssetSoftwareInstallResponseList() {
        return assetSoftwareInstallResponseList;
    }

    public void setAssetSoftwareInstallResponseList(List<AssetSoftwareInstallResponse> assetSoftwareInstallResponseList) {
        this.assetSoftwareInstallResponseList = assetSoftwareInstallResponseList;
    }
}
