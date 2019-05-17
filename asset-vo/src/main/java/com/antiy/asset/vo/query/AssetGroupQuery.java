package com.antiy.asset.vo.query;

import javax.validation.constraints.Size;

import com.antiy.common.base.ObjectQuery;
import com.antiy.common.encoder.Encode;
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
    @Size(message = "资产组名称长度不能大于60", max = 60)
    private String  name;
    /**
     * 是否全查询
     */
    @ApiModelProperty("是否全查询")
    private boolean all;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String memo;
    /**
     * 创建人ID
     */
    @ApiModelProperty("创建人ID")
    @Encode
    private String createUser;

    public boolean isAll() {
        return all;
    }

    public void setAll(boolean all) {
        this.all = all;
    }

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

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    @Override
    public String toString() {
        return "AssetGroupQuery{" + "name='" + name + '\'' + ", memo='" + memo + '\'' + ", createUser='" + createUser
               + '\'' + '}';
    }

    @Override
    public void validate() throws RequestParamValidateException {

    }
}