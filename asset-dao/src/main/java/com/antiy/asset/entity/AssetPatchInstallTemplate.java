package com.antiy.asset.entity;

import com.antiy.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>装机模板与补丁关系表</p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */

public class AssetPatchInstallTemplate extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 模板主键
     */
    private Integer           installTemplateId;
    /**
     * 补丁主键
     */
    private Integer           patchId;
    /**
     * 创建时间
     */
    private Long              gmtCreate;
    /**
     * 修改时间
     */
    private Long              gmtModified;
    /**
     * 创建人
     */
    private String            createUser;
    /**
     * 修改人
     */
    private String            modifiedUser;
    /**
     * 状态：1-未删除,0-已删除
     */
    private Integer           status;

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
    public String toString() {
        return "AssetPatchInstallTemplate{" + ", installTemplateId=" + installTemplateId + ", patchId=" + patchId
               + ", gmtCreate=" + gmtCreate + ", gmtModified=" + gmtModified + ", createUser=" + createUser
               + ", modifiedUser=" + modifiedUser + ", status=" + status + "}";
    }
}