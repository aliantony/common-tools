package com.antiy.asset.entity;


import com.antiy.common.base.BaseEntity;

public class AssetOaOrderHandle extends BaseEntity {

  private static final long serialVersionUID = 1L;

  private String orderNumber;
  /**
   * 资产id
   */
  private String assetId;


  public String getOrderNumber() {
    return orderNumber;
  }

  public void setOrderNumber(String orderNumber) {
    this.orderNumber = orderNumber;
  }

  public String getAssetId() {
    return assetId;
  }

  public void setAssetId(String assetId) {
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
