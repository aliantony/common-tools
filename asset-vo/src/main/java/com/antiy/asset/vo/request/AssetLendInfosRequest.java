package com.antiy.asset.vo.request;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class AssetLendInfosRequest {
    /**
     *  资产id
     */
    @ApiModelProperty("资产id")
    private List<String> assetIds;
    /**
     *  用户id
     */
    @ApiModelProperty("用户id")
    private Integer useId;
    /**
     *  订单id
     */
    @ApiModelProperty("订单id")
    private String orderNumber;
    /**
     *  归还日期
     */
    @ApiModelProperty("预计归还日期")
    private Long lendPeriods;
    /**
     *  出借目的
     */
    @ApiModelProperty("出借目的")
    private String lendPurpose;
    /**
     *  出借状态 1 已借出 2 已归还
     */
    @ApiModelProperty("出借状态")
    private Integer lendStatus;

    /**
     *  出借日期
     */
    @ApiModelProperty("出借日期")
    private Long lendTime;

    public List<String> getAssetIds() {
        return assetIds;
    }

    public void setAssetIds(List<String> assetIds) {
        this.assetIds = assetIds;
    }

    public Integer getUseId() {
        return useId;
    }

    public void setUseId(Integer useId) {
        this.useId = useId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
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

    @Override
    public String toString() {
        return "AssetLendInfosRequest{" +
                "assetIds=" + assetIds +
                ", useId=" + useId +
                ", orderNumber='" + orderNumber + '\'' +
                ", lendPeriods=" + lendPeriods +
                ", lendPurpose='" + lendPurpose + '\'' +
                ", lendStatus=" + lendStatus +
                ", lendTime=" + lendTime +
                '}';
    }
}
