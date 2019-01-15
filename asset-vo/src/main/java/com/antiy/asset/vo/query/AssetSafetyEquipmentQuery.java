package com.antiy.asset.vo.query;

import com.antiy.common.base.ObjectQuery;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetSafetyEquipment 查询条件 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetSafetyEquipmentQuery extends ObjectQuery implements ObjectValidator {
    /**
     * 特征库配置
     */
    @ApiModelProperty("特征库配置")
    private String featureLibrary;
    /**
     * 策略配置
     */
    @ApiModelProperty("策略配置")
    private String strategy;

    public String getFeatureLibrary() {
        return featureLibrary;
    }

    public void setFeatureLibrary(String featureLibrary) {
        this.featureLibrary = featureLibrary;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    @Override
    public void validate() throws RequestParamValidateException {

    }
}