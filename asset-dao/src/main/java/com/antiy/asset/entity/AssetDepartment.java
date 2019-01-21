package com.antiy.asset.entity;

import com.antiy.common.base.BaseEntity;

/**
 * <p> 资产部门信息 </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */

public class AssetDepartment extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 部门名
     */
    private String            name;

    /**
     * 上级部门
     */
    private Integer           parentId;
    /**
     * 创建时间
     */
    private Long              gmtCreate;
    /**
     * 修改时间
     */
    private Long              gmtModified;
    /**
     * 备注
     */
    private String            memo;
    /**
     * 创建人
     */
    private Integer           createUser;
    /**
     * 修改人
     */
    private Integer           modifyUser;
    /**
     * 状态,1未删除,0已删除
     */
    private Integer           status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Long getGmtModified() {
        return System.currentTimeMillis();
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
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

    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }

    @Override
    public String toString() {
        return "AssetDepartment{" + "name='" + name + '\'' + ", parentId=" + parentId + ", gmtCreate=" + gmtCreate
               + ", gmtModified=" + gmtModified + ", memo='" + memo + '\'' + ", createUser=" + createUser
               + ", modifyUser=" + modifyUser + ", status=" + status + '}';
    }
}