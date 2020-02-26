package com.antiy.asset.vo.response;

import com.antiy.common.base.BaseEntity;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetCompositionReportResponse 响应对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetCompositionReportResponse extends BaseEntity {
    /**
     * 资产型号
     */
    @ApiModelProperty("编号")
    private String  number;

    /**
     * 首次入网时间
     */
    @ApiModelProperty(value = "区域")
    private String  area;

    /**
     * 首次入网时间
     */
    @ApiModelProperty(value = "首次入网时间")
    private Long    firstEnterNett;

    @ApiModelProperty(value = "重要程度")
    private Integer importanceDegree;

    /**
     * ip
     */
    @ApiModelProperty("ip")
    private String  ips;

    /**
     * 使用者名称
     */
    @ApiModelProperty("使用者")
    private String  responsibleUserName;
    /**
     * 厂商
     */
    @ApiModelProperty("厂商")
    private String  manufacturer;
    /**
     * 首次入网时间
     */
    @ApiModelProperty(value = "基准模板")
    private String  baseTemplate;
    /**
     * 首次入网时间
     */
    @ApiModelProperty(value = "已修复漏洞")
    private Integer vulCount;
    /**
     * 首次入网时间
     */
    @ApiModelProperty(value = "未修复漏洞")
    private Integer noVulCount;

    /**
     * 首次入网时间
     */
    @ApiModelProperty(value = "已安装补丁")
    private Integer patchCount;
    /**
     * 首次入网时间
     */
    @ApiModelProperty(value = "未安装补丁")
    private Integer noPatchCount;

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

    public Long getFirstEnterNett() {
        return firstEnterNett;
    }

    public void setFirstEnterNett(Long firstEnterNett) {
        this.firstEnterNett = firstEnterNett;
    }

    public Integer getImportanceDegree() {
        return importanceDegree;
    }

    public void setImportanceDegree(Integer importanceDegree) {
        this.importanceDegree = importanceDegree;
    }

    public String getIps() {
        return ips;
    }

    public void setIps(String ips) {
        this.ips = ips;
    }

    public String getResponsibleUserName() {
        return responsibleUserName;
    }

    public void setResponsibleUserName(String responsibleUserName) {
        this.responsibleUserName = responsibleUserName;
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

    public Integer getVulCount() {
        return vulCount;
    }

    public void setVulCount(Integer vulCount) {
        this.vulCount = vulCount;
    }

    public Integer getNoVulCount() {
        return noVulCount;
    }

    public void setNoVulCount(Integer noVulCount) {
        this.noVulCount = noVulCount;
    }

    public Integer getPatchCount() {
        return patchCount;
    }

    public void setPatchCount(Integer patchCount) {
        this.patchCount = patchCount;
    }

    public Integer getNoPatchCount() {
        return noPatchCount;
    }

    public void setNoPatchCount(Integer noPatchCount) {
        this.noPatchCount = noPatchCount;
    }
}