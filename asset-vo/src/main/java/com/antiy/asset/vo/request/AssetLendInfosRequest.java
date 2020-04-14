package com.antiy.asset.vo.request;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class AssetLendInfosRequest {
    /**
     * 资产id
     */
    @ApiModelProperty("资产id")
    private List<String> assetIdList;
    /**
     * 订单id
     */
    @ApiModelProperty("订单id")
    private String orderNumber;

    /**
     * 订单id
     */
    @ApiModelProperty("订单id")
    private Integer useId;


    public List<String> getAssetIdList() {
        return assetIdList;
    }

    public void setAssetIdList(List<String> assetIdList) {
        this.assetIdList = assetIdList;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Integer getUseId() {
        return useId;
    }

    public void setUseId(Integer useId) {
        this.useId = useId;
    }

    @Override
    public String toString() {
        return "AssetLendInfosRequest{" +
                "assetIdList=" + assetIdList +
                ", orderNumber='" + orderNumber + '\'' +
                ", useId=" + useId +
                '}';
    }
}
