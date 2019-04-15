package com.antiy.asset.vo.response;

import com.antiy.common.base.BaseResponse;

import java.util.List;

/**
 * @Auther: liucp
 * @Date: 2019/4/12 16:00
 * @Description:
 */
public class AlarmAssetIdResponse {

    private Integer currentAlarmAssetIdNum;

    private List<BaseResponse> assetIdList;

    public Integer getCurrentAlarmAssetIdNum() {
        return assetIdList.size();
    }

    public void setCurrentAlarmAssetIdNum(Integer currentAlarmAssetIdNum) {
        this.currentAlarmAssetIdNum = currentAlarmAssetIdNum;
    }

    public List<BaseResponse> getAssetIdList() {
        return assetIdList;
    }

    public void setAssetIdList(List<BaseResponse> assetIdList) {
        this.assetIdList = assetIdList;
    }
}
