package com.antiy.asset.entity;

import java.util.List;

/**
 * <p> AssetOperationRecordMapper 数据库返回对象 </p>
 *
 * @author lvliang
 * @since 2018-12-27
 */

public class AssetOperationRecordMapper {
    /**
     * 被操作的对象ID
     */
    private Integer        targetObject;
    /**
     * 被操作对象名称
     */
    private String         targetName;
    /**
     * 状态
     */
    private Integer        targetStatus;
    /**
     * 操作人名字
     */
    private String         operateUserName;
    /**
     * 操作内容
     */
    private String         content;
    /**
     * 创建时间
     */
    private Long           gmtCreate;
    /**
     * 类型（1.准入实施、2.效果检查、3.资产退役、4.验证退役方案、5.实施退役方案）
     */
    private Integer        type;
    /**
     * 结果
     */
    private Integer        result;
    /**
     * 实施时间
     */
    private Long           putintoTime;
    /**
     * 实施人
     */
    private String         putintoUser;
    /**
     * 附件信息（[{"XXX文件","10.12.5.1:5566/file/asset/dj"}]）
     */
    private List<String[]> files;

    public Integer getTargetObject() {
        return targetObject;
    }

    public void setTargetObject(Integer targetObject) {
        this.targetObject = targetObject;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
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

    public String getOperateUserName() {
        return operateUserName;
    }

    public void setOperateUserName(String operateUserName) {
        this.operateUserName = operateUserName;
    }

    public List<String[]> getFiles() {
        return files;
    }

    public void setFiles(List<String[]> files) {
        this.files = files;
    }
}