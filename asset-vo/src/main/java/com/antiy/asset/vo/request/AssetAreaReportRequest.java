package com.antiy.asset.vo.request;

import com.antiy.common.base.BaseRequest;
import com.antiy.common.encoder.Encode;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author: zhangbing
 * @date: 2019/3/26 11:18
 * @description:
 */
public class AssetAreaReportRequest extends BaseRequest {

    @ApiModelProperty(value = "父区域id信息")
    @Encode(message = "父区域id解密失败")
    private String       parentAreaId;

    @ApiModelProperty(value = "父区域名字")
    private String       parentAreaName;

    @ApiModelProperty(value = "子区域id列表信息")
    @Encode(message = "子区域id解密失败")
    private List<String> childrenAradIds;

    public String getParentAreaName() {
        return parentAreaName;
    }

    public void setParentAreaName(String parentAreaName) {
        this.parentAreaName = parentAreaName;
    }

    public String getParentAreaId() {
        return parentAreaId;
    }

    public String getParentAreaIdInteger() {
        return parentAreaId;
    }

    public void setParentAreaId(String parentAreaId) {
        this.parentAreaId = parentAreaId;
    }

    public List<String> getChildrenAradIds() {
        return childrenAradIds;
    }

    public void setChildrenAradIds(List<String> childrenAradIds) {
        this.childrenAradIds = childrenAradIds;
    }
}
