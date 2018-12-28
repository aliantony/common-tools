package com.antiy.asset.entity.vo.query;

import com.antiy.common.base.ObjectQuery;
import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
import com.antiy.common.validation.ObjectValidator;
import com.antiy.common.exception.RequestParamValidateException;
/**
 * <p>
 * Scheme 查询条件
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class SchemeQuery extends ObjectQuery implements ObjectValidator{

    @Override
    public void validate() throws RequestParamValidateException {

    }
}