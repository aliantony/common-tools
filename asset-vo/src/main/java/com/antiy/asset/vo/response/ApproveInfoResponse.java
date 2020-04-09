package com.antiy.asset.vo.response;

public class ApproveInfoResponse {
    //审批单号
    private String orderNumber;

    //申请人
    private String orderUser;

    //所属组织
    private String department;

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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
