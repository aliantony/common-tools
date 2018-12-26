package com.antiy.asset.vo.response;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Auther: zhangbing
 * @Date: 2018/11/22 09:22
 * @Description: 软件资产详情VO
 */
@ApiModel(value = "软件详情响应VO")
public class SoftwareDetailResponse extends SoftwareResponse {
    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private Integer    licenseId;

    /**
     * 购买日期
     */
    @ApiModelProperty("购买日期")
    @JsonFormat(locale = "zh", pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date       busyDate;

    /**
     * 有效期限
     */
    @ApiModelProperty("有效期限")
    @JsonFormat(locale = "zh", pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date       expiryDate;

    /**
     * 定购成本
     */
    @ApiModelProperty("定购成本")
    private BigDecimal orderCost;

    /**
     * 许可密钥
     */
    @ApiModelProperty("许可密钥")
    private String     licenseSecretKey;

    /**
     * 状态,1 可用,0不可用
     */
    @ApiModelProperty("状态,1 可用,0不可用")
    private String     status;

    public Integer getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(Integer licenseId) {
        this.licenseId = licenseId;
    }

    public Date getBusyDate() {
        return busyDate;
    }

    public void setBusyDate(Date busyDate) {
        this.busyDate = busyDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public BigDecimal getOrderCost() {
        return orderCost;
    }

    public void setOrderCost(BigDecimal orderCost) {
        this.orderCost = orderCost;
    }

    public String getLicenseSecretKey() {
        return licenseSecretKey;
    }

    public void setLicenseSecretKey(String licenseSecretKey) {
        this.licenseSecretKey = licenseSecretKey;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
