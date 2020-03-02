package com.antiy.asset.base;

import java.util.List;

/**
 * 区域基础类
 * @author zhangyajun
 * @create 2020-03-02 12:37
 **/
public class AreaBase {
    private List<String>  areaList;

    private List<Integer> statusList;

    public List<String> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<String> areaList) {
        this.areaList = areaList;
    }

    public List<Integer> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<Integer> statusList) {
        this.statusList = statusList;
    }
}
