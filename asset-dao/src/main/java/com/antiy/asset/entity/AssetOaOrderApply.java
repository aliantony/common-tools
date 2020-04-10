package com.antiy.asset.entity;


import com.antiy.common.base.BaseEntity;

/**
 * <p>订单申请信息表</p>
 *
 * @author shenliang
 * @since 2020-04-07
 */

public class AssetOaOrderApply extends BaseEntity {


    private static final long serialVersionUID = 1L;

    /**
     * 订单号
     */
    private String orderNumber;
    /**
     * 申请内容
     */
    private String content;
    /**
     * 只针对借用类型，借用开始时间
     */
    private Long lendStartTime;
    /**
     * 只针对借用类型，借用结束时间
     */
    private Long lendEndTime;
    /**
     * 只针对借用类型，借用目的
     */
    private String lendPurpose;
    /**
     * 针对退回和报废，资产编号
     */
    private String assetNumber;
    /**
     * 申请人id
     */
    private Integer applyUserId;
    /**
     * 申请人姓名
     */
    private String applyUserName;
    /**
     * 申请时间
     */
    private Long applyTime;


    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getLendStartTime() {
        return lendStartTime;
    }

    public void setLendStartTime(Long lendStartTime) {
        this.lendStartTime = lendStartTime;
    }

    public Long getLendEndTime() {
        return lendEndTime;
    }

    public void setLendEndTime(Long lendEndTime) {
        this.lendEndTime = lendEndTime;
    }

    public String getLendPurpose() {
        return lendPurpose;
    }

    public void setLendPurpose(String lendPurpose) {
        this.lendPurpose = lendPurpose;
    }

    public String getAssetNumber() {
        return assetNumber;
    }

    public void setAssetNumber(String assetNumber) {
        this.assetNumber = assetNumber;
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

    public Long getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Long applyTime) {
        this.applyTime = applyTime;
    }

    @Override
    public String toString() {
        return "AssetOaOrderApply{" +
                ", orderNumber=" + orderNumber +
                ", content=" + content +
                ", lendStartTime=" + lendStartTime +
                ", lendEndTime=" + lendEndTime +
                ", lendPurpose=" + lendPurpose +
                ",  assetNumber=" + assetNumber +
                ", applyUserId=" + applyUserId +
                ", applyUserName=" + applyUserName +
                ", applyTime=" + applyTime +
                "}";
    }
}