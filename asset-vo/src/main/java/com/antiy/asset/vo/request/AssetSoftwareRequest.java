package com.antiy.asset.vo.request;

import java.util.Arrays;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetSoftwareRequest 请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */
@ApiModel(value = "软件请求")
public class AssetSoftwareRequest extends BasicRequest implements ObjectValidator {
    // @ApiModelProperty(value = "登记流程数据")
    // @Valid
    // private ManualStartActivityRequest activityRequest;
    // @ApiModelProperty(value = "流程处理")
    // @Valid
    // private ActivityHandleRequest request;
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
     * 文件校验方式
     */
    @ApiModelProperty(value = "文件校验方式(必填)", required = true)
    @NotBlank(message = "文件包校验方式不能为空")
    @Size(message = "校验方式不能超过30位", max = 30)
    private String                      checkType;
    /**
     * MD5/SHA
     */
    @ApiModelProperty("MD5/SHA")
    @Size(message = "MD5/SHA不为32位", max = 32, min = 32)
    private String                      md5Code;
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
    private Long                        size;
    /**
     * 操作系统(WINDOWS7-32-64,WINDOWS8-64)
     */
    @ApiModelProperty(value = "操作系统,用于下发")
    @Encode
    private String                      operationSystem;
    /**
     * 操作系统(WINDOWS7-32-64,WINDOWS8-64)
     */
    @ApiModelProperty(value = "操作系统数组")
    @Encode
    private String[]                    operationSystems;
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
    @Size(message = "软件名称字段长度不能超过80位", max = 80, min = 1)
    @ApiModelProperty(value = "软件名称")
    private String                      name;
    /**
     * 上传的软件名称
     */
    @ApiModelProperty(value = "上传的软件名称")
    @NotBlank(message = "上传的软件名称不能为空")
    private String                      uploadSoftwareName;
    /**
     * 安装包路径
     */
    @ApiModelProperty(value = "安装包路径")
    @NotBlank(message = "安装包路径不能为空")
    private String                      path;
    /**
     * 版本
     */
    @ApiModelProperty(value = "软件版本")
    @NotBlank(message = "软件版本不能为空")
    @Size(message = "软件版本不能超过30位", max = 30)
    // @Pattern(regexp = "^[+]?\\d{1,2}\\.\\d{1,2}\\.\\d{1,2}$", message = "软件版本格式不正确")
    private String                      version;
    /**
     * 厂商
     */
    @ApiModelProperty(value = "厂商")
    @Size(message = "资产厂商不能超过30位", max = 80)
    private String                      manufacturer;
    /**
     * 软件描述
     */
    @ApiModelProperty(value = "软件描述")
    @Size(message = "描述不能超过300位", max = 300)
    private String                      description;

    /**
     * 序列号
     */
    @ApiModelProperty(value = "序列号")
    @Size(message = "资产序列号不能超过30位", max = 30)
    private String                      serial;

    /**
     * 到期时间
     */
    @ApiModelProperty(value = "到期时间")
    @Max(message = "时间超出范围", value = 9999999999999L)
    private Long                        serviceLife;

    /**
     * 软件状态：1待登记2待分析3可安装4已退役5不予登记
     */
    @ApiModelProperty(value = "软件状态：1待登记2待分析3可安装4已退役5不予登记")
    private Integer                     softwareStatus;
    /**
     * 0-免费软件，1-商业软件
     */
    @ApiModelProperty(value = "软件是否免费")
    @Max(value = 1, message = "授权类型出错")
    @Min(value = 0, message = "授权类型出错")
    private Integer                     authorization;
    /**
     * 上报来源:1-自动上报，2-人工上报
     */
    @ApiModelProperty(value = "上报来源", allowableValues = "1-自动上报，2-人工上报")
    // @NotNull(message = "上报来源不能为空")
    private Integer                     reportSource;
    /**
     * 端口
     */
    @ApiModelProperty(value = "协议")
    @Size(message = "协议长度不能超过30位", max = 30)
    private String                      protocol;
    /**
     * 语言
     */
    @ApiModelProperty(value = "语言")
    @Size(message = "协议长度不能超过64位", max = 64)
    private String                      language;
    /**
     * 发布时间
     */
    @ApiModelProperty(value = "发布时间")
    @Max(message = "时间超出范围", value = 9999999999999L)
    private Long                        releaseTime;

    /**
     * 发布者
     */
    @ApiModelProperty(value = "发布者")
    private String                      publisher;
    /**
     * 备注
     */
    @ApiModelProperty(value = "配置建议")
    @Size(message = "配置建议不能超过300位", max = 300)
    private String                      memo;

    @ApiModelProperty(value = "硬件资产和软件资产关联表Id")
    @Encode
    private String                      assetSoftwareRelationId;

    @ApiModelProperty(value = "购买日期")
    private Long                        buyDate;

    @ApiModelProperty(value = "安装说明书地址")
    private String                      manualDocUrl;

    @ApiModelProperty(value = "安装说明书名字")
    private String                      manualDocName;

    public String getManualDocUrl() {
        return manualDocUrl;
    }

    public String getOperationSystem() {
        return operationSystem;
    }

    public void setOperationSystem(String operationSystem) {
        this.operationSystem = operationSystem;
    }

    public void setManualDocUrl(String manualDocUrl) {
        this.manualDocUrl = manualDocUrl;
    }

    public String getManualDocName() {
        return manualDocName;
    }

    public void setManualDocName(String manualDocName) {
        this.manualDocName = manualDocName;
    }

    public Long getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Long buyDate) {
        this.buyDate = buyDate;
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

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
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

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
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

    public String[] getAssetIds() {
        return assetIds;
    }

    public void setAssetIds(String[] assetIds) {
        this.assetIds = assetIds;
    }

    public String getMd5Code() {
        return md5Code;
    }

    public void setMd5Code(String md5Code) {
        this.md5Code = md5Code;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public Long getServiceLife() {
        return serviceLife;
    }

    public void setServiceLife(Long serviceLife) {
        this.serviceLife = serviceLife;
    }

    public String[] getOperationSystems() {
        return operationSystems;
    }

    public void setOperationSystems(String[] operationSystems) {
        this.operationSystems = operationSystems;
    }

    public String getCheckType() {
        return checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    @Override
    public String toString() {
        return "AssetSoftwareRequest{" + "softwareLicenseRequest=" + softwareLicenseRequest
               + ", assetPortProtocolRequest=" + assetPortProtocolRequest + ", assetIds=" + Arrays.toString(assetIds)
               + ", checkType='" + checkType + '\'' + ", md5Code='" + md5Code + '\'' + ", id='" + id + '\'' + ", size="
               + size + ", operationSystem='" + operationSystem + '\'' + ", operationSystems="
               + Arrays.toString(operationSystems) + ", categoryModel='" + categoryModel + '\'' + ", name='" + name
               + '\'' + ", uploadSoftwareName='" + uploadSoftwareName + '\'' + ", path='" + path + '\'' + ", version='"
               + version + '\'' + ", manufacturer='" + manufacturer + '\'' + ", description='" + description + '\''
               + ", serial='" + serial + '\'' + ", serviceLife=" + serviceLife + ", softwareStatus=" + softwareStatus
               + ", authorization=" + authorization + ", reportSource=" + reportSource + ", protocol='" + protocol
               + '\'' + ", language='" + language + '\'' + ", releaseTime=" + releaseTime + ", publisher='" + publisher
               + '\'' + ", memo='" + memo + '\'' + ", assetSoftwareRelationId='" + assetSoftwareRelationId + '\''
               + ", buyDate=" + buyDate + ", manualDocUrl='" + manualDocUrl + '\'' + ", manualDocName='" + manualDocName
               + '\'' + '}';
    }
}