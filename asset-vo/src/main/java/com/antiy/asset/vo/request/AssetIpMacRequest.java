package com.antiy.asset.vo.request;

import com.antiy.common.base.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import com.antiy.common.validation.ObjectValidator;
import com.antiy.common.exception.RequestParamValidateException;

/**
 * <p> AssetIpMacRequest 请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetIpMacRequest extends BaseRequest implements ObjectValidator {

    /**
     * 资产主表
     */
    @ApiModelProperty("资产主表")
    private Integer assetId;
    /**
     * IP
     */
    @ApiModelProperty("IP")
    private String  ip;
    /**
     * MAC
     */
    @ApiModelProperty("MAC")
    private String  mac;

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

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    @Override
    public void validate() throws RequestParamValidateException {

    }

}