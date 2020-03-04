package com.antiy.asset.entity;

import com.antiy.common.base.BaseEntity;

/**
 * <p>资产监控规则表</p>
 *
 * @author zhangyajun
 * @since 2020-03-02
 */

public class AssetMonitorRule extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一键
     */
    private String            uniqueId;
    /**
     * 规则名称
     */
    private String            name;
    /**
     * 区域
     */
    private String            areaId;
    /**
     * 告警等级
     */
    private String            alarmLevel;
    /**
     * 状态
     */
    private String            ruleStatus;
    /**
     * CPU监控
     */
    private Integer           cpuThreshold;
    /**
     * 内存监控
     */
    private Integer           memoryThreshold;
    /**
     * 总磁盘监控
     */
    private Integer           diskThreshold;
    /**
     * 创建时间
     */
    private Long              gmtCreate;
    /**
     * 更新时间
     */
    private Long              gmtModified;
    /**
     * 创建人
     */
    private Integer           createUser;
    /**
     * 修改人
     */
    private Integer           modifyUser;
    /**
     * 状态：1-未删除,0-已删除
     */
    private Integer           status;
    /**
     * 运行异常监控阈值和单位
     */
    private String            runtimeExceptionThreshold;
    /**
     * 关联资产数量
     */
    private Integer           relatedAssetAmount;

    public String getRuntimeExceptionThreshold() {
        return runtimeExceptionThreshold;
    }

    public void setRuntimeExceptionThreshold(String runtimeExceptionThreshold) {
        this.runtimeExceptionThreshold = runtimeExceptionThreshold;
    }

    public Integer getRelatedAssetAmount() {
        return relatedAssetAmount;
    }

    public void setRelatedAssetAmount(Integer relatedAssetAmount) {
        this.relatedAssetAmount = relatedAssetAmount;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAlarmLevel() {
        return alarmLevel;
    }

    public void setAlarmLevel(String alarmLevel) {
        this.alarmLevel = alarmLevel;
    }

    public String getRuleStatus() {
        return ruleStatus;
    }

    public void setRuleStatus(String ruleStatus) {
        this.ruleStatus = ruleStatus;
    }

    public Integer getCpuThreshold() {
        return cpuThreshold;
    }

    public void setCpuThreshold(Integer cpuThreshold) {
        this.cpuThreshold = cpuThreshold;
    }

    public Integer getMemoryThreshold() {
        return memoryThreshold;
    }

    public void setMemoryThreshold(Integer memoryThreshold) {
        this.memoryThreshold = memoryThreshold;
    }

    public Integer getDiskThreshold() {
        return diskThreshold;
    }

    public void setDiskThreshold(Integer diskThreshold) {
        this.diskThreshold = diskThreshold;
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
    public String toString() {
        return "AssetMonitorRule{" + ", uniqueId=" + uniqueId + ", name=" + name + ", areaId=" + areaId
               + ", alarmLevel=" + alarmLevel + ", ruleStatus=" + ruleStatus + ", cpuThreshold=" + cpuThreshold
               + ", memoryThreshold=" + memoryThreshold + ", diskThreshold=" + diskThreshold + ", gmtCreate="
               + gmtCreate + ", gmtModified=" + gmtModified + ", createUser=" + createUser + ", modifyUser="
               + modifyUser + ", status=" + status + "}";
    }
}