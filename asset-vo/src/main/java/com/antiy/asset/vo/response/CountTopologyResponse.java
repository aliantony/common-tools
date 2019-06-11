package com.antiy.asset.vo.response;

import java.util.List;
import java.util.Map;

public class CountTopologyResponse {
    String                    status;
    List<Map<String, String>> list;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Map<String, String>> getList() {
        return list;
    }

    public void setList(List<Map<String, String>> list) {
        this.list = list;
    }
}
