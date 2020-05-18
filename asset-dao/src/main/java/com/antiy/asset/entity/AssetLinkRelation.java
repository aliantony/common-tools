package com.antiy.asset.entity;

import com.antiy.asset.vo.response.AssetMacRelationResponse;
import com.antiy.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * <p>通联关系表</p>
 *
 * @author zhangyajun
 * @since 2019-04-02
 */

public class AssetLinkRelation extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 资产主键
     */
    private String            assetId;
    /**
     * 资产名称
     */
    private String            assetName;
    /**
     * 资产IP
     */
    private String            assetIp;
    /**
     * 资产端口
     */
    private String            assetPort;
    /**
     * 资产品类型号
     */
    private Integer           categoryModel;
    /**
     * 资产品类型号名称
     */
    private String            categoryModelName;
    /**
     * 父级设备主键
     */
    private String            parentAssetId;
    /**
     * 关联资产名称
     */
    private String            parentAssetName;
    /**
     * 父级设备IP
     */
    private String            parentAssetIp;
    /**
     * 父级设备端口
     */
    private String            parentAssetPort;
    /**
     * 关联资产品类型号
     */
    private Integer           parentCategoryModel;
    /**
     * 关联资产品类型号名称
     */
    private String            parentCategoryModelName;
    /**
     * 创建时间
     */
    private Long              gmtCreate;
    /**
     * 修改时间
     */
    private Long              gmtModified;
    /**
     * 备注
     */
    private String            memo;
    /**
     * 创建人
     */
    private Integer           createUser;
    /**
     * 修改人
     */
    private Integer           modifyUser;
    /**
     * 状态,1未删除,0已删除
     */
    private Integer           status;
    /**
     * 设备类型
     */
    @ApiModelProperty("设备类型")
    private String      categoryType;

    /**
     * 父类资产编号
     */
    private String parentAssetNumber;

    /**
     * 父类资产区域id
     */
    private String parentAssetAreaId;

    /**
     * 父类资产区域名称
     */
    private String parentAssetAreaName;

    /**
     * 父类资产负责人
     */
    private String parentAssetUserId;

    /**
     * 父类资产负责人名称
     */
    private String parentAssetUserName;

    /**
     * 父类资产负责人所属组织
     */
    private String parentAssetUserDepartment;

    /**
     * 配件房间号
     */
    private String partRoomNo;

    /**
     * 办公室网口
     */
    private Integer officeNet;

    /**
     * 办公室网口状态
     */
    private String officeNetState;

    /**
     * 交换机状态
     */
    private String switchState;

    /**
     * vlan号
     */
    private String vlan;

    /**
     * 自定义名称
     */
    private String customName;

    /**
     * 自定义内容
     */
    private String customContent;

    /**
     * 资产mac
     */
    private List<AssetMacRelationResponse> assetMac;

    /**
     * 父类资产mac
     */
    private List<AssetMacRelationResponse> parentAssetMac;


    public List<AssetMacRelationResponse> getAssetMac() {
        return assetMac;
    }

    public void setAssetMac(List<AssetMacRelationResponse> assetMac) {
        this.assetMac = assetMac;
    }

    public List<AssetMacRelationResponse> getParentAssetMac() {
        return parentAssetMac;
    }

    public void setParentAssetMac(List<AssetMacRelationResponse> parentAssetMac) {
        this.parentAssetMac = parentAssetMac;
    }

    public String getParentAssetAreaName() {
        return parentAssetAreaName;
    }

    public void setParentAssetAreaName(String parentAssetAreaName) {
        this.parentAssetAreaName = parentAssetAreaName;
    }

    public String getParentAssetUserName() {
        return parentAssetUserName;
    }

    public void setParentAssetUserName(String parentAssetUserName) {
        this.parentAssetUserName = parentAssetUserName;
    }

    public String getParentAssetUserDepartment() {
        return parentAssetUserDepartment;
    }

    public void setParentAssetUserDepartment(String parentAssetUserDepartment) {
        this.parentAssetUserDepartment = parentAssetUserDepartment;
    }

    public String getParentAssetNumber() {
        return parentAssetNumber;
    }

    public void setParentAssetNumber(String parentAssetNumber) {
        this.parentAssetNumber = parentAssetNumber;
    }

    public String getParentAssetAreaId() {
        return parentAssetAreaId;
    }

    public void setParentAssetAreaId(String parentAssetAreaId) {
        this.parentAssetAreaId = parentAssetAreaId;
    }

    public String getParentAssetUserId() {
        return parentAssetUserId;
    }

    public void setParentAssetUserId(String parentAssetUserId) {
        this.parentAssetUserId = parentAssetUserId;
    }

    public String getPartRoomNo() {
        return partRoomNo;
    }

    public void setPartRoomNo(String partRoomNo) {
        this.partRoomNo = partRoomNo;
    }

    public Integer getOfficeNet() {
        return officeNet;
    }

    public void setOfficeNet(Integer officeNet) {
        this.officeNet = officeNet;
    }

    public String getOfficeNetState() {
        return officeNetState;
    }

    public void setOfficeNetState(String officeNetState) {
        this.officeNetState = officeNetState;
    }

    public String getSwitchState() {
        return switchState;
    }

    public void setSwitchState(String switchState) {
        this.switchState = switchState;
    }

    public String getVlan() {
        return vlan;
    }

    public void setVlan(String vlan) {
        this.vlan = vlan;
    }


    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getParentAssetId() {
        return parentAssetId;
    }

    public void setParentAssetId(String parentAssetId) {
        this.parentAssetId = parentAssetId;
    }

    public String getAssetIp() {
        return assetIp;
    }

    public void setAssetIp(String assetIp) {
        this.assetIp = assetIp;
    }

    public String getAssetPort() {
        return assetPort;
    }

    public void setAssetPort(String assetPort) {
        this.assetPort = assetPort;
    }

    public String getParentAssetIp() {
        return parentAssetIp;
    }

    public void setParentAssetIp(String parentAssetIp) {
        this.parentAssetIp = parentAssetIp;
    }

    public String getParentAssetPort() {
        return parentAssetPort;
    }

    public void setParentAssetPort(String parentAssetPort) {
        this.parentAssetPort = parentAssetPort;
    }

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Long getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Integer getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(Integer modifyUser) {
        this.modifyUser = modifyUser;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCategoryModel() {
        return categoryModel;
    }

    public void setCategoryModel(Integer categoryModel) {
        this.categoryModel = categoryModel;
    }

    public Integer getParentCategoryModel() {
        return parentCategoryModel;
    }

    public void setParentCategoryModel(Integer parentCategoryModel) {
        this.parentCategoryModel = parentCategoryModel;
    }

    public String getCategoryModelName() {
        return categoryModelName;
    }

    public void setCategoryModelName(String categoryModelName) {
        this.categoryModelName = categoryModelName;
    }

    public String getParentCategoryModelName() {
        return parentCategoryModelName;
    }

    public void setParentCategoryModelName(String parentCategoryModelName) {
        this.parentCategoryModelName = parentCategoryModelName;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getParentAssetName() {
        return parentAssetName;
    }

    public void setParentAssetName(String parentAssetName) {
        this.parentAssetName = parentAssetName;
    }

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public String getCustomContent() {
        return customContent;
    }

    public void setCustomContent(String customContent) {
        this.customContent = customContent;
    }

    @Override
    public String toString() {
        return "AssetLinkRelation{" +
                "assetId='" + assetId + '\'' +
                ", assetName='" + assetName + '\'' +
                ", assetIp='" + assetIp + '\'' +
                ", assetPort='" + assetPort + '\'' +
                ", categoryModel=" + categoryModel +
                ", categoryModelName='" + categoryModelName + '\'' +
                ", parentAssetId='" + parentAssetId + '\'' +
                ", parentAssetName='" + parentAssetName + '\'' +
                ", parentAssetIp='" + parentAssetIp + '\'' +
                ", parentAssetPort='" + parentAssetPort + '\'' +
                ", parentCategoryModel=" + parentCategoryModel +
                ", parentCategoryModelName='" + parentCategoryModelName + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", memo='" + memo + '\'' +
                ", createUser=" + createUser +
                ", modifyUser=" + modifyUser +
                ", status=" + status +
                ", categoryType=" + categoryType +
                ", parentAssetNumber='" + parentAssetNumber + '\'' +
                ", parentAssetAreaId='" + parentAssetAreaId + '\'' +
                ", parentAssetAreaName='" + parentAssetAreaName + '\'' +
                ", parentAssetUserId='" + parentAssetUserId + '\'' +
                ", parentAssetUserName='" + parentAssetUserName + '\'' +
                ", parentAssetUserDepartment='" + parentAssetUserDepartment + '\'' +
                ", partRoomNo='" + partRoomNo + '\'' +
                ", officeNet=" + officeNet +
                ", officeNetState='" + officeNetState + '\'' +
                ", switchState='" + switchState + '\'' +
                ", vlan='" + vlan + '\'' +
                ", customName='" + customName + '\'' +
                ", customContent='" + customContent + '\'' +
                ", assetMac=" + assetMac +
                ", parentAssetMac=" + parentAssetMac +
                '}';
    }
}