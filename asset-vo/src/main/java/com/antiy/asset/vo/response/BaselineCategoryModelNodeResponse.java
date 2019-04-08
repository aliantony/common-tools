package com.antiy.asset.vo.response;

import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

public class BaselineCategoryModelNodeResponse extends BaselineCategoryModelResponse {
    @ApiModelProperty(value = "子节点")
    private List<BaselineCategoryModelNodeResponse> childrenNode = new ArrayList<>();

    public List<BaselineCategoryModelNodeResponse> getChildrenNode() {
        return childrenNode;
    }

    public void setChildrenNode(List<BaselineCategoryModelNodeResponse> childrenNode) {
        this.childrenNode = childrenNode;
    }

    @Override
    public String toString() {
        return "AssetCategoryModelNodeResponse{" +
                "childrenNode=" + childrenNode +
                '}';
    }
}
