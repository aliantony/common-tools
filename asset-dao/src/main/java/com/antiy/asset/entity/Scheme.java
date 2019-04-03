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
    private String  assetId;
    /**
     * 类型（1.准入实施、2.效果检查、3.制定待退役方案、4.验证退役方案、5.实施退役方案、6.硬件基准配置分析、7.硬件基准验证、、8.软件基准配置分析）
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
     * 工单级别(1提示2紧急2重要3次要)
     */
    private Integer orderLevel;
    /**
     * 预计开始时间
     */
    private Long    expecteStartTime;
    /**
     * 预计结束时间
     */
    private Long    expecteEndTime;
    /**
     * 附件路径
     */
    private String  fileInfo;
    /**
     * 方案来源
     */
    private Integer schemeSource;
    /**
     * 扩展字段,JSON串,基准分析才有
     */
    private String  extension;
    /**
     * 下一个资产状态
     */
    private Integer assetNextStatus;
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
    /**
     * 方案内容
     */
    private String  content;

    public Integer getAssetNextStatus() {
        return assetNextStatus;
    }

    public void setAssetNextStatus(Integer assetNextStatus) {
        this.assetNextStatus = assetNextStatus;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
        return System.currentTimeMillis();
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

    public Integer getOrderLevel() {
        return orderLevel;
    }

    public void setOrderLevel(Integer orderLevel) {
        this.orderLevel = orderLevel;
    }

    public Long getExpecteStartTime() {
        return expecteStartTime;
    }

    public void setExpecteStartTime(Long expecteStartTime) {
        this.expecteStartTime = expecteStartTime;
    }

    public Long getExpecteEndTime() {
        return expecteEndTime;
    }

    public void setExpecteEndTime(Long expecteEndTime) {
        this.expecteEndTime = expecteEndTime;
    }

    public String getFileInfo() {
        return fileInfo;
    }

    public void setFileInfo(String fileInfo) {
        this.fileInfo = fileInfo;
    }

    public Integer getSchemeSource() {
        return schemeSource;
    }

    public void setSchemeSource(Integer schemeSource) {
        this.schemeSource = schemeSource;
    }

    @Override
    public String toString() {
        return "Scheme{" + "assetId='" + assetId + '\'' + ", type=" + type + ", result=" + result + ", putintoUserId="
               + putintoUserId + ", putintoTime=" + putintoTime + ", putintoUser='" + putintoUser + '\''
               + ", orderLevel=" + orderLevel + ", expecteStartTime=" + expecteStartTime + ", expecteEndTime="
               + expecteEndTime + ", fileInfo='" + fileInfo + '\'' + ", schemeSource=" + schemeSource + ", extension='"
               + extension + '\'' + ", assetNextStatus=" + assetNextStatus + ", gmtCreate=" + gmtCreate
               + ", gmtModified=" + gmtModified + ", memo='" + memo + '\'' + ", createUser=" + createUser
               + ", modifyUser=" + modifyUser + ", status=" + status + ", content='" + content + '\'' + '}';
    }
}