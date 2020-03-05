package com.antiy.asset.vo.response;

import com.antiy.asset.vo.enums.AssetCategoryEnum;
import com.antiy.asset.vo.enums.AssetSourceEnum;
import com.antiy.asset.vo.enums.InstallType;
import com.antiy.asset.vo.request.AssetCustomizeRequest;
import com.antiy.common.encoder.Encode;
import com.antiy.common.utils.JsonUtil;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * <p> AssetResponse 响应对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetResponse extends BaseResponse {

    /**
     * 业务名称
     */
    @ApiModelProperty("业务名称")
    private String businessName;
    /**
     * 资产编号
     */
    @ApiModelProperty("资产编号")
    private String                         number;
    @ApiModelProperty("stepNode")
    private String                         stepNode;
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
     * ips
     */
    @ApiModelProperty("ips用于列表展示")
    private String                         ips;
    /**
     * mac
     */
    @ApiModelProperty("mac用于列表展示")
    private String                         macs;
    /**
     * 序列号
     */
    @ApiModelProperty("序列号")
    private String                         serial;
    /**
     * 品类
     */
    @ApiModelProperty("品类")
    private Integer                        categoryModel;
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
     * 品类名称
     */
    @ApiModelProperty("版本")
    private String                         version;
    /**
     * 厂商
     */
    @ApiModelProperty("厂商")
    private String                         manufacturer;

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    /**
     * 资产状态：1-待登记，2-不予登记，3-待配置，4-待验证，5-待入网，6待检查，7-已入网，8-待退役，9-已退役
     */
    @ApiModelProperty("资产状态")
    private Integer                        assetStatus;
    /**
     * 操作系统,如果type为IDS或者IPS则此字段存放软件版本信息
     */
    @ApiModelProperty("操作系统")
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
     * 配置模板id
     */
    @ApiModelProperty("配置模板id")
    private String                         baselineTemplateId;
    /**
     * 配置模板名称
     */
    @ApiModelProperty("配置模板名称")
    private String                         baselineTemplateName;
    /**
     * 装机模板id
     */
    @ApiModelProperty("装机模板id")
    @Encode
    private String                         installTemplateId;
    /**
     * 装机模板id
     */
    @ApiModelProperty("不加密的装机模板id")
    private String                         decryptInstallTemplateId;

    /**
     * 上报来源
     */
    @ApiModelProperty("上报来源")
    private Integer                        assetSource;
    /**
     * 上报来源名称
     */
    @ApiModelProperty("上报来源名称")
    private String                         assetSourceName;
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
    @ApiModelProperty("联系电话")
    private String                         contactTel;
    /**
     * 资产准入状态
     */
    @ApiModelProperty("资产准入状态:1已允许，2已禁止")
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

    @ApiModelProperty(value = "配置模板关联时间")
    private Long                           baselineTemplateCorrelationGmt;

    @ApiModelProperty(value = "装机模板关联时间")
    private Long                           installTemplateCorrelationGmt;

    @ApiModelProperty(value = "解密id")
    private String                         decryptId;

    @ApiModelProperty(value = "上一步状态")
    private Integer                        originStatus;

    @ApiModelProperty("网络连接状态：1-在线，2-离线，3-未知")
    private Integer netStatus;
    @ApiModelProperty("物理位置")
    private String location;
    @ApiModelProperty("从属业务集合")
    private List<AssetBusinessResponse> dependentBusiness;
    @ApiModelProperty("是否孤岛设备：1是,0否")
    private Integer           isOrphan;
    @ApiModelProperty("自定义字段")
    private List<AssetCustomizeRequest> customField;

    public List<AssetCustomizeRequest> getCustomField() {
        return customField;
    }

    public void setCustomField(String customField) {
        this.customField = JsonUtil.json2List(customField,AssetCustomizeRequest.class);
    }

    public Integer getIsOrphan() {
        return isOrphan;
    }

    public void setIsOrphan(Integer isOrphan) {
        this.isOrphan = isOrphan;
    }

    public Integer getNetStatus() {
        return netStatus;
    }

    public void setNetStatus(Integer netStatus) {
        this.netStatus = netStatus;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<AssetBusinessResponse> getDependentBusiness() {
        return dependentBusiness;
    }

    public void setDependentBusiness(List<AssetBusinessResponse> dependentBusiness) {
        this.dependentBusiness = dependentBusiness;
    }

    public Integer getOriginStatus() {
        return originStatus;
    }

    public void setOriginStatus(Integer originStatus) {
        this.originStatus = originStatus;
    }

    public String getBaselineTemplateName() {
        return baselineTemplateName;
    }

    public void setBaselineTemplateName(String baselineTemplateName) {
        this.baselineTemplateName = baselineTemplateName;
    }

    public String getAssetSourceName() {
        return assetSourceName;
    }

    public void setAssetSourceName(String assetSourceName) {
        this.assetSourceName = AssetSourceEnum.getNameByCode(assetSource);
    }

    public String getIps() {
        return ips;
    }

    public String getBaselineTemplateId() {
        return baselineTemplateId;
    }

    public void setBaselineTemplateId(String baselineTemplateId) {
        this.baselineTemplateId = baselineTemplateId;
    }

    public String getInstallTemplateId() {
        return installTemplateId;
    }

    public void setInstallTemplateId(String installTemplateId) {
        this.installTemplateId = installTemplateId;
    }

    public void setIps(String ips) {
        this.ips = ips;
    }

    public String getMacs() {
        return macs;
    }

    public void setMacs(String macs) {
        this.macs = macs;
    }

    public String getOperationSystemNotice() {
        return operationSystemNotice;
    }

    public void setOperationSystemNotice(String operationSystemNotice) {
        this.operationSystemNotice = operationSystemNotice;
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

    public String getDecryptInstallTemplateId() {
        return decryptInstallTemplateId;
    }

    public void setDecryptInstallTemplateId(String decryptInstallTemplateId) {
        this.decryptInstallTemplateId = decryptInstallTemplateId;
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
        this.categoryModelName = AssetCategoryEnum.getNameByCode(categoryModel);
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

    public Integer getCategoryModel() {
        return categoryModel;
    }

    public void setCategoryModel(Integer categoryModel) {
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

    public Long getBaselineTemplateCorrelationGmt() {
        return baselineTemplateCorrelationGmt;
    }

    public void setBaselineTemplateCorrelationGmt(Long baselineTemplateCorrelationGmt) {
        this.baselineTemplateCorrelationGmt = baselineTemplateCorrelationGmt;
    }

    public Long getInstallTemplateCorrelationGmt() {
        return installTemplateCorrelationGmt;
    }

    public void setInstallTemplateCorrelationGmt(Long installTemplateCorrelationGmt) {
        this.installTemplateCorrelationGmt = installTemplateCorrelationGmt;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }

    public String getDecryptId() {
        return decryptId;
    }

    public void setDecryptId(String decryptId) {
        this.decryptId = decryptId;
    }

    @Override
    public String toString() {
        return "AssetResponse{" + "number='" + number + '\'' + ", name='" + name + '\'' + ", assetGroup='" + assetGroup
               + '\'' + ", assetGroups=" + assetGroups + ", ip=" + ip + ", mac=" + mac + ", ips='" + ips + '\''
               + ", macs='" + macs + '\'' + ", serial='" + serial + '\'' + ", categoryModel=" + categoryModel
               + ", categoryType=" + categoryType + ", categoryModelName='" + categoryModelName + '\'' + ", version='"
               + version + '\'' + ", manufacturer='" + manufacturer + '\'' + ", assetStatus=" + assetStatus
               + ", operationSystem='" + operationSystem + '\'' + ", operationSystemName='" + operationSystemName + '\''
               + ", operationSystemNotice='" + operationSystemNotice + '\'' + ", uuid='" + uuid + '\''
               + ", responsibleUserId='" + responsibleUserId + '\'' + ", responsibleUserName='" + responsibleUserName
               + '\'' + ", baselineTemplateId='" + baselineTemplateId + '\'' + ", installTemplateId='"
               + installTemplateId + '\'' + ", assetSource=" + assetSource + ", importanceDegree=" + importanceDegree
               + ", describle='" + describle + '\'' + ", serviceLife=" + serviceLife + ", contactTel='" + +'\''
               + ", buyDate=" + buyDate + ", warranty='" + warranty + '\'' + ", admittanceStatus=" + admittanceStatus
               + ", gmtCreate=" + gmtCreate + ", firstEnterNett=" + firstEnterNett + ", areaId='" + areaId + '\''
               + ", areaName='" + areaName + '\'' + ", houseLocation='" + houseLocation + '\'' + ", installType="
               + installType + ", installTypeName='" + installTypeName + '\'' + ", waitingTaskReponse="
               + waitingTaskReponse + ", vulCount='" + vulCount + '\'' + ", patchCount='" + patchCount + '\''
               + ", alarmCount='" + alarmCount + '\'' + '}';
    }

    public String getStepNode() {
        return stepNode;
    }

    public void setStepNode(String stepNode) {
        this.stepNode = stepNode;
    }
}