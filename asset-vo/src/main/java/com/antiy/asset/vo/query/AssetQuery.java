package com.antiy.asset.vo.query;

import java.util.Arrays;
import java.util.List;

import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.common.base.ObjectQuery;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.utils.ParamterExceptionUtils;
import com.antiy.common.validation.ObjectValidator;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.collections.CollectionUtils;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

/**
 * <p> Asset 查询条件 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetQuery extends ObjectQuery implements ObjectValidator {
    /**
     * 资产id列表
     */
    @Encode
    private String[]     ids;

    /**
     * 综合查询条件
     */
    @ApiModelProperty("综合查询条件")
    @Size(max = 30, message = "综合查询条件不能超过30")
    private String       multipleQuery;

    /**
     * 资产名称
     */
    @ApiModelProperty("资产名称")
    private String       name;
    /**
     * 资产名称,重复判读使用
     */

    private String       assetName;
    /**
     * ip,重复判读使用
     */

    private String       ip;
    /**
     * 是否网络设备,重复判读使用
     */

    private Integer      isNet;
    /**
     * 是否安全设备,重复判读使用
     */

    private Integer      isSafety;

    /**
     * 资产编号
     */
    @ApiModelProperty("资产编号")
    private String       number;
    /**
     * 序列号
     */
    @ApiModelProperty("序列号")
    private String       serial;
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

    @ApiModelProperty("二级品类")
    /**
     * 行政区划主键列表
     */
    @Encode
    private String[]     areaIds;
    /**
     * 厂商
     */
    @ApiModelProperty("厂商")
    private String       manufacturer;
    /**
     * 厂商列表
     */
    @ApiModelProperty("厂商列表")
    private List<String> manufacturers;

    @ApiModelProperty(value = "排除的厂商列表")
    private List<String> removeManufacturers;

    public List<String> getRemoveManufacturers() {
        return removeManufacturers;
    }

    public void setRemoveManufacturers(List<String> removeManufacturers) {
        this.removeManufacturers = removeManufacturers;
    }

    /**
     * 资产状态：1-待登记，2-不予登记，3-待配置，4-待验证，5-待入网，6待检查，7-已入网，8-待退役，9-已退役
     */
    @ApiModelProperty("资产状态：1-待登记，2-不予登记，3-待配置，4-待验证，5-待入网，6待检查，7-已入网，8-待退役，9-已退役")
    @Max(value = 9,message = "资产状态最大为9")
    @Max(value = 1,message = "资产状态最小为1")
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
    private String        responsibleUserId;

    /**
     * 上报来源,1-自动上报，2-人工上报
     */
    @ApiModelProperty("上报来源,1-自动上报，2-人工上报")
    @Max(message = "上报来源最大为2", value = 2)
    @Min(message = "上报来源最小为1", value = 1)
    private Integer       assetSource;
    /**
     * 0-不重要(not_major),1- 一般(general),3-重要(major),
     */
    @ApiModelProperty("0-不重要(not_major),1- 一般(general),3-重要(major),")
    @Max(message = "重要程度最大为3", value = 3)
    @Min(message = "重要程度最小为1", value = 1)
    private Integer       importanceDegree;

    /**
     * 父类资源Id
     */
    @ApiModelProperty("父类资源Id")
    @Encode
    private String        parentId;

    /**
     * 资产状态
     */
    @ApiModelProperty("资产状态[列表]")
    private List<Integer> assetStatusList;

    /**
     * 资产组id
     */
    @ApiModelProperty("资产组id")
    @Encode
    private String        assetGroup;
    /**
     * 时间类型（1：发现。2：入网，3：到期）
     */
    @ApiModelProperty("时间类型（1：发现。2：入网。3：到期）")
    @Max(message = "时间类型最大为3", value = 3)
    @Min(message = "时间类型最小为1", value = 1)
    private Integer       timeType;

    @Encode
    @ApiModelProperty(value = "软件资产Id")
    private String        softwareId;
    /**
     * 资产准入状态
     */
    @ApiModelProperty("准入状态，1待设置，2已允许，3已禁止")
    @Max(message = "准入状态最大为3", value = 3)
    @Min(message = "准入状态最小为1", value = 1)
    private Integer       admittanceStatus;

    /**
     * 资产准入状态
     */
    @ApiModelProperty("责任人")
    private String        responsibleUserName;

    /**
     * 创建时间
     */
    private Long          gmtCreate;
    /**
     * 首次入网时间
     */
    @ApiModelProperty("首次入网时间")
    private Long          firstEnterNett;
    @ApiModelProperty("开始时间")
    private Long          beginTime;
    @ApiModelProperty("结束时间")
    private Long          endTime;

    @ApiModelProperty(value = "是否查询漏洞个数,true 表示查询，false表示不查询", allowableValues = "true,false")
    private Boolean       queryVulCount;

    @ApiModelProperty(value = "是否查询补丁个数,true 表示查询，false表示不查询", allowableValues = "true,false")
    private Boolean       queryPatchCount;

    @ApiModelProperty(value = "是否查询告警个数，true表示查询，false表示不查询", allowableValues = "true,false")
    private Boolean       queryAlarmCount;

    @ApiModelProperty(value = "是否资产组关联资产查询，true表示查询，false表示不查询", allowableValues = "true,false")
    private Boolean       associateGroup;

    @ApiModelProperty(value = "资产组ID")
    @Encode(message = "资产组ID解密失败")
    private String        groupId;
    /**
     * 需要排除的id
     */
    private Integer       exceptId;
    /**
     * 维护方式（安装方式）
     */
    @ApiModelProperty("维护方式（安装方式）1:人工,2:自动")
    @Max(message = "维护方式最大为2", value = 2)
    @Min(message = "维护方式最小为1", value = 1)
    private String        installType;

    public Integer getExceptId() {
        return exceptId;
    }

    public void setExceptId(Integer exceptId) {
        this.exceptId = exceptId;
    }

    private List<String> existAssociateIds;

    public List<String> getExistAssociateIds() {
        return existAssociateIds;
    }

    public void setExistAssociateIds(List<String> existAssociateIds) {
        this.existAssociateIds = existAssociateIds;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Boolean getAssociateGroup() {
        return associateGroup;
    }

    public void setAssociateGroup(Boolean associateGroup) {
        this.associateGroup = associateGroup;
    }

    public Boolean getQueryVulCount() {
        return queryVulCount;
    }

    public void setQueryVulCount(Boolean queryVulCount) {
        this.queryVulCount = queryVulCount;
    }

    public Boolean getQueryPatchCount() {
        return queryPatchCount;
    }

    public void setQueryPatchCount(Boolean queryPatchCount) {
        this.queryPatchCount = queryPatchCount;
    }

    public Boolean getQueryAlarmCount() {
        return queryAlarmCount;
    }

    public void setQueryAlarmCount(Boolean queryAlarmCount) {
        this.queryAlarmCount = queryAlarmCount;
    }

    public List<String> getManufacturers() {
        return manufacturers;
    }

    public void setManufacturers(List<String> manufacturers) {
        this.manufacturers = manufacturers;
    }

    @ApiModelProperty(value = "是否从控制台进入，默认false 不从控制台进入，true为控制台进入")
    private Boolean enterControl = false;

    public Boolean getEnterControl() {
        return enterControl;
    }

    public void setEnterControl(Boolean enterControl) {
        this.enterControl = enterControl;
    }

    public Long getFirstEnterNett() {
        return firstEnterNett;
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

    public void setFirstEnterNett(Long firstEnterNett) {
        this.firstEnterNett = firstEnterNett;
    }

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

    public String getAssetGroup() {
        return assetGroup;
    }

    public void setAssetGroup(String assetGroup) {
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

    public String getResponsibleUserName() {
        return responsibleUserName;
    }

    public void setResponsibleUserName(String responsibleUserName) {
        this.responsibleUserName = responsibleUserName;
    }

    @Override
    public String toString() {
        return "AssetQuery{" + "ids=" + Arrays.toString(ids) + ", multipleQuery='" + multipleQuery + '\'' + ", name='"
               + name + '\'' + ", assetName='" + assetName + '\'' + ", ip='" + ip + '\'' + ", isNet=" + isNet
               + ", isSafety=" + isSafety + ", number='" + number + '\'' + ", serial='" + serial + '\''
               + ", categoryModel='" + categoryModel + '\'' + ", categoryModels=" + Arrays.toString(categoryModels)
               + ", areaIds=" + Arrays.toString(areaIds) + ", manufacturer='" + manufacturer + '\''
               + ", manufacturers=" + manufacturers + ", removeManufacturers=" + removeManufacturers + ", assetStatus="
               + assetStatus + ", operationSystem='" + operationSystem + '\'' + ", uuid='" + uuid + '\''
               + ", responsibleUserId='" + responsibleUserId + '\'' + ", assetSource=" + assetSource
               + ", importanceDegree=" + importanceDegree + ", parentId='" + parentId + '\'' + ", assetStatusList="
               + assetStatusList + ", assetGroup='" + assetGroup + '\'' + ", timeType=" + timeType + ", softwareId='"
               + softwareId + '\'' + ", admittanceStatus=" + admittanceStatus + ", responsibleUserName='"
               + responsibleUserName + '\'' + ", gmtCreate=" + gmtCreate + ", firstEnterNett=" + firstEnterNett
               + ", beginTime=" + beginTime + ", endTime=" + endTime + ", queryVulCount=" + queryVulCount
               + ", queryPatchCount=" + queryPatchCount + ", queryAlarmCount=" + queryAlarmCount + ", associateGroup="
               + associateGroup + ", groupId='" + groupId + '\'' + ", exceptId=" + exceptId + ", existAssociateIds="
               + existAssociateIds + ", enterControl=" + enterControl + '}';
    }

    @Override
    public void validate() throws RequestParamValidateException {
        if (timeType != null) {
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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getIsNet() {
        return isNet;
    }

    public void setIsNet(Integer isNet) {
        this.isNet = isNet;
    }

    public Integer getIsSafety() {
        return isSafety;
    }

    public void setIsSafety(Integer isSafety) {
        this.isSafety = isSafety;
    }

    public String getInstallType() {
        return installType;
    }

    public void setInstallType(String installType) {
        this.installType = installType;
    }
}
