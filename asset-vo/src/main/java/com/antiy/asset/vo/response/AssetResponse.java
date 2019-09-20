package com.antiy.asset.vo.response;

import java.util.List;

import com.antiy.asset.vo.enums.InstallType;
import com.antiy.common.encoder.Encode;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetResponse 响应对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetResponse extends BaseResponse {
    /**
     * 资产编号
     */
    @ApiModelProperty("资产编号")
    private String                         number;
    /**
     * 资产名称
     */
    @ApiModelProperty("资产名称")
    private String                         name;
    /**
     * 资产组
     */
    @ApiModelProperty("资产组")
    private String                         assetGroup;
    /**
     * 资产组列表
     */
    @ApiModelProperty("资产组列表")
    private List<AssetGroupResponse>       assetGroups;
    /**
     * ip
     */
    @ApiModelProperty("ip")
    private List<AssetIpRelationResponse>  ip;
    /**
     * mac
     */
    @ApiModelProperty("mac")
    private List<AssetMacRelationResponse> mac;
    /**
     * 序列号
     */
    @ApiModelProperty("序列号")
    private String                         serial;
    /**
     * 品类
     */
    @ApiModelProperty("品类")
    @Encode
    private String                         categoryModel;
    /**
     * 设备类型
     */
    @ApiModelProperty("设备类型")
    private CategoryType                   categoryType;

    /**
     * 品类名称
     */
    @ApiModelProperty("品类名称")
    private String                         categoryModelName;
    /**
     * 厂商
     */
    @ApiModelProperty("厂商")
    private String                         manufacturer;
    /**
     * 资产状态：1-待登记，2-不予登记，3-待配置，4-待验证，5-待入网，6待检查，7-已入网，8-待退役，9-已退役
     */
    @ApiModelProperty("资产状态")
    private Integer                        assetStatus;
    /**
     * 操作系统,如果type为IDS或者IPS则此字段存放软件版本信息
     */
    @ApiModelProperty("操作系统")
    @Encode
    private String                         operationSystem;

    /**
     * 操作系统名
     */
    @ApiModelProperty("操作系统名")
    private String                         operationSystemName;
    /**
     * 操作系统提示
     */
    @ApiModelProperty("操作系统提示")
    private String                         operationSystemNotice;

    /**
     * 设备uuid
     */
    @ApiModelProperty("设备uuid")
    private String                         uuid;
    /**
     * 责任人主键
     */
    @ApiModelProperty("责任人主键")
    @Encode
    private String                         responsibleUserId;
    /**
     * 责任人名称
     */
    @ApiModelProperty("责任人名称")
    private String                         responsibleUserName;


    /**
     * 上报来源,1-自动上报，2-人工上报
     */
    @ApiModelProperty("上报来源")
    private Integer                        assetSource;
    /**
     * 1核心2重要3一般
     */
    @ApiModelProperty("1核心2重要3一般")
    private Integer                        importanceDegree;
    /**
     * 描述
     */
    @ApiModelProperty("描述")
    private String                         describle;

    /**
     * 使用到期时间
     */
    @ApiModelProperty("使用到期时间")
    private Long                           serviceLife;
    /**
     * 制造日期
     */
    @ApiModelProperty("制造日期")
    private Long                           buyDate;
    /**
     * 保修期
     */
    @ApiModelProperty("保修期")
    private String                         warranty;
    /**
     * 资产准入状态
     */
    @ApiModelProperty("资产准入状态:待设置，2已允许，3已禁止")
    private Integer                        admittanceStatus;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Long                           gmtCreate;

    /**
     * 首次入网时间
     */
    @ApiModelProperty("首次入网时间")
    private Long                           firstEnterNett;
    /**
     * 首次入网时间
     */
    @ApiModelProperty("首次发现时间")
    private Long                           firstDiscoverTime;
    /**
     * 行政区划主键
     */
    @ApiModelProperty("行政区划主键")
    @Encode
    private String                         areaId;
    /**
     * 行政区划名称
     */
    @ApiModelProperty("行政区划名称")
    private String                         areaName;

    /**
     * 机房位置
     */
    @ApiModelProperty("机房位置")
    private String                         houseLocation;
    /**
     * 安装方式1人工2自动
     */
    @ApiModelProperty("安装方式1人工2自动")
    private Integer                        installType;
    /**
     * 安装方式1人工2自动
     */
    @ApiModelProperty("安装方式1人工2自动")
    private String                         installTypeName;
    /**
     * 资产流程信息
     */
    @ApiModelProperty("资产流程信息")
    private WaitingTaskReponse             waitingTaskReponse;

    @ApiModelProperty(value = "漏洞个数")
    private String                         vulCount;

    @ApiModelProperty(value = "补丁个数")
    private String                         patchCount;

    @ApiModelProperty(value = "告警个数")
    private String                         alarmCount;


    public String getOperationSystemNotice() {
        return operationSystemNotice;
    }

    public void setOperationSystemNotice(String operationSystemNotice) {
        this.operationSystemNotice = operationSystemNotice;
    }

    public Long getFirstDiscoverTime() {
        return firstDiscoverTime;
    }

    public void setFirstDiscoverTime(Long firstDiscoverTime) {
        this.firstDiscoverTime = firstDiscoverTime;
    }



    public String getInstallTypeName() {
        return installTypeName;
    }

    public void setInstallTypeName(String installTypeName) {
        this.installTypeName = InstallType.getInstallTypeByCode(installType) != null
            ? InstallType.getInstallTypeByCode(installType).getStatus()
            : "";
    }

    public String getVulCount() {
        return vulCount;
    }

    public void setVulCount(String vulCount) {
        this.vulCount = vulCount;
    }

    public String getPatchCount() {
        return patchCount;
    }

    public void setPatchCount(String patchCount) {
        this.patchCount = patchCount;
    }

    public String getAlarmCount() {
        return alarmCount;
    }

    public void setAlarmCount(String alarmCount) {
        this.alarmCount = alarmCount;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getInstallType() {
        return installType;
    }

    public void setInstallType(Integer installType) {
        this.installType = installType;
    }

    public WaitingTaskReponse getWaitingTaskReponse() {
        return waitingTaskReponse;
    }

    public void setWaitingTaskReponse(WaitingTaskReponse waitingTaskReponse) {
        this.waitingTaskReponse = waitingTaskReponse;
    }

    public String getDescrible() {
        return describle;
    }

    public void setDescrible(String describle) {
        this.describle = describle;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getResponsibleUserName() {
        return responsibleUserName;
    }

    public void setResponsibleUserName(String responsibleUserName) {
        this.responsibleUserName = responsibleUserName;
    }

    public Integer getAdmittanceStatus() {
        return admittanceStatus;
    }

    public void setAdmittanceStatus(Integer admittanceStatus) {
        this.admittanceStatus = admittanceStatus;
    }

    public String getCategoryModelName() {
        return categoryModelName;
    }

    public void setCategoryModelName(String categoryModelName) {
        this.categoryModelName = categoryModelName;
    }

    public String getAssetGroup() {
        return assetGroup;
    }

    public void setAssetGroup(String assetGroup) {
        this.assetGroup = assetGroup;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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

    public String getCategoryModel() {
        return categoryModel;
    }

    public void setCategoryModel(String categoryModel) {
        this.categoryModel = categoryModel;
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


    public Long getServiceLife() {
        return serviceLife;
    }

    public void setServiceLife(Long serviceLife) {
        this.serviceLife = serviceLife;
    }

    public Long getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Long buyDate) {
        this.buyDate = buyDate;
    }

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    public List<AssetGroupResponse> getAssetGroups() {
        return assetGroups;
    }

    public void setAssetGroups(List<AssetGroupResponse> assetGroups) {
        this.assetGroups = assetGroups;
    }

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Long getFirstEnterNett() {
        return firstEnterNett;
    }

    public void setFirstEnterNett(Long firstEnterNett) {
        this.firstEnterNett = firstEnterNett;
    }

    public String getHouseLocation() {
        return houseLocation;
    }

    public void setHouseLocation(String houseLocation) {
        this.houseLocation = houseLocation;
    }

    public String getOperationSystemName() {
        return operationSystemName;
    }

    public void setOperationSystemName(String operationSystemName) {
        this.operationSystemName = operationSystemName;
    }

    public CategoryType getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(CategoryType categoryType) {
        this.categoryType = categoryType;
    }

    public List<AssetIpRelationResponse> getIp() {
        return ip;
    }

    public void setIp(List<AssetIpRelationResponse> ip) {
        this.ip = ip;
    }

    public List<AssetMacRelationResponse> getMac() {
        return mac;
    }

    public void setMac(List<AssetMacRelationResponse> mac) {
        this.mac = mac;
    }

    @Override
    public String toString() {
        return "AssetResponse{" +
                "number='" + number + '\'' +
                ", name='" + name + '\'' +
                ", assetGroup='" + assetGroup + '\'' +
                ", assetGroups=" + assetGroups +
                ", ip=" + ip +
                ", mac=" + mac +
                ", serial='" + serial + '\'' +
                ", categoryModel='" + categoryModel + '\'' +
                ", categoryType=" + categoryType +
                ", categoryModelName='" + categoryModelName + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", assetStatus=" + assetStatus +
                ", operationSystem='" + operationSystem + '\'' +
                ", operationSystemName='" + operationSystemName + '\'' +
                ", operationSystemNotice='" + operationSystemNotice + '\'' +
                ", uuid='" + uuid + '\'' +
                ", responsibleUserId='" + responsibleUserId + '\'' +
                ", responsibleUserName='" + responsibleUserName + '\'' +
                ", assetSource=" + assetSource +
                ", importanceDegree=" + importanceDegree +
                ", describle='" + describle + '\'' +
                ", serviceLife=" + serviceLife +
                ", buyDate=" + buyDate +
                ", warranty='" + warranty + '\'' +
                ", admittanceStatus=" + admittanceStatus +
                ", gmtCreate=" + gmtCreate +
                ", firstEnterNett=" + firstEnterNett +
                ", firstDiscoverTime=" + firstDiscoverTime +
                ", areaId='" + areaId + '\'' +
                ", areaName='" + areaName + '\'' +
                ", houseLocation='" + houseLocation + '\'' +
                ", installType=" + installType +
                ", installTypeName='" + installTypeName + '\'' +
                ", waitingTaskReponse=" + waitingTaskReponse +
                ", vulCount='" + vulCount + '\'' +
                ", patchCount='" + patchCount + '\'' +
                ", alarmCount='" + alarmCount + '\'' +
                '}';
    }
}