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
    private Integer           alarmLevel;
    /**
     * 状态
     */
    private Integer           ruleStatus;
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
     * 时间单位：HOUR/DAY
     */
    private String            unit;
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

    public Integer getAlarmLevel() {
        return alarmLevel;
    }

    public void setAlarmLevel(Integer alarmLevel) {
        this.alarmLevel = alarmLevel;
    }

    public Integer getRuleStatus() {
        return ruleStatus;
    }

    public void setRuleStatus(Integer ruleStatus) {
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
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
               + ", memoryThreshold=" + memoryThreshold + ", diskThreshold=" + diskThreshold + ", unit=" + unit
               + ", gmtCreate=" + gmtCreate + ", gmtModified=" + gmtModified + ", createUser=" + createUser
               + ", modifyUser=" + modifyUser + ", status=" + status + "}";
    }
}