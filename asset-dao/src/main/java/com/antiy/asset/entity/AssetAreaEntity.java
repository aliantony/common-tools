package com.antiy.asset.entity;

/**
 * @author: zhangbing
 * @date: 2019/3/26 10:55
 * @description:
 */
public class AssetAreaEntity {

    /**
     * 区域资产名字
     */
    private int    areaAssetCount;

    /**
     * 区域Id
     */
    private String areaId;

    /**
     * 时间
     */
    private String date;

    public int getAreaAssetCount() {
        return areaAssetCount;
    }

    public void setAreaAssetCount(int areaAssetCount) {
        this.areaAssetCount = areaAssetCount;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
