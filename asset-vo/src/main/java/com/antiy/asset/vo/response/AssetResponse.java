package com.antiy.asset.vo.response;

import java.util.List;

import com.antiy.asset.vo.enums.*;
import com.antiy.asset.vo.request.AssetCustomizeRequest;
import com.antiy.common.encoder.Encode;
import com.antiy.common.utils.JsonUtil;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetResponse 响应对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetResponse extends BaseResponse {

    @Encode
    @ApiModelProperty("操作记录表id")
    String                                 assetOperationRecordId;
    @ApiModelProperty("报废暂存备注")
    private String                         temporaryInfo;
    /**
     * 厂商名称版本
     */
    @ApiModelProperty("厂商名称版本")
    private String                         mnv;
    /**
     * 业务名称
     */
    @ApiModelProperty("业务名称")
    private String                         businessName;
    @ApiModelProperty("所属组织")
    private String                         departmentName;
    /**
     * 资产编号
     */
    @ApiModelProperty("资产编号")
    private String                         number;
    @ApiModelProperty("stepNode:IN_NET(入网流程),EXCEPTION(异常流程),CHANGE(变更流程),SCRAP(报废流程),RETIRE(退回流程)")
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
     * 从属业务名称集合
     */
    @ApiModelProperty("从属业务名称集合")
    private List<AssetBusinessResponse>    assetBusinessResponse;
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
     * 序列表
     */
    @ApiModelProperty("序列表 ")
    private String                         serial;
    /**
     * 品类
     */
    @ApiModelProperty("品类")
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
     * 品类名称
     */
    @ApiModelProperty("版本")
    private String                         version;
    /**
     * 厂商
     */
    @ApiModelProperty("厂商")
    private String                         manufacturer;
    /**
     * 装机时间
     */
    @ApiModelProperty("装机时间")
    private Long                           installDate;
    /**
     * 启用时间
     */
    @ApiModelProperty("启用时间")
    private Long                           activiateDate;
    /**
     * 到期提醒
     */
    @ApiModelProperty("到期提醒")
    private Long                           expirationReminder;
    /**
     * 网络类型:1红网，2篮网
     */
    @ApiModelProperty("网络类型:1红网，2篮网")
    @Encode
    private String                         netType;
    /**
     * 网络类型名称
     */
    private String                         netTypeName;
    /**
     * 是否借用：1-借用，2-未借用
     */
    @ApiModelProperty("是否可借用：1-可借用，2-不可借用")
    private Integer                        isBorrow;
    /**
     * key
     */
    @ApiModelProperty("key")
    private String                         key;

    /**
     * 资产状态：详情查看AssetStatusEnum
     */
    @ApiModelProperty("资产状态")
    private Integer                        assetStatus;
    /**
     * 操作系统
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
     * 资产来源
     */
    @ApiModelProperty("资产来源")
    private Integer                        assetSource;
    /**
     * 资产来源名称
     */
    @ApiModelProperty("资产来源名称")
    private String                         assetSourceName;
    /**
     * 重要程度
     */
    @ApiModelProperty("重要程度")
    private Integer                        importanceDegree;
    /**
     * 重要程度名称
     */
    @ApiModelProperty("重要程度名称")
    private String                         importanceDegreeName;
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
     * 购买日期
     */
    @ApiModelProperty("购买日期")
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
     * 资产准入状态名称
     */
    @ApiModelProperty("资产准入状态名称")
    private String                         admittanceStatusName;
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
     * 维护方式1人工2自动
     */
    @ApiModelProperty("维护方式1人工2自动")
    private Integer                        installType;
    /**
     * 维护方式名称
     */
    @ApiModelProperty("维护方式名称")
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
    /**
     * 首次发现时间
     */
    @ApiModelProperty(value = "首次发现时间")
    private Long                           firstDiscoverTime;
    @ApiModelProperty(value = "解密id")
    private String                         decryptId;

    @ApiModelProperty(value = "上一步状态")
    private Integer                        originStatus;

    @ApiModelProperty("网络连接状态：1-在线，2-离线，3-未知")
    private Integer                        netStatus;
    @ApiModelProperty("网络连接状态名称")
    private String                         netStatusName;
    @ApiModelProperty("物理位置")
    private String                         location;
    @ApiModelProperty("从属业务名称,详情展示")
    private String                         assetBusiness;
    @ApiModelProperty("从属业务集合")
    private List<AssetBusinessResponse>    dependentBusiness;
    @ApiModelProperty("是否孤岛设备：1是,2否")
    private Integer                        isOrphan;
    @ApiModelProperty("自定义字段")
    private List<AssetCustomizeRequest>    customField;

    @ApiModelProperty("机器名")
    private String                         machineName;
    @ApiModelProperty("是否涉密：1是，2-否")
    private Integer                        isSecrecy;
    @ApiModelProperty("国资码")
    private String                         code;

    @ApiModelProperty("附件")
    private String                         fileInfo;
    @ApiModelProperty("审批内容")
    private String                         note;

    @ApiModelProperty("审批人主键")
    @Encode
    private String                         checkUserId;
    @ApiModelProperty("审批人名字")
    private String                         checkUserName;
    @ApiModelProperty("操作日志内容")
    private String                         content;
    @Encode
    @ApiModelProperty("执行人主键")
    private String                         executeUserId;
    @ApiModelProperty("执行人名字")
    private String                         executeUserName;

    /**
     * 备注
     */
    @ApiModelProperty("描述")
    private String                         memo;
    /**
     * 行颜色
     */
    @ApiModelProperty("行颜色:green,yellow")
    private String                         rowColor;
    /**
     * 软件版本
     */
    @ApiModelProperty("软件版本")
    private String                         softVersion;
    /**
     * 是否被整改过：1,已整改,2未整改
     */
    @ApiModelProperty("是否被整改过：1,已整改,2未整改,3整改中")
    private Integer                        rectification;

    public Integer getRectification() {
        return rectification;
    }

    public void setRectification(Integer rectification) {
        this.rectification = rectification;
    }

    public String getSoftVersion() {
        return softVersion;
    }

    public void setSoftVersion(String softVersion) {
        this.softVersion = softVersion;
    }

    public String getRowColor() {
        return rowColor;
    }

    public void setRowColor(String rowColor) {
        this.rowColor = rowColor;
    }

    public String getNetTypeName() {
        return netTypeName;
    }

    public void setNetTypeName(String netTypeName) {
        this.netTypeName = netTypeName;
    }

    public String getMemo() {
        return memo;
    }

    public String getTemporaryInfo() {
        return temporaryInfo;
    }

    public void setTemporaryInfo(String temporaryInfo) {
        this.temporaryInfo = temporaryInfo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getAdmittanceStatusName() {
        return admittanceStatusName;
    }

    public void setAdmittanceStatusName(String admittanceStatusName) {
        this.admittanceStatusName = admittanceStatusName;
    }

    public String getImportanceDegreeName() {
        return importanceDegreeName;
    }

    public void setImportanceDegreeName(String importanceDegreeName) {
        this.importanceDegreeName = importanceDegreeName;
    }

    public List<AssetBusinessResponse> getAssetBusinessResponse() {
        return assetBusinessResponse;
    }

    public String getNetStatusName() {
        return netStatusName;
    }

    public void setNetStatusName(String netStatusName) {
        this.netStatusName = netStatusName;
    }

    public void setAssetBusinessResponse(List<AssetBusinessResponse> assetBusinessResponse) {
        this.assetBusinessResponse = assetBusinessResponse;
    }

    public Long getFirstDiscoverTime() {
        return firstDiscoverTime;
    }

    public void setFirstDiscoverTime(Long firstDiscoverTime) {
        this.firstDiscoverTime = firstDiscoverTime;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getMnv() {
        return mnv;
    }

    public void setMnv(String mnv) {
        this.mnv = mnv;
    }

    public String getAssetBusiness() {
        return assetBusiness;
    }

    public void setAssetBusiness(String assetBusiness) {
        this.assetBusiness = assetBusiness;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public Long getInstallDate() {
        return installDate;
    }

    public void setInstallDate(Long installDate) {
        this.installDate = installDate;
    }

    public Long getActiviateDate() {
        return activiateDate;
    }

    public void setActiviateDate(Long activiateDate) {
        this.activiateDate = activiateDate;
    }

    public Long getExpirationReminder() {
        return expirationReminder;
    }

    public void setExpirationReminder(Long expirationReminder) {
        this.expirationReminder = expirationReminder;
    }

    public String getNetType() {
        return netType;
    }

    public void setNetType(String netType) {
        this.netType = netType;
    }

    public Integer getIsBorrow() {
        return isBorrow;
    }

    public void setIsBorrow(Integer isBorrow) {
        this.isBorrow = isBorrow;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setCustomField(List<AssetCustomizeRequest> customField) {
        this.customField = customField;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public Integer getIsSecrecy() {
        return isSecrecy;
    }

    public void setIsSecrecy(Integer isSecrecy) {
        this.isSecrecy = isSecrecy;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getExecuteUserId() {
        return executeUserId;
    }

    public void setExecuteUserId(String executeUserId) {
        this.executeUserId = executeUserId;
    }

    public String getExecuteUserName() {
        return executeUserName;
    }

    public void setExecuteUserName(String executeUserName) {
        this.executeUserName = executeUserName;
    }

    public String getFileInfo() {
        return fileInfo;
    }

    public void setFileInfo(String fileInfo) {
        this.fileInfo = fileInfo;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCheckUserId() {
        return checkUserId;
    }

    public void setCheckUserId(String checkUserId) {
        this.checkUserId = checkUserId;
    }

    public String getCheckUserName() {
        return checkUserName;
    }

    public void setCheckUserName(String checkUserName) {
        this.checkUserName = checkUserName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<AssetCustomizeRequest> getCustomField() {
        return customField;
    }

    public void setCustomField(String customField) {
        this.customField = JsonUtil.json2List(customField, AssetCustomizeRequest.class);
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
        this.netStatusName = AssetNetStatusEnum.getMsgByCode(netStatus);
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
        this.admittanceStatusName = AdmittanceStatusEnum.getAdmittanceStatusEnum(admittanceStatus);
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
        this.importanceDegreeName = AssetImportanceDegreeEnum.getMsgByCode(importanceDegree);
    }

    public String getAssetOperationRecordId() {
        return assetOperationRecordId;
    }

    public void setAssetOperationRecordId(String assetOperationRecordId) {
        this.assetOperationRecordId = assetOperationRecordId;
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