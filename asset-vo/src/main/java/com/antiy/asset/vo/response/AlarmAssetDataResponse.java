package com.antiy.asset.vo.response;

import java.util.List;

public class AlarmAssetDataResponse {
    private List<AlarmAssetResponse> alarmAssetResponses;

    public List<AlarmAssetResponse> getAlarmAssetResponses() {
        return alarmAssetResponses;
    }

    public void setAlarmAssetResponses(List<AlarmAssetResponse> alarmAssetResponses) {
        this.alarmAssetResponses = alarmAssetResponses;
    }

    public AlarmAssetDataResponse(List<AlarmAssetResponse> alarmAssetResponses) {
        this.alarmAssetResponses = alarmAssetResponses;
    }

    public AlarmAssetDataResponse() {
    }
}
