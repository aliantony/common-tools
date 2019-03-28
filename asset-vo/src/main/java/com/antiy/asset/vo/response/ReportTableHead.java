package com.antiy.asset.vo.response;

/**
 * 表格表头对象
 */
public class ReportTableHead {
    private String name;
    private String key;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ReportTableHead(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public ReportTableHead() {
    }
}