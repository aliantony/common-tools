package com.antiy.asset.vo.response;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @auther: zhangbing
 * @date: 2019/1/14 13:28
 * @description:
 */
@ApiModel(value = "软件详情信息")
public class AssetSoftwareDetailResponse extends AssetSoftwareResponse {

    @ApiModelProperty(value = "软件端口信息")
    private List<AssetPortProtocolResponse>    softwarePort;

    @ApiModelProperty(value = "软件许可")
    private List<AssetSoftwareLicenseResponse> softwareLicense;

    public List<AssetPortProtocolResponse> getSoftwarePort() {
        return softwarePort;
    }

    public void setSoftwarePort(List<AssetPortProtocolResponse> softwarePort) {
        this.softwarePort = softwarePort;
    }

    public List<AssetSoftwareLicenseResponse> getSoftwareLicense() {
        return softwareLicense;
    }

    public void setSoftwareLicense(List<AssetSoftwareLicenseResponse> softwareLicense) {
        this.softwareLicense = softwareLicense;
    }
}
