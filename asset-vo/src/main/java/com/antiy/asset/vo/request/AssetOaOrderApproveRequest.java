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
    private Integer approvalUserId;
    /**
     * 审批人姓名
     */
    @ApiModelProperty("审批人姓名")
    private String approvalUserName;


    public Integer getApprovalUserId() {
        return approvalUserId;
    }

    public void setApprovalUserId(Integer approvalUserId) {
        this.approvalUserId = approvalUserId;
    }


    public String getApprovalUserName() {
        return approvalUserName;
    }

    public void setApprovalUserName(String approvalUserName) {
        this.approvalUserName = approvalUserName;
    }


    @Override
    public void validate() throws RequestParamValidateException {

    }

}