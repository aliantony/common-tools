package com.antiy.asset.entity;

import com.antiy.common.base.BaseEntity;

/**
 * <p>CPE过滤表</p>
 *
 * @author zhangyajun
 * @since 2020-03-15
 */

public class AssetCpeTree extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一键
     */
    private String            uniqueId;
    /**
     * 父id
     */
    private String            pid;
    /**
     * 标题
     */
    private String            title;
    /**
     * 数据修改时间
     */
    private Long              gmtModified;
    /**
     * 创建时间
     */
    private Long              gmtCreate;
    /**
     * 创建人
     */
    private String            createUser;
    /**
     * 修改人
     */
    private String            modifiedUser;
    /**
     * 1正常，0删除
     */
    private Integer           status;

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getModifiedUser() {
        return modifiedUser;
    }

    public void setModifiedUser(String modifiedUser) {
        this.modifiedUser = modifiedUser;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AssetCpeTree{" + ", uniqueId=" + uniqueId + ", pid=" + pid + ", title=" + title + ", gmtModified="
               + gmtModified + ", gmtCreate=" + gmtCreate + ", createUser=" + createUser + ", modifiedUser="
               + modifiedUser + ", status=" + status + "}";
    }
}