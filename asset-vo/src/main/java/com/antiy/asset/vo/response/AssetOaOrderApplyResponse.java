package com.antiy.asset.vo.response;

import com.antiy.common.base.BaseEntity;

/**
 * <p>
 * AssetOaOrderApplyResponse 响应对象
 * </p>
 *
 * @author shenliang
 * @since 2020-04-07
 */

public class AssetOaOrderApplyResponse extends BaseEntity {
    /**
     * 订单表id
     */
    private String orderNumber;
    /**
     * 申请人id
     */
    private Integer applyUserId;
    /**
     * 申请人姓名
     */
    private String applyUserName;

    public String getorderNumber() {
        return orderNumber;
    }

    public void setorderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Integer getApplyUserId() {
        return applyUserId;
    }

    public void setApplyUserId(Integer applyUserId) {
        this.applyUserId = applyUserId;
    }


    public String getApplyUserName() {
        return applyUserName;
    }

    public void setApplyUserName(String applyUserName) {
        this.applyUserName = applyUserName;
    }


    @Override
    public String toString() {
        return "AssetOaOrderApplyResponse{" +
                ", orderNumber=" + orderNumber +
                ", applyUserId=" + applyUserId +
                ", applyUserName=" + applyUserName +
                "}";
    }
}