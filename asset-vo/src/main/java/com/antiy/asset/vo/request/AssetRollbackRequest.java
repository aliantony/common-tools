package com.antiy.asset.vo.request;

import com.antiy.common.base.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author liulusheng
 * @since 2020/2/25
 */
@ApiModel("资产回滚信息类")
public class AssetRollbackRequest extends BaseRequest {

    @ApiModelProperty("资产id")
    private String assetId;
    @ApiModelProperty("回滚信息")
    List<RollBack> rollBackInfo;
    @ApiModelProperty("创建时间")
    private long gmtCreate;
    @ApiModelProperty("修改时间")
    private long gmtModified;
    @ApiModelProperty("创建人")
    private String createUser;
    @ApiModelProperty("修改人")
    private String modifyUser;

    public long getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(long gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public List<RollBack> getRollBackInfo() {
        return rollBackInfo;
    }

    public void setRollBackInfo(List<RollBack> rollBackInfo) {
        this.rollBackInfo = rollBackInfo;
    }

    public long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }



}


