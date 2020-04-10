package com.antiy.asset.vo.response;

import com.antiy.common.base.BaseEntity;

/**
 * <p>
 * AssetOaOrderApproveResponse 响应对象
 * </p>
 *
 * @author shenliang
 * @since 2020-04-07
 */

public class AssetOaOrderApproveResponse extends BaseEntity {
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
    private String approveTime;


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


    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(String approveTime) {
        this.approveTime = approveTime;
    }

    @Override
    public String toString() {
        return "AssetOaOrderApproveResponse{" +
                ", orderNumber=" + orderNumber +
                ", approveUserId=" + approveUserId +
                ", approveUserName=" + approveUserName +
                ", view=" + view +
                ", approveTime=" + approveTime +
                "}";
    }
}