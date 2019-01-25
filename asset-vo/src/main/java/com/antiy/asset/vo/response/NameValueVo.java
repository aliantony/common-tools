package com.antiy.asset.vo.response;

import java.util.List;

/**
 * @author zhangyajun
 * @create 2019-01-25 14:31
 **/
public class NameValueVo<T> {
    private String name;
    private List<T> data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
