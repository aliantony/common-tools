package com.antiy.asset.vo.response;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetGroupRelationResponse 响应对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetGroupRelationResponse extends BaseResponse {
    /**
     * 资产ID
     */
    @ApiModelProperty("资产ID")
    private String                         assetId;

    /**
     * 网卡信息
     */
    @ApiModelProperty("资产名称")
    private String                         assetName;

    /**
     * 资产编号
     */
    @ApiModelProperty("资产编号")
    private String                         number;
    /**
     * 厂商
     */
    @ApiModelProperty("厂商")
    private String                         manufacturer;
    /**
     * 品类型号
     */
    @ApiModelProperty("品类型号")
    private String                         categoryModelName;
    /**
     * 网卡信息
     */
    @ApiModelProperty("网卡信息")
    private List<AssetNetworkCardResponse> networkCardResponseList;
    /**
     * 资产组名称
     */
    @ApiModelProperty("资产组名称")
    private String                         assetGroupName;
    /**
     * 资产状态名称
     */
    @ApiModelProperty("资产状态名称")
    private String                         assetStatusName;
    /**
     * 首次发现时间
     */
    @ApiModelProperty("首次发现时间")
    private Long                           gmtCreate;

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getCategoryModelName() {
        return categoryModelName;
    }

    public void setCategoryModelName(String categoryModelName) {
        this.categoryModelName = categoryModelName;
    }

    public List<AssetNetworkCardResponse> getNetworkCardResponseList() {
        return networkCardResponseList;
    }

    public void setNetworkCardResponseList(List<AssetNetworkCardResponse> networkCardResponseList) {
        this.networkCardResponseList = networkCardResponseList;
    }

    public String getAssetGroupName() {
        return assetGroupName;
    }

    public void setAssetGroupName(String assetGroupName) {
        this.assetGroupName = assetGroupName;
    }

    public String getAssetStatusName() {
        return assetStatusName;
    }

    public void setAssetStatusName(String assetStatusName) {
        this.assetStatusName = assetStatusName;
    }

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}