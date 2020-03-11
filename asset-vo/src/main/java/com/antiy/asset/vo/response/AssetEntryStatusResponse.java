package com.antiy.asset.vo.response;

import com.antiy.common.encoder.Encode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author liulusheng
 * @since 2020/3/5
 */
@ApiModel(description = "准入状态响应类，对接漏洞，补丁，配置")
public class AssetEntryStatusResponse {
    @ApiModelProperty("资产id,加密id")
    @Encode
    private String assetId;
    @ApiModelProperty("准入状态：1-以允许，2-已禁止")
    private int entryStatus;

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public int getEntryStatus() {
        return entryStatus;
    }

    public void setEntryStatus(int entryStatus) {
        this.entryStatus = entryStatus;
    }
}
