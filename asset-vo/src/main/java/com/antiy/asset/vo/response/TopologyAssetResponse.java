package com.antiy.asset.vo.response;

import com.antiy.common.encoder.Encode;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class TopologyAssetResponse {
    private String                  status;
    private String                  version;
    private List<TopologyNodeAsset> data;

    public class TopologyNodeAsset extends BaseResponse {
        @ApiModelProperty("ip")
        private String ip;
        @ApiModelProperty("资产id")
        @Encode
        private String asset_id;
        @ApiModelProperty("资产名")
        private String asset_name;
        @ApiModelProperty("操作系统")
        private String os;
        @ApiModelProperty("机房位置")
        private String houseLocation;
        @ApiModelProperty("联系电话")
        private String telephone;
        @ApiModelProperty("维护方式")
        private String installType;
        @ApiModelProperty("资产组")
        private String assetGroup;

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getAsset_id() {
            return asset_id;
        }

        public void setAsset_id(String asset_id) {
            this.asset_id = asset_id;
        }

        public String getAsset_name() {
            return asset_name;
        }

        public void setAsset_name(String asset_name) {
            this.asset_name = asset_name;
        }

        public String getOs() {
            return os;
        }

        public void setOs(String os) {
            this.os = os;
        }

        public String getHouseLocation() {
            return houseLocation;
        }

        public void setHouseLocation(String houseLocation) {
            this.houseLocation = houseLocation;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getInstallType() {
            return installType;
        }

        public void setInstallType(String installType) {
            this.installType = installType;
        }

        public String getAssetGroup() {
            return assetGroup;
        }

        public void setAssetGroup(String assetGroup) {
            this.assetGroup = assetGroup;
        }
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

    public List<TopologyNodeAsset> getData() {
        return data;
    }

    public void setData(List<TopologyNodeAsset> data) {
        this.data = data;
    }
}
