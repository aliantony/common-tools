package com.antiy.asset.vo.request;

import java.util.List;

/**
 * @author: zhangbing
 * @date: 2019/4/16 16:52
 * @description:
 */
public class BaseLineTemplateRequest {
    private Integer      checkType;
    private List<String> assetIdList;

    public Integer getCheckType() {
        return checkType;
    }

    public void setCheckType(Integer checkType) {
        this.checkType = checkType;
    }

    public List<String> getAssetIdList() {
        return assetIdList;
    }

    public void setAssetIdList(List<String> assetIdList) {
        this.assetIdList = assetIdList;
    }
}
