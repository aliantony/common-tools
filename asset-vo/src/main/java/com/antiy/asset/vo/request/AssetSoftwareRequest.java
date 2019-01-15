package com.antiy.asset.vo.request;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * <p> AssetSoftwareRequest 请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetSoftwareRequest extends BasicRequest implements ObjectValidator {

    private static final long serialVersionUID = 1L;

   private AssetSoftwareLicenseRequest softwareLicenseRequest;

    public AssetPortProtocolRequest getAssetPortProtocolRequest() {
        return assetPortProtocolRequest;
    }

    public void setAssetPortProtocolRequest(AssetPortProtocolRequest assetPortProtocolRequest) {
        this.assetPortProtocolRequest = assetPortProtocolRequest;
    }

    private AssetPortProtocolRequest assetPortProtocolRequest;
   private String assetIds;

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
     * 软件标签
     */
    @ApiModelProperty(value = "软件标签")
    @Encode
    private String                      softwareLabel;
    /**
     * 1-待登记，2-不予登记，3-待配置，4-待验证，5-待入网，6-已入网，7-待退役，8-已退役
     */
    @ApiModelProperty(value = "软件状态")
    private Integer                     softwareStatus;
    /**
     * 0-免费软件，1-商业软件
     */
    @ApiModelProperty(value = "软件是否免费")
    @NotBlank(message = "软件是否免费不能为空")
    private Integer                     authorization;
    /**
     * 上报来源:1-自动上报，2-人工上报
     */
    @ApiModelProperty(value = "上报来源", allowableValues = "1-自动上报，2-人工上报")
    @NotBlank(message = "上报来源不能为空")
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

    public AssetSoftwareLicenseRequest getSoftwareLicenseRequest() {
        return softwareLicenseRequest;
    }

    public void setSoftwareLicenseRequest(AssetSoftwareLicenseRequest softwareLicenseRequest) {
        this.softwareLicenseRequest = softwareLicenseRequest;
    }

    public String getAssetIds() {
        return assetIds;
    }

    public void setAssetIds(String assetIds) {
        this.assetIds = assetIds;
    }
}