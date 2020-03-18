package com.antiy.asset.vo.request;

import com.antiy.common.base.BaseRequest;
import io.swagger.annotations.ApiModelProperty;

public class AssetCorrectingRequest extends BaseRequest {

    @ApiModelProperty("漏洞流程")
    private ActivityHandleRequest vlunActivity;
    @ApiModelProperty("配置流程")
    private ActivityHandleRequest baseLineActivity;

    public ActivityHandleRequest getVlunActivity() {
        return vlunActivity;
    }

    public void setVlunActivity(ActivityHandleRequest vlunActivity) {
        this.vlunActivity = vlunActivity;
    }

    public ActivityHandleRequest getBaseLineActivity() {
        return baseLineActivity;
    }

    public void setBaseLineActivity(ActivityHandleRequest baseLineActivity) {
        this.baseLineActivity = baseLineActivity;
    }
}
