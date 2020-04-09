package com.antiy.asset.vo.request;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * AssetOaOrderApplyRequest 请求对象
 * </p>
 *
 * @author shenliang
 * @since 2020-04-07
 */

public class AssetOaOrderApplyRequest extends BasicRequest implements ObjectValidator {

    /**
     * 申请人id
     */
    @ApiModelProperty("申请人id")
    private Integer applyUserId;
    /**
     * 申请人姓名
     */
    @ApiModelProperty("申请人姓名")
    private String applyUserName;


    public Integer getApplyUserId() {
        return applyUserId;
    }

    public void setApplyUserId(Integer applyUserId) {
        this.applyUserId = applyUserId;
    }


    public String getApplyUserName() {
        return applyUserName;
    }

    public void setApplyUserName(String applyUserName) {
        this.applyUserName = applyUserName;
    }


    @Override
    public void validate() throws RequestParamValidateException {

    }

}