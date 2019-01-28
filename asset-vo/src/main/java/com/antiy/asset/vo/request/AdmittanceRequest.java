package com.antiy.asset.vo.request;

import com.antiy.common.base.BaseRequest;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * @auther: zhangbing
 * @date: 2019/1/26 14:03
 * @description:
 */
@ApiModel(value = "准入状态")
public class AdmittanceRequest extends BaseRequest implements ObjectValidator {
    @ApiModelProperty("准入状态，1待设置，2已允许，3已禁止")
    @NotNull
    private Integer admittanceStatus;

    public Integer getAdmittanceStatus() {
        return admittanceStatus;
    }

    public void setAdmittanceStatus(Integer admittanceStatus) {
        this.admittanceStatus = admittanceStatus;
    }

    @Override
    public void validate() throws RequestParamValidateException {
        if (admittanceStatus != null && (admittanceStatus != 1 && admittanceStatus != 2 && admittanceStatus != 3)) {
            throw new RequestParamValidateException("准入状态异常");
        }
    }
}
