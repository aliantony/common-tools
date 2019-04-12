package com.antiy.asset.entity;

import com.antiy.common.base.BaseEntity;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetOperationRecordMapper 数据库返回对象 </p>
 *
 * @author zhangyajun
 * @since 2019-01-24
 */

public class AssetOperationRecordBarPO extends BaseEntity {
    /**
     * 创建时间
     */
    private Long    gmtCreate;
    /**
     * 处理结果
     */
    @ApiModelProperty("处理结果:=1同意/0拒绝")
    private Integer processResult;
    /**
     * 操作人角色名称
     */
    @ApiModelProperty("操作人角色名称")
    private String  roleName;
    /**
     * 操作人姓名
     */
    @ApiModelProperty("操作人姓名")
    private String  userName;
    /**
     * 附件信息（[{"XXX文件","10.12.5.1:5566/file/asset/dj"}]）
     */
    @ApiModelProperty("附件信息")
    private String  fileInfo;

    @ApiModelProperty("备注")
    private String  memo;

    @ApiModelProperty("原始状态")
    private Integer originStatus;

    @ApiModelProperty("操作描述")
    private String  content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getOriginStatus() {
        return originStatus;
    }

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Integer getProcessResult() {
        return processResult;
    }

    public void setProcessResult(Integer processResult) {
        this.processResult = processResult;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFileInfo() {
        return fileInfo;
    }

    public void setFileInfo(String fileInfo) {
        this.fileInfo = fileInfo;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public void setOriginStatus(Integer originStatus) {
        this.originStatus = originStatus;
    }
}