package com.antiy.asset.entity;


import com.antiy.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>出借订单拒绝表</p>
 *
 * @author shenliang
 * @since 2020-04-09
 */

public class AssetOaOrderResult extends BaseEntity {


    private static final long serialVersionUID = 1L;

    @ApiModelProperty("是否出借，1是 0否")
    private Integer lendStatus;

    @ApiModelProperty("拒绝原因")
    private String refuseReason;

    @ApiModelProperty("订单流水号")
    private String orderNumber;

    @ApiModelProperty("借出人")
    private Integer lendUserId;

    @ApiModelProperty("借出日期")
    private Long lendTime;

    @ApiModelProperty("归还日期")
    private Long returnTime;

    @ApiModelProperty("借出说明")
    private String lendRemark;

    @ApiModelProperty("方案")
    private String plan;

    @ApiModelProperty("附件地址")
    private String fileUrl;

    @ApiModelProperty("附件名")
    private String fileName;

    @ApiModelProperty("执行人")
    private Integer excuteUserId;

    @ApiModelProperty("处理类型")
    private Integer handleType;

    @ApiModelProperty("记录生成时间")
    private Long gmtCreate;



    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }


    public Integer getLendStatus() {
        return lendStatus;
    }

    public void setLendStatus(Integer lendStatus) {
        this.lendStatus = lendStatus;
    }


    public String getRefuseReason() {
        return refuseReason;
    }

    public void setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason;
    }

    public Integer getLendUserId() {
        return lendUserId;
    }

    public void setLendUserId(Integer lendUserId) {
        this.lendUserId = lendUserId;
    }

    public Long getLendTime() {
        return lendTime;
    }

    public void setLendTime(Long lendTime) {
        this.lendTime = lendTime;
    }

    public Long getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Long returnTime) {
        this.returnTime = returnTime;
    }

    public String getLendRemark() {
        return lendRemark;
    }

    public void setLendRemark(String lendRemark) {
        this.lendRemark = lendRemark;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getExcuteUserId() {
        return excuteUserId;
    }

    public void setExcuteUserId(Integer excuteUserId) {
        this.excuteUserId = excuteUserId;
    }

    public Integer getHandleType() {
        return handleType;
    }

    public void setHandleType(Integer handleType) {
        this.handleType = handleType;
    }

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }


    @Override
    public String toString() {
        return "AssetOaOrderResult{" +
                ", orderNumber=" + orderNumber +
                ", lendStatus=" + lendStatus +
                ", refuseReason=" + refuseReason +
                ", lendUserId=" + lendUserId +
                ", lendTime=" + lendTime +
                ", returnTime=" + returnTime +
                ", lendRemark=" + lendRemark +
                ", plan=" + plan +
                ", fileUrl=" + fileUrl +
                ", fileName=" + fileName +
                ", excuteUserId=" + excuteUserId +
                ", handleType=" + handleType +
                ", gmtCreate=" + gmtCreate +
                "}";
    }
}