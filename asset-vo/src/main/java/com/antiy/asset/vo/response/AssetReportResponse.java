package com.antiy.asset.vo.response;

import java.util.List;

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
    private List<String>     abscissa;
    /**
     * 数据
     */
    private List<ReportData> dataList;

    public List<String> getAbscissa() {
        return abscissa;
    }

    public void setAbscissa(List<String> abscissa) {
        this.abscissa = abscissa;
    }

    public List<ReportData> getDataList() {
        return dataList;
    }

    public void setDataList(List<ReportData> dataList) {
        this.dataList = dataList;
    }

    @Override
    public String toString() {
        return "AssetReportResponse{" + "abscissa=" + abscissa + ", dataList=" + dataList + '}';
    }

    class ReportData {
        /**
         * 分类
         */
        private String        classify;
        /**
         * 总数
         */
        private List<Integer> total;
        /**
         * 新增数
         */
        private List<Integer> addNumber;

        public String getClassify() {
            return classify;
        }

        public void setClassify(String classify) {
            this.classify = classify;
        }

        public List<Integer> getTotal() {
            return total;
        }

        public void setTotal(List<Integer> total) {
            this.total = total;
        }

        public List<Integer> getAddNumber() {
            return addNumber;
        }

        public void setAddNumber(List<Integer> addNumber) {
            this.addNumber = addNumber;
        }

        @Override
        public String toString() {
            return "ReportData{" + "classify='" + classify + '\'' + ", total=" + total + ", addNumber=" + addNumber
                   + '}';
        }
    }
}
