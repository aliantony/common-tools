package com.antiy.asset.vo.request;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * AssetOaOrderLendRequest 请求对象
 * </p>
 *
 * @author shenliang
 * @since 2020-04-09
 */

public class AssetOaOrderLendRequest extends BasicRequest implements ObjectValidator {

    /**
     * 订单表编号
     */
    @ApiModelProperty("订单表编号")
    private String orderNumber;
    /**
     * 是否拒绝出借，1是0否
     */
    @ApiModelProperty("是否拒绝出借，1是0否")
    private Integer lendStatus;
    /**
     * 拒绝原因
     */
    @ApiModelProperty("拒绝原因 ")
    private String refuseReason;


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


    @Override
    public void validate() throws RequestParamValidateException {

    }

}