package com.antiy.asset.entity;


import com.antiy.common.base.BaseEntity;

/**
 * <p>出借订单拒绝表</p>
 *
 * @author shenliang
 * @since 2020-04-09
 */

public class AssetOaOrderLend extends BaseEntity {


private static final long serialVersionUID = 1L;

    /**
    *  订单表编号
    */
    private String orderNumber;
    /**
    *  是否拒绝出借，1是0否
    */
    private Integer lendStatus;
    /**
    *  拒绝原因 
    */
    private String refuseReason;



    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
    this.orderNumber = orderNumber;
    }


    public Integer getLendStatus() {
        return lendStatus;
    }

    public void setLendStatus(Integer lendStatus) {
    this.lendStatus = lendStatus;
    }


    public String getRefuseReason() {
        return refuseReason;
    }

    public void setRefuseReason(String refuseReason) {
    this.refuseReason = refuseReason;
    }


    @Override
    public String toString() {
            return "AssetOaOrderLend{" +
                        ", orderNumber=" + orderNumber +
                        ", lendStatus=" + lendStatus +
                        ", refuseReason=" + refuseReason +
            "}";
    }
}