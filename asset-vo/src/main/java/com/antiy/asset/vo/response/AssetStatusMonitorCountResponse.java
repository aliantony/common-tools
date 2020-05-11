package com.antiy.asset.vo.response;

import io.swagger.annotations.ApiModelProperty;

public class AssetStatusMonitorCountResponse {
    @ApiModelProperty("进程")
    private Integer process;
    @ApiModelProperty("软件")
    private Integer software;
    @ApiModelProperty("服务")
    private Integer service;

    public Integer getProcess() {
        return process;
    }

    public void setProcess(Integer process) {
        this.process = process;
    }

    public Integer getSoftware() {
        return software;
    }

    public void setSoftware(Integer software) {
        this.software = software;
    }

    public Integer getService() {
        return service;
    }

    public void setService(Integer service) {
        this.service = service;
    }
}
