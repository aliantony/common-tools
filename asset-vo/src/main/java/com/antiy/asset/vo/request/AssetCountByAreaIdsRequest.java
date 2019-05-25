package com.antiy.asset.vo.request;

import java.util.List;

import com.antiy.common.base.BaseRequest;
import com.antiy.common.encoder.Encode;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Author chenhuasheng
 * @description
 * @create 2019/5/23 10:41
 */
public class AssetCountByAreaIdsRequest extends BaseRequest {
    @ApiModelProperty("areaIds")
    @Encode
    private List<String> areaIds;

    public List<String> getAreaIds() {
        return areaIds;
    }

    public void setAreaIds(List<String> areaIds) {
        this.areaIds = areaIds;
    }
}
