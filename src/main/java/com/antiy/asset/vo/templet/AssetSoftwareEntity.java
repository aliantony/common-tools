package com.antiy.asset.vo.templet;


import com.antiy.asset.excel.annotation.ExcelField;

/**
 * <p>
 * 软件信息表
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-19
 */

public class AssetSoftwareEntity {


    private static final long serialVersionUID = 1L;

    /**
     * 软件名称
     */
    @ExcelField(value = "name", align = 1, title = "软件名称", type = 0)
    private String name;

    /**
     * 软件大小(KB)
     */
    @ExcelField(value = "size", align = 1, title = "软件大小(KB)", type = 0)
    private Integer size;


    /**
     * 操作系统(WINDTO;WS7-32-64,WINDTO;WS8-64)
     */
    @ExcelField(value = "operationSystem", align = 1, title = "操作系统", type = 0)
    private String operationSystem;


    /**
     * 软件品类
     */
    @ExcelField(value = "category", align = 1, title = "软件品类", type = 0)
    private Integer category;


    /**
     * 软件型号
     */
    @ExcelField(value = "model", align = 1, title = "软件型号", type = 0)
    private Integer model;


    /**
     * 上传的软件名称
     */
    @ExcelField(value = "uploadSoftwareName", align = 1, title = "上传的软件名称", type = 1)
    private String uploadSoftwareName;


    /**
     * 安装包路径
     */
    @ExcelField(value = "path", align = 1, title = "安装包路径", type = 1)
    private String path;


    /**
     * 版本
     */
    @ExcelField(value = "version", align = 1, title = "版本", type = 0)
    private String version;


    /**
     * 制造商
     */
    @ExcelField(value = "manufacturer", align = 1, title = "制造商", type = 0)
    private String manufacturer;


    /**
     * 软件描述
     */
    @ExcelField(value = "description", align = 1, title = "软件描述", type = 0)
    private String description;


    /**
     * 软件标签
     */
    @ExcelField(value = "softwareLabel", align = 1, title = "软件标签", type = 0)
    private Integer softwareLabel;


    /**
     * 1-待登记，2-不予登记，3-待配置，4-待验证，5-待入网，6-已入网，7-待退役，8-已退役
     */
    @ExcelField(value = "softwareStatus", align = 1, title = "软件资产状态", type = 0, dictType = "software_status")
    private Integer softwareStatus;


    /**
     * 1-免费软件，2-商业软件
     */
    @ExcelField(value = "authorization", align = 1, title = "授权", type = 0, dictType = "authorization")
    private Integer authorization;


    /**
     * 上报来源:1-自动上报，2-人工上报
     */
    @ExcelField(value = "reportSource", align = 1, title = "上报来源", type = 0, dictType = "report_source")
    private Integer reportSource;


    /**
     * 端口
     */
    @ExcelField(value = "port", align = 1, title = "端口", type = 0)
    private String port;


    /**
     * 语言
     */
    @ExcelField(value = "language", align = 1, title = "语言", type = 0)
    private String language;


    /**
     * 发布时间
     */
    @ExcelField(value = "releaseTime", align = 1, title = "发布时间", type = 0, isDate = true)
    private Long releaseTime;


    /**
     * 发布者
     */
    @ExcelField(value = "publisher", align = 1, title = "发布者", type = 0)
    private String publisher;

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


    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }


    public Integer getModel() {
        return model;
    }

    public void setModel(Integer model) {
        this.model = model;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getUploadSoftwareName() {
        return uploadSoftwareName;
    }

    public void setUploadSoftwareName(String uploadSoftwareName) {
        this.uploadSoftwareName = uploadSoftwareName;
    }


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }


    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Integer getSoftwareLabel() {
        return softwareLabel;
    }

    public void setSoftwareLabel(Integer softwareLabel) {
        this.softwareLabel = softwareLabel;
    }


    public Integer getSoftwareStatus() {
        return softwareStatus;
    }

    public void setSoftwareStatus(Integer softwareStatus) {
        this.softwareStatus = softwareStatus;
    }


    public Integer getAuthorization() {
        return authorization;
    }

    public void setAuthorization(Integer authorization) {
        this.authorization = authorization;
    }


    public Integer getReportSource() {
        return reportSource;
    }

    public void setReportSource(Integer reportSource) {
        this.reportSource = reportSource;
    }


    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }


    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }


    public Long getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Long releaseTime) {
        this.releaseTime = releaseTime;
    }


    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @Override
    public String toString() {
        return "AssetSoftwareEntity{" +
                "name='" + name + '\'' +
                ", size=" + size +
                ", operationSystem='" + operationSystem + '\'' +
                ", category=" + category +
                ", model=" + model +
                ", uploadSoftwareName='" + uploadSoftwareName + '\'' +
                ", path='" + path + '\'' +
                ", version='" + version + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", description='" + description + '\'' +
                ", softwareLabel=" + softwareLabel +
                ", softwareStatus=" + softwareStatus +
                ", authorization=" + authorization +
                ", reportSource=" + reportSource +
                ", port='" + port + '\'' +
                ", language='" + language + '\'' +
                ", releaseTime=" + releaseTime +
                ", publisher='" + publisher + '\'' +
                '}';
    }

    public AssetSoftwareEntity(String name, Integer size, String operationSystem, Integer category, Integer model, String uploadSoftwareName, String path, String version, String manufacturer, String description, Integer softwareLabel, Integer softwareStatus, Integer authorization, Integer reportSource, String port, String language, Long releaseTime, String publisher) {
        this.name = name;
        this.size = size;
        this.operationSystem = operationSystem;
        this.category = category;
        this.model = model;
        this.uploadSoftwareName = uploadSoftwareName;
        this.path = path;
        this.version = version;
        this.manufacturer = manufacturer;
        this.description = description;
        this.softwareLabel = softwareLabel;
        this.softwareStatus = softwareStatus;
        this.authorization = authorization;
        this.reportSource = reportSource;
        this.port = port;
        this.language = language;
        this.releaseTime = releaseTime;
        this.publisher = publisher;
    }

    public AssetSoftwareEntity() {
    }
}
