package com.antiy.asset.vo.response;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetSoftwareResponse 响应对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetSoftwareResponse extends BaseResponse {

    /**
     * 软件大小(KB)
     */
    @ApiModelProperty("软件大小(KB)")
    private Integer size;
    /**
     * 操作系统(WINDTO;WS7-32-64,WINDTO;WS8-64)
     */
    @ApiModelProperty("操作系统(WINDTO;WS7-32-64,WINDTO;WS8-64)")
    private String  operationSystem;
    /**
     * 软件品类
     */
    @ApiModelProperty("软件品类")
    private Integer categoryModel;

    /**
     * 软件品类名字
     */
    @ApiModelProperty(value = "软件品类名字")
    private String  categoryModelName;

    /**
     * 软件名称
     */
    @ApiModelProperty("软件名称")
    private String  name;
    /**
     * 上传的软件名称
     */
    @ApiModelProperty("上传的软件名称")
    private String  uploadSoftwareName;
    /**
     * 安装包路径
     */
    @ApiModelProperty("安装包路径")
    private String  path;
    /**
     * 版本
     */
    @ApiModelProperty("版本")
    private String  version;
    /**
     * 厂商
     */
    @ApiModelProperty("厂商")
    private String  manufacturer;
    /**
     * 软件描述
     */
    @ApiModelProperty("软件描述")
    private String  description;

    /**
     * 软件状态：1待登记2待分析3可安装4已退役5不予登记
     */
    @ApiModelProperty("软件状态：1待登记2待分析3可安装4已退役5不予登记")
    private Integer softwareStatus;

    /**
     * 0-免费软件，1-商业软件
     */
    @ApiModelProperty("1-免费软件，2-商业软件")
    private Integer authorization;
    /**
     * 上报来源:1-自动上报，2-人工上报
     */
    @ApiModelProperty("上报来源:1-自动上报，2-人工上报")
    private Integer reportSource;
    /**
     * 端口
     */
    @ApiModelProperty("端口")
    private String  port;
    /**
     * 语言
     */
    @ApiModelProperty("语言")
    private String  language;
    /**
     * 发布时间
     */
    @ApiModelProperty("发布时间")
    private Long    releaseTime;
    /**
     * 发布者
     */
    @ApiModelProperty("发布者")
    private String  publisher;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Long    gmtCreate;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String  memo;
    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private Long    gmtModified;
    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    private Integer createUser;
    /**
     * 修改人
     */
    @ApiModelProperty("修改人")
    private Integer modifyUser;
    /**
     * 状态,0 未删除,1已删除
     */
    @ApiModelProperty("状态,0 未删除,1已删除")
    private Integer status;

    /**
     * 关联硬件资产数
     */
    @ApiModelProperty(value = "关联 硬件资产数")
    private Integer assetCount;
    /**
     * 软件许可密钥
     */
    @ApiModelProperty(value = "软件许可密钥")
    private String  licenseSecretKey;

    public String getLicenseSecretKey() {
        return licenseSecretKey;
    }

    public void setLicenseSecretKey(String licenseSecretKey) {
        this.licenseSecretKey = licenseSecretKey;
    }

    public Integer getAssetCount() {
        return assetCount;
    }

    public void setAssetCount(Integer assetCount) {
        this.assetCount = assetCount;
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

    public Integer getCategoryModel() {
        return categoryModel;
    }

    public void setCategoryModel(Integer categoryModel) {
        this.categoryModel = categoryModel;
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

    public String getCategoryModelName() {
        return categoryModelName;
    }

    public void setCategoryModelName(String categoryModelName) {
        this.categoryModelName = categoryModelName;
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

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Long getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Integer getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(Integer modifyUser) {
        this.modifyUser = modifyUser;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}