package com.antiy.asset.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetLicenseResponse 资产报表返回对象 </p>
 *
 * @author lvliang
 * @since 2019-03-26
 */
@ApiModel
public class AssetLicenseResponse extends BaseResponse {
    /**
     *
     */
    @ApiModelProperty("校验结果")
    private Boolean result;

    @ApiModelProperty("消息")
    private String  msg;

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
