package com.antiy.asset.entity;

/**
 * @Author: lvliang
 * @Date: 2020/6/3 13:57
 */
public class AssetArea {
    /**
     * 资产id
     */
    private String id;
    /**
     * 资产区域
     */
    private String area;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Override
    public String toString() {
        return "AssetArea{" + "id='" + id + '\'' + ", area='" + area + '\'' + '}';
    }
}
