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
    /**
     * 资产名称
     */
    private String            assetName;
    /**
     * 类型（1.准入实施、2.效果检查、3.资产退役、4.验证退役方案、5.实施退役方案）
     */
    private Integer           type;
    /**
     * 结果
     */
    private Integer           result;
    /**
     * 实施时间
     */
    private Long              putintoTime;
    /**
     * 实施人
     */
    private String            putintoUser;
    /**
     * 附件路径
     */
    private String            filepath;

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

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
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

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    @Override
    public String toString() {
        return "AssetOperationRecord{" + ", targetObject=" + targetObject + ", targetTableName=" + targetTableName
               + ", content=" + content + ", createUser=" + createUser + ", gmtCreate=" + gmtCreate + "}";
    }
}