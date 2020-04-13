package com.antiy.asset.vo.response;

import io.swagger.annotations.ApiModelProperty;

public class ApproveListResponse {
    @ApiModelProperty("审批单ID")
    private String orderId;

    @ApiModelProperty("审批单编号")
    private String orderNumber;

    @ApiModelProperty("审批类型")
    private String orderType = "出借审批";

    @ApiModelProperty("申请人")
    private String orderUser;

    @ApiModelProperty("时间")
    private String time;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderUser() {
        return orderUser;
    }

    public void setOrderUser(String orderUser) {
        this.orderUser = orderUser;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "ApproveListResponse{" +
                "orderId='" + orderId + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                ", orderType='" + orderType + '\'' +
                ", orderUser='" + orderUser + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
