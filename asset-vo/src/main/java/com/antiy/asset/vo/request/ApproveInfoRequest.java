package com.antiy.asset.vo.request;

public class ApproveInfoRequest {
    //审批单号
    private String orderNumber;

    //申请人
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
}
