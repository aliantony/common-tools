package com.antiy.asset.vo.response;

import java.util.List;

public class TopologyCategoryCountResponse {
    private String                 status;
    private List<CategoryResponse> data;

    public class CategoryResponse {
        private String  asset_name;
        private Integer num;

        public String getAsset_name() {
            return asset_name;
        }

        public void setAsset_name(String asset_name) {
            this.asset_name = asset_name;
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

    public List<CategoryResponse> getData() {
        return data;
    }

    public void setData(List<CategoryResponse> data) {
        this.data = data;
    }
}
