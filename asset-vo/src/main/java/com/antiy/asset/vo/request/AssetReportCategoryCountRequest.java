package com.antiy.asset.vo.request;

import javax.validation.constraints.NotNull;

import com.antiy.asset.vo.enums.ShowCycleType;
import com.antiy.common.base.BasicRequest;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetCpuRequest 请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */
@ApiModel(value = "资产分类统计请求对象", subTypes = ShowCycleType.class)
public class AssetReportCategoryCountRequest extends BasicRequest implements ObjectValidator {

    @ApiModelProperty(value = "展示时间类型", example = "THIS_WEEK,THIS_MONTH,THIS_QUARTER,THIS_YEAR,ASSIGN_TIME")
    @NotNull(message = "展示时间类型不能为空")
    private ShowCycleType showCycleType;

    @Override
    public void validate() throws RequestParamValidateException {

    }

    public ShowCycleType getShowCycleType() {
        return showCycleType;
    }

    public void setShowCycleType(ShowCycleType showCycleType) {
        this.showCycleType = showCycleType;
    }
}