package com.antiy.asset.vo.request;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.antiy.asset.base.BasicRequest;
import com.antiy.asset.exception.RequestParamValidateException;
import com.antiy.asset.validation.ObjectValidator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Auther: zhangyajun
 * @Date: 2018/11/21 13:18
 * @Description: 软件信息VO对象
 */
@ApiModel(value = "软件对象")
public class MemoryRequest extends BasicRequest implements ObjectValidator {

    /**
     * 内存类型:0-未知，1-DDR2,2-DDR3,3-DDR4
     */
    @ApiModelProperty("内存类型:0-未知，1-DDR2,2-DDR3,3-DDR4")
    @NotBlank(message = "内存容量不能为空")
    private Integer memoryType;

    /**
     * 内存容量
     */
    @ApiModelProperty("内存容量")
    @NotNull(message = "内存容量不能为空")
    private Integer capacity;

    /**
     * 内存主频(MHz)
     */
    @ApiModelProperty("内存主频(MHz)")
    private Integer frequency;

    /**
     * 插槽类型:0-SDRAM,1-SIMM,2-DIMM,3-RIMM
     */
    @ApiModelProperty("插槽类型:0-SDRAM,1-SIMM,2-DIMM,3-RIMM")
    private Integer slotType;

    /**
     * 是否带散热片:0-不带，1-带
     */
    @ApiModelProperty("是否带散热片:0-不带，1-带")
    private Integer isHeatsink;

    /**
     * 针脚数
     */
    @ApiModelProperty("针脚数")
    private Integer stitch;

    /**
     * 质保时间
     */
    @ApiModelProperty("质保时间")
    @NotNull(message = "质保时间不能为空")
    private Date    warrantyDate;

    /**
     * 联系电话
     */
    @ApiModelProperty("联系电话")
    private String  telephone;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String  memo;

    /**
     * 状态,1 可用,0不可用
     */
    @ApiModelProperty("状态,1 可用,0不可用")
    private Integer status;

    @Override
    public void validate() throws RequestParamValidateException {

    }
}