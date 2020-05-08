package com.antiy.asset.vo.response;

import com.antiy.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

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
     * 订单号
     */
    @ApiModelProperty("订单号")
    private String orderNumber;

    /**
     * 申请内容
     */
    @ApiModelProperty("申请内容")
    private String content;

    /**
     * 只针对借用类型，借用开始时间
     */
    @ApiModelProperty("只针对借用类型，借用开始时间")
    private Long lendStartTime;

    /**
     * 只针对借用类型，借用结束时间
     */
    @ApiModelProperty("只针对借用类型，借用结束时间")
    private Long lendEndTime;

    /**
     * 只针对借用类型，借用时间区间
     */
    @ApiModelProperty("只针对借用类型，借用时间区间")
    private String lendTime;

    /**
     * 只针对借用类型，借用目的
     */
    @ApiModelProperty("只针对借用类型，借用目的")
    private String lendPurpose;

    /**
     * 针对退回和报废，资产编号
     */
    @ApiModelProperty("针对退回和报废，资产编号")
    private String assetNumber;

    /**
     * 针对退回和报废，ip
     */
    @ApiModelProperty("针对退回和报废，ip")
    private String assetIp;

    /**
     * 针对退回和报废，mac
     */
    @ApiModelProperty("针对退回和报废，mac")
    private String assetMac;

    /**
     * 申请人id
     */
    @ApiModelProperty("申请人id")
    private Integer applyUserId;

    /**
     * 申请人姓名
     */
    @ApiModelProperty("申请人姓名")
    private String applyUserName;

    /**
     * 申请时间
     */
    @ApiModelProperty("申请时间")
    private Long applyTime;

    /**
     * 申请时间字符串
     */
    @ApiModelProperty("申请时间字符串")
    private String applyTimeStr;


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

    public String getAssetIp() {
        return assetIp;
    }

    public void setAssetIp(String assetIp) {
        this.assetIp = assetIp;
    }

    public String getAssetMac() {
        return assetMac;
    }

    public void setAssetMac(String assetMac) {
        this.assetMac = assetMac;
    }

    public String getLendTime() {
        return lendTime;
    }

    public void setLendTime(String lendTime) {
        this.lendTime = lendTime;
    }

    public String getApplyTimeStr() {
        return applyTimeStr;
    }

    public void setApplyTimeStr(String applyTimeStr) {
        this.applyTimeStr = applyTimeStr;
    }

    @Override
    public String toString() {
        return "AssetOaOrderApply{" +
                ", orderNumber=" + orderNumber +
                ", content=" + content +
                ", lendStartTime=" + lendStartTime +
                ", lendEndTime=" + lendEndTime +
                ", lendPurpose=" + lendPurpose +
                ", assetNumber=" + assetNumber +
                ", assetIp=" + assetIp +
                ", assetMac=" + assetMac +
                ", applyUserId=" + applyUserId +
                ", applyUserName=" + applyUserName +
                ", applyTime=" + applyTime +
                ", applyTimeStr=" + applyTimeStr +
                ", lendTime=" + lendTime +
                "}";
    }
}