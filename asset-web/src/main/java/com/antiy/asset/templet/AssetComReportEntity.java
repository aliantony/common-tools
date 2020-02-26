package com.antiy.asset.templet;

import io.swagger.annotations.ApiModelProperty;

public class AssetComReportEntity {

    /**
     * 资产型号
     */
    @ApiModelProperty("编号")
    private String number;

    /**
     * 首次入网时间
     */
    @ApiModelProperty(value = "区域")
    private String area;

    /**
     * 首次入网时间
     */
    @ApiModelProperty(value = "首次入网时间")
    private String firstEnterNett;

    @ApiModelProperty(value = "重要程度")
    private String importanceDegree;

    /**
     * ip
     */
    @ApiModelProperty("ip")
    private String ips;

    /**
     * 首次入网时间
     */
    @ApiModelProperty(value = "使用者")
    private String user;
    /**
     * 厂商
     */
    @ApiModelProperty("厂商")
    private String manufacturer;
    /**
     * 首次入网时间
     */
    @ApiModelProperty(value = "基准模板")
    private String baseTemplate;
    /**
     * 首次入网时间
     */
    @ApiModelProperty(value = "已修复漏洞")
    private String vulCount;
    /**
     * 首次入网时间
     */
    @ApiModelProperty(value = "未修复漏洞")
    private String noVulCount;

    /**
     * 首次入网时间
     */
    @ApiModelProperty(value = "已安装补丁")
    private String patchCount;
    /**
     * 首次入网时间
     */
    @ApiModelProperty(value = "未安装补丁")
    private String noPatchCount;

    public String getImportanceDegree() {
        return importanceDegree;
    }

    public void setImportanceDegree(String importanceDegree) {
        this.importanceDegree = importanceDegree;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getFirstEnterNett() {
        return firstEnterNett;
    }

    public void setFirstEnterNett(String firstEnterNett) {
        this.firstEnterNett = firstEnterNett;
    }

    public String getIps() {
        return ips;
    }

    public void setIps(String ips) {
        this.ips = ips;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getBaseTemplate() {
        return baseTemplate;
    }

    public void setBaseTemplate(String baseTemplate) {
        this.baseTemplate = baseTemplate;
    }

    public String getVulCount() {
        return vulCount;
    }

    public void setVulCount(String vulCount) {
        this.vulCount = vulCount;
    }

    public String getNoVulCount() {
        return noVulCount;
    }

    public void setNoVulCount(String noVulCount) {
        this.noVulCount = noVulCount;
    }

    public String getPatchCount() {
        return patchCount;
    }

    public void setPatchCount(String patchCount) {
        this.patchCount = patchCount;
    }

    public String getNoPatchCount() {
        return noPatchCount;
    }

    public void setNoPatchCount(String noPatchCount) {
        this.noPatchCount = noPatchCount;
    }
}
