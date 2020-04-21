package com.antiy.asset.vo.response;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: lvliang
 * @Date: 2020/4/21 15:27
 */
@ApiModel
public class AssetAssemblyDetailResponse {
    /**
     * 组件类型
     */
    @ApiModelProperty("组件类型")
    private String                      type;
    /**
     * 类型名称
     */
    @ApiModelProperty("类型名称")
    private String                      typeName;
    /**
     * 组件数量
     */
    @ApiModelProperty("组件数量")
    private Integer                     count;
    /**
     * 组件详情
     */
    @ApiModelProperty("组件详情")
    private List<AssetAssemblyResponse> assemblyResponseList;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<AssetAssemblyResponse> getAssemblyResponseList() {
        return assemblyResponseList;
    }

    public void setAssemblyResponseList(List<AssetAssemblyResponse> assemblyResponseList) {
        this.assemblyResponseList = assemblyResponseList;
    }
}
