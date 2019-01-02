package com.antiy.asset.vo.query;

import com.antiy.common.base.ObjectQuery;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * Scheme 查询条件
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */
@Data
public class SchemeQuery extends ObjectQuery implements ObjectValidator {

    /**
     * 类型（1.准入实施、2.效果检查、3.资产退役、4.验证退役方案、5.实施退役方案）
     */
    @ApiModelProperty("类型（1.准入实施、2.效果检查、3.资产退役、4.验证退役方案、5.实施退役方案）")
    private Integer type;

    @Override
    public void validate() throws RequestParamValidateException {

    }
}