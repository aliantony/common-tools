package com.antiy.asset.templet;

import java.util.List;

/**
 * 导入结果
 */
public class ImportResult<T> {

    private String  msg;

    private List<T> dataList;

    public ImportResult() {
    }

    public ImportResult(String msg, List<T> dataList) {
        this.msg = msg;
        this.dataList = dataList;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }
}
