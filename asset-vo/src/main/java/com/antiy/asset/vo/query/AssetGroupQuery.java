package com.antiy.asset.vo.query;

import com.antiy.common.base.ObjectQuery;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetGroup 查询条件 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetGroupQuery extends ObjectQuery implements ObjectValidator {

    /**
     * 资产组名称
     */
    @ApiModelProperty("资产组名称")
    private String name;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String memo;
    /**
     * 创建人名称
     */
    @ApiModelProperty("创建人名称")
    private String createUserName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    @Override
    public void validate() throws RequestParamValidateException {

    }
}