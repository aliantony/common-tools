package com.antiy.asset.dto;

public class AssetMonitorDTO {
    /**
     * 资产id
     */
    private String assetId;
    /**
     * 资产ip
     */
    private String ip;
    /**
     * 资产编号
     */
    private String number;
    /**
     * 资产Mac
     */
    private String mac;
    /**
     * 当前值
     */
    private String current;
    /**
     * 阈值
     */
    private String threshole;
    /**
     * 告警生成时间
     */
    private Long time;

    /**
     * 告警码 1050001内存不足 1050002磁盘空间不足 1050003cpu占用过高 1050004运行异常 1050005资产退役
     */
    private String alarmCode;

    public String getAlarmCode() {
        return alarmCode;
    }

    public void setAlarmCode(String alarmCode) {
        this.alarmCode = alarmCode;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public String getThreshole() {
        return threshole;
    }

    public void setThreshole(String threshole) {
        this.threshole = threshole;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
