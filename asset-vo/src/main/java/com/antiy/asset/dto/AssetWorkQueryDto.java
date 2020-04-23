package com.antiy.asset.dto;

import java.util.List;

/**
 * @Author ygh
 * @Description 资产工作台查询
 * @Date 2020/4/22
 */
public class AssetWorkQueryDto {

    /**
     * 资产状态：1-待登记，2-不予登记，3-整改中，4-入网待审批，5-入网审批未通过，6-待准入,7-已入网，8-变更中, 9-待退役，10-退役待审批
     * 11-退役审批未通过 12-已退役 13-待报废 14-报废待审批 15-报废审批未通过 16-已报废
     */
    private Integer AssetStatus;


    /**
     * 上报来源:1-资产探测，2-人工登记，3-代理上报
     */
    private Integer AssetSource;


    /**
     * 区域id
     */
    private List<String> areaId;


    public Integer getAssetSource() {
        return AssetSource;
    }

    public void setAssetSource(Integer assetSource) {
        AssetSource = assetSource;
    }

    public Integer getAssetStatus() {
        return AssetStatus;
    }

    public void setAssetStatus(Integer assetStatus) {
        AssetStatus = assetStatus;
    }


    public List<String> getAreaId() {
        return areaId;
    }

    public void setAreaId(List<String> areaId) {
        this.areaId = areaId;
    }
}
