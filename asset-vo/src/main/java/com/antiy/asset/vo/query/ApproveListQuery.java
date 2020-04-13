package com.antiy.asset.vo.query;

import com.antiy.common.base.ObjectQuery;
import io.swagger.annotations.ApiModelProperty;

public class ApproveListQuery extends ObjectQuery {

    //综合查询
    @ApiModelProperty("综合查询")
    private String blurQueryField;

    public String getBlurQueryField() {
        return blurQueryField;
    }

    public void setBlurQueryField(String blurQueryField) {
        this.blurQueryField = blurQueryField;
    }

    @Override
    public String toString() {
        return "ApproveListQuery{" +
                "blurQueryField='" + blurQueryField + '\'' +
                '}';
    }
}
