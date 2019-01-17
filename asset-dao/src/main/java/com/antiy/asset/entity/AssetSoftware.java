package com.antiy.asset.entity;

import com.antiy.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p> 软件信息表 </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */

public class AssetSoftware extends BaseEntity {

    /**
    *  软件大小(KB)
    */
    private Integer size;
    /**
    *  操作系统(WINDOWS7-32-64,WINDOWS8-64)
    */
    private String operationSystem;
    /**
    *  软件品类
    */
    private Integer categoryModel;
    /**
     * 软件品类名字
     */
    @ApiModelProperty(value = "软件品类名字")
    private String  categoryModelName;
    /**
    *  软件名称
    */
    private String name;
    /**
    *  上传的软件名称
    */
    private String uploadSoftwareName;
    /**
    *  安装包路径
    */
    private String path;
    /**
    *  版本
    */
    private String version;
    /**
    *  厂商
    */
    private String manufacturer;
    /**
    *  软件描述
    */
    private String description;
    /**
    *  资产组
    */
    private String assetGroup;
    /**
    *  软件标签
    */
    private Integer softwareLabel;
    /**
    *  1-待登记，2-不予登记，3-待配置，4-待验证，5-待入网，6-已入网，7-待退役，8-已退役
    */
    private Integer softwareStatus;
    /**
    *  0-免费软件，1-商业软件
    */
    private Integer authorization;
    /**
    *  上报来源:1-自动上报，2-人工上报
    */
    private Integer reportSource;
    /**
    *  端口
    */
    private String port;
    /**
    *  语言
    */
    private String language;
    /**
    *  发布时间
    */
    private Long releaseTime;
    /**
    *  发布者
    */
    private String publisher;
    /**
    *  创建时间
    */
    private Long gmtCreate;
    /**
    *  备注
    */
    private String memo;
    /**
    *  更新时间
    */
    private Long gmtModified;
    /**
    *  创建人
    */
    private Integer createUser;
    /**
    *  修改人
    */
    private Integer modifyUser;
    /**
    *  状态,0 未删除,1已删除
    */
    private Integer status;

    private Long              buyDate;
    /**
     * 到期时间
     */
    private Long              serviceLife;

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

    public String getCategoryModelName() {
        return categoryModelName;
    }

    public void setCategoryModelName(String categoryModelName) {
        this.categoryModelName = categoryModelName;
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


    public String getAssetGroup() {
        return assetGroup;
    }

    public void setAssetGroup(String assetGroup) {
    this.assetGroup = assetGroup;
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


    @Override
    public String toString() {
            return "AssetSoftware{" +
                        ", size=" + size +
                        ", operationSystem=" + operationSystem +
                        ", categoryModel=" + categoryModel +
                        ", name=" + name +
                        ", uploadSoftwareName=" + uploadSoftwareName +
                        ", path=" + path +
                        ", version=" + version +
                        ", manufacturer=" + manufacturer +
                        ", description=" + description +
                        ", assetGroup=" + assetGroup +
                        ", softwareLabel=" + softwareLabel +
                        ", softwareStatus=" + softwareStatus +
                        ", authorization=" + authorization +
                        ", reportSource=" + reportSource +
                        ", port=" + port +
                        ", language=" + language +
                        ", releaseTime=" + releaseTime +
                        ", publisher=" + publisher +
                        ", gmtCreate=" + gmtCreate +
                        ", memo=" + memo +
                        ", gmtModified=" + gmtModified +
                        ", createUser=" + createUser +
                        ", modifyUser=" + modifyUser +
                        ", status=" + status +
            "}";
    }
}