package com.antiy.asset.vo.response;

import java.util.Map;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "资产统计数据")
public class AssetCountResponse {
    /**
     * 名称
     */
    @ApiModelProperty("资产统计数据映射集合,对应为类别名-统计数")
    private Map<String, Long> map;

    public Map<String, Long> getMap() {
        return map;
    }

    public void setMap(Map<String, Long> map) {
        this.map = map;
    }

    @Override
    public String toString() {
        return "AssetCountResponse{" +
                "map=" + map +
                '}';
    }
}
