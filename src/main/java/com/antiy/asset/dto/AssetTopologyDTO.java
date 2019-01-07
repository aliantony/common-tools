package com.antiy.asset.dto;

import com.antiy.common.base.BaseEntity;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p> 资产拓扑表 数据对象 </p>
 *
 * @author zhangyajun
 * @since 2019-01-07
 */

public class AssetTopologyDTO extends BaseEntity {

    /**
     * 拓扑类型
     */
    @ApiModelProperty("拓扑类型")
    private Integer topologyType;
    /**
     * 拓扑关系
     */
    @ApiModelProperty("拓扑关系")
    private String  relation;
    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    private Integer createUser;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Long    gmtCreate;

    public Integer getTopologyType() {
        return topologyType;
    }

    public void setTopologyType(Integer topologyType) {
        this.topologyType = topologyType;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    @Override
    public String toString() {
        return "AssetTopology{" + ", topologyType=" + topologyType + ", relation=" + relation + ", createUser="
               + createUser + ", gmtCreate=" + gmtCreate + "}";
    }
}