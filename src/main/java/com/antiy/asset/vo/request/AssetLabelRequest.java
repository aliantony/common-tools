package com.antiy.asset.vo.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.antiy.asset.base.BasicRequest;
import com.antiy.asset.exception.RequestParamValidateException;
import com.antiy.asset.validation.ObjectValidator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Auther: zhangyajun
 * @Date: 2018/11/21 13:18
 * @Description: 资产标签信息VO对象
 */
@Data
@ApiModel(value = "资产标签对象")
public class AssetLabelRequest extends BasicRequest implements ObjectValidator {
    /**
     * 标签类型,1标示资产，2软件
     */
    @ApiModelProperty("标签类型,1标示资产，2软件")
    @NotNull(message = "标签类型不能为空")
    private Integer assetLabelType;

    /**
     * 标签名称
     */
    @ApiModelProperty("标签名称")
    @NotBlank(message = "标签名称不能为空")
    private String  assetLabelName;

    /**
     * 标签描述
     */
    @ApiModelProperty("标签描述")
    private String  assetLabelDescribe;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String  memo;

    /**
     * 状态,1 可用,0不可用
     */
    @ApiModelProperty("状态,1 可用,0不可用")
    @NotNull(message = "状态不能为空")
    private Integer status;

    @Override
    public void validate() throws RequestParamValidateException {
    }
}
