package com.antiy.asset.vo.request;

import java.util.List;

import javax.validation.Valid;

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
    private List<String>               assetId;

    @ApiModelProperty("软件id")
    private List<Long>                 softId;

    @ApiModelProperty(value = "启动流程数据")
    @Valid
    private ManualStartActivityRequest manualStartActivityRequest;

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

    public ManualStartActivityRequest getManualStartActivityRequest() {
        return manualStartActivityRequest;
    }

    public void setManualStartActivityRequest(ManualStartActivityRequest manualStartActivityRequest) {
        this.manualStartActivityRequest = manualStartActivityRequest;
    }

    @Override
    public void validate() throws RequestParamValidateException {

    }
}