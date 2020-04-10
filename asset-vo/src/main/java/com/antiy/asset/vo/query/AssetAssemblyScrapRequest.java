package com.antiy.asset.vo.query;

import com.antiy.asset.vo.request.AssetAssemblyRequest;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.encoder.Encode;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class AssetAssemblyScrapRequest extends BaseRequest {

    @ApiModelProperty("资产id")
    @Encode
    private List<String> assetId;
    @ApiModelProperty("组件")
    List<AssetAssemblyRequest> assetAssemblyRequestList;

    @ApiModelProperty("资产id")
    private String  temporaryInfo;

    public String getTemporaryInfo() {
        return temporaryInfo;
    }

    public void setTemporaryInfo(String temporaryInfo) {
        this.temporaryInfo = temporaryInfo;
    }

    public List<String> getAssetId() {
        return assetId;
    }

    public void setAssetId(List<String> assetId) {
        this.assetId = assetId;
    }

    public List<AssetAssemblyRequest> getAssetAssemblyRequestList() {
        return assetAssemblyRequestList;
    }

    public void setAssetAssemblyRequestList(List<AssetAssemblyRequest> assetAssemblyRequestList) {
        this.assetAssemblyRequestList = assetAssemblyRequestList;
    }
}
