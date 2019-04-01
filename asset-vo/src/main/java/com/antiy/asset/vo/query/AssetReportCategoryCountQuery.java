package com.antiy.asset.vo.query;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.antiy.asset.vo.enums.ReportFormType;
import com.antiy.asset.vo.enums.ShowCycleType;
import com.antiy.common.base.ObjectQuery;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetCpu 查询条件 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */
@ApiModel(value = "资产分类统计请求对象", subTypes = ShowCycleType.class)
public class AssetReportCategoryCountQuery extends ObjectQuery implements ObjectValidator {

    @ApiModelProperty(value = "展示时间类型", example = "THIS_WEEK,THIS_MONTH,THIS_QUARTER,THIS_YEAR,ASSIGN_TIME")
    @NotNull(message = "展示时间类型不能为空")
    private ShowCycleType  showCycleType;

    @ApiModelProperty(value = "勿传")
    private String         format;

    @ApiModelProperty(value = "区域ID 不用传")
    private List<Integer>  areaIds;

    @ApiModelProperty(value = "报表类型 分别为总数统计/新增趋势统计", example = "ALL,NEW,TABLE")
    private ReportFormType reportFormType;

    @Override
    public void validate() throws RequestParamValidateException {

    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public ShowCycleType getShowCycleType() {
        return showCycleType;
    }

    public void setShowCycleType(ShowCycleType showCycleType) {
        this.showCycleType = showCycleType;
    }

    public List<Integer> getAreaIds() {
        return areaIds;
    }

    public void setAreaIds(List<Integer> areaIds) {
        this.areaIds = areaIds;
    }

    public ReportFormType getReportFormType() {
        return reportFormType;
    }

    public void setReportFormType(ReportFormType reportFormType) {
        this.reportFormType = reportFormType;
    }
}