package com.antiy.asset.vo.query;

import com.antiy.common.base.ObjectQuery;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * AssetOaOrderHandle 查询条件
 * </p>
 *
 * @author shenliang
 * @since 2020-04-07
 */

public class AssetOaOrderHandleQuery extends ObjectQuery {
    @ApiModelProperty("订单流水号")
    private String orderNumber;

    @ApiModelProperty("资产id")
    private Integer assetId;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }
}