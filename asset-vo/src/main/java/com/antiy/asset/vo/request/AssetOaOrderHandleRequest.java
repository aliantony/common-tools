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

    @ApiModelProperty("是否拒绝，1是 0否")
    private Integer isRefuse;

    @ApiModelProperty("拒绝原因")
    private String refuseReason;

    @ApiModelProperty("订单流水号")
    private String orderNumber;
    /**
     * 资产id
     */
    @ApiModelProperty("资产id")
    private List<Integer> assetIds;


    public List<Integer> getAssetIds() {
        return assetIds;
    }

    public void setAssetIds(List<Integer> assetIds) {
        this.assetIds = assetIds;
    }

    public Integer getIsRefuse() {
        return isRefuse;
    }

    public void setIsRefuse(Integer isRefuse) {
        this.isRefuse = isRefuse;
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

    @Override
    public void validate() throws RequestParamValidateException {

    }

}