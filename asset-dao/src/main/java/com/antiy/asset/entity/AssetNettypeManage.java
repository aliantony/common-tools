package com.antiy.asset.entity;


import com.antiy.common.base.BaseEntity;
/**
 * <p></p>
 *
 * @author wangqian
 * @since 2020-04-07
 */

public class AssetNettypeManage extends BaseEntity {


private static final long serialVersionUID = 1L;

    /**
    *  网络类型名称
    */
    private String netTypeName;
    /**
    *  描述
    */
    private String description;
    /**
    *  状态 1、正常  0、删除
    */
    private Integer status;
    /**
    *  创建人
    */
    private Integer createUser;
    /**
    *  创建时间
    */
    private Long gmtCreate;
    /**
    *  修改人
    */
    private Integer modifiedUser;
    /**
    *  修改时间
    */
    private Long gmtModified;



    public String getNetTypeName() {
        return netTypeName;
    }

    public void setNetTypeName(String netTypeName) {
        this.netTypeName = netTypeName;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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


    public Integer getModifiedUser() {
        return modifiedUser;
    }

    public void setModifiedUser(Integer modifiedUser) {
        this.modifiedUser = modifiedUser;
    }


    public Long getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }


    @Override
    public String toString() {
            return "AssetNettypeManage{" +
                        ", netTypeName=" + netTypeName +
                        ", description=" + description +
                        ", status=" + status +
                        ", createUser=" + createUser +
                        ", gmtCreate=" + gmtCreate +
                        ", modifiedUser=" + modifiedUser +
                        ", gmtModified=" + gmtModified +
            "}";
    }
}