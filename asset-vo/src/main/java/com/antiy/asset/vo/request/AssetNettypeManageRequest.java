package com.antiy.asset.vo.request;

import com.antiy.common.base.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import com.antiy.common.validation.ObjectValidator;
import com.antiy.common.exception.RequestParamValidateException;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * AssetNettypeManageRequest 请求对象
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetNettypeManageRequest extends BaseRequest implements ObjectValidator{

    /**
     *  网络类型名称
     */
    @ApiModelProperty("网络类型名称")
    @NotBlank(message = "网络类型名称不能为空")
    private String netTypeName;
    /**
     *  描述
     */
    @ApiModelProperty("描述")
    private String description;



    public String getNetTypeName() {
        return netTypeName;
    }

    public void setNetTypeName(String netTypeName) {
    this.netTypeName = netTypeName;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
    this.description = description;
    }


    @Override
    public void validate() throws RequestParamValidateException {

    }

}