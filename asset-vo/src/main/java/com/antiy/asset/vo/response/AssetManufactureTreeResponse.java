package com.antiy.asset.vo.response;

import java.util.List;

/**
 * 树形厂商数据
 * @author zhangyajun
 * @create 2020-03-05 10:06
 **/
public class AssetManufactureTreeResponse {
    private String productName;
    private String version;
    private String id;
    private String       assetId;
    private String pid;
    private String netStatus;
    private String       netStatusName;
    private String       name;
    private String       number;
    private List<String> ips;

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getNetStatusName() {
        return netStatusName;
    }

    public void setNetStatusName(String netStatusName) {
        this.netStatusName = netStatusName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public List<String> getIps() {
        return ips;
    }

    public void setIps(List<String> ips) {
        this.ips = ips;
    }

    public String getNetStatus() {
        return netStatus;
    }

    public void setNetStatus(String netStatus) {
        this.netStatus = netStatus;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}
