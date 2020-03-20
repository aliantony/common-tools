package com.antiy.asset.vo.query;

import com.antiy.common.base.ObjectQuery;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * <p> AssetCompositionReport 查询条件 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetCompositionReportQuery extends ObjectQuery {
    @ApiModelProperty("status")
    private Integer       status;

    @ApiModelProperty("1 excel  2 cvs ")
    private Integer       exportType;
    @ApiModelProperty("1 本周  2 本月 3 本季度  4 本年 ")
    private Integer       timeType;

    /**
     * 行政区划主键列表
     */
    @ApiModelProperty("行政区划主键列表")
    private String[]      areaIds;
    @ApiModelProperty("基准模板id")
    private List<Integer> baselineTemplateIds;
    /**
     * 厂商列表
     */
    @ApiModelProperty("厂商列表")
    private List<String>  manufacturers;
    /**
     * 厂商列表
     */
    @ApiModelProperty("重要程度")
    private List<Integer> importanceDegrees;

    @ApiModelProperty(" 已修复漏洞 1小于等于，2-大于等于")
    private Integer       vulCountType;
    @ApiModelProperty(" 已修复漏洞 数量")
    private Integer       vulCount;
    @ApiModelProperty(" 已安装补丁 1小于等于，2-大于等于")
    private Integer       patchCountType;
    @ApiModelProperty(" 已安装补丁 数量")
    private Integer       patchCount;
    @ApiModelProperty(" 未修复漏洞 1小于等于，2-大于等于")
    private Integer       noVulCountType;
    @ApiModelProperty("未修复 漏洞 数量")
    private Integer       noVulCount;
    @ApiModelProperty(" 未安装补丁 1小于等于，2-大于等于")
    private Integer       noPatchCountType;
    @ApiModelProperty(" 未安装补丁 数量")
    private Integer       noPatchCount;


    @ApiModelProperty("导出开始条数")
    private Integer       start;

    @ApiModelProperty("导出结束条数")
    private Integer       end;

    @ApiModelProperty("首次入网时间起始时间")
    private Long          firstEnterStartTime;
    @ApiModelProperty("首次入网时间结束时间")
    private Long          firstEnterEndTime;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getExportType() {
        return exportType;
    }

    public void setExportType(Integer exportType) {
        this.exportType = exportType;
    }

    public String[] getAreaIds() {
        return areaIds;
    }

    public void setAreaIds(String[] areaIds) {
        this.areaIds = areaIds;
    }

    public List<String> getManufacturers() {
        return manufacturers;
    }

    public void setManufacturers(List<String> manufacturers) {
        this.manufacturers = manufacturers;
    }

    public Integer getVulCountType() {
        return vulCountType;
    }

    public void setVulCountType(Integer vulCountType) {
        this.vulCountType = vulCountType;
    }

    public Integer getVulCount() {
        return vulCount;
    }

    public void setVulCount(Integer vulCount) {
        this.vulCount = vulCount;
    }

    public Integer getPatchCountType() {
        return patchCountType;
    }

    public void setPatchCountType(Integer patchCountType) {
        this.patchCountType = patchCountType;
    }

    public Integer getPatchCount() {
        return patchCount;
    }

    public void setPatchCount(Integer patchCount) {
        this.patchCount = patchCount;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    public Long getFirstEnterStartTime() {
        return firstEnterStartTime;
    }

    public void setFirstEnterStartTime(Long firstEnterStartTime) {
        this.firstEnterStartTime = firstEnterStartTime;
    }

    public Long getFirstEnterEndTime() {
        return firstEnterEndTime;
    }

    public void setFirstEnterEndTime(Long firstEnterEndTime) {
        this.firstEnterEndTime = firstEnterEndTime;
    }

    public List<Integer> getImportanceDegrees() {
        return importanceDegrees;
    }

    public void setImportanceDegrees(List<Integer> importanceDegrees) {
        this.importanceDegrees = importanceDegrees;
    }

    public List<Integer> getBaselineTemplateIds() {
        return baselineTemplateIds;
    }

    public void setBaselineTemplateIds(List<Integer> baselineTemplateIds) {
        this.baselineTemplateIds = baselineTemplateIds;
    }

    public Integer getTimeType() {
        return timeType;
    }

    public void setTimeType(Integer timeType) {
        this.timeType = timeType;
    }

    public Integer getNoVulCountType() {
        return noVulCountType;
    }

    public void setNoVulCountType(Integer noVulCountType) {
        this.noVulCountType = noVulCountType;
    }

    public Integer getNoVulCount() {
        return noVulCount;
    }

    public void setNoVulCount(Integer noVulCount) {
        this.noVulCount = noVulCount;
    }

    public Integer getNoPatchCountType() {
        return noPatchCountType;
    }

    public void setNoPatchCountType(Integer noPatchCountType) {
        this.noPatchCountType = noPatchCountType;
    }

    public Integer getNoPatchCount() {
        return noPatchCount;
    }

    public void setNoPatchCount(Integer noPatchCount) {
        this.noPatchCount = noPatchCount;
    }
}