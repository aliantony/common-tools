package com.antiy.asset.vo.response;

import com.antiy.common.base.BaseResponse;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetSysServiceLibResponse 响应对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetSysServiceLibResponse extends BaseResponse {

    /**
     * 业务主键
     */
    @ApiModelProperty("业务主键")
    private String  businessId;
    /**
     * 服务名
     */
    @ApiModelProperty("服务名")
    private String  service;
    /**
     * 显示名
     */
    @ApiModelProperty("显示名")
    private String  displayName;
    /**
     * 服务类型
     */
    @ApiModelProperty("服务类型")
    private String  serviceClasses;
    /**
     * 服务类型
     */
    @ApiModelProperty("服务类型")
    private String  serviceClassesStr;
    /**
     * 启动参数
     */
    @ApiModelProperty("启动参数")
    private String  startupParameter;
    /**
     * 描述
     */
    @ApiModelProperty("描述")
    private String  describ;
    /**
     * 是否入库：1已入库、2未入库
     */
    @ApiModelProperty("是否入库：1已入库、2未入库")
    private Integer isStorage;
    /**
     * 状态
     */
    @ApiModelProperty("状态")
    private Integer status;
    /**
     * 创建时间
     */
    @ApiModelProperty("收录时间")
    private Long    gmtCreate;

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

    public String getServiceClasses() {
        return serviceClasses;
    }

    public void setServiceClasses(String serviceClasses) {
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

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getServiceClassesStr() {
        return serviceClassesStr;
    }

    public void setServiceClassesStr(String serviceClassesStr) {
        this.serviceClassesStr = serviceClassesStr;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    @Override
    public String toString() {
        return "SysServiceLibResponse{" + "businessId=" + businessId + ", service='" + service + '\''
               + ", displayName='" + displayName + '\'' + ", serviceClasses=" + serviceClasses + ", startupParameter='"
               + startupParameter + '\'' + ", describ='" + describ + '\'' + ", isStorage=" + isStorage + '}';
    }
}