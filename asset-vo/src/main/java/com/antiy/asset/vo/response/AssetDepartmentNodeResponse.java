package com.antiy.asset.vo.response;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Auther: zhangbing
 * @Date: 2019/1/8 14:29
 * @Description:
 */
@ApiModel(value = "资产组织树型显示")
public class AssetDepartmentNodeResponse extends AssetDepartmentResponse {
    @ApiModelProperty(value = "级次")
    private Integer                           level;
    @ApiModelProperty(value = "是否可编辑")
    private boolean                           readOnly;
    @ApiModelProperty(value = "子节点")
    private List<AssetDepartmentNodeResponse> childrenNode;

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public List<AssetDepartmentNodeResponse> getChildrenNode() {
        return childrenNode;
    }

    public void setChildrenNode(List<AssetDepartmentNodeResponse> childrenNode) {
        this.childrenNode = childrenNode;
    }

    @Override
    public String toString() {
        return "AssetDepartmentNodeResponse{" + "level=" + level + ", readOnly=" + readOnly + ", childrenNode="
               + childrenNode + '}';
    }
}
