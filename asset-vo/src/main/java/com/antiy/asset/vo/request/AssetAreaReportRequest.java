package com.antiy.asset.vo.request;

import java.util.List;

import com.antiy.common.base.BaseRequest;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author: zhangbing
 * @date: 2019/3/26 11:18
 * @description:
 */
public class AssetAreaReportRequest extends BaseRequest {

    @ApiModelProperty(value = "父区域id信息")
    private Integer       parentAreaId;

    @ApiModelProperty(value = "子区域id列表信息")
    private List<Integer> childrenAradIds;

    public Integer getParentAreaId() {
        return parentAreaId;
    }

    public void setParentAreaId(Integer parentAreaId) {
        this.parentAreaId = parentAreaId;
    }

    public List<Integer> getChildrenAradIds() {
        return childrenAradIds;
    }

    public void setChildrenAradIds(List<Integer> childrenAradIds) {
        this.childrenAradIds = childrenAradIds;
    }
}
