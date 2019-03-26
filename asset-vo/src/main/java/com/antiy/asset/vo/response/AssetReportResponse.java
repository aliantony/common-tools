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

        public ReportData() {
        }
    }
}
