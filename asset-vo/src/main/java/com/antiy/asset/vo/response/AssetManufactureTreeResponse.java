package com.antiy.asset.vo.response;

/**
 * 树形厂商数据
 * @author zhangyajun
 * @create 2020-03-05 10:06
 **/
public class AssetManufactureTreeResponse {
    private String productName;
    private String version;
    private String id;
    private String pid;
    private String netStatus;

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
