package com.antiy.asset.vo.query;

import com.antiy.common.base.QueryCondition;
import io.swagger.annotations.ApiModelProperty;

public class AssetDetialCondition extends QueryCondition {
    /**
     * 是否需要查询cpu,true:是，false否
     */
    @ApiModelProperty("是否需要查询cpu,true:是，false否")
    private Boolean isNeedCpu;
    /**
     * 是否需要查询网卡,true:是，false否
     */
    @ApiModelProperty("是否需要查询网卡,true:是，false否")
    private Boolean isNeedNetwork;
    /**
     * 是否需要查询内存,true:是，false否
     */
    @ApiModelProperty("是否需要查询内存,true:是，false否")
    private Boolean isNeedMemory;
    /**
     * 是否需要查询主板,true:是，false否
     */
    @ApiModelProperty("是否需要查询主板,true:是，false否")
    private Boolean isNeedMainboard;
    /**
     * 是否需要查询硬盘,true:是，false否
     */
    @ApiModelProperty("是否需要查询硬盘,true:是，false否")
    private Boolean isNeedHarddisk;
    /**
     * 是否需要查询cpu,true:是，false否
     */
    @ApiModelProperty("是否需要查询cpu,true:是，false否")
    private Boolean isNeedSoftware;

    public Boolean getIsNeedCpu() {
        return isNeedCpu == null ? false : true;
    }

    public void setIsNeedCpu(Boolean needCpu) {
        isNeedCpu = needCpu;
    }

    public Boolean getIsNeedNetwork() {
        return isNeedNetwork == null ? false : true;
    }

    public void setIsNeedNetwork(Boolean needNetwork) {
        isNeedNetwork = needNetwork;
    }

    public Boolean getIsNeedMemory() {
        return isNeedMemory == null ? false : true;
    }

    public void setIsNeedMemory(Boolean needMemory) {
        isNeedMemory = needMemory;
    }

    public Boolean getIsNeedMainboard() {
        return isNeedMainboard == null ? false : true;
    }

    public void setIsNeedMainboard(Boolean needMainboard) {
        isNeedMainboard = needMainboard;
    }

    public Boolean getIsNeedHarddisk() {
        return isNeedHarddisk == null ? false : true;
    }

    public void setIsNeedHarddisk(Boolean needHarddisk) {
        isNeedHarddisk = needHarddisk;
    }

    public Boolean getIsNeedSoftware() {
        return isNeedSoftware == null ? false : true;
    }

    public void setIsNeedSoftware(Boolean needSoftware) {
        isNeedSoftware = needSoftware;
    }
}
