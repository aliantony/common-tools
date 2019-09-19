package com.antiy.asset.vo.request;

import com.antiy.common.base.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import com.antiy.common.validation.ObjectValidator;
import com.antiy.common.exception.RequestParamValidateException;

/**
 * <p> AssetServiceDependRequest 请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetServiceDependRequest extends BaseRequest implements ObjectValidator {

    /**
     * 服务id
     */
    @ApiModelProperty("服务id")
    private Long    serviceId;
    /**
     * 服务名
     */
    @ApiModelProperty("服务名")
    private String  serviceName;
    /**
     * 依赖服务id
     */
    @ApiModelProperty("依赖服务id")
    private Long    dependService;
    /**
     * 依赖服务名
     */
    @ApiModelProperty("依赖服务名")
    private String  dependServiceName;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String  memo;
    /**
     * 修改时间
     */
    @ApiModelProperty("修改时间")
    private Long    gmtModified;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Long    gmtCreate;
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
     * 状态：1-正常，0-删除
     */
    @ApiModelProperty("状态：1-正常，0-删除")
    private Integer status;

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
    public void validate() throws RequestParamValidateException {

    }

}