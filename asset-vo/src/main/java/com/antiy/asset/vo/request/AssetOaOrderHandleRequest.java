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

    @ApiModelProperty("订单流水号")
    private String orderNumber;
    /**
     * 资产id
     */
    @ApiModelProperty("资产id")
    private List<Integer> assetIds;


    public String getorderNumber() {
        return orderNumber;
    }

    public void setorderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }


    public List<Integer> getAssetIds() {
        return assetIds;
    }

    public void setAssetIds(List<Integer> assetIds) {
        this.assetIds = assetIds;
    }

    @Override
    public void validate() throws RequestParamValidateException {

    }

}