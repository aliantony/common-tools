package com.antiy.asset.vo.request;

import com.antiy.common.base.BasicRequest;
import io.swagger.annotations.ApiModelProperty;
import com.antiy.common.validation.ObjectValidator;
import com.antiy.common.exception.RequestParamValidateException;

import javax.validation.Valid;

/**
 * <p>
 * AssetChangeRecordRequest 请求对象
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetChangeRecordRequest extends BasicRequest implements ObjectValidator{
    @ApiModelProperty("硬件资产信息")
    @Valid
    private  AssetOuterRequest assetOuterRequest;
    /**
     *  1--硬件资产，2--软件资产
     */
    @ApiModelProperty("1--硬件资产，2--软件资产")
    private Integer type;
    /**
     *  状态,1未删除,0已删除
     */
    @ApiModelProperty("状态,1未删除,0已删除")
    private Integer status;
    /**
     *  创建人
     */
    @ApiModelProperty("创建人")
    private Integer createUser;
    /**
     *  是否存储,1--已经存储,0--未存储
     */
    @ApiModelProperty("是否存储,1--已经存储,0--未存储")
    private Integer isStore;
    /**
     *  修改对象的JSON串
     */
    @ApiModelProperty("修改对象的JSON串")
    private String changeVal;
    /**
     *  创建时间
     */
    @ApiModelProperty("创建时间")
    private Long gmtCreate;
    /**
     *  更新时间
     */
    @ApiModelProperty("更新时间")
    private Long gmtModified;
    /**
     *  备注
     */
    @ApiModelProperty("备注")
    private String memo;
    /**
     *  业务主键Id
     */
    @ApiModelProperty("业务主键Id")
    private Integer businessId;

    @ApiModelProperty(value = "流程数据")
    @Valid
    ManualStartActivityRequest                 activityRequest;

    public ManualStartActivityRequest getActivityRequest() {
        return activityRequest;
    }

    public void setActivityRequest(ManualStartActivityRequest activityRequest) {
        this.activityRequest = activityRequest;
    }


    public Integer getType() {
    return type;
    }

public void setType(Integer type) {
    this.type = type;
    }


    public Integer getStatus() {
    return status;
    }

public void setStatus(Integer status) {
    this.status = status;
    }


    public Integer getCreateUser() {
    return createUser;
    }

public void setCreateUser(Integer createUser) {
    this.createUser = createUser;
    }


    public Integer getIsStore() {
    return isStore;
    }

public void setIsStore(Integer isStore) {
    this.isStore = isStore;
    }


    public String getChangeVal() {
    return changeVal;
    }

public void setChangeVal(String changeVal) {
    this.changeVal = changeVal;
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


    public Integer getBusinessId() {
    return businessId;
    }

public void setBusinessId(Integer businessId) {
    this.businessId = businessId;
    }


    @Override
    public void validate() throws RequestParamValidateException {

    }

    public AssetOuterRequest getAssetOuterRequest() {
        return assetOuterRequest;
    }

    public void setAssetOuterRequest(AssetOuterRequest assetOuterRequest) {
        this.assetOuterRequest = assetOuterRequest;
    }
}