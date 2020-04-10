package com.antiy.asset.vo.request;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * AssetOaOrderApproveRequest 请求对象
 * </p>
 *
 * @author shenliang
 * @since 2020-04-07
 */

public class AssetOaOrderApproveRequest extends BasicRequest implements ObjectValidator {

    /**
     * 审批人id
     */
    @ApiModelProperty("审批人id")
    private Integer approveUserId;
    /**
     * 审批人姓名
     */
    @ApiModelProperty("审批人姓名")
    private String approveUserName;
    /**
     * 订单编号
     */
    @ApiModelProperty("订单编号")
    private String orderNumber;

    /**
     * 审批意见
     */
    @ApiModelProperty("审批意见")
    private String view;
    /**
     * 审批时间
     */
    @ApiModelProperty("审批时间")
    private Long approveTime;


    public Integer getApproveUserId() {
        return approveUserId;
    }

    public void setApproveUserId(Integer approveUserId) {
        this.approveUserId = approveUserId;
    }

    public String getApproveUserName() {
        return approveUserName;
    }

    public void setApproveUserName(String approveUserName) {
        this.approveUserName = approveUserName;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public Long getapproveTime() {
        return approveTime;
    }

    public void setapproveTime(Long approveTime) {
        this.approveTime = approveTime;
    }

    @Override
    public void validate() throws RequestParamValidateException {

    }

}