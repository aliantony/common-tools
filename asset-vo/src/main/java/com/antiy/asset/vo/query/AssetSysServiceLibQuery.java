package com.antiy.asset.vo.query;

import com.antiy.common.base.ObjectQuery;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * <p> AssetSysServiceLib 查询条件 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetSysServiceLibQuery extends ObjectQuery {
    /**
     * 服务名
     */
    @ApiModelProperty("服务名")
    private String     service;
    /**
     * 服务名
     */
    @ApiModelProperty("显示名")
    private String     displayName;
    /**
     * 服务类型
     */
    @ApiModelProperty("服务类型")
    private Integer    serviceClasses;
    /**
     * 是否入库：1已入库、2未入库
     */
    @ApiModelProperty("是否入库：1已入库、2未入库")
    private Integer    isStorage;

    @ApiModelProperty("排除的业务id")
    private String     serviceBusinessId;

    @ApiModelProperty("对应的业务类型，softwareDepend-软件依赖服务，protocol-协议，dependService-服务依赖服务,softwareProvide-软件提供服务,patchServer-补丁服务,vulServer-漏洞服务")
    private String     serviceType;

    private List<Long> serviceIdList;

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public Integer getServiceClasses() {
        return serviceClasses;
    }

    public void setServiceClasses(Integer serviceClasses) {
        this.serviceClasses = serviceClasses;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Integer getIsStorage() {
        return isStorage;
    }

    public void setIsStorage(Integer isStorage) {
        this.isStorage = isStorage;
    }

    public String getServiceBusinessId() {
        return serviceBusinessId;
    }

    public void setServiceBusinessId(String serviceBusinessId) {
        this.serviceBusinessId = serviceBusinessId;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public List<Long> getServiceIdList() {
        return serviceIdList;
    }

    public void setServiceIdList(List<Long> serviceIdList) {
        this.serviceIdList = serviceIdList;
    }
}