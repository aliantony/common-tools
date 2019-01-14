package com.antiy.asset.entity;

import com.antiy.common.base.BaseEntity;

/**
 * <p>资产操作记录表</p>
 *
 * @author zhangyajun
 * @since 2019-01-14
 */

public class AssetOperationRecord extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 被操作的对象ID
     */
    private Integer           targetObjectId;
    /**
     * 被修改的表名称
     */
    private String            targetTableName;
    /**
     * 状态
     */
    private String            targetStatusName;
    /**
     * 操作内容
     */
    private String            content;
    /**
     * 操作人ID
     */
    private Integer           operateUserId;
    /**
     * 操作人名字
     */
    private String            operateUserName;
    /**
     * 方案表ID
     */
    private Integer           schemaId;
    /**
     * 创建时间
     */
    private Long              gmtCreate;

    public Integer getTargetObjectId() {
        return targetObjectId;
    }

    public void setTargetObjectId(Integer targetObjectId) {
        this.targetObjectId = targetObjectId;
    }

    public String getTargetTableName() {
        return targetTableName;
    }

    public void setTargetTableName(String targetTableName) {
        this.targetTableName = targetTableName;
    }

    public String getTargetStatusName() {
        return targetStatusName;
    }

    public void setTargetStatusName(String targetStatusName) {
        this.targetStatusName = targetStatusName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getOperateUserId() {
        return operateUserId;
    }

    public void setOperateUserId(Integer operateUserId) {
        this.operateUserId = operateUserId;
    }

    public String getOperateUserName() {
        return operateUserName;
    }

    public void setOperateUserName(String operateUserName) {
        this.operateUserName = operateUserName;
    }

    public Integer getSchemaId() {
        return schemaId;
    }

    public void setSchemaId(Integer schemaId) {
        this.schemaId = schemaId;
    }

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    @Override
    public String toString() {
        return "AssetOperationRecord{" + ", targetObjectId=" + targetObjectId + ", targetTableName=" + targetTableName
               + ", targetStatusName=" + targetStatusName + ", content=" + content + ", operateUserId=" + operateUserId
               + ", operateUserName=" + operateUserName + ", schemaId=" + schemaId + ", gmtCreate=" + gmtCreate + "}";
    }
}