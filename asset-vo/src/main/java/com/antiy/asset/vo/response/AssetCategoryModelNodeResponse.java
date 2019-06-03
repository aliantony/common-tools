package com.antiy.asset.vo.response;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class AssetCategoryModelNodeResponse extends AssetCategoryModelResponse {
    @ApiModelProperty(value = "级次")
    private Integer                              level;
    @ApiModelProperty(value = "是否可编辑")
    private boolean                              readOnly;
    @ApiModelProperty(value = "子节点")
    private List<AssetCategoryModelNodeResponse> childrenNode;

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public List<AssetCategoryModelNodeResponse> getChildrenNode() {
        return childrenNode;
    }

    public void setChildrenNode(List<AssetCategoryModelNodeResponse> childrenNode) {
        this.childrenNode = childrenNode;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "AssetCategoryModelNodeResponse{" + "level=" + level + ", readOnly=" + readOnly + ", childrenNode="
               + childrenNode + '}';
    }
}
