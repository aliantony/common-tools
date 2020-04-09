package com.antiy.asset.entity;


import com.antiy.common.base.BaseEntity;

/**
 * <p>订单申请信息表</p>
 *
 * @author shenliang
 * @since 2020-04-07
 */

public class AssetOaOrderApply extends BaseEntity {


    private static final long serialVersionUID = 1L;

    /**
     * 订单表id
     */
    private String orderNumber;
    /**
     * 申请人id
     */
    private Integer applyUserId;
    /**
     * 申请人姓名
     */
    private String applyUserName;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Integer getApplyUserId() {
        return applyUserId;
    }

    public void setApplyUserId(Integer applyUserId) {
        this.applyUserId = applyUserId;
    }


    public String getApplyUserName() {
        return applyUserName;
    }

    public void setApplyUserName(String applyUserName) {
        this.applyUserName = applyUserName;
    }


    @Override
    public String toString() {
        return "AssetOaOrderApply{" +
                ", orderNumber=" + orderNumber +
                ", applyUserId=" + applyUserId +
                ", applyUserName=" + applyUserName +
                "}";
    }
}