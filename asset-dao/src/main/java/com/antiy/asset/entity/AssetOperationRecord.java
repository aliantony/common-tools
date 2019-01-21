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
     * 被操作对象类别
     */
    private String            targetType;
    /**
     * 状态
     */
    private Integer           targetStatus;
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
    private Integer           schemeId;
    /**
     * 创建时间
     */
    private Long              gmtCreate;
    /**
     * 创建人
     */
    private Integer           createUser;

    public Integer getTargetObjectId() {
        return targetObjectId;
    }

    public void setTargetObjectId(Integer targetObjectId) {
        this.targetObjectId = targetObjectId;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public Integer getTargetStatus() {
        return targetStatus;
    }

    public void setTargetStatus(Integer targetStatus) {
        this.targetStatus = targetStatus;
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

    public Integer getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(Integer schemeId) {
        this.schemeId = schemeId;
    }

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    @Override
    public String toString() {
        return "AssetOperationRecord{" + ", targetObjectId=" + targetObjectId + ", targetType=" + targetType
               + ", targetStatus=" + targetStatus + ", content=" + content + ", operateUserId=" + operateUserId
               + ", operateUserName=" + operateUserName + ", schemeId=" + schemeId + ", gmtCreate=" + gmtCreate + "}";
    }
}