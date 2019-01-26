package com.antiy.asset.vo.request;

import com.antiy.common.base.BaseRequest;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @auther: zhangbing
 * @date: 2019/1/26 14:03
 * @description:
 */
@ApiModel(value = "准入状态")
public class AdmittanceRequest extends BaseRequest {
    @ApiModelProperty("准入状态，1待设置，2已允许，3已禁止")
    private Integer admittanceStatus;

    public Integer getAdmittanceStatus() {
        return admittanceStatus;
    }

    public void setAdmittanceStatus(Integer admittanceStatus) {
        this.admittanceStatus = admittanceStatus;
    }

}
