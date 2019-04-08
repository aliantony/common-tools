package com.antiy.asset.vo.query;

import java.util.List;

import com.antiy.common.base.ObjectQuery;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.utils.ParamterExceptionUtils;
import com.antiy.common.validation.ObjectValidator;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetSoftware 查询条件 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetSoftwareQuery extends ObjectQuery implements ObjectValidator {
    @Encode
    private String        id;
    /**
     * 操作系统(WINDTO;WS7-32-64,WINDTO;WS8-64)
     */
    @ApiModelProperty("操作系统(WINDTO;WS7-32-64,WINDTO;WS8-64)")
    private String        operationSystem;
    /**
     * 软件品类
     */
    @ApiModelProperty("软件品类")
    @Encode
    private String        categoryModel;

    @ApiModelProperty("综合查询")
    private String        multipleQuery;

    @ApiModelProperty("软件品类型号列表")
    @Encode
    private String[]      categoryModels;
    /**
     * 序列号
     */
    @ApiModelProperty(value = "序列号")
    private String        serial;

    @ApiModelProperty("区域id列表")
    @Encode
    private String[]      areaIds;
    /**
     * 软件名称
     */
    @ApiModelProperty("软件名称")
    private String        name;

    private String        assetName;

    /**
     * 版本
     */
    @ApiModelProperty("版本")
    private String        version;
    /**
     * 厂商
     */
    @ApiModelProperty("厂商")
    private String        manufacturer;

    /**
     * 软件标签
     */
    @ApiModelProperty("软件标签")
    private Integer       softwareLabel;
    /**
     * 1待登记2待分析3可安装4已退役5不予登记
     */
    @ApiModelProperty("1待登记2待分析3可安装4已退役5不予登记")
    private Integer       softwareStatus;

    @ApiModelProperty("1待登记2待分析3可安装4已退役5不予登记")
    private List<Integer> softwareStatusList;

    /**
     * 0-免费软件，1-商业软件
     */
    @ApiModelProperty("0-免费软件，1-商业软件")
    private Integer       authorization;
    /**
     * 上报来源:1-自动上报，2-人工上报
     */
    @ApiModelProperty("上报来源:1-自动上报，2-人工上报")
    private Integer       reportSource;
    /**
     * 端口
     */
    @ApiModelProperty("端口")
    private String        port;
    /**
     * 语言
     */
    @ApiModelProperty("语言")
    private String        language;
    /**
     * 发布时间
     */
    @ApiModelProperty("发布时间")
    private Long          releaseTime;
    @ApiModelProperty("开始时间")
    private Long          beginTime;
    @ApiModelProperty("结束时间")
    private Long          endTime;
    /**
     * 发布者
     */
    @ApiModelProperty("发布者")
    private String        publisher;

    /**
     * 安装方式
     */
    @ApiModelProperty("安装方式")
    private Integer       installType;



    /**
     * 安装状态
     */
    @ApiModelProperty("安装状态")
    private Integer       installStatus;
    /**
     * 状态,0 未删除,1已删除
     */
    @ApiModelProperty("状态,0 未删除,1已删除")
    private Integer       status;

    @ApiModelProperty(value = "是否查询关联资产数量")
    private Boolean       queryAssetCount = false;

    @ApiModelProperty(value = "资产组")
    private String        assetGroup;
    @ApiModelProperty(value = "硬件编号")
    private String        number;
    @ApiModelProperty(value = "资产id")
    private String        assetId;

    @ApiModelProperty(value = "1表示查询发布时间，2表示查询到期时间")
    private Integer       queryTime;

    @ApiModelProperty(value = "软件资产Id列表")
    private List<String>  ids;

    @ApiModelProperty(value = "流程引擎当前代办任务Id")
    private List<String>  taskBussinessIds;

    @ApiModelProperty(value = "是否从控制台进入，默认false 不从控制台进入，true为控制台进入")
    private Boolean       enterControl    = false;

    public Boolean getEnterControl() {
        return enterControl;
    }

    public void setEnterControl(Boolean enterControl) {
        this.enterControl = enterControl;
    }

    public List<String> getTaskBussinessIds() {
        return taskBussinessIds;
    }

    public void setTaskBussinessIds(List<String> taskBussinessIds) {
        this.taskBussinessIds = taskBussinessIds;
    }

    public Integer getInstallStatus() {
        return installStatus;
    }

    public void setInstallStatus(Integer installStatus) {
        this.installStatus = installStatus;
    }

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public Integer getQueryTime() {
        return queryTime;
    }

    public void setQueryTime(Integer queryTime) {
        this.queryTime = queryTime;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getInstallType() {
        return installType;
    }

    public void setInstallType(Integer installType) {
        this.installType = installType;
    }

    @Override
    public Long getBeginTime() {
        return beginTime;
    }

    @Override
    public void setBeginTime(Long beginTime) {
        this.beginTime = beginTime;
    }

    @Override
    public Long getEndTime() {
        return endTime;
    }

    @Override
    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAssetGroup() {
        return assetGroup;
    }

    public void setAssetGroup(String assetGroup) {
        this.assetGroup = assetGroup;
    }

    public Boolean getQueryAssetCount() {
        return queryAssetCount;
    }

    public void setQueryAssetCount(Boolean queryAssetCount) {
        this.queryAssetCount = queryAssetCount;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCategoryModel() {
        return categoryModel;
    }

    public void setCategoryModel(String categoryModel) {
        this.categoryModel = categoryModel;
    }

    public String[] getCategoryModels() {
        return categoryModels;
    }

    public void setCategoryModels(String[] categoryModels) {
        this.categoryModels = categoryModels;
    }

    public String[] getAreaIds() {
        return areaIds;
    }

    public void setAreaIds(String[] areaIds) {
        this.areaIds = areaIds;
    }

    public List<Integer> getSoftwareStatusList() {
        return softwareStatusList;
    }

    public void setSoftwareStatusList(List<Integer> softwareStatusList) {
        this.softwareStatusList = softwareStatusList;
    }

    public String getMultipleQuery() {
        return multipleQuery;
    }

    public void setMultipleQuery(String multipleQuery) {
        this.multipleQuery = multipleQuery;
    }


    @Override
    public void validate() throws RequestParamValidateException {
        if (queryTime != null) {
            ParamterExceptionUtils.isNull(beginTime, "开始时间为空");
            ParamterExceptionUtils.isNull(endTime, "结束时间为空");
            ParamterExceptionUtils.isTrue(endTime > beginTime, "结束时间必须大于开始时间");
        }
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }
}