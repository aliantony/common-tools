package com.antiy.asset.vo.query;

import com.antiy.common.base.ObjectQuery;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p> Scheme 查询条件 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */
public class SchemeQuery extends ObjectQuery implements ObjectValidator {

    /**
     * 类型（1.准入实施、2.效果检查、3.资产退役、4.验证退役方案、5.实施退役方案）
     */
    @ApiModelProperty("类型（1.准入实施、2.效果检查、3.资产退役、4.验证退役方案、5.实施退役方案）")
    private Integer type;

    /**
     * 实施人
     */
    @ApiModelProperty("实施人")
    private String  putintoUser;

    @Override
    public void validate() throws RequestParamValidateException {

    }

    public String getPutintoUser() {
        return putintoUser;
    }

    public void setPutintoUser(String putintoUser) {
        this.putintoUser = putintoUser;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}