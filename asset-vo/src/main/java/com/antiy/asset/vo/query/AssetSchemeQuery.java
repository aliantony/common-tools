package com.antiy.asset.vo.query;

import com.antiy.common.base.ObjectQuery;
import com.antiy.common.encoder.Encode;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class AssetSchemeQuery extends ObjectQuery {
    @ApiModelProperty("资产id")
    @Encode
    List<String> assetIds;
    Integer orginStatusOne;
    Integer orginStatusTwo;
    Integer targetStatus;


    public List<String> getAssetIds() {
        return assetIds;
    }

    public void setAssetIds(List<String> assetIds) {
        this.assetIds = assetIds;
    }

    public Integer getOrginStatusOne() {
        return orginStatusOne;
    }

    public void setOrginStatusOne(Integer orginStatusOne) {
        this.orginStatusOne = orginStatusOne;
    }

    public Integer getOrginStatusTwo() {
        return orginStatusTwo;
    }

    public void setOrginStatusTwo(Integer orginStatusTwo) {
        this.orginStatusTwo = orginStatusTwo;
    }

    public Integer getTargetStatus() {
        return targetStatus;
    }

    public void setTargetStatus(Integer targetStatus) {
        this.targetStatus = targetStatus;
    }
}
