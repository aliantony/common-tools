package com.antiy.asset.entity;

import com.antiy.common.base.BaseEntity;

/**
 * <p> 资产操作记录表 </p>
 *
 * @author zhangyajun
 * @since 2019-01-07
 */

public class AssetOperationRecord extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 被操作的对象ID
     */
    private Integer           targetObject;
    /**
     * 被修改的表名称
     */
    private String            targetTableName;
    /**
     * 操作内容
     */
    private String            content;
    /**
     * 创建人
     */
    private Integer           createUser;
    /**
     * 创建时间
     */
    private Long              gmtCreate;

    public Integer getTargetObject() {
        return targetObject;
    }

    public void setTargetObject(Integer targetObject) {
        this.targetObject = targetObject;
    }

    public String getTargetTableName() {
        return targetTableName;
    }

    public void setTargetTableName(String targetTableName) {
        this.targetTableName = targetTableName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
        return "AssetOperationRecord{" + ", targetObject=" + targetObject + ", targetTableName=" + targetTableName
               + ", content=" + content + ", createUser=" + createUser + ", gmtCreate=" + gmtCreate + "}";
    }
}