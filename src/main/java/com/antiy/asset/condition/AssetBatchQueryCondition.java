package com.antiy.asset.condition;

import javax.validation.constraints.NotNull;

import com.antiy.asset.base.ObjectQuery;
import com.antiy.asset.exception.RequestParamValidateException;
import com.antiy.asset.validation.ObjectValidator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Auther: zhangyajun
 * @Date: 2018/11/22 09:28
 * @Description: 资产列表和导出查询条件
 */
@Data
@ApiModel(value = "资产列表查询条件")
public class AssetBatchQueryCondition extends ObjectQuery implements ObjectValidator {

    @ApiModelProperty(value = "资产名称")
    @NotNull(message = "资产名称不能为空")
    private String name;


    @Override
    public void validate() throws RequestParamValidateException {
    }
}
