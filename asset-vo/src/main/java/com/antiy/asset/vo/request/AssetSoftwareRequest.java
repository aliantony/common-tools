package com.antiy.asset.vo.request;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetSoftwareRequest 请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetSoftwareRequest extends BasicRequest implements ObjectValidator {

    private static final long serialVersionUID = 1L;

   private AssetSoftwareLicenseRequest softwareLicenseRequest;
    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private Integer               id;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
    public void validate() throws RequestParamValidateException {

    }

    public AssetSoftwareLicenseRequest getSoftwareLicenseRequest() {
        return softwareLicenseRequest;
    }

    public void setSoftwareLicenseRequest(AssetSoftwareLicenseRequest softwareLicenseRequest) {
        this.softwareLicenseRequest = softwareLicenseRequest;
    }
}