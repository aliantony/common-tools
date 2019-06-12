package com.antiy.asset.vo.response;

import java.util.List;

public class TopologyListResponse {
    private String             status;
    private Integer            pageSize;
    private Integer            currentPage;
    private Integer            total;
    private List<TopologyNode> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public List<TopologyNode> getData() {
        return data;
    }

    public void setData(List<TopologyNode> data) {
        this.data = data;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public class TopologyNode {
        private String asset_ip;
        private String asset_id;
        private String person_name;
        private String asset_type;
        private String asset_group;
        private String asset_area;
        private String asset_untreated_warning;
        private String asset_unrepair;
        private String system_uninstall;

        public String getAsset_id() {
            return asset_id;
        }

        public void setAsset_id(String asset_id) {
            this.asset_id = asset_id;
        }

        public String getAsset_ip() {
            return asset_ip;
        }

        public void setAsset_ip(String asset_ip) {
            this.asset_ip = asset_ip;
        }

        public String getPerson_name() {
            return person_name;
        }

        public void setPerson_name(String person_name) {
            this.person_name = person_name;
        }

        public String getAsset_type() {
            return asset_type;
        }

        public void setAsset_type(String asset_type) {
            this.asset_type = asset_type;
        }

        public String getAsset_group() {
            return asset_group;
        }

        public void setAsset_group(String asset_group) {
            this.asset_group = asset_group;
        }

        public String getAsset_area() {
            return asset_area;
        }

        public void setAsset_area(String asset_area) {
            this.asset_area = asset_area;
        }

        public String getAsset_untreated_warning() {
            return asset_untreated_warning;
        }

        public void setAsset_untreated_warning(String asset_untreated_warning) {
            this.asset_untreated_warning = asset_untreated_warning;
        }

        public String getAsset_unrepair() {
            return asset_unrepair;
        }

        public void setAsset_unrepair(String asset_unrepair) {
            this.asset_unrepair = asset_unrepair;
        }

        public String getSystem_uninstall() {
            return system_uninstall;
        }

        public void setSystem_uninstall(String system_uninstall) {
            this.system_uninstall = system_uninstall;
        }
    }
}
