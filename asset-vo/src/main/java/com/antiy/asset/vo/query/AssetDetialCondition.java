package com.antiy.asset.vo.query;

import com.antiy.common.base.QueryCondition;
import io.swagger.annotations.ApiModelProperty;

public class AssetDetialCondition extends QueryCondition {
    /**
     * 是否需要查询cpu,1:是，0否
     */
    @ApiModelProperty("是否需要查询cpu,1:是，0否")
    private Integer isNeedCpu;
    /**
     * 是否需要查询网卡,1:是，0否
     */
    @ApiModelProperty("是否需要查询网卡,1:是，0否")
    private Integer isNeedNetwork;
    /**
     * 是否需要查询内存,1:是，0否
     */
    @ApiModelProperty("是否需要查询内存,1:是，0否")
    private Integer isNeedMemory;
    /**
     * 是否需要查询主板,1:是，0否
     */
    @ApiModelProperty("是否需要查询主板,1:是，0否")
    private Integer isNeedMainboard;
    /**
     * 是否需要查询硬盘,1:是，0否
     */
    @ApiModelProperty("是否需要查询硬盘,1:是，0否")
    private Integer isNeedHarddisk;
    /**
     * 是否需要查询cpu,1:是，0否
     */
    @ApiModelProperty("是否需要查询cpu,1:是，0否")
    private Integer isNeedSoftware;

    public Integer getIsNeedCpu() {
        return isNeedCpu;
    }

    public void setIsNeedCpu(Integer isNeedCpu) {
        this.isNeedCpu = isNeedCpu;
    }

    public Integer getIsNeedNetwork() {
        return isNeedNetwork;
    }

    public void setIsNeedNetwork(Integer isNeedNetwork) {
        this.isNeedNetwork = isNeedNetwork;
    }

    public Integer getIsNeedMemory() {
        return isNeedMemory;
    }

    public void setIsNeedMemory(Integer isNeedMemory) {
        this.isNeedMemory = isNeedMemory;
    }

    public Integer getIsNeedMainboard() {
        return isNeedMainboard;
    }

    public void setIsNeedMainboard(Integer isNeedMainboard) {
        this.isNeedMainboard = isNeedMainboard;
    }

    public Integer getIsNeedHarddisk() {
        return isNeedHarddisk;
    }

    public void setIsNeedHarddisk(Integer isNeedHarddisk) {
        this.isNeedHarddisk = isNeedHarddisk;
    }

    public Integer getIsNeedSoftware() {
        return isNeedSoftware;
    }

    public void setIsNeedSoftware(Integer isNeedSoftware) {
        this.isNeedSoftware = isNeedSoftware;
    }
}
