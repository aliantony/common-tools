package com.antiy.asset.vo.request;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetOperationRecordRequest 请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetOperationRecordRequest extends BasicRequest implements ObjectValidator {

    /**
     * 被操作的对象ID
     */
    @ApiModelProperty("被操作的对象ID")
    @Encode
    private String  targetObjectId;
    /**
     * 被操作对象类别
     */
    @ApiModelProperty("被操作对象类别")
    private String  targetType;
    /**
     * 操作内容
     */
    @ApiModelProperty("操作内容")
    private String  content;
    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    private Integer createUser;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Long    gmtCreate;

    /**
     * 审批人id
     */
    @ApiModelProperty("审批人id")
    @Encode
    private String checkUserId;

    /**
     * 审批人姓名
     */
    @ApiModelProperty("审批人姓名")
    private String  checkUserName;
    /**
     * 执行人id
     */
    @ApiModelProperty("执行人id")
    @Encode
    private String executeUserId;
    /**
     * 执行人名称
     */
    @ApiModelProperty("执行人姓名")
    private String executeUserName;

    /**
     * 附件
     *
     */
    @ApiModelProperty("附件，多个用逗号隔开")
    /**
     * 附件
     */
    private String fileInfo;
    /**
     * 任务id
     *
     */
    private Integer taskId;

    public String getFileInfo() {
        return fileInfo;
    }

    public void setFileInfo(String fileInfo) {
        this.fileInfo = fileInfo;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getCheckUserName() {
        return checkUserName;
    }

    public void setCheckUserName(String checkUserName) {
        this.checkUserName = checkUserName;
    }

    public String getCheckUserId() {
        return checkUserId;
    }

    public void setCheckUserId(String checkUserId) {
        this.checkUserId = checkUserId;
    }

    public String getExecuteUserId() {
        return executeUserId;
    }

    public void setExecuteUserId(String executeUserId) {
        this.executeUserId = executeUserId;
    }

    public String getExecuteUserName() {
        return executeUserName;
    }

    public void setExecuteUserName(String executeUserName) {
        this.executeUserName = executeUserName;
    }

    public String getTargetTableName() {
        return targetType;
    }

    public void setTargetTableName(String targetType) {
        this.targetType = targetType;
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

    public String getTargetObjectId() {
        return targetObjectId;
    }

    public void setTargetObjectId(String targetObjectId) {
        this.targetObjectId = targetObjectId;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    @Override
    public String toString() {
        return "AssetOperationRecordRequest{" +
                "targetObjectId='" + targetObjectId + '\'' +
                ", targetType='" + targetType + '\'' +
                ", content='" + content + '\'' +
                ", createUser=" + createUser +
                ", gmtCreate=" + gmtCreate +
                '}';
    }

    @Override
    public void validate() throws RequestParamValidateException {

    }

}