package com.antiy.asset.vo.query;

import java.util.List;

import javax.validation.constraints.Size;

import com.antiy.common.base.ObjectQuery;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 资产列表查询参数
 * @Author: lvliang
 * @Date: 2020/4/8 10:04
 */
@ApiModel
public class AssetMultipleQuery extends ObjectQuery implements ObjectValidator {
    /**
     * 待办事项的资产id
     */
    private String[]      ids;
    @ApiModelProperty(value = "是否从控制台进入，默认false 不从控制台进入，true为控制台进入")
    private Boolean       enterControl = false;
    @ApiModelProperty("综合查询条件")
    @Size(max = 30, message = "综合查询条件不能超过30")
    private String        multipleQuery;

    @ApiModelProperty("资产类型")
    private String        categoryModels;
    private List<String>  categoryModelList;
    @ApiModelProperty("资产状态")
    private List<Integer> assetStatusList;

    @ApiModelProperty("物理位置")
    private String        location;

    @ApiModelProperty("机房位置")
    private String        houseLocation;

    @ApiModelProperty("key")
    private String        key;

    /**
     * 资产名称
     */
    @ApiModelProperty(value = "资产名称")
    private List<String>  names;

    /**
     * 厂商
     */
    @ApiModelProperty("厂商")
    private List<String>  manufacturerList;

    /**
     * 版本
     */
    @ApiModelProperty("版本")
    private List<String>  versionList;
    /**
     * 操作系统
     */
    @ApiModelProperty("操作系统")
    private List<Long>    operationSystemList;

    /**
     * 资产来源
     */
    @ApiModelProperty("资产来源")
    private List<Integer> assetSourceList;

    @ApiModelProperty("从属业务")
    @Encode
    private String        assetBusiness;

    /**
     * 资产组
     */
    @ApiModelProperty("资产组")
    @Encode
    private List<String>  assetGroupList;
    /**
     * 资产组主键
     */
    @ApiModelProperty(value = "资产组",hidden = true)
    @Encode
    private String  assetGroup;
    /**
     * 网络类型
     */
    @ApiModelProperty("网络类型")
    @Encode
    private String        netType;

    /**
     * 归属区域
     */
    @Encode
    @ApiModelProperty("归属区域")
    private List<String>  areaIds;

    /**
     * 使用者
     */
    @ApiModelProperty("使用者")
    @Encode
    private List<String>  responsibleUserIdList;
    /**
     * 所属组织
     */
    @ApiModelProperty("所属组织")
    @Encode
    private List<String>  departmentList;

    /**
     * 资产准入状态
     */
    @ApiModelProperty("准入状态：1-已允许，2-已禁止")
    private Integer       admittanceStatus;

    /**
     * 是否可借用
     */
    @ApiModelProperty("是否可借用：1-可借用，2-不可借用")
    private Integer       isBorrow;

    @ApiModelProperty("基准模板id")
    @Encode
    private String        baselineTemplateId;
    @ApiModelProperty("网络连接")
    private Integer       netStatus;
    @ApiModelProperty("是否涉密")
    private Integer       isSecrecy;
    @ApiModelProperty("是否孤岛设备")
    private Integer       isOrphan;
    /**
     * 1-核心，2-重要，3-一般
     */
    @ApiModelProperty("1-核心，2-重要，3-一般")
    private List<Integer> importanceDegreeList;
    /**
     * 维护方式
     */
    @ApiModelProperty("维护方式")
    private List<Integer> installTypeList;
    @ApiModelProperty("到期时间起始时间")
    private Long          serviceLifeStartTime;
    @ApiModelProperty("到期时间结束时间")
    private Long          serviceLifeEndTime;
    @ApiModelProperty("首次发现时间起始时间")
    private Long          firstEnterStartTime;
    @ApiModelProperty("首次发现时间结束时间")
    private Long          firstEnterEndTime;

    @ApiModelProperty("装机时间开始时间")
    private Long          installDateStartTime;
    @ApiModelProperty("装机时间结束时间")
    private Long          installDateEndTime;
    @ApiModelProperty("启用时间开始时间")
    private Long          activiateDateStartTime;
    @ApiModelProperty("启用时间结束时间")
    private Long          activiateDateEndTime;
    @ApiModelProperty("更新时间起始时间")
    private Long          updateStartTime;
    @ApiModelProperty("更新时间结束时间")
    private Long          updateEndTime;
    @ApiModelProperty("是否未知资产")
    private boolean       unknownAssets;

    public String getAssetGroup() {
        return assetGroup;
    }

    public void setAssetGroup(String assetGroup) {
        this.assetGroup = assetGroup;
    }

    public List<String> getCategoryModelList() {
        return categoryModelList;
    }

    public void setCategoryModelList(List<String> categoryModelList) {
        this.categoryModelList = categoryModelList;
    }

    public Boolean getUnknownAssets() {
        return unknownAssets;
    }

    public void setUnknownAssets(Boolean unknownAssets) {
        this.unknownAssets = unknownAssets;
    }

    public Boolean getEnterControl() {
        return enterControl;
    }

    public void setEnterControl(Boolean enterControl) {
        this.enterControl = enterControl;
    }

    public String[] getIds() {
        return ids;
    }

    public void setIds(String[] ids) {
        this.ids = ids;
    }

    public Long getUpdateStartTime() {
        return updateStartTime;
    }

    public void setUpdateStartTime(Long updateStartTime) {
        this.updateStartTime = updateStartTime;
    }

    public Long getUpdateEndTime() {
        return updateEndTime;
    }

    public void setUpdateEndTime(Long updateEndTime) {
        this.updateEndTime = updateEndTime;
    }

    public List<String> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(List<String> departmentList) {
        this.departmentList = departmentList;
    }

    public String getMultipleQuery() {
        return multipleQuery;
    }

    public void setMultipleQuery(String multipleQuery) {
        this.multipleQuery = multipleQuery;
    }

    public String getCategoryModels() {
        return categoryModels;
    }

    public void setCategoryModels(String categoryModels) {
        this.categoryModels = categoryModels;
    }

    public List<Integer> getAssetStatusList() {
        return assetStatusList;
    }

    public void setAssetStatusList(List<Integer> assetStatusList) {
        this.assetStatusList = assetStatusList;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getHouseLocation() {
        return houseLocation;
    }

    public void setHouseLocation(String houseLocation) {
        this.houseLocation = houseLocation;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public List<String> getManufacturerList() {
        return manufacturerList;
    }

    public void setManufacturerList(List<String> manufacturerList) {
        this.manufacturerList = manufacturerList;
    }

    public List<String> getVersionList() {
        return versionList;
    }

    public void setVersionList(List<String> versionList) {
        this.versionList = versionList;
    }

    public List<Long> getOperationSystemList() {
        return operationSystemList;
    }

    public void setOperationSystemList(List<Long> operationSystemList) {
        this.operationSystemList = operationSystemList;
    }

    public List<Integer> getAssetSourceList() {
        return assetSourceList;
    }

    public void setAssetSourceList(List<Integer> assetSourceList) {
        this.assetSourceList = assetSourceList;
    }

    public String getAssetBusiness() {
        return assetBusiness;
    }

    public void setAssetBusiness(String assetBusiness) {
        this.assetBusiness = assetBusiness;
    }

    public List<String> getAssetGroupList() {
        return assetGroupList;
    }

    public void setAssetGroupList(List<String> assetGroupList) {
        this.assetGroupList = assetGroupList;
    }

    public String getNetType() {
        return netType;
    }

    public void setNetType(String netType) {
        this.netType = netType;
    }

    public List<String> getAreaIds() {
        return areaIds;
    }

    public void setAreaIds(List<String> areaIds) {
        this.areaIds = areaIds;
    }

    public List<String> getResponsibleUserIdList() {
        return responsibleUserIdList;
    }

    public void setResponsibleUserIdList(List<String> responsibleUserIdList) {
        this.responsibleUserIdList = responsibleUserIdList;
    }

    public Integer getAdmittanceStatus() {
        return admittanceStatus;
    }

    public void setAdmittanceStatus(Integer admittanceStatus) {
        this.admittanceStatus = admittanceStatus;
    }

    public Integer getIsBorrow() {
        return isBorrow;
    }

    public void setIsBorrow(Integer isBorrow) {
        this.isBorrow = isBorrow;
    }

    public String getBaselineTemplateId() {
        return baselineTemplateId;
    }

    public void setBaselineTemplateId(String baselineTemplateId) {
        this.baselineTemplateId = baselineTemplateId;
    }

    public Integer getNetStatus() {
        return netStatus;
    }

    public void setNetStatus(Integer netStatus) {
        this.netStatus = netStatus;
    }

    public Integer getIsSecrecy() {
        return isSecrecy;
    }

    public void setIsSecrecy(Integer isSecrecy) {
        this.isSecrecy = isSecrecy;
    }

    public Integer getIsOrphan() {
        return isOrphan;
    }

    public void setIsOrphan(Integer isOrphan) {
        this.isOrphan = isOrphan;
    }

    public List<Integer> getImportanceDegreeList() {
        return importanceDegreeList;
    }

    public void setImportanceDegreeList(List<Integer> importanceDegreeList) {
        this.importanceDegreeList = importanceDegreeList;
    }

    public List<Integer> getInstallTypeList() {
        return installTypeList;
    }

    public void setInstallTypeList(List<Integer> installTypeList) {
        this.installTypeList = installTypeList;
    }

    public Long getServiceLifeStartTime() {
        return serviceLifeStartTime;
    }

    public void setServiceLifeStartTime(Long serviceLifeStartTime) {
        this.serviceLifeStartTime = serviceLifeStartTime;
    }

    public Long getServiceLifeEndTime() {
        return serviceLifeEndTime;
    }

    public void setServiceLifeEndTime(Long serviceLifeEndTime) {
        this.serviceLifeEndTime = serviceLifeEndTime;
    }

    public Long getFirstEnterStartTime() {
        return firstEnterStartTime;
    }

    public void setFirstEnterStartTime(Long firstEnterStartTime) {
        this.firstEnterStartTime = firstEnterStartTime;
    }

    public Long getFirstEnterEndTime() {
        return firstEnterEndTime;
    }

    public void setFirstEnterEndTime(Long firstEnterEndTime) {
        this.firstEnterEndTime = firstEnterEndTime;
    }

    public Long getInstallDateStartTime() {
        return installDateStartTime;
    }

    public void setInstallDateStartTime(Long installDateStartTime) {
        this.installDateStartTime = installDateStartTime;
    }

    public Long getInstallDateEndTime() {
        return installDateEndTime;
    }

    public void setInstallDateEndTime(Long installDateEndTime) {
        this.installDateEndTime = installDateEndTime;
    }

    public Long getActiviateDateStartTime() {
        return activiateDateStartTime;
    }

    public void setActiviateDateStartTime(Long activiateDateStartTime) {
        this.activiateDateStartTime = activiateDateStartTime;
    }

    public Long getActiviateDateEndTime() {
        return activiateDateEndTime;
    }

    public void setActiviateDateEndTime(Long activiateDateEndTime) {
        this.activiateDateEndTime = activiateDateEndTime;
    }

    @Override
    public void validate() throws RequestParamValidateException {

    }
}
