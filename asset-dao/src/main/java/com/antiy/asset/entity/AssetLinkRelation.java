package com.antiy.asset.entity;

import com.antiy.asset.vo.response.CategoryType;
import com.antiy.common.base.BaseEntity;

import io.swagger.annotations.ApiModelProperty;

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
    private CategoryType      categoryType;

    public CategoryType getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(CategoryType categoryType) {
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

    @Override
    public String toString() {
        return "AssetLinkRelation{" + "assetId=" + assetId + ", assetName='" + assetName + '\'' + ", assetIp='"
               + assetIp + '\'' + ", assetPort='" + assetPort + '\'' + ", categoryModel='" + categoryModel + '\''
               + ", categoryModelName='" + categoryModelName + '\'' + ", parentAssetId=" + parentAssetId
               + ", parentAssetName='" + parentAssetName + '\'' + ", parentAssetIp='" + parentAssetIp + '\''
               + ", parentAssetPort='" + parentAssetPort + '\'' + ", parentCategoryModel='" + parentCategoryModel + '\''
               + ", parentCategoryModelName='" + parentCategoryModelName + '\'' + ", gmtCreate=" + gmtCreate
               + ", gmtModified=" + gmtModified + ", memo='" + memo + '\'' + ", createUser=" + createUser
               + ", modifyUser=" + modifyUser + ", status=" + status + ", categoryType=" + categoryType + '}';
    }
}