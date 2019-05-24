package com.antiy.asset.vo.request;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Author chenhuasheng
 * @description
 * @create 2019/5/23 10:41
 */
public class AssetCountByAreaIdsRequest {
    @ApiModelProperty("areaIds")
    private Integer[] areaIds;

    public Integer[] getAreaIds() {
        return areaIds;
    }

    public void setAreaIds(Integer[] areaIds) {
        this.areaIds = areaIds;
    }
}
