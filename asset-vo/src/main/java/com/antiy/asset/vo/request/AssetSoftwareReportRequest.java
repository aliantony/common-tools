package com.antiy.asset.vo.request;

import java.util.List;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetSoftwareRequest 请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */
@ApiModel(value = "软件请求")
public class AssetSoftwareReportRequest extends BasicRequest implements ObjectValidator {

    @ApiModelProperty("资产id")
    @Encode
    private List<String> assetId;

    @ApiModelProperty("软件id")
    private List<Long>   softId;

    public List<String> getAssetId() {
        return assetId;
    }

    public void setAssetId(List<String> assetId) {
        this.assetId = assetId;
    }

    public List<Long> getSoftId() {
        return softId;
    }

    public void setSoftId(List<Long> softId) {
        this.softId = softId;
    }

    @Override
    public void validate() throws RequestParamValidateException {

    }
}