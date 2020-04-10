package com.antiy.asset.vo.response;

import com.antiy.common.base.BaseEntity;

import java.util.List;

/**
 * <p>
 * AssetOaOrderResponse 响应对象
 * </p>
 *
 * @author shenliang
 * @since 2020-04-07
 */

public class AssetOaOrderResponse extends BaseEntity {

    /**
     * 流水号
     */
    private String number;
    /**
     * 1入网审批，2退回审批，3报废审批，4出借审批
     */
    private Integer orderType;
    /**
     * 1待处理，2已处理
     */
    private Integer orderStatus;
    /**
     * 时间
     */
    private Long gmtCreate;

    private AssetOaOrderApplyResponse assetOaOrderApplyResponse;

    private List<AssetOaOrderApproveResponse> assetOaOrderApproveResponses;

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

    public AssetOaOrderApplyResponse getAssetOaOrderApplyResponse() {
        return assetOaOrderApplyResponse;
    }

    public void setAssetOaOrderApplyResponse(AssetOaOrderApplyResponse assetOaOrderApplyResponse) {
        this.assetOaOrderApplyResponse = assetOaOrderApplyResponse;
    }

    public List<AssetOaOrderApproveResponse> getAssetOaOrderApproveResponses() {
        return assetOaOrderApproveResponses;
    }

    public void setAssetOaOrderApproveResponses(List<AssetOaOrderApproveResponse> assetOaOrderApproveResponses) {
        this.assetOaOrderApproveResponses = assetOaOrderApproveResponses;
    }
}