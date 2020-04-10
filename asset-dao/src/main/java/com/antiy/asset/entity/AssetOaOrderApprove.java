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
    private Integer approveUserId;
    /**
     * 审批人姓名
     */
    private String approveUserName;
    /**
     * 审批意见
     */
    private String view;

    /**
     * 审批时间
     */
    private Long approveTime;



    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Integer getApproveUserId() {
        return approveUserId;
    }

    public void setApproveUserId(Integer approveUserId) {
        this.approveUserId = approveUserId;
    }


    public String getApproveUserName() {
        return approveUserName;
    }

    public void setApproveUserName(String approveUserName) {
        this.approveUserName = approveUserName;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public Long getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(Long approveTime) {
        this.approveTime = approveTime;
    }

    @Override
    public String toString() {
        return "AssetOaOrderApprove{" +
                ", orderNumber=" + orderNumber +
                ", approveUserId=" + approveUserId +
                ", view=" + view +
                ", approveTime=" + approveTime +
                "}";
    }
}