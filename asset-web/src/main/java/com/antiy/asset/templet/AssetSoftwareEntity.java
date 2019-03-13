package com.antiy.asset.templet;

import com.antiy.asset.annotation.ExcelField;

/**
 * <p> 软件信息表 </p>
 *
 * @author zhangyajun
 * @since 2018-12-19
 */

public class AssetSoftwareEntity {

    /**
     * 软件名称
     */
    @ExcelField(value = "name", align = 1, title = "软件名称", type = 0)
    private String            name;
    /**
     * 软件品类
     */
    @ExcelField(value = "category", align = 1, title = "软件品类", type = 0, dictType = "software_category")
    private String            category;
    /**
     * 软件名称
     */
    @ExcelField(value = "manufacturer", align = 1, title = "软件厂商", type = 0)
    private String            manufacturer;
    /**
     * 序列号
     */
    @ExcelField(value = "serial", align = 1, title = "序列号", type = 0)
    private String            serial;

    /**
     * 软件版本
     */
    @ExcelField(value = "version", align = 1, title = "软件版本", type = 0)
    private String            version;

    /**
     * 操作系统(WINDTO;WS7-32-64,WINDTO;WS8-64)
     */
    @ExcelField(value = "operationSystem", align = 1, title = "兼容系统", type = 0)
    private String            operationSystem;


    /**
     * 1-免费软件，2-商业软件
     */
    @ExcelField(value = "authorization", align = 1, title = "授权", type = 0, dictType = "authorization")
    private Integer           authorization;

    /**
     * MD5/SHA
     */
    @ExcelField(value = "MD5", align = 1, title = "MD5/SHA", type = 0)
    private String            MD5;
    /**
     * 购买时间
     */
    @ExcelField(value = "buy_date", align = 1, title = "购买时间", isDate = true)
    private Long              buyDate;

    /**
     * 到期时间
     */
    @ExcelField(value = "service_life", align = 1, title = "到期时间", isDate = true)
    private Long              serviceLife;
    /**
     * 发布时间
     */
    @ExcelField(value = "releaseTime", align = 1, title = "发布时间", type = 0, isDate = true)
    private Long              releaseTime;
    /**
     * 软件地址
     */
    @ExcelField(value = "filePath", align = 1, title = "软件地址", type = 0)
    private String            filePath;

    /**
     * 软件大小
     */
    @ExcelField(value = "size", align = 1, title = "软件大小", type = 0)
    private Integer           size;

    /**
     * 软件描述
     */
    @ExcelField(value = "description", align = 1, title = "软件描述", type = 0)
    private String            description;

    public Integer getAuthorization() {
        return authorization;
    }

    public void setAuthorization(Integer authorization) {
        this.authorization = authorization;
    }

    @Override
    public String toString() {
        return "AssetSoftwareEntity{" + "name='" + name + '\'' + ", manufacturer='" + manufacturer + '\''
               + ", serial='" + serial + '\'' + ", size=" + size + ", operationSystem='" + operationSystem + '\''
               + ", category=" + category + ", version='" + version + '\'' + ", description='" + description + '\''
               + ", authorization=" + authorization + ", releaseTime=" + releaseTime + ", buyDate=" + buyDate
               + ", serviceLife=" + serviceLife + '}';
    }

    public String getName() {
        return name;
    }

    public Long getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Long releaseTime) {
        this.releaseTime = releaseTime;
    }

    public Long getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Long buyDate) {
        this.buyDate = buyDate;
    }

    public Long getServiceLife() {
        return serviceLife;
    }

    public void setServiceLife(Long serviceLife) {
        this.serviceLife = serviceLife;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getOperationSystem() {
        return operationSystem;
    }

    public void setOperationSystem(String operationSystem) {
        this.operationSystem = operationSystem;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMD5() {
        return MD5;
    }

    public void setMD5(String MD5) {
        this.MD5 = MD5;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }


}
