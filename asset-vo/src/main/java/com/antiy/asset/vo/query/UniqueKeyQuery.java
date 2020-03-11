package com.antiy.asset.vo.query;

import com.antiy.common.base.ObjectQuery;
import com.antiy.common.encoder.Encode;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class UniqueKeyQuery extends ObjectQuery {

    @Encode
    @ApiModelProperty("区域")
    private List<String> areaList;
    @ApiModelProperty("唯一键")
    private String uniqueId;

    public List<String> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<String> areaList) {
        this.areaList = areaList;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }
}
