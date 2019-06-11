package com.antiy.asset.vo.response;

import java.util.List;
import java.util.Map;

public class CountTopologyResponse {
    String                    status;
    List<Map<String, String>> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Map<String, String>> getList() {
        return data;
    }

    public void setList(List<Map<String, String>> list) {
        this.data = list;
    }
}
