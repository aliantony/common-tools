package com.antiy.asset.vo.request;

import javax.validation.constraints.NotBlank;

import com.antiy.asset.base.BasicRequest;
import com.antiy.asset.exception.RequestParamValidateException;
import com.antiy.asset.validation.ObjectValidator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Auther: zhangyajun
 * @Date: 2018/11/21 13:18
 * @Description: CPU信息VO对象
 */
@Data
@ApiModel(value = "CPU对象")
public class CpuRequest extends BasicRequest implements ObjectValidator {

    /**
     * 处理器名称
     */
    
    @ApiModelProperty("处理器名称")
    @NotBlank(message = "处理器名称不能为空")
    private String  cpuName;

    /**
     * 制造商
     */
    @ApiModelProperty("制造商")
    private String  manufacturer;

    /**
     * CPU速度(GHz)
     */
    @ApiModelProperty("CPU速度(GHz)")
    private Integer cpuSpeed;

    /**
     * 处理器核数量
     */
    @ApiModelProperty("	处理器核数量")
    private Integer cpuCoreSize;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String  memo;

    /**
     * 状态,1 可用,0不可用
     */
    @ApiModelProperty("状态,1 可用,0不可用")
    @NotBlank(message = "状态不能为空")
    private Integer  status;

    @Override
    public void validate() throws RequestParamValidateException {

    }
}