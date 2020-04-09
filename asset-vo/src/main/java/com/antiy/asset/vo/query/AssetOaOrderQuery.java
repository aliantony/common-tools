package com.antiy.asset.vo.query;

import com.antiy.common.base.ObjectQuery;

/**
 * <p>
 * AssetOaOrder 查询条件
 * </p>
 *
 * @author shenliang
 * @since 2020-04-07
 */

public class AssetOaOrderQuery extends ObjectQuery {
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
     * 开始时间
     */
    private Long startTime;
    /**
     * 结束时间
     */
    private Long endTime;


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


    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    @Override
    public Long getEndTime() {
        return endTime;
    }

    @Override
    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "AssetOaOrderQuery{" +
                ", number=" + number +
                ", orderType=" + orderType +
                ", orderStatus=" + orderStatus +
                ", lendStatus=" + lendStatus +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                "}";
    }

}