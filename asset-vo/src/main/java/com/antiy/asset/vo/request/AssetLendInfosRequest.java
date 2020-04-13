package com.antiy.asset.vo.request;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class AssetLendInfosRequest {
    /**
     *  资产id
     */
    @ApiModelProperty("资产id")
    private List<String> assetIdList;
    /**
     *  订单id
     */
    @ApiModelProperty("订单id")
    private String orderNumber;

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

    @Override
    public String toString() {
        return "AssetLendInfosRequest{" +
                "assetIdList=" + assetIdList +
                ", orderNumber='" + orderNumber + '\'' +
                '}';
    }
}
