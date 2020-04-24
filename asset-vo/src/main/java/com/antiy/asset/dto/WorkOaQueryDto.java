package com.antiy.asset.dto;

/**
 * @Author ygh
 * @Description 工作台OA订单查询Dto
 * @Date 2020/4/22
 */
public class WorkOaQueryDto {

    /**
     * 订单类型 1入网审批，2退回审批，3报废审批，4出借审批
     */
    private Integer OrderType;

    /**
     * 状态 1待处理，2已处理
     */
    private Integer OrderStatus;

    public Integer getOrderType() {
        return OrderType;
    }

    public void setOrderType(Integer orderType) {
        OrderType = orderType;
    }

    public Integer getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        OrderStatus = orderStatus;
    }
}
