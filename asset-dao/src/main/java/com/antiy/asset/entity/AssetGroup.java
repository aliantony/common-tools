package com.antiy.asset.entity;

import com.antiy.common.base.BaseEntity;

/**
 * <p> 资产组表 </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */

public class AssetGroup extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用途
     */
    private String            purpose;
    /**
     * 重要程度(0-不重要(not_major),1- 一般(general),3-重要(major),)
     */
    private Integer           importantDegree;
    /**
     * 资产组名称
     */
    private String            name;
    /**
     * 创建人名字
     */
    private String            createUserName;
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

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public Integer getImportantDegree() {
        return importantDegree;
    }

    public void setImportantDegree(Integer importantDegree) {
        this.importantDegree = importantDegree;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "AssetGroup{" +
                "purpose='" + purpose + '\'' +
                ", importantDegree=" + importantDegree +
                ", name='" + name + '\'' +
                ", createUserName='" + createUserName + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", memo='" + memo + '\'' +
                ", createUser='" + createUser + '\'' +
                ", modifyUser='" + modifyUser + '\'' +
                ", status=" + status +
                '}';
    }
}