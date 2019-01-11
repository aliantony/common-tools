package com.antiy.asset.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @Auther: zhangbing
 * @Date: 2019/1/8 14:29
 * @Description:
 */
@ApiModel(value = "资产组织树型显示")
public class AssetDepartmentNodeResponse extends AssetDepartmentResponse {

    @ApiModelProperty(value = "子节点")
    private List<AssetDepartmentNodeResponse> childrenNode;

    public List<AssetDepartmentNodeResponse> getChildrenNode() {
        return childrenNode;
    }

    public void setChildrenNode(List<AssetDepartmentNodeResponse> childrenNode) {
        this.childrenNode = childrenNode;
    }
}
