package com.antiy.asset.vo.response;

import com.antiy.common.encoder.Encode;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetOperationRecordResponse 响应对象 </p>
 *
 * @author lvliang
 * @since 2018-12-27
 */

public class AssetOperationRecordResponse extends BaseResponse {
    /**
     * 被操作的对象ID
     */
    @ApiModelProperty(value = "操作对象Id")
    @Encode
    private String  targetObjectId;
    /**
     * 被操作对象类别
     */
    private String  targetType;
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
     * 方案表ID
     */
    private Integer schemaId;
    /**
     * 创建时间
     */
    private Long    gmtCreate;
    /**
     * 类型（1.准入实施、2.效果检查、3.资产退役、4.验证退役方案、5.实施退役方案）
     */
    private Integer type;
    /**
     * 结果
     */
    private Integer result;
    /**
     * 实施时间
     */
    private Long    putintoTime;
    /**
     * 实施人
     */
    private String  putintoUser;
    /**
     * 附件信息（[{"XXX文件","10.12.5.1:5566/file/asset/dj"}]）
     */
    private String  fileInfo;

    public String getOperateUserName() {
        return operateUserName;
    }

    public void setOperateUserName(String operateUserName) {
        this.operateUserName = operateUserName;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public Long getPutintoTime() {
        return putintoTime;
    }

    public void setPutintoTime(Long putintoTime) {
        this.putintoTime = putintoTime;
    }

    public String getPutintoUser() {
        return putintoUser;
    }

    public void setPutintoUser(String putintoUser) {
        this.putintoUser = putintoUser;
    }

    public String getTargetObjectId() {
        return targetObjectId;
    }

    public void setTargetObjectId(String targetObjectId) {
        this.targetObjectId = targetObjectId;
    }

    public String getFileInfo() {
        return fileInfo;
    }

    public void setFileInfo(String fileInfo) {
        this.fileInfo = fileInfo;
    }
}