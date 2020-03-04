package com.antiy.asset.vo.request;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p> 资产监控规则请求对象 </p>
 *
 * @author zhangyajun
 * @since 2020-3-2
 */

@ApiModel("资产监控规则请求对象")
public class AssetMonitorRuleRequest {
    /**
     * 唯一键
     */
    @ApiModelProperty("唯一键")
    private String                         uniqueId;
    @Size(max = 50)
    @NotBlank(message = "规则名称不能为空")
    @ApiModelProperty("规则名称")
    private String                         name;
    @NotBlank(message = "区域不能为空")
    @ApiModelProperty("区域")
    private String                         areaId;
    @NotBlank(message = "告警等级不能为空")
    @ApiModelProperty("告警等级")
    private String                         alarmLevel;
    @NotBlank(message = "状态不能为空")
    @ApiModelProperty("状态")
    private String                         ruleStatus;
    @NotBlank(message = "CPU监控不能为空")
    @ApiModelProperty("CPU监控")
    private Integer                        cpuThreshold;
    @NotBlank(message = "内存监控不能为空")
    @ApiModelProperty("内存监控")
    private Integer                        memoryThreshold;
    @NotBlank(message = "总磁盘监控不能为空")
    @ApiModelProperty("总磁盘监控")
    private Integer                        diskThreshold;
    @NotBlank(message = "运行异常监控不能为空")
    @ApiModelProperty("运行异常监控")
    private AssetRuntimeExceptionThreshold runtimeExceptionThreshold;
    @ApiModelProperty("已选中资产")
    private List<String>                   relatedAsset;

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

    public AssetRuntimeExceptionThreshold getRuntimeExceptionThreshold() {
        return runtimeExceptionThreshold;
    }

    public void setRuntimeExceptionThreshold(AssetRuntimeExceptionThreshold runtimeExceptionThreshold) {
        this.runtimeExceptionThreshold = runtimeExceptionThreshold;
    }

    public List<String> getRelatedAsset() {
        return relatedAsset;
    }

    public void setRelatedAsset(List<String> relatedAsset) {
        this.relatedAsset = relatedAsset;
    }
}