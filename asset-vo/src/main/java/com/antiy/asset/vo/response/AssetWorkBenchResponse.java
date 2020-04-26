package com.antiy.asset.vo.response;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Author ygh
 * @Description 资产工作台response
 * @Date 2020/4/22
 */
public class AssetWorkBenchResponse {


    @ApiModelProperty("退回申请 oa订单退回待处理")
    private Integer backApplyNum;

    @ApiModelProperty("报废申请 oa订单报废待处理")
    private Integer scapeApplyNum;

    @ApiModelProperty("退回执行 资产状态为带退回")
    private Integer backExeNum;

    @ApiModelProperty("报废 资产状态为带报废")
    private Integer scapeExeNum;

    @ApiModelProperty("安全整改 已跳过安全整改的以入网资产")
    private Integer safeChangeNum;

    @ApiModelProperty("整改处理 资产状态包括整改中，已入网")
    private Integer changeHandleNum;

    @ApiModelProperty("代理上报 未知资产")
    private Integer proxyUpNum;

    @ApiModelProperty("准入实施 带准入状态的资产")
    private Integer accessDoNum;

    public Integer getBackApplyNum() {
        return backApplyNum;
    }

    public void setBackApplyNum(Integer backApplyNum) {
        this.backApplyNum = backApplyNum;
    }

    public Integer getScapeApplyNum() {
        return scapeApplyNum;
    }

    public void setScapeApplyNum(Integer scapeApplyNum) {
        this.scapeApplyNum = scapeApplyNum;
    }

    public Integer getBackExeNum() {
        return backExeNum;
    }

    public void setBackExeNum(Integer backExeNum) {
        this.backExeNum = backExeNum;
    }

    public Integer getScapeExeNum() {
        return scapeExeNum;
    }

    public void setScapeExeNum(Integer scapeExeNum) {
        this.scapeExeNum = scapeExeNum;
    }

    public Integer getSafeChangeNum() {
        return safeChangeNum;
    }

    public void setSafeChangeNum(Integer safeChangeNum) {
        this.safeChangeNum = safeChangeNum;
    }

    public Integer getChangeHandleNum() {
        return changeHandleNum;
    }

    public void setChangeHandleNum(Integer changeHandleNum) {
        this.changeHandleNum = changeHandleNum;
    }

    public Integer getProxyUpNum() {
        return proxyUpNum;
    }

    public void setProxyUpNum(Integer proxyUpNum) {
        this.proxyUpNum = proxyUpNum;
    }

    public Integer getAccessDoNum() {
        return accessDoNum;
    }

    public void setAccessDoNum(Integer accessDoNum) {
        this.accessDoNum = accessDoNum;
    }
}
