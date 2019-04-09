package com.antiy.asset.templet;

import java.util.Arrays;
import java.util.List;

// 封装的excel表单
public class ReportForm {

    // 导出的第一行excel表头
    private String       title;
    // 导出的第二行标题
    private List<String> headerList;
    // 导出的第一列标题
    private List<String> columnList;
    // 表单对应的数据 data[0][3] 指第一行第四列的数据
    private String[][]   data;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getHeaderList() {
        return headerList;
    }

    public void setHeaderList(List<String> headerList) {
        this.headerList = headerList;
    }

    public List<String> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<String> columnList) {
        this.columnList = columnList;
    }

    public String[][] getData() {
        return data;
    }

    public void setData(String[][] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ReportForm{" + "title='" + title + '\'' + ", headerList=" + headerList + ", columnList=" + columnList
               + ", data=" + Arrays.toString(data) + '}';
    }
}
