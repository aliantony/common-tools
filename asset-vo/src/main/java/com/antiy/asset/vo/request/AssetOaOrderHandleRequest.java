package com.antiy.asset.vo.request;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * <p>
 * AssetOaOrderHandleRequest 请求对象
 * </p>
 *
 * @author shenliang
 * @since 2020-04-07
 */

public class AssetOaOrderHandleRequest extends BasicRequest implements ObjectValidator {

    @ApiModelProperty("是否出借，1是 0否")
    private Integer lendStatus;

    @ApiModelProperty("拒绝原因")
    private String refuseReason;

    @ApiModelProperty("订单流水号")
    private String orderNumber;
    /**
     * 资产id
     */
    @ApiModelProperty("资产id")
    private List<String> assetIds;

    @ApiModelProperty("借出人")
    private Integer lendUserId;

    @ApiModelProperty("借出日期")
    private Long lendTime;

    @ApiModelProperty("归还日期")
    private Long returnTime;

    @ApiModelProperty("借出说明")
    private String lendRemark;

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

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public List<String> getAssetIds() {
        return assetIds;
    }

    public void setAssetIds(List<String> assetIds) {
        this.assetIds = assetIds;
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


    @Override
    public void validate() throws RequestParamValidateException {

    }

}