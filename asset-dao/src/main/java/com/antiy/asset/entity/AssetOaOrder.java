package com.antiy.asset.entity;


import com.antiy.common.base.BaseEntity;

/**
 * <p>OA订单表</p>
 *
 * @author shenliang
 * @since 2020-04-07
 */

public class AssetOaOrder extends BaseEntity {


    private static final long serialVersionUID = 1L;

    /**
     * 流水号
     */
    private String number;
    /**
     * 1入网审批，2退回审批，3报废审批，4出借审批
     */
    private Integer orderType;
    /**
     * 1待处理，2已处理
     */
    private Integer orderStatus;
    /**
     * 是否出借，只针对出借订单，1是，0否
     */
    private Integer lendStatus;
    /**
     * 时间
     */
    private Long gmtCreate;


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }


    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }


    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }


    public Integer getLendStatus() {
        return lendStatus;
    }

    public void setLendStatus(Integer lendStatus) {
        this.lendStatus = lendStatus;
    }


    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    @Override
    public String toString() {
        return "AssetOaOrder{" +
                ", number=" + number +
                ", orderType=" + orderType +
                ", orderStatus=" + orderStatus +
                ", lendStatus=" + lendStatus +
                ", gmtCreate=" + gmtCreate +
                "}";
    }
}