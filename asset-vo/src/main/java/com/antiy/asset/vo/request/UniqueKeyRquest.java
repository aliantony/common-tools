package com.antiy.asset.vo.request;

import io.swagger.annotations.ApiModelProperty;

public class UniqueKeyRquest  {

    @ApiModelProperty("唯一键")
    private String uniqueId;

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }
}
