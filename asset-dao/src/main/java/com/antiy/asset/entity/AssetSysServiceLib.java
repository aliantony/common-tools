package com.antiy.asset.entity;

import com.antiy.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>服务表</p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */

public class AssetSysServiceLib extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 业务主键
     */
    private Long              businessId;
    /**
     * 服务名
     */
    private String            service;
    /**
     * 显示名
     */
    private String            displayName;
    /**
     * 服务类型
     */
    private Integer           serviceClasses;
    /**
     * 启动参数
     */
    private String            startupParameter;
    /**
     * 描述
     */
    private String            describ;
    /**
     * 是否入库：1已入库、2未入库
     */
    private Integer           isStorage;
    /**
     * 修改时间
     */
    private Long              gmtModified;
    /**
     * 创建时间
     */
    private Long              gmtCreate;
    /**
     * 创建人
     */
    private String            createUser;
    /**
     * 修改人
     */
    private String            modifiedUser;
    /**
     * 状态：1-正常，0-删除
     */
    private Integer           status;

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Integer getServiceClasses() {
        return serviceClasses;
    }

    public void setServiceClasses(Integer serviceClasses) {
        this.serviceClasses = serviceClasses;
    }

    public String getStartupParameter() {
        return startupParameter;
    }

    public void setStartupParameter(String startupParameter) {
        this.startupParameter = startupParameter;
    }

    public String getDescrib() {
        return describ;
    }

    public void setDescrib(String describ) {
        this.describ = describ;
    }

    public Integer getIsStorage() {
        return isStorage;
    }

    public void setIsStorage(Integer isStorage) {
        this.isStorage = isStorage;
    }

    public Long getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
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
        return "AssetSysServiceLib{" + ", businessId=" + businessId + ", service=" + service + ", displayName="
               + displayName + ", serviceClasses=" + serviceClasses + ", startupParameter=" + startupParameter
               + ", describ=" + describ + ", isStorage=" + isStorage + ", gmtModified=" + gmtModified + ", gmtCreate="
               + gmtCreate + ", createUser=" + createUser + ", modifiedUser=" + modifiedUser + ", status=" + status
               + "}";
    }
}