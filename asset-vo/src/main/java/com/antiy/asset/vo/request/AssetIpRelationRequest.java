package com.antiy.asset.vo.request;

import com.antiy.common.base.BaseRequest;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * <p> AssetIpRelationRequest 请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetIpRelationRequest extends BaseRequest implements ObjectValidator {

    /**
     * 资产主表
     */
    @ApiModelProperty("资产主表")
    private Integer assetId;

    /**
     * IP
     */
    @ApiModelProperty("IP")
    @Pattern(regexp = "^((25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))$", message = "ip地址错误")
    @Size(message = "IP地址长度应该在7-15位", min = 7, max = 15)
    private String  ip;

    /**
     * 网口
     */
    @ApiModelProperty("网口")
    private Integer net;
    /**
     * 状态：1-未删除,0-已删除
     */
    @ApiModelProperty("状态：1-未删除,0-已删除")
    private Integer status;

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getNet() {
        return net;
    }

    public void setNet(Integer net) {
        this.net = net;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public void validate() throws RequestParamValidateException {

    }

}