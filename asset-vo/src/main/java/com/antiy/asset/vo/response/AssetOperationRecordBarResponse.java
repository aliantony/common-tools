package com.antiy.asset.vo.response;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * <p> AssetOperationRecordResponse 响应对象 </p>
 *
 * @author lvliang
 * @since 2018-12-27
 */

public class AssetOperationRecordBarResponse extends BaseResponse {
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
    private List<AssetStatusBarResponse> fileInfos;

    @ApiModelProperty("备注")
    private String  memo;

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

    public List<AssetStatusBarResponse> getFileInfos() {
        return fileInfos;
    }

    public void setFileInfos(List<AssetStatusBarResponse> fileInfos) {
        this.fileInfos = fileInfos;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}