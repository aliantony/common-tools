package com.antiy.asset.vo.request;

import com.antiy.common.base.BaseRequest;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * <p> AssetMacRelationRequest 请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetMacRelationRequest extends BaseRequest implements ObjectValidator {

    /**
     * 资产主表
     */
    @ApiModelProperty("资产主表")
    private Integer assetId;
    /**
     * mac
     */
    @ApiModelProperty("mac")
    @NotBlank(message = "mac不能为空")
    @Pattern(regexp = "^(([a-f0-9A-F]{2}:)|([a-f0-9A-F]{2}-)){5}[a-f0-9A-F]{2}$", message = "mac地址错误")
    @Size(message = "MAC地址长度应该为17位", max = 17, min = 17)
    private String  mac;
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

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
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