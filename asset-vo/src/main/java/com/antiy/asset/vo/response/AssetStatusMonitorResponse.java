package com.antiy.asset.vo.response;

import com.antiy.common.base.BaseResponse;
import com.antiy.common.encoder.Encode;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * AssetStatusMonitorResponse 响应对象
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetStatusMonitorResponse extends BaseResponse {
    /**
     *  资产id
     */
    @ApiModelProperty("资产id")
    @Encode
    private String assetId;

    /**
     *软件业务id
     */
    @ApiModelProperty("软件业务id  有值表示资产与软件关联")
    private Long softwareId;

    /**
     *软件业务id
     */
    @ApiModelProperty("软件业务id")
    private Long businessId;
    /**
     *  cpu使用率
     */
    @ApiModelProperty("cpu使用率")
    private Integer cpuUsedRate;
    /**
     *  内存总量
     */
    @ApiModelProperty("内存总量")
    private Integer memoryTotal;
    /**
     *  已使用内存
     */
    @ApiModelProperty("已使用内存")
    private Integer memoryUsed;
    /**
     *  内存使用率
     */
    @ApiModelProperty("内存使用率")
    private Integer memoryUsedRate;
    /**
     *  硬盘总量
     */
    @ApiModelProperty("硬盘总量")
    private Integer diskTotal;
    /**
     *  硬盘已使用容量
     */
    @ApiModelProperty("硬盘已使用容量")
    private Integer diskUsed;
    /**
     *  硬盘总使用率
     */
    @ApiModelProperty("硬盘总使用率")
    private Integer diskTotalusedRate;
    /**
     *  硬盘详细信息
     */
    @ApiModelProperty("硬盘详细信息")
    private String diskDetail;
    /**
     *  监控规则id
     */
    @ApiModelProperty("监控规则id")
    private Integer ruleId;
    /**
     *  更新时间
     */
    @ApiModelProperty("更新时间")
    private Long gmtModified;
    /**
     *  监控资源类型 1、进程 2、软件 3、服务 4、性能
     */
    @ApiModelProperty("监控资源类型")
    private Integer type;
    /**
     *  监控对象名称
     */
    @ApiModelProperty("监控对象名称")
    private String name;
    /**
     *  描述
     */
    @ApiModelProperty("描述")
    private String description;
    /**
     *  进程位置
     */
    @ApiModelProperty("进程位置")
    private String location;
    /**
     *  软件厂商
     */
    @ApiModelProperty("软件厂商")
    private String supplier;
    /**
     *  安装时间
     */
    @ApiModelProperty("安装时间")
    private Long installedTime;
    /**
     *  软件版本
     */
    @ApiModelProperty("软件版本")
    private String version;
    /**
     *  逻辑状态
     */

    private Integer status;
    /**
     *  服务运行该状态： e.g. 正在运行
     */
    @ApiModelProperty("服务运行该状态")
    private String serviceStatus;
    /**
     *  创建时间
     */
    @ApiModelProperty("创建时间")
    private Long gmtCreate;
    /**
     *  创建人
     */
    @ApiModelProperty("创建人")
    private Integer createUser;
    /**
     *  更新人
     */
    private Integer modifyUser;
    /**
     *  进程id
     */
    @ApiModelProperty("进程id")
    private Integer pid;
    /**
     *  资产网络状态
     */
    @ApiModelProperty("资产网络状态")
    private String networkStatus;



    @ApiModelProperty("资产与软件关联关系描述")
    private String relationDesc;

    public void setRelationDesc(String relationDesc) {
        this.relationDesc = relationDesc;
    }

    public String getRelationDesc() {

        return relationDesc;
    }
/*  public String getRelationDesc() {
        return AssetSoftwareRelationEnum.getMsgByCode(relation);
    }*/


    /**
     * 监控规则
     *
     */
    private AssetMonitorRuleResponse assetMonitorRuleResponse;

    public Long getSoftwareId() {
        return softwareId;
    }
    public void setSoftwareId(Long softwareId) {
        this.softwareId = softwareId;
    }

    public AssetMonitorRuleResponse getAssetMonitorRuleResponse() {
        return assetMonitorRuleResponse;
    }

    public void setAssetMonitorRuleResponse(AssetMonitorRuleResponse assetMonitorRuleResponse) {
        this.assetMonitorRuleResponse = assetMonitorRuleResponse;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public Integer getCpuUsedRate() {
        return cpuUsedRate;
    }

    public void setCpuUsedRate(Integer cpuUsedRate) {
        this.cpuUsedRate = cpuUsedRate;
    }

    public Integer getMemoryTotal() {
        return memoryTotal;
    }

    public void setMemoryTotal(Integer memoryTotal) {
        this.memoryTotal = memoryTotal;
    }

    public Integer getMemoryUsed() {
        return memoryUsed;
    }

    public void setMemoryUsed(Integer memoryUsed) {
        this.memoryUsed = memoryUsed;
    }

    public Integer getMemoryUsedRate() {
        return memoryUsedRate;
    }

    public void setMemoryUsedRate(Integer memoryUsedRate) {
        this.memoryUsedRate = memoryUsedRate;
    }

    public Integer getDiskTotal() {
        return diskTotal;
    }

    public void setDiskTotal(Integer diskTotal) {
        this.diskTotal = diskTotal;
    }

    public Integer getDiskUsed() {
        return diskUsed;
    }

    public void setDiskUsed(Integer diskUsed) {
        this.diskUsed = diskUsed;
    }

    public Integer getDiskTotalusedRate() {
        return diskTotalusedRate;
    }

    public void setDiskTotalusedRate(Integer diskTotalusedRate) {
        this.diskTotalusedRate = diskTotalusedRate;
    }

    public String getDiskDetail() {
        return diskDetail;
    }

    public void setDiskDetail(String diskDetail) {
        this.diskDetail = diskDetail;
    }

    public Integer getRuleId() {
        return ruleId;
    }

    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
    }

    public Long getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public Long getInstalledTime() {
        return installedTime;
    }

    public void setInstalledTime(Long installedTime) {
        this.installedTime = installedTime;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
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

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getNetworkStatus() {
        return networkStatus;
    }

    public void setNetworkStatus(String networkStatus) {
        this.networkStatus = networkStatus;
    }

}