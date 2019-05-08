package com.antiy.asset.vo.request;

import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotBlank;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.utils.ParamterExceptionUtils;
import com.antiy.common.validation.ObjectValidator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author: zhangbing
 * @date: 2019/3/25 15:47
 * @description: 报表统计查询sql
 */
@ApiModel(value = "报表统计查询条件")
public class ReportQueryRequest extends BasicRequest implements ObjectValidator {

    /**
     * 资产状态
     */
    @ApiModelProperty(value = "资产状态")
    private Integer                      assetStatus;

    @ApiModelProperty(value = "开始时间(时间戳)", required = false)
    // @NotNull(message = "开始时间不能为空")
    private Long                         startTime;

    @ApiModelProperty(value = "结束时间(时间戳)", required = false)
    // @NotNull(message = "结束时间不能为空")
    private Long                         endTime;

    @ApiModelProperty(value = "时间类型,1-本周,2-本月,3-本季度,4-本年,5-时间范围", required = true)
    @NotBlank(message = "时间类型不能为空")
    private String                       timeType;

    @ApiModelProperty(value = "top条件，默认false不显示top,true 则需要显示top5")
    private Boolean                      topFive;
    @ApiModelProperty(value = "数据库使用时间转换，默认不传")
    private String                       sqlTime;

    @ApiModelProperty(value = "区域查询条件")
    private List<AssetAreaReportRequest> assetAreaIds;

    @ApiModelProperty(value = "顶级区域ID")
    @Encode
    private String                      topAreaId;
    @ApiModelProperty(value = "顶级区域名称")
    private String                       topAreaName;
    // 用户登陆的区域查询条件
    @ApiModelProperty(value = "区域列表 不用传")
    private List<Integer>                areaIds;
    @ApiModelProperty(value = "资产组ids 不用传")
    private List<Integer>                groupIds;
    /**
     * 导出报表文件名
     */
    @ApiModelProperty("导出报表文件名")
    private String                       exportFileName;

    public String getExportFileName() {
        return exportFileName;
    }

    public void setExportFileName(String exportFileName) {
        this.exportFileName = exportFileName;
    }

    public String getTopAreaName() {
        return topAreaName;
    }

    public void setTopAreaName(String topAreaName) {
        this.topAreaName = topAreaName;
    }

    public String getTopAreaId() {
        return topAreaId;
    }

    public void setTopAreaId(String topAreaId) {
        this.topAreaId = topAreaId;
    }

    public List<AssetAreaReportRequest> getAssetAreaIds() {
        return assetAreaIds;
    }

    public void setAssetAreaIds(List<AssetAreaReportRequest> assetAreaIds) {
        this.assetAreaIds = assetAreaIds;
    }

    public Integer getAssetStatus() {
        return assetStatus;
    }

    public void setAssetStatus(Integer assetStatus) {
        this.assetStatus = assetStatus;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public String getTimeType() {
        return timeType;
    }

    public void setTimeType(String timeType) {
        this.timeType = timeType;
    }

    public List<Integer> getAreaIds() {
        return areaIds;
    }

    public void setAreaIds(List<Integer> areaIds) {
        this.areaIds = areaIds;
    }

    @Override
    public void validate() throws RequestParamValidateException {
        if (!Objects.isNull(endTime) && !Objects.isNull(startTime)) {
            ParamterExceptionUtils.isTrue(endTime > startTime, "开始时间必须小于结束时间");
        }
    }

    public Boolean getTopFive() {
        return topFive;
    }

    public void setTopFive(Boolean topFive) {
        this.topFive = topFive;
    }

    public String getSqlTime() {
        return sqlTime;
    }

    public void setSqlTime(String sqlTime) {
        this.sqlTime = sqlTime;
    }

    public List<Integer> getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(List<Integer> groupIds) {
        this.groupIds = groupIds;
    }
}
