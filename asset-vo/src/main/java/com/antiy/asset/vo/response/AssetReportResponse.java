package com.antiy.asset.vo.response;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetReportResponse 资产报表返回对象 </p>
 *
 * @author lvliang
 * @since 2019-03-26
 */
public class AssetReportResponse extends BaseResponse {
    /**
     * 横坐标值
     */
    @ApiModelProperty("横坐标值")
    private List<String>     date;
    /**
     * 横坐标值
     */
    @ApiModelProperty("总数")
    private List<Integer>     alldata;
    /**
     * 数据
     */
    @ApiModelProperty("返回数据")
    private List<ReportData> list;

    public List<String> getDate() {
        return date;
    }

    public void setDate(List<String> date) {
        this.date = date;
    }

    public List<ReportData> getList() {
        return list;
    }

    public void setList(List<ReportData> list) {
        this.list = list;
    }

    public List<Integer> getAlldata() {
        return alldata;
    }

    public void setAlldata(List<Integer> alldata) {
        this.alldata = alldata;
    }
}

