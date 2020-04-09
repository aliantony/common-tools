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
    private Integer approvalUserId;
    /**
     * 审批人姓名
     */
    private String approvalUserName;


    public String getorderNumber() {
        return orderNumber;
    }

    public void setorderNumber(String orderNumber) {
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
        return "AssetOaOrderApproveResponse{" +
                ", orderNumber=" + orderNumber +
                ", approvalUserId=" + approvalUserId +
                ", approvalUserName=" + approvalUserName +
                "}";
    }
}