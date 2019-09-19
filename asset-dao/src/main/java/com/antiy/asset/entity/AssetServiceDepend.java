package com.antiy.asset.entity;

import com.antiy.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>服务依赖的服务表</p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */

public class AssetServiceDepend extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 服务id
     */
    private Long              serviceId;
    /**
     * 服务名
     */
    private String            serviceName;
    /**
     * 依赖服务id
     */
    private Long              dependService;
    /**
     * 依赖服务名
     */
    private String            dependServiceName;
    /**
     * 备注
     */
    private String            memo;
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

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Long getDependService() {
        return dependService;
    }

    public void setDependService(Long dependService) {
        this.dependService = dependService;
    }

    public String getDependServiceName() {
        return dependServiceName;
    }

    public void setDependServiceName(String dependServiceName) {
        this.dependServiceName = dependServiceName;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
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
        return "AssetServiceDepend{" + ", serviceId=" + serviceId + ", serviceName=" + serviceName + ", dependService="
               + dependService + ", dependServiceName=" + dependServiceName + ", memo=" + memo + ", gmtModified="
               + gmtModified + ", gmtCreate=" + gmtCreate + ", createUser=" + createUser + ", modifiedUser="
               + modifiedUser + ", status=" + status + "}";
    }
}