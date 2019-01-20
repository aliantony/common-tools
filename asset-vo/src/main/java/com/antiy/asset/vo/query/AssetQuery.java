package com.antiy.asset.vo.query;

import java.util.List;

import com.antiy.common.base.ObjectQuery;
import com.antiy.common.encoder.Encode;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p> Asset 查询条件 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetQuery extends ObjectQuery {
    /**
     * 资产id列表
     */
    @Encode
    private String[]      ids;

    /**
     * 综合查询条件
     */
    @ApiModelProperty("综合查询条件")
    private String        multipleQuery;

    /**
     * 资产名称
     */
    @ApiModelProperty("资产名称")
    private String        name;
    /**
     * 序列号
     */
    @ApiModelProperty("序列号")
    private String        serial;
    /**
     * 品类型号
     */
    @ApiModelProperty("品类型号")
    private Integer       categoryModel;
    /**
     * 品类型号列表
     */
    @ApiModelProperty("品类型号列表")
    private Integer[]     categoryModels;
    /**
     * 行政区划主键列表
     */
    @Encode
    private Integer[]     areaIds;
    /**
     * 厂商
     */
    @ApiModelProperty("厂商")
    private String        manufacturer;
    /**
     * 资产状态：1-待登记，2-不予登记，3-待配置，4-待验证，5-待入网，6-已入网，7-待退役，8-已退役
     */
    @ApiModelProperty("资产状态：1-待登记，2-不予登记，3-待配置，4-待验证，5-待入网，6-已入网，7-待退役，8-已退役")
    private Integer       assetStatus;
    /**
     * 操作系统,如果type为IDS或者IPS则此字段存放软件版本信息
     */
    @ApiModelProperty("操作系统,如果type为IDS或者IPS则此字段存放软件版本信息")
    private String        operationSystem;

    /**
     * 设备uuid
     */
    @ApiModelProperty("设备uuid")
    private String        uuid;
    /**
     * 责任人主键
     */
    @ApiModelProperty("责任人主键")
    private Integer       responsibleUserId;

    /**
     * 上报来源,1-自动上报，2-人工上报
     */
    @ApiModelProperty("上报来源,1-自动上报，2-人工上报")
    private Integer       assetSource;
    /**
     * 0-不重要(not_major),1- 一般(general),3-重要(major),
     */
    @ApiModelProperty("0-不重要(not_major),1- 一般(general),3-重要(major),")
    private Integer       importanceDegree;

    /**
     * 父类资源Id
     */
    @ApiModelProperty("父类资源Id")
    private Integer       parentId;

    /**
     * 是否入网,0表示未入网,1表示入网
     */
    @ApiModelProperty("是否入网,0表示未入网,1表示入网")
    private Boolean       isInnet;

    /**
     * 资产状态
     */
    @ApiModelProperty("资产状态")
    private List<Integer> assetStatusList;

    /**
     * 资产组id
     */
    @ApiModelProperty("资产组id")
    private Integer       assetGroup;
    /**
     * 首次发现时间还是首次入网时间（1：发现。2：入网）
     */
    @ApiModelProperty("首次发现时间还是首次入网时间（1：发现。2：入网）")
    private Integer       timeType;

    @Encode
    @ApiModelProperty(value = "软件资产Id")
    private String        softwareId;

    /**
     * 资产准入状态
     */
    @ApiModelProperty("资产准入状态")
    private Integer admittanceStatus;

    public String getSoftwareId() {
        return softwareId;
    }

    public void setSoftwareId(String softwareId) {
        this.softwareId = softwareId;
    }

    public String[] getIds() {
        return ids;
    }

    public void setIds(String[] ids) {
        this.ids = ids;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public Integer getCategoryModel() {
        return categoryModel;
    }

    public void setCategoryModel(Integer categoryModel) {
        this.categoryModel = categoryModel;
    }

    public Integer[] getCategoryModels() {
        return categoryModels;
    }

    public void setCategoryModels(Integer[] categoryModels) {
        this.categoryModels = categoryModels;
    }

    public Integer[] getAreaIds() {
        return areaIds;
    }

    public void setAreaIds(Integer[] areaIds) {
        this.areaIds = areaIds;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Integer getAssetStatus() {
        return assetStatus;
    }

    public void setAssetStatus(Integer assetStatus) {
        this.assetStatus = assetStatus;
    }

    public String getOperationSystem() {
        return operationSystem;
    }

    public void setOperationSystem(String operationSystem) {
        this.operationSystem = operationSystem;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getResponsibleUserId() {
        return responsibleUserId;
    }

    public void setResponsibleUserId(Integer responsibleUserId) {
        this.responsibleUserId = responsibleUserId;
    }

    public Integer getAssetSource() {
        return assetSource;
    }

    public void setAssetSource(Integer assetSource) {
        this.assetSource = assetSource;
    }

    public Integer getImportanceDegree() {
        return importanceDegree;
    }

    public void setImportanceDegree(Integer importanceDegree) {
        this.importanceDegree = importanceDegree;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Boolean getInnet() {
        return isInnet;
    }

    public void setInnet(Boolean innet) {
        isInnet = innet;
    }

    public List<Integer> getAssetStatusList() {
        return assetStatusList;
    }

    public void setAssetStatusList(List<Integer> assetStatusList) {
        this.assetStatusList = assetStatusList;
    }

    public String getMultipleQuery() {
        return multipleQuery;
    }

    public void setMultipleQuery(String multipleQuery) {
        this.multipleQuery = multipleQuery;
    }

    public Integer getAssetGroup() {
        return assetGroup;
    }

    public void setAssetGroup(Integer assetGroup) {
        this.assetGroup = assetGroup;
    }

    public Integer getTimeType() {
        return timeType;
    }

    public void setTimeType(Integer timeType) {
        this.timeType = timeType;
    }

    public Integer getAdmittanceStatus() {
        return admittanceStatus;
    }

    public void setAdmittanceStatus(Integer admittanceStatus) {
        this.admittanceStatus = admittanceStatus;
    }
}
