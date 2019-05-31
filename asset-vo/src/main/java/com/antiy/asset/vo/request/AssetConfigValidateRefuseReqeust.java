package com.antiy.asset.vo.request;

import javax.validation.constraints.Size;

import com.antiy.common.base.BaseRequest;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 资产配置验证拒绝请求对象
 * @auther: zhangbing
 * @date: 2019/1/22 13:42
 * @description:
 */
@ApiModel(value = "资产配置验证拒绝请求对象")
public class AssetConfigValidateRefuseReqeust extends BaseRequest implements ObjectValidator {

    @ApiModelProperty("备注信息")
    @Size(max = 300)
    private String memo;

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public void validate() throws RequestParamValidateException {

    }
}
