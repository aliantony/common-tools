package com.antiy.asset.vo.response;

import java.util.List;
import java.util.Map;

/**
 * 拓扑图的返回
 */
public class AssetTopologyNodeResponse {
    private String                                   status;
    private List<Map<String, AssetTopologyRelation>> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Map<String, AssetTopologyRelation>> getData() {
        return data;
    }

    public void setData(List<Map<String, AssetTopologyRelation>> data) {
        this.data = data;
    }
}
