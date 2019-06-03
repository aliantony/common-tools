package com.antiy.asset.vo.response;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class AssetCategoryModelNodeResponse extends AssetCategoryModelResponse {
    @ApiModelProperty(value = "级次")
    private Integer                              levelType;
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

    public Integer getLevelType() {
        return levelType;
    }

    public void setLevelType(Integer levelType) {
        this.levelType = levelType;
    }

    @Override
    public String toString() {
        return "AssetCategoryModelNodeResponse{" + "levelType=" + levelType + ", readOnly=" + readOnly
               + ", childrenNode=" + childrenNode + '}';
    }
}
