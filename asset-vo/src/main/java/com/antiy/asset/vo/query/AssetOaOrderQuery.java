package com.antiy.asset.vo.query;

import com.antiy.common.base.ObjectQuery;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * AssetOaOrder 查询条件
 * </p>
 *
 * @author shenliang
 * @since 2020-04-07
 */

public class AssetOaOrderQuery extends ObjectQuery {
    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private Integer id;
    /**
     * 流水号
     */
    @ApiModelProperty("流水号")
    private String number;
    /**
     * 1入网审批，2退回审批，3报废审批，4出借审批
     */
    @ApiModelProperty("1入网审批，2退回审批，3报废审批，4出借审批")
    private Integer orderType;
    /**
     * 1待处理，2已处理
     */
    @ApiModelProperty("1待处理，2已处理")
    private Integer orderStatus;
    /**
     * 开始时间
     */
    @ApiModelProperty("开始时间")
    private Long startTime;
    /**
     * 结束时间
     */
    @ApiModelProperty("结束时间")
    private Long endTime;
    /**
     * 时间
     */
    @ApiModelProperty("时间")
    private Long gmtCreate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }


    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    @Override
    public Long getEndTime() {
        return endTime;
    }

    @Override
    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    @Override
    public String toString() {
        return "AssetOaOrderQuery{" +
                ", id=" + id +
                ", number=" + number +
                ", orderType=" + orderType +
                ", orderStatus=" + orderStatus +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", gmtCreate=" + gmtCreate +
                "}";
    }

}