package com.antiy.asset.entity;

import com.antiy.common.base.BaseEntity;

/**
 * <p> 资产拓扑表 </p>
 *
 * @author zhangyajun
 * @since 2019-01-07
 */

public class AssetTopology extends BaseEntity {

    /**
     * 拓扑类型
     */
    private Integer topologyType;
    /**
     * 拓扑关系
     */
    private String  relation;
    /**
     * 创建人
     */
    private Integer createUser;
    /**
     * 创建时间
     */
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