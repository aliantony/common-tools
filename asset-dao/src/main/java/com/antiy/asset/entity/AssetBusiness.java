package com.antiy.asset.entity;


import com.antiy.common.base.BaseEntity;

/**
 * <p></p>
 *
 * @author zhangyajun
 * @since 2020-02-17
 */

public class AssetBusiness extends BaseEntity {


private static final long serialVersionUID = 1L;

    /**
    *  业务名称（中文字符，去重）
    */
    private String name;
    /**
    *  业务重要性：1-高，2-中，3-低
    */
    private Integer importance;
    /**
    *  描述
    */
    private String description;
    /**
    *  创建时间
    */
    private Long gmtCreate;
    /**
    *  更新时间
    */
    private Long gmtModified;
    /**
    *  创建人
    */
    private Integer createUser;
    /**
    *  修改人
    */
    private Integer modifyUser;
    /**
    *  状态：1-未删除,0-已删除
    */
    private Integer status;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Integer getImportance() {
        return importance;
    }

    public void setImportance(Integer importance) {
        this.importance = importance;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }


    public Long getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }


    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }


    public Integer getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(Integer modifyUser) {
        this.modifyUser = modifyUser;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    @Override
    public String toString() {
            return "AssetBusiness{" +
                        ", name=" + name +
                        ", importance=" + importance +
                        ", description=" + description +
                        ", gmtCreate=" + gmtCreate +
                        ", gmtModified=" + gmtModified +
                        ", createUser=" + createUser +
                        ", modifyUser=" + modifyUser +
                        ", status=" + status +
            "}";
    }
}