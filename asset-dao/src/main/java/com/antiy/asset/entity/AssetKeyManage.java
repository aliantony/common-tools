package com.antiy.asset.entity;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author chenchaowu
 */
public class AssetKeyManage {

    /**
     * 主键ID
     */
    @ApiModelProperty("主键ID")
    private Integer id;

    /**
     * key编号
     */
    @ApiModelProperty("key编号")
    private String keyNum;

    /**
     * 使用者类型 0:设备 1:用户
     */
    @ApiModelProperty("使用者类型")
    private Integer keyUserType;

    /**
     * 使用者类型名
     */
    @ApiModelProperty("使用者类型名")
    private String typeName;

    /**
     * 使用者--资产ID or 用户ID
     */
    @ApiModelProperty("使用者 --资产ID or 用户ID")
    private Integer keyUserId;

    /**
     * 使用者 -- 资产编号 or 用户名
     */
    @ApiModelProperty("使用者 -- 资产编号 or 用户名")
    private String userNumName;

    /**
     * 领用时间
     */
    @ApiModelProperty("领用时间")
    private Long recipTime;

    /**
     * 领用状态 0:未领用 1:领用 2:冻结
     */
    @ApiModelProperty("领用状态")
    private Integer recipState;

    /**
     * 领用状态名
     */
    @ApiModelProperty("领用状态名")
    private String stateName;

    /**
     * key创建人
     */
    @ApiModelProperty("创建人")
    private Integer createUser;

    /**
     * key变更人
     */
    @ApiModelProperty("变更人")
    private Integer modifiedUser;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Long gmtCreate;

    /**
     * 变更时间
     */
    @ApiModelProperty("变更时间")
    private Long gmtModified;

    /**
     * 是否删除 0--未删除 1--已删除
     */
    private Integer isDelete;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKeyNum() {
        return keyNum;
    }

    public void setKeyNum(String keyNum) {
        this.keyNum = keyNum;
    }

    public Integer getRecipState() {
        return recipState;
    }

    public void setRecipState(Integer recipState) {
        this.recipState = recipState;
    }

    public Integer getKeyUserType() {
        return keyUserType;
    }

    public void setKeyUserType(Integer keyUserType) {
        this.keyUserType = keyUserType;
    }

    public Integer getKeyUserId() {
        return keyUserId;
    }

    public void setKeyUserId(Integer keyUserId) {
        this.keyUserId = keyUserId;
    }

    public String getUserNumName() {
        return userNumName;
    }

    public void setUserNumName(String userNumName) {
        this.userNumName = userNumName;
    }

    public Long getRecipTime() {
        return recipTime;
    }

    public void setRecipTime(Long recipTime) {
        this.recipTime = recipTime;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Integer getModifiedUser() {
        return modifiedUser;
    }

    public void setModifiedUser(Integer modifiedUser) {
        this.modifiedUser = modifiedUser;
    }

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Long getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public String toString() {
        return "AreaKeyManageResponse{" +
                "id='" + id + '\'' +
                ", keyNum=" + keyNum +
                ", keyUserType=" + keyUserType +
                ", typeName=" + typeName +
                ", keyUserId=" + keyUserId +
                ", userNumName=" + userNumName +
                ", recipTime=" + recipTime +
                ", recipState=" + recipState +
                ", stateName=" + stateName +
                ", createUser=" + createUser +
                ", modifiedUser=" + modifiedUser +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", isDelete=" + isDelete +
                '}';
    }
}
