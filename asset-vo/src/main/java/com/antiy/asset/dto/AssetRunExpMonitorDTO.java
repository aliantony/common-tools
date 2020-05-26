package com.antiy.asset.dto;

import java.util.List;

public class AssetRunExpMonitorDTO {

    /**
     * 运行异常监控
     */
    private String runtimeExceptionThreshold;

    private List<AssetMonitorDTO> assetDTOs;

    public List<AssetMonitorDTO> getAssetDTOs() {
        return assetDTOs;
    }

    public void setAssetDTOs(List<AssetMonitorDTO> assetDTOs) {
        this.assetDTOs = assetDTOs;
    }

    public String getRuntimeExceptionThreshold() {
        return runtimeExceptionThreshold;
    }

    public void setRuntimeExceptionThreshold(String runtimeExceptionThreshold) {
        this.runtimeExceptionThreshold = runtimeExceptionThreshold;
    }

    public static class AssetRunExpTime {
        //阈值
        private Integer  runtimeExceptionThreshold;
        //单位
        private String unit;

        public Integer getRuntimeExceptionThreshold() {
            return runtimeExceptionThreshold;
        }

        public void setRuntimeExceptionThreshold(Integer runtimeExceptionThreshold) {
            this.runtimeExceptionThreshold = runtimeExceptionThreshold;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }
    }


}
