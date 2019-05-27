package com.antiy.asset.vo.request;

import com.antiy.common.base.BaseRequest;
import com.antiy.common.encoder.Encode;

public class ExportTemplateRequest extends BaseRequest {
    @Encode
    private String[] type;

    public String[] getType() {
        return type;
    }

    public void setType(String[] type) {
        this.type = type;
    }
}
