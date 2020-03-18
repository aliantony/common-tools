package com.antiy.asset.vo.request;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class UniqueKeyRquest  {

    @ApiModelProperty("唯一键")
    private String uniqueId;

    @ApiModelProperty("唯一键")
    private List<String> uniqueIds;
    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public List<String> getUniqueIds() {
        return uniqueIds;
    }

    public void setUniqueIds(List<String> uniqueIds) {
        this.uniqueIds = uniqueIds;
    }
}
