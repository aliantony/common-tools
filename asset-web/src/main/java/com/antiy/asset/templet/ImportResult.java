package com.antiy.asset.templet;

import java.util.List;

/**
 * 导入结果
 */
public class ImportResult {

    private String  msg;

    private List<?> dataList;

    public ImportResult() {
    }

    public ImportResult(String msg, List<?> dataList) {
        this.msg = msg;
        this.dataList = dataList;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<?> getDataList() {
        return dataList;
    }

    public void setDataList(List<?> dataList) {
        this.dataList = dataList;
    }
}
