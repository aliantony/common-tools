package com.antiy.asset.vo.query;

import com.antiy.common.base.ObjectQuery;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * <p> AssetCompositionReport 查询条件 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetCompositionReportQuery extends ObjectQuery {
    @ApiModelProperty("name")
    private String       name;
    @ApiModelProperty("status")
    private Integer      status;

    @ApiModelProperty("1 excel  2 cvs 3 xml")
    private Integer      exportType;

    /**
     * 行政区划主键列表
     */
    @ApiModelProperty("行政区划主键列表")
    private String[]     areaIds;

    /**
     * 厂商列表
     */
    @ApiModelProperty("厂商列表")
    private List<String> manufacturers;

    /**
     * 1-核心，2-重要，3-一般
     */
    @ApiModelProperty("1-核心，2-重要，3-一般")
    @Max(message = "重要程度最大为3", value = 3)
    @Min(message = "重要程度最小为1", value = 1)
    private Integer      importanceDegree;
    @ApiModelProperty(" 漏洞 1小于等于，2-大于等于")
    private Integer      vulCountType;
    @ApiModelProperty(" 漏洞 数量")
    private Integer      vulCount;
    @ApiModelProperty(" 补丁 1小于等于，2-大于等于")
    private Integer      patchCountType;
    @ApiModelProperty(" 补丁 数量")
    private Integer      patchCount;

    /**
     * 排序规则
     */
    @ApiModelProperty("排序规则")
    private SortRule     sortRule;

    @ApiModelProperty("导出开始条数")
    private Integer      start;

    @ApiModelProperty("导出结束条数")
    private Integer      end;

    @ApiModelProperty("首次入网时间起始时间")
    private Long         firstEnterStartTime;
    @ApiModelProperty("首次入网时间结束时间")
    private Long         firstEnterEndTime;
    @ApiModelProperty("基准模板id")
    private String       baselineTemplateId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public Integer getImportanceDegree() {
        return importanceDegree;
    }

    public void setImportanceDegree(Integer importanceDegree) {
        this.importanceDegree = importanceDegree;
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

    public SortRule getSortRule() {
        return sortRule;
    }

    public void setSortRule(SortRule sortRule) {
        this.sortRule = sortRule;
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

    public String getBaselineTemplateId() {
        return baselineTemplateId;
    }

    public void setBaselineTemplateId(String baselineTemplateId) {
        this.baselineTemplateId = baselineTemplateId;
    }
}