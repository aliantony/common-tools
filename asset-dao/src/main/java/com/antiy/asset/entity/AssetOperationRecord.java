package com.antiy.asset.entity;

import com.antiy.common.base.BaseEntity;

/**
 * <p>资产操作记录表</p>
 *
 * @author zhangyajun
 * @since 2019-01-14
 */

public class AssetOperationRecord extends BaseEntity {

    /**
     * 被操作的对象ID
     */
    private String  targetObjectId;
    /**
     * 状态
     */
    private Integer targetStatus;
    /**
     * 操作内容
     */
    private String  content;
    /**
     * 操作人ID
     */
    private Integer operateUserId;
    /**
     * 操作人名字
     */
    private String  operateUserName;
    /**
     * 创建时间
     */
    private Long    gmtCreate;
    /**
     * 创建人
     */
    private Integer createUser;

    /**
     * 状态：1-未删除,0-已删除
     */
    private Integer status;

    /**
     * 处理结果. 0拒绝1同意
     */
    private Integer processResult;

    /**
     * 原始状态
     */
    private Integer originStatus;

    /**
     * 输入的备注信息
     */
    private String note;

    /**
     * 上传的文件信息;JSON数组
     */
    private String fileInfo;


    public Integer getOriginStatus() {
        return originStatus;
    }

    public void setOriginStatus(Integer originStatus) {
        this.originStatus = originStatus;
    }

    public String getTargetObjectId() {
        return targetObjectId;
    }

    public void setTargetObjectId(String targetObjectId) {
        this.targetObjectId = targetObjectId;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getProcessResult() {
        return processResult;
    }

    public void setProcessResult(Integer processResult) {
        this.processResult = processResult;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getFileInfo() {
        return fileInfo;
    }

    public void setFileInfo(String fileInfo) {
        this.fileInfo = fileInfo;
    }

    @Override
    public String toString() {
        return "AssetOperationRecord{" +
                "targetObjectId='" + targetObjectId + '\'' +
                ", targetStatus=" + targetStatus +
                ", content='" + content + '\'' +
                ", operateUserId=" + operateUserId +
                ", operateUserName='" + operateUserName + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", createUser=" + createUser +
                ", processResult=" + processResult +
                ", originStatus=" + originStatus +
                ", note='" + note + '\'' +
                ", fileInfo='" + fileInfo + '\'' +
                '}';
    }
}