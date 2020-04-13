package com.antiy.asset.vo.request;

import io.swagger.annotations.ApiModelProperty;

public class ApproveInfoRequest {
    @ApiModelProperty("审批单号")
    private String orderNumber;

    @ApiModelProperty("申请人")
    private String orderUser;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderUser() {
        return orderUser;
    }

    public void setOrderUser(String orderUser) {
        this.orderUser = orderUser;
    }

    @Override
    public String toString() {
        return "ApproveInfoRequest{" +
                "orderNumber='" + orderNumber + '\'' +
                ", orderUser='" + orderUser + '\'' +
                '}';
    }
}
