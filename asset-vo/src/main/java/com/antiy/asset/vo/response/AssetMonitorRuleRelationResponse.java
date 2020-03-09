package com.antiy.asset.vo.response;

import com.antiy.common.base.BaseResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetMonitorRuleRelationResponse 响应对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */
@ApiModel(description = "监控规则关联的资产")
public class AssetMonitorRuleRelationResponse extends BaseResponse {
    @ApiModelProperty("资产id,未加密")
    private String assetId;
    @ApiModelProperty("资产名称")
    private String name;
    @ApiModelProperty("资产编号")
    private String number;
    @ApiModelProperty("ip")
    private String ip;
    @ApiModelProperty("mac")
    private String mac;
    @ApiModelProperty("厂商")
    private String supplier;
    @ApiModelProperty("资产组")
    private String assetGroup;
    @ApiModelProperty("重要程度:1核心，2重要，3一般")
    private Integer importance;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getAssetGroup() {
        return assetGroup;
    }

    public void setAssetGroup(String assetGroup) {
        this.assetGroup = assetGroup;
    }

    public Integer getImportance() {
        return importance;
    }

    public void setImportance(Integer importance) {
        this.importance = importance;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }
}