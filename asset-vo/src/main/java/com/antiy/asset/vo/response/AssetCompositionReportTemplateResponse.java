package com.antiy.asset.vo.response;

import com.antiy.common.base.BaseEntity;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetCompositionReportResponse 响应对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetCompositionReportTemplateResponse extends BaseEntity {
    /**
     * 资产型号
     */
    @ApiModelProperty("name")
    private String name;
    private String queryCondition;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQueryCondition() {
        return queryCondition;
    }

    public void setQueryCondition(String queryCondition) {
        this.queryCondition = queryCondition;
    }
}