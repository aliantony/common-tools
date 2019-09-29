package com.antiy.asset.vo.response;


import java.util.List;

/**
 * 拓扑关系数据
 */
public class AssetTopologyRelation {
    // 信息
    private String                 info;
    // 信息
    private String                 id;
    // 中心点
    private List<Double>          middlePoint;
    // 提示信息
    private List<String>           notices;
    // 展示角度
    private AssetTopologyViewAngle view_angle;
    // 数据
    private AssetTopologyJsonData  json_data;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<Double> getMiddlePoint() {
        return middlePoint;
    }

    public void setMiddlePoint(List<Double> middlePoint) {
        this.middlePoint = middlePoint;
    }

    public List<String> getNotices() {
        return notices;
    }

    public void setNotices(List<String> notices) {
        this.notices = notices;
    }

    public AssetTopologyViewAngle getView_angle() {
        return view_angle;
    }

    public void setView_angle(AssetTopologyViewAngle view_angle) {
        this.view_angle = view_angle;
    }

    public AssetTopologyJsonData getJson_data() {
        return json_data;
    }

    public void setJson_data(AssetTopologyJsonData json_data) {
        this.json_data = json_data;
    }
}
