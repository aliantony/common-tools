package com.antiy.asset.vo.response;

import java.util.List;

public class AssetTopologyIpSearchResposne {
    private String         status;
    private List<IpSearch> data;

    public class IpSearch {
        private String asset_id;
        private String ip;
        private String person_name;
        private String department_name;

        public String getAsset_id() {
            return asset_id;
        }

        public void setAsset_id(String asset_id) {
            this.asset_id = asset_id;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getPerson_name() {
            return person_name;
        }

        public void setPerson_name(String person_name) {
            this.person_name = person_name;
        }

        public String getDepartment_name() {
            return department_name;
        }

        public void setDepartment_name(String department_name) {
            this.department_name = department_name;
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<IpSearch> getData() {
        return data;
    }

    public void setData(List<IpSearch> data) {
        this.data = data;
    }
}
