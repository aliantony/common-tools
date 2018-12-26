package com.antiy.asset.vo.request;

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
 * @Description: 硬盘信息VO对象
 */
@Data
@ApiModel(value = "硬盘对象")
public class HardDiskRequest extends BasicRequest implements ObjectValidator {

    /**
     * 硬盘型号
     */
    @ApiModelProperty("硬盘型号")
    private String  model;

    /**
     * 机构的序列号
     */
    @ApiModelProperty("机构的序列号")
    private String  organizationSerialNumber;

    /**
     * 容量 (MB)
     */
    @ApiModelProperty("	容量 (MB)")
    @NotNull(message = "容量不能为空")
    private Integer capacity;

    /**
     * 磁盘类型,1 SATA,2,SSD
     */
    @ApiModelProperty("磁盘类型,1 SATA,2,SSD")
    private String  diskType;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String  memo;

    /**
     * 状态,1 可用,0不可用
     */
    @NotNull(message = "状态不能为空")
    @ApiModelProperty("状态,1 可用,0不可用")
    private Integer status;

    @Override
    public void validate() throws RequestParamValidateException {

    }
}