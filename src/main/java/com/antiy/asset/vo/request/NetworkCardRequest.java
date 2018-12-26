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
 * @Description: 网卡信息VO对象
 */
@Data
@ApiModel(value = "网卡对象")
public class NetworkCardRequest extends BasicRequest implements ObjectValidator {

    /**
     * 资产主键
     */
    @ApiModelProperty("资产主键")
    @NotNull(message = "资产主键不能为空")
    private Integer assetId;

    /**
     * IP地址
     */
    @ApiModelProperty("IP地址")
    private Integer ipAddress;

    /**
     * MAC地址
     */
    @ApiModelProperty("MAC地址")
    private String  macAddress;

    /**
     * 网卡
     */
    @ApiModelProperty("网卡")
    private String  network;

    /**
     * 默认网关
     */
    @ApiModelProperty("默认网关")
    private Integer defaultGateway;

    /**
     * 网络地址
     */
    @ApiModelProperty("网络地址")
    private Integer networdAddress;

    /**
     * 子网掩码
     */
    @ApiModelProperty("子网掩码")
    private Integer subnetMask;

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
