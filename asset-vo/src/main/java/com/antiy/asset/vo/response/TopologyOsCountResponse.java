package com.antiy.asset.vo.response;

import java.util.List;

public class TopologyOsCountResponse {
    private String                 status;
    private List<TopologyOsCountResponse.OsResponse> data;

    public class OsResponse {
        private String  os_type;
        private Integer num;

        public String getOs_type() {
            return os_type;
        }

        public void setOs_type(String os_type) {
            this.os_type = os_type;
        }

        public Integer getNum() {
            return num;
        }

        public void setNum(Integer num) {
            this.num = num;
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<TopologyOsCountResponse.OsResponse> getData() {
        return data;
    }

    public void setData(List<TopologyOsCountResponse.OsResponse> data) {
        this.data = data;
    }

}
