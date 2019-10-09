package com.antiy.asset.vo.request;

import com.antiy.common.base.BaseRequest;
import com.antiy.common.encoder.Encode;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class ProcessTemplateRequest extends BaseRequest {

    @ApiModelProperty(value = "流程为入网状态时:传入true")
    private boolean      flag;
    @Encode
    @ApiModelProperty(value = "计算设备id数组")
    private List<String> comIds;
    @Encode
    @ApiModelProperty(value = "全部id数组")
    private List<String> ids;

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

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
