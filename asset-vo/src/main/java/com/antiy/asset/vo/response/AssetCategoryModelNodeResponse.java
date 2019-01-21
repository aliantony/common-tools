package com.antiy.asset.vo.response;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class AssetCategoryModelNodeResponse extends AssetCategoryModelResponse {
    @ApiModelProperty(value = "子节点")
    private List<AssetCategoryModelResponse> childrenNode;

    public List<AssetCategoryModelResponse> getChildrenNode() {
        return childrenNode;
    }

    public void setChildrenNode(List<AssetCategoryModelResponse> childrenNode) {
        this.childrenNode = childrenNode;
    }
}
