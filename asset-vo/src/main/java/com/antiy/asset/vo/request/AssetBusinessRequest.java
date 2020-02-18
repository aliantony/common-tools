package com.antiy.asset.vo.request;

import com.antiy.common.base.BaseRequest;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import java.util.List;

/**
 * <p>
 * AssetBusinessRequest 请求对象
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetBusinessRequest extends BaseRequest implements ObjectValidator{
    @ApiModelProperty("业务关联的资产id")

    List<AssetBusinessRelationRequest> assetRelaList;
    /**
     *  业务名称（中文字符，去重）
     */
    @ApiModelProperty("业务名称（中文字符，去重）")
    private String name;
    /**
     *  业务重要性：1-高，2-中，3-低
     */
    @ApiModelProperty("业务重要性：1-高，2-中，3-低")
    @Range(min=1,max=3)
    private Integer importance;
    /**
     *  描述
     */
    @ApiModelProperty("描述")
    @Length(max=50)
    private String description;
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



    public String getName() {
        return name;
    }

    public void setName(String name) {
    this.name = name;
    }


    public Integer getImportance() {
        return importance;
    }

    public void setImportance(Integer importance) {
    this.importance = importance;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
    this.description = description;
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

    public List<AssetBusinessRelationRequest> getAssetRelaList() {
        return assetRelaList;
    }

    public void setAssetRelaList(List<AssetBusinessRelationRequest> assetRelaList) {
        this.assetRelaList = assetRelaList;
    }
}