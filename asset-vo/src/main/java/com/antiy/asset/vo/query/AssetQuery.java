package com.antiy.asset.vo.query;

import com.antiy.common.base.ObjectQuery;
import com.antiy.common.encoder.Encode;
import io.swagger.annotations.ApiModelProperty;

import java.util.Arrays;
import java.util.List;

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
     * 资产名称
     */
    @ApiModelProperty("资产编号")
    private String        number;
    /**
     * 序列号
     */
    @ApiModelProperty("序列号")
    private String        serial;
    /**
     * 品类型号
     */
    @ApiModelProperty("品类型号")
    @Encode
    private String       categoryModel;
    /**
     * 品类型号列表
     */
    @ApiModelProperty("品类型号列表")
    @Encode
    private String[]     categoryModels;
    /**
     * 行政区划主键列表
     */
    @Encode
    private String[]     areaIds;
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
    @Encode
    private String       responsibleUserId;

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
    @Encode
    private String       parentId;

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
    /**
     * 创建时间
     */
    private Long              gmtCreate;

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

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

    public String getResponsibleUserId() {
        return responsibleUserId;
    }

    public void setResponsibleUserId(String responsibleUserId) {
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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "AssetQuery{" +
                "ids=" + Arrays.toString(ids) +
                ", multipleQuery='" + multipleQuery + '\'' +
                ", name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", serial='" + serial + '\'' +
                ", categoryModel='" + categoryModel + '\'' +
                ", categoryModels=" + Arrays.toString(categoryModels) +
                ", areaIds=" + Arrays.toString(areaIds) +
                ", manufacturer='" + manufacturer + '\'' +
                ", assetStatus=" + assetStatus +
                ", operationSystem='" + operationSystem + '\'' +
                ", uuid='" + uuid + '\'' +
                ", responsibleUserId='" + responsibleUserId + '\'' +
                ", assetSource=" + assetSource +
                ", importanceDegree=" + importanceDegree +
                ", parentId='" + parentId + '\'' +
                ", isInnet=" + isInnet +
                ", assetStatusList=" + assetStatusList +
                ", assetGroup=" + assetGroup +
                ", timeType=" + timeType +
                ", softwareId='" + softwareId + '\'' +
                ", admittanceStatus=" + admittanceStatus +
                ", gmtCreate=" + gmtCreate +
                '}';
    }
}
