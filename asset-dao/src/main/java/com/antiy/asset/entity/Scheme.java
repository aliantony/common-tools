package com.antiy.asset.entity;

import com.antiy.common.base.BaseEntity;

/**
 * <p> 方案表 </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */

public class Scheme extends BaseEntity {

    /**
     * 资产主键
     */
    private Integer assetd;
    /**
     * 类型（1.准入实施、2.效果检查、3.制定待退役方案、4.验证退役方案、5.实施退役方案）
     */
    private Integer type;
    /**
     * 结果
     */
    private Integer result;
    /**
     * 实施用户主键
     */
    private Integer putintoUserId;
    /**
     * 实施时间
     */
    private Long    putintoTime;
    /**
     * 实施人
     */
    private String  putintoUser;
    /**
     * 附件路径
     */
    private String  fileInfo;
    /**
     * 创建时间
     */
    private Long    gmtCreate;
    /**
     * 更新时间
     */
    private Long    gmtModified;
    /**
     * 备注
     */
    private String  memo;
    /**
     * 创建人
     */
    private Integer createUser;
    /**
     * 修改人
     */
    private Integer modifyUser;
    /**
     * 状态,1未删除,0已删除
     */
    private Integer status;

    public Integer getAssetd() {
        return assetd;
    }

    public void setAssetd(Integer assetd) {
        this.assetd = assetd;
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

    public Integer getPutintoUserId() {
        return putintoUserId;
    }

    public void setPutintoUserId(Integer putintoUserId) {
        this.putintoUserId = putintoUserId;
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
        return fileInfo;
    }

    public void setFilepath(String fileInfo) {
        this.fileInfo = fileInfo;
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

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Integer getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(Integer modifyUser) {
        this.modifyUser = modifyUser;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Scheme{" + ", type=" + type + ", result=" + result + ", putintoUserId=" + putintoUserId
               + ", putintoTime=" + putintoTime + ", putintoUser=" + putintoUser + ", fileInfo=" + fileInfo
               + ", gmtCreate=" + gmtCreate + ", gmtModified=" + gmtModified + ", memo=" + memo + ", createUser="
               + createUser + ", modifyUser=" + modifyUser + ", status=" + status + "}";
    }
}