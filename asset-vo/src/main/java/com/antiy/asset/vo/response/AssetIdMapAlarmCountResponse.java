package com.antiy.asset.vo.response;

import com.antiy.common.encoder.Encode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("资产对应的告警数量")
public class AssetIdMapAlarmCountResponse {
    @ApiModelProperty("该资产对应的告警数量")
    private Integer count;

    @ApiModelProperty("该资产id")
    @Encode
    private String id;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "AssetIdMapAlarmCountResponse{" +
                "count=" + count +
                ", id=" + id +
                '}';
    }
}
