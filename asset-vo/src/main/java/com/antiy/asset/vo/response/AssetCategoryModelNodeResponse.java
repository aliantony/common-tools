package com.antiy.asset.vo.response;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class AssetCategoryModelNodeResponse extends AssetCategoryModelResponse {
    @ApiModelProperty(value = "级次")
    private Integer                              levelType;
    @ApiModelProperty(value = "是否可删除:true可以删除 ，false不可以")
    private boolean                              deleteOnly;
    @ApiModelProperty(value = "是否可变更:true可以变更，false不可以")
    private boolean                              changeOnly;
    @ApiModelProperty(value = "是否可添加子节点:true可以添加，false不可以")
    private boolean                              addOnly;
    @ApiModelProperty(value = "关联资产数")
    private Long                                 count;

    @ApiModelProperty(value = "子节点")
    private List<AssetCategoryModelNodeResponse> childrenNode;

    public boolean isDeleteOnly() {
        return deleteOnly;
    }

    public void setDeleteOnly(boolean deleteOnly) {
        this.deleteOnly = deleteOnly;
    }

    public boolean isChangeOnly() {
        return changeOnly;
    }

    public void setChangeOnly(boolean changeOnly) {
        this.changeOnly = changeOnly;
    }

    public boolean isAddOnly() {
        return addOnly;
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

    public boolean getAddOnly() {
        return addOnly;
    }

    public void setAddOnly(boolean addOnly) {
        this.addOnly = addOnly;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "AssetCategoryModelNodeResponse{" +
                "levelType=" + levelType +
                ", deleteOnly=" + deleteOnly +
                ", changeOnly=" + changeOnly +
                ", addOnly=" + addOnly +
                ", count=" + count +
                ", childrenNode=" + childrenNode +
                '}';
    }
}
