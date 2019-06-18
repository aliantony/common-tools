package com.antiy.asset.vo.response;

import java.util.List;
import java.util.Map;

public class AssetTopologyAlarmResponse {
    private List<Map> data;
    private String    status;
    private String    version;

    public List<Map> getData() {
        return data;
    }

    public void setData(List<Map> data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

}
