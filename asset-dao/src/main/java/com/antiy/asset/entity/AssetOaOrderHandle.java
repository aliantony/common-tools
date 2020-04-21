package com.antiy.asset.entity;


import com.antiy.common.base.BaseEntity;

public class AssetOaOrderHandle extends BaseEntity {

  private static final long serialVersionUID = 1L;

  private String orderNumber;
  /**
   * 资产id
   */
  private Integer assetId;


  public String getOrderNumber() {
    return orderNumber;
  }

  public void setOrderNumber(String orderNumber) {
    this.orderNumber = orderNumber;
  }

  public Integer getAssetId() {
    return assetId;
  }

  public void setAssetId(Integer assetId) {
    this.assetId = assetId;
  }

  @Override
  public String toString() {
    return "AssetOaOrderHandle{" +
            ", orderNumber=" + orderNumber +
            ", assetId=" + assetId +
            "}";
  }

}
