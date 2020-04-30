package com.antiy.asset.vo.response;

import io.swagger.annotations.ApiModelProperty;

public class AssetCorrectIInfoResponse {
    @ApiModelProperty("扫描是否结束")
    private Boolean isScan;
    /**
     * 漏洞数量
     */
    @ApiModelProperty("漏洞数量")
    private int  amount;
    @ApiModelProperty("漏洞已修复数量")
    private  int repairedCount;
    @ApiModelProperty("漏洞修复失败数量")
    private int failureCount;
    @ApiModelProperty("漏洞忽略数量")
    private int ignoreCount;
    @ApiModelProperty("漏洞缓解数量")
    private int laxation;
    @ApiModelProperty("漏洞状态描述")
    private String vlunDesc;

    @ApiModelProperty("核查状态")
    private String checkStatus;
    @ApiModelProperty("配置状态")
    private String configStatus ;
    /**
     * 漏洞处置流程是否完成
     * true :完成
     * false: 未完成
     */
    private Boolean isDeal;
    @ApiModelProperty("流程推动标志： 0 不需要手动推动  1 需要手动推动 ")
    private  String needManualPush;

    public String getNeedManualPush() {
        return needManualPush;
    }

    public void setNeedManualPush(String needManualPush) {
        this.needManualPush = needManualPush;
    }

    public Boolean getDeal() {
            if(amount==repairedCount+failureCount+ignoreCount+laxation){
                return true;
            }
            return false;
        }

    public int getLaxation() {
        return laxation;
    }

    public void setLaxation(int laxation) {
        this.laxation = laxation;
    }

    public Boolean getScan() {
        return isScan;
    }

    public void setScan(Boolean scan) {
        isScan = scan;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getRepairedCount() {
        return repairedCount;
    }

    public void setRepairedCount(int repairedCount) {
        this.repairedCount = repairedCount;
    }

    public int getFailureCount() {
        return failureCount;
    }

    public void setFailureCount(int failureCount) {
        this.failureCount = failureCount;
    }

    public int getIgnoreCount() {
        return ignoreCount;
    }

    public void setIgnoreCount(int ignoreCount) {
        this.ignoreCount = ignoreCount;
    }

    public String getVlunDesc() {
        return vlunDesc;
    }

    public void setVlunDesc(String vlunDesc) {
        this.vlunDesc = vlunDesc;
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getConfigStatus() {
        return configStatus;
    }

    public void setConfigStatus(String configStatus) {
        this.configStatus = configStatus;
    }
}
