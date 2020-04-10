package com.antiy.asset.vo.request;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * <p>
 * AssetOaOrderRequest 请求对象
 * </p>
 *
 * @author shenliang
 * @since 2020-04-07
 */

public class AssetOaOrderRequest extends BasicRequest implements ObjectValidator {

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
     * 时间
     */
    @ApiModelProperty("时间")
    private Long gmtCreate;

    /**
     * 订单申请信息
     */
    private AssetOaOrderApplyRequest assetOaOrderApplyRequest;

    /**
     * 订单审核信息
     */
    private List<AssetOaOrderApproveRequest> assetOaOrderApproveRequests;


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


    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public AssetOaOrderApplyRequest getAssetOaOrderApplyRequest() {
        return assetOaOrderApplyRequest;
    }

    public void setAssetOaOrderApplyRequest(AssetOaOrderApplyRequest assetOaOrderApplyRequest) {
        this.assetOaOrderApplyRequest = assetOaOrderApplyRequest;
    }

    public List<AssetOaOrderApproveRequest> getAssetOaOrderApproveRequests() {
        return assetOaOrderApproveRequests;
    }

    public void setAssetOaOrderApproveRequests(List<AssetOaOrderApproveRequest> assetOaOrderApproveRequests) {
        this.assetOaOrderApproveRequests = assetOaOrderApproveRequests;
    }

    @Override
    public void validate() throws RequestParamValidateException {

    }

}