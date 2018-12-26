package com.antiy.asset.condition;

import com.antiy.asset.base.ObjectQuery;
import com.antiy.asset.exception.RequestParamValidateException;
import com.antiy.asset.validation.ObjectValidator;

import io.swagger.annotations.ApiModel;

/**
 * @Auther: zhangyajun
 * @Date: 2018/11/22 09:28
 * @Description: 资产详情列表和导出查询条件
 */
@ApiModel(value = "资产详情列表查询条件")
public class AssetDetailBatchQueryCondition extends ObjectQuery implements ObjectValidator {

    @Override
    public void validate() throws RequestParamValidateException {
    }
}
