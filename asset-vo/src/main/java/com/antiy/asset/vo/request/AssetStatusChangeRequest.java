package com.antiy.asset.vo.request;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;

/**
 * @auther: zhangyajun
 * @date: 2019/5/20 15:16
 * @description:
 */
@ApiModel(value = "硬件资产不予登记")
public class AssetStatusChangeRequest extends BasicRequest implements ObjectValidator {



    @ApiModelProperty(value = "执行流程参数对象", notes = "可选")
    private ActivityHandleRequest activityHandleRequest;

    /**
     * 资产主键
     */
    @Encode
    @ApiModelProperty("资产主键")
    @NotEmpty(message = "资产主键不能为空")
    private String [] assetId;

    public ActivityHandleRequest getActivityHandleRequest() {
        return activityHandleRequest;
    }

    public void setActivityHandleRequest(ActivityHandleRequest activityHandleRequest) {
        this.activityHandleRequest = activityHandleRequest;
    }

    public String[] getAssetId() {
        return assetId;
    }

    public void setAssetId(String[] assetId) {
        this.assetId = assetId;
    }

    @Override
    public void validate() throws RequestParamValidateException {

    }
}
