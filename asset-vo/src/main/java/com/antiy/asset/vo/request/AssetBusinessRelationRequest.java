package com.antiy.asset.vo.request;

import com.antiy.common.base.BaseRequest;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * AssetBusinessRelationRequest 请求对象
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetBusinessRelationRequest extends BaseRequest implements ObjectValidator{

    /**
     *  业务主键id
     */
    @ApiModelProperty("业务主键id")
    private Integer assetBusinessId;
    /**
     *  资产主键id
     */
    @ApiModelProperty("资产主键id")
    private Integer assetId;
    /**
     *  业务影响：1-高，2-中，3-低
     */
    @ApiModelProperty("业务影响：1-高，2-中，3-低")
    private Integer businessInfluence;
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
     *  创建人
     */
    @ApiModelProperty("创建人")
    private Integer createUser;
    /**
     *  修改人
     */
    @ApiModelProperty("修改人")
    private Integer modifyUser;
    /**
     *  状态：1-未删除,0-已删除
     */
    @ApiModelProperty("状态：1-未删除,0-已删除")
    private Integer status;



    public Integer getAssetBusinessId() {
        return assetBusinessId;
    }

    public void setAssetBusinessId(Integer assetBusinessId) {
    this.assetBusinessId = assetBusinessId;
    }


    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
    this.assetId = assetId;
    }


    public Integer getBusinessInfluence() {
        return businessInfluence;
    }

    public void setBusinessInfluence(Integer businessInfluence) {
    this.businessInfluence = businessInfluence;
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
    public void validate() throws RequestParamValidateException {

    }

}