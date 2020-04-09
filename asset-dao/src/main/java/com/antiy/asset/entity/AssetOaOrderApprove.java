package com.antiy.asset.entity;


import com.antiy.common.base.BaseEntity;

/**
 * <p>订单审批信息表</p>
 *
 * @author shenliang
 * @since 2020-04-07
 */

public class AssetOaOrderApprove extends BaseEntity {


    private static final long serialVersionUID = 1L;

    /**
     * 订单表id
     */
    private String orderNumber;
    /**
     * 审批人id
     */
    private Integer approvalUserId;
    /**
     * 审批人姓名
     */
    private String approvalUserName;


    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Integer getApprovalUserId() {
        return approvalUserId;
    }

    public void setApprovalUserId(Integer approvalUserId) {
        this.approvalUserId = approvalUserId;
    }


    public String getApprovalUserName() {
        return approvalUserName;
    }

    public void setApprovalUserName(String approvalUserName) {
        this.approvalUserName = approvalUserName;
    }


    @Override
    public String toString() {
        return "AssetOaOrderApprove{" +
                ", orderNumber=" + orderNumber +
                ", approvalUserId=" + approvalUserId +
                ", approvalUserName=" + approvalUserName +
                "}";
    }
}