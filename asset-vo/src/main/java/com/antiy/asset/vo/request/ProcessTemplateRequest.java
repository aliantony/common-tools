package com.antiy.asset.vo.request;

import com.antiy.common.base.BaseRequest;
import com.antiy.common.encoder.Encode;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class ProcessTemplateRequest extends BaseRequest {
    @Encode
    @ApiModelProperty(value = "流程当前状态")
    private String       type;
    // @Encode
    @ApiModelProperty(value = "计算设备id数组")
    private List<String> comIds;
    // @Encode
    @ApiModelProperty(value = "全部id数组")
    private List<String> ids;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public List<String> getComIds() {
        return comIds;
    }

    public void setComIds(List<String> comIds) {
        this.comIds = comIds;
    }
}
