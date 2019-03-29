package com.antiy.asset.vo.response;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class ReportData {
    /**
     * 分类
     */
    @ApiModelProperty("分类")
    private String        classify;
    /**
     * 总数
     */
    @ApiModelProperty("总数")
    private List<Integer> data;
    /**
     * 新增数
     */
    @ApiModelProperty("新增数")
    private List<Integer> add;

    public String getClassify() {
        return classify;
    }

    public ReportData() {
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public List<Integer> getData() {
        return data;
    }

    public void setData(List<Integer> data) {
        this.data = data;
    }

    public List<Integer> getAdd() {
        return add;
    }

    public void setAdd(List<Integer> add) {
        this.add = add;
    }

}