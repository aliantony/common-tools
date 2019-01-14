package com.antiy.asset.vo.response;

import java.util.List;
import java.util.Map;

/**
 * <p> AssetOperationRecordResponse 响应对象 </p>
 *
 * @author lvliang
 * @since 2018-12-27
 */

public class AssetOperationRecordResponse {
    /**
     * 被操作的对象ID
     */
    private Integer                   targetObjectIdId;
    /**
     * 被操作对象类别
     */
    private String                    targetType;
    /**
     * 状态
     */
    private Integer                   targetStatus;
    /**
     * 操作内容
     */
    private String                    content;
    /**
     * 操作人ID
     */
    private Integer                   operateUserId;
    /**
     * 操作人名字
     */
    private String                    operateUserName;
    /**
     * 方案表ID
     */
    private Integer                   schemaId;
    /**
     * 创建时间
     */
    private Long                      gmtCreate;
    /**
     * 创建人
     */
    private Integer                   createUser;
    /**
     * 附件信息（[{"XXX文件","10.12.5.1:5566/file/asset/dj"}]）
     */
    private List<Map<String, String>> files;

    public List<Map<String, String>> getFiles() {
        return files;
    }

    public void setFiles(List<Map<String, String>> files) {
        this.files = files;
    }

    public String getOperateUserName() {
        return operateUserName;
    }

    public void setOperateUserName(String operateUserName) {
        this.operateUserName = operateUserName;
    }

    public Integer getTargetObjectIdId() {
        return targetObjectIdId;
    }

    public void setTargetObjectIdId(Integer targetObjectIdId) {
        this.targetObjectIdId = targetObjectIdId;
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

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }
}