package com.antiy.asset.vo.request;

import com.antiy.common.base.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author liulusheng
 * @since 2020/3/3
 */
@ApiModel(value = "准入记录request实体", description = "准入记录VO对象")
public class AssetEntryRecordRequest extends BaseRequest {
    @ApiModelProperty("资产id")
    private String assetId;
    @ApiModelProperty("准入来源：1-资产入网，2-资产退役，3-漏洞扫描，4-配置扫描，5-补丁安装，6-准入管理")
    private int entrySource;
    @ApiModelProperty("准入结果：1成功，0失败")
    private int entryResult;
    @ApiModelProperty("成功后的状态: 1已运行，2已禁止")
    private int changeStatus;
    private long gmtCreate;
    private int createUser;
    private int status = 1;

    public AssetEntryRecordRequest(String assetId, int entrySource, int entryResult, int changeStatus, long gmtCreate, int createUser) {
        this.assetId = assetId;
        this.entrySource = entrySource;
        this.entryResult = entryResult;
        this.changeStatus = changeStatus;
        this.gmtCreate = gmtCreate;
        this.createUser = createUser;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

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

    public long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public int getCreateUser() {
        return createUser;
    }

    public void setCreateUser(int createUser) {
        this.createUser = createUser;
    }
}
