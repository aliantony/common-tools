package com.antiy.asset.vo.request;

public class AssetLendInfoRequest {
    /**
     *  资产id
     */
    private Integer assetId;
    /**
     *  用户id
     */
    private Integer useId;
    /**
     *  订单id
     */
    private Integer orderNumber;
    /**
     *  归还日期
     */
    private Long lendPeriods;
    /**
     *  出借目的
     */
    private String lendPurpose;
    /**
     *  出借状态 1 已借出 2 已归还
     */
    private Integer lendStatus;

    /**
     *  出借日期
     */
    private Long lendTime;

    /**
     *  唯一键
     */
    private Long uniqueId;

    /**
     * 归还时间
     *
     */
    private Long returnTime;

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }

    public Integer getUseId() {
        return useId;
    }

    public void setUseId(Integer useId) {
        this.useId = useId;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Long getLendPeriods() {
        return lendPeriods;
    }

    public void setLendPeriods(Long lendPeriods) {
        this.lendPeriods = lendPeriods;
    }

    public String getLendPurpose() {
        return lendPurpose;
    }

    public void setLendPurpose(String lendPurpose) {
        this.lendPurpose = lendPurpose;
    }

    public Integer getLendStatus() {
        return lendStatus;
    }

    public void setLendStatus(Integer lendStatus) {
        this.lendStatus = lendStatus;
    }

    public Long getLendTime() {
        return lendTime;
    }

    public void setLendTime(Long lendTime) {
        this.lendTime = lendTime;
    }

    public Long getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(Long uniqueId) {
        this.uniqueId = uniqueId;
    }

    public Long getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Long returnTime) {
        this.returnTime = returnTime;
    }

    @Override
    public String toString() {
        return "AssetLendInfoRequest{" +
                "assetId=" + assetId +
                ", useId=" + useId +
                ", orderNumber=" + orderNumber +
                ", lendPeriods=" + lendPeriods +
                ", lendPurpose='" + lendPurpose + '\'' +
                ", lendStatus=" + lendStatus +
                ", lendTime=" + lendTime +
                ", uniqueId=" + uniqueId +
                ", returnTime=" + returnTime +
                '}';
    }
}
