package com.antiy.asset.vo.request;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.antiy.common.base.BaseRequest;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @auther: zhangbing
 * @date: 2019/1/26 14:03
 * @description:
 */
@ApiModel(value = "准入状态")
public class AreaIdRequest extends BaseRequest implements ObjectValidator {
    @ApiModelProperty("区域ID不能为空")
    @NotNull(message = "区域ID不能为空")
    @Valid
    @Encode
    private List<String> areaIds;

    public List<String> getAreaIds() {
        return areaIds;
    }

    public void setAreaIds(List<String> areaIds) {
        this.areaIds = areaIds;
    }

    @Override
    public void validate() throws RequestParamValidateException {

    }
}
