package com.antiy.asset.vo.request;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Arrays;

/**
 * <p> AssetSoftwareRequest 请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */
@ApiModel(value = "软件请求")
public class AssetSoftwareRequest extends BasicRequest implements ObjectValidator {
    @ApiModelProperty(value = "登记流程数据")
    ManualStartActivityRequest activityRequest;
    @Valid
    @ApiModelProperty(value = "软件license")
    private AssetSoftwareLicenseRequest softwareLicenseRequest;

    @ApiModelProperty(value = "端口信息")
    @Valid
    private AssetPortProtocolRequest    assetPortProtocolRequest;

    @ApiModelProperty(value = "资产Id")
    @Encode
    private String[]                    assetIds;
    /**
     *  MD5/SHA
     */
    @ApiModelProperty("MD5/SHA")
    private String md5Code;
    /**
     * 主键
     */
    @ApiModelProperty("主键")
    @Encode
    private String                      id;
    /**
     * 软件大小(KB)
     */
    @ApiModelProperty(value = "软件大小")
    private Integer                     size;
    /**
     * 操作系统(WINDOWS7-32-64,WINDOWS8-64)
     */
    @ApiModelProperty(value = "操作系统")
    @NotBlank(message = "操作系统不能为空")
    private String                      operationSystem;
    /**
     * 软件品类
     */
    @ApiModelProperty(value = "软件品类")
    @Encode
    @NotBlank(message = "软件品类不能为空")
    private String                      categoryModel;
    /**
     * 软件名称
     */
    @NotBlank(message = "软件名称不能为空")
    @Size(message = "软件名称字段长度不能超过20位", max = 20)
    @ApiModelProperty(value = "软件名称")
    private String                      name;
    /**
     * 上传的软件名称
     */
    @ApiModelProperty(value = "上传的软件名称")
    private String                      uploadSoftwareName;
    /**
     * 安装包路径
     */
    @ApiModelProperty(value = "安装包路径")
    private String                      path;
    /**
     * 版本
     */
    @ApiModelProperty(value = "软件版本")
    @NotBlank(message = "软件版本不能为空")
    private String                      version;
    /**
     * 厂商
     */
    @ApiModelProperty(value = "厂商")
    private String                      manufacturer;
    /**
     * 软件描述
     */
    @ApiModelProperty(value = "软件描述")
    private String                      description;
    /**
     * 资产组
     */
    @ApiModelProperty(value = "资产组")
    private String                      assetGroup;

    /**
     * 序列号
     */
    @ApiModelProperty(value = "序列号")
    private String                      serial;
    /**
     * 软件标签
     */
    @ApiModelProperty(value = "软件标签")
    @Encode
    private String                      softwareLabel;
    /**
     * 软件状态：1待登记2待分析3可安装4已退役5不予登记
     */
    @ApiModelProperty(value = "软件状态：1待登记2待分析3可安装4已退役5不予登记")
    private Integer                     softwareStatus;
    /**
     * 0-免费软件，1-商业软件
     */
    @ApiModelProperty(value = "软件是否免费")
    @NotNull(message = "软件是否免费不能为空")
    private Integer                     authorization;
    /**
     * 上报来源:1-自动上报，2-人工上报
     */
    @ApiModelProperty(value = "上报来源", allowableValues = "1-自动上报，2-人工上报")
    @NotNull(message = "上报来源不能为空")
    private Integer                     reportSource;
    /**
     * 端口
     */
    @ApiModelProperty(value = "端口")
    private String                      port;
    /**
     * 语言
     */
    @ApiModelProperty(value = "语言")
    private String                      language;
    /**
     * 发布时间
     */
    @ApiModelProperty(value = "发布时间")
    private Long                        releaseTime;
    /**
     * 发布者
     */
    @ApiModelProperty(value = "发布者")
    private String                      publisher;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String                      memo;

    @ApiModelProperty(value = "硬件资产和软件资产关联表Id")
    @Encode
    private String                      assetSoftwareRelationId;

    @ApiModelProperty(value = "流程处理")
    @NotNull(message = "处理流程不能为空")
    @Valid
    private ActivityHandleRequest       request;

    public ActivityHandleRequest getRequest() {
        return request;
    }

    public void setRequest(ActivityHandleRequest request) {
        this.request = request;
    }

    public String getAssetSoftwareRelationId() {
        return assetSoftwareRelationId;
    }

    public void setAssetSoftwareRelationId(String assetSoftwareRelationId) {
        this.assetSoftwareRelationId = assetSoftwareRelationId;
    }

    public AssetPortProtocolRequest getAssetPortProtocolRequest() {
        return assetPortProtocolRequest;
    }

    public void setAssetPortProtocolRequest(AssetPortProtocolRequest assetPortProtocolRequest) {
        this.assetPortProtocolRequest = assetPortProtocolRequest;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getCategoryModel() {
        return categoryModel;
    }

    public void setCategoryModel(String categoryModel) {
        this.categoryModel = categoryModel;
    }

    public String getSoftwareLabel() {
        return softwareLabel;
    }

    public void setSoftwareLabel(String softwareLabel) {
        this.softwareLabel = softwareLabel;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public void validate() throws RequestParamValidateException {

    }
    public ManualStartActivityRequest getActivityRequest() {
        return activityRequest;
    }

    public void setActivityRequest(ManualStartActivityRequest activityRequest) {
        this.activityRequest = activityRequest;
    }

    public AssetSoftwareLicenseRequest getSoftwareLicenseRequest() {
        return softwareLicenseRequest;
    }

    public void setSoftwareLicenseRequest(AssetSoftwareLicenseRequest softwareLicenseRequest) {
        this.softwareLicenseRequest = softwareLicenseRequest;
    }

    public String[] getAssetIds() {
        return assetIds;
    }

    public void setAssetIds(String[] assetIds) {
        this.assetIds = assetIds;
    }

    @Override
    public String toString() {
        return "AssetSoftwareRequest{" +
                "activityRequest=" + activityRequest +
                ", softwareLicenseRequest=" + softwareLicenseRequest +
                ", assetPortProtocolRequest=" + assetPortProtocolRequest +
                ", assetIds=" + Arrays.toString (assetIds) +
                ", md5Code='" + md5Code + '\'' +
                ", id='" + id + '\'' +
                ", size=" + size +
                ", operationSystem='" + operationSystem + '\'' +
                ", categoryModel='" + categoryModel + '\'' +
                ", name='" + name + '\'' +
                ", uploadSoftwareName='" + uploadSoftwareName + '\'' +
                ", path='" + path + '\'' +
                ", version='" + version + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", description='" + description + '\'' +
                ", assetGroup='" + assetGroup + '\'' +
                ", serial='" + serial + '\'' +
                ", softwareLabel='" + softwareLabel + '\'' +
                ", softwareStatus=" + softwareStatus +
                ", authorization=" + authorization +
                ", reportSource=" + reportSource +
                ", port='" + port + '\'' +
                ", language='" + language + '\'' +
                ", releaseTime=" + releaseTime +
                ", publisher='" + publisher + '\'' +
                ", memo='" + memo + '\'' +
                ", assetSoftwareRelationId='" + assetSoftwareRelationId + '\'' +
                ", request=" + request +
                '}';
    }

    public String getMd5Code() {
        return md5Code;
    }

    public void setMd5Code(String md5Code) {
        this.md5Code = md5Code;
    }
}