package com.antiy.asset.vo.request;

import com.antiy.common.base.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import com.antiy.common.validation.ObjectValidator;
import com.antiy.common.exception.RequestParamValidateException;

/**
 * <p> AssetPatchInstallTemplateRequest 请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetPatchInstallTemplateRequest extends BaseRequest implements ObjectValidator {

    /**
     * 模板主键
     */
    @ApiModelProperty("模板主键")
    private Integer installTemplateId;
    /**
     * 补丁主键
     */
    @ApiModelProperty("补丁主键")
    private Integer patchId;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Long    gmtCreate;
    /**
     * 修改时间
     */
    @ApiModelProperty("修改时间")
    private Long    gmtModified;
    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    private String  createUser;
    /**
     * 修改人
     */
    @ApiModelProperty("修改人")
    private String  modifiedUser;
    /**
     * 状态：1-未删除,0-已删除
     */
    @ApiModelProperty("状态：1-未删除,0-已删除")
    private Integer status;

    public Integer getInstallTemplateId() {
        return installTemplateId;
    }

    public void setInstallTemplateId(Integer installTemplateId) {
        this.installTemplateId = installTemplateId;
    }

    public Integer getPatchId() {
        return patchId;
    }

    public void setPatchId(Integer patchId) {
        this.patchId = patchId;
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

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getModifiedUser() {
        return modifiedUser;
    }

    public void setModifiedUser(String modifiedUser) {
        this.modifiedUser = modifiedUser;
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