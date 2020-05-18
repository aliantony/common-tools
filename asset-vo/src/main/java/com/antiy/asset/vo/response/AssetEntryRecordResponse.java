package com.antiy.asset.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author liulusheng
 * @since 2020/3/3
 */
@ApiModel(value = "AssetEntryRecordResponse", description = "准入记录VO对象")
public class AssetEntryRecordResponse extends BaseResponse {
    @ApiModelProperty("准入来源：1-资产入网，2-资产退回，3-漏洞管理，4-配置扫描，5-补丁安装，6-准入管理，7-资产变更，8-资产报废,9-未知资产登记")
    private int entrySource;
    @ApiModelProperty("准入结果：1成功，0失败")
    private int entryResult;
    @ApiModelProperty("成功后的状态: 1已运行，2已禁止")
    private int changeStatus;
    @ApiModelProperty("创建时间")
    private Long gmtCreate;
    @ApiModelProperty(value = "创建用户id",hidden = true)
    private int createUser;
    @ApiModelProperty("创建用户姓名")
    private String createUserName;



    public int getEntrySource() {
        return entrySource;
    }

    public void setEntrySource(int entrySource) {
        this.entrySource = entrySource;
    }

    public int getEntryResult() {
        return entryResult;
    }

    public void setEntryResult(int entryResult) {
        this.entryResult = entryResult;
    }

    public int getChangeStatus() {
        return changeStatus;
    }

    public void setChangeStatus(int changeStatus) {
        this.changeStatus = changeStatus;
    }

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public int getCreateUser() {
        return createUser;
    }

    public void setCreateUser(int createUser) {
        this.createUser = createUser;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }
}
