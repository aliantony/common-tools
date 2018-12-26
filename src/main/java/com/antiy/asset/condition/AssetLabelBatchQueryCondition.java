package com.antiy.asset.condition;

import javax.validation.constraints.NotNull;

import com.antiy.asset.base.ObjectQuery;
import com.antiy.asset.exception.RequestParamValidateException;
import com.antiy.asset.validation.ObjectValidator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Auther: zhangyajun
 * @Date: 2018/11/22 09:28
 * @Description: 资产标签列表和导出查询条件
 */
@ApiModel(value = "资产标签列表查询条件")
public class AssetLabelBatchQueryCondition extends ObjectQuery implements ObjectValidator {

    @ApiModelProperty(value = "资产标签")
    @NotNull(message = "资产标签不能为空")
    private String assetLabelName;


    @Override
    public void validate() throws RequestParamValidateException {
    }
}
