package com.antiy.asset.vo.query;

import com.antiy.asset.vo.enums.SortEnum;
import io.swagger.annotations.ApiModelProperty;

public class SortRule {
    /**
     * 排序字段名
     */
    @ApiModelProperty("排序字段名")
    private String   field;
    /**
     * 排序规则，DESC:倒叙，ASC:正序
     */
    @ApiModelProperty("排序规则，DESC:倒叙，ASC:正序")
    private SortEnum sort;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public SortEnum getSort() {
        return sort;
    }

    public void setSort(SortEnum sort) {
        this.sort = sort;
    }
}
