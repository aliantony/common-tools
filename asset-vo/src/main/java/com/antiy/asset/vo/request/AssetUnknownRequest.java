package com.antiy.asset.vo.request;

import java.util.List;

import com.antiy.common.base.BaseRequest;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @auther: zhangbing
 * @date: 2019/1/26 14:03
 * @description:
 */
@ApiModel(value = "未知资产统计请求对象")
public class AssetUnknownRequest extends BaseRequest implements ObjectValidator {
    @ApiModelProperty(value = "区域ID", hidden = true)
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
