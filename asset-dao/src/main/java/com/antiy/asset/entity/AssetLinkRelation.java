package com.antiy.asset.entity;

import com.antiy.common.base.BaseEntity;

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
    private Integer           assetId;
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
    private String            categoryModel;
    /**
     * 资产品类型号名称
     */
    private String            categoryModelName;
    /**
     * 父级设备主键
     */
    private Integer           parentAssetId;
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
    private String            parentCategoryModel;
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

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
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

    public Integer getParentAssetId() {
        return parentAssetId;
    }

    public void setParentAssetId(Integer parentAssetId) {
        this.parentAssetId = parentAssetId;
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

    public String getCategoryModel() {
        return categoryModel;
    }

    public void setCategoryModel(String categoryModel) {
        this.categoryModel = categoryModel;
    }

    public String getCategoryModelName() {
        return categoryModelName;
    }

    public void setCategoryModelName(String categoryModelName) {
        this.categoryModelName = categoryModelName;
    }

    public String getParentCategoryModel() {
        return parentCategoryModel;
    }

    public void setParentCategoryModel(String parentCategoryModel) {
        this.parentCategoryModel = parentCategoryModel;
    }

    public String getParentCategoryModelName() {
        return parentCategoryModelName;
    }

    public void setParentCategoryModelName(String parentCategoryModelName) {
        this.parentCategoryModelName = parentCategoryModelName;
    }

    @Override
    public String toString() {
        return "AssetLinkRelation{" + "assetId=" + assetId + ", assetIp='" + assetIp + '\'' + ", assetPort='"
               + assetPort + '\'' + ", categoryModel='" + categoryModel + '\'' + ", categoryModelName='"
               + categoryModelName + '\'' + ", parentAssetId=" + parentAssetId + ", parentAssetIp='" + parentAssetIp
               + '\'' + ", parentAssetPort='" + parentAssetPort + '\'' + ", parentCategoryModel='" + parentCategoryModel
               + '\'' + ", parentCategoryModelName='" + parentCategoryModelName + '\'' + ", gmtCreate=" + gmtCreate
               + ", gmtModified=" + gmtModified + ", memo='" + memo + '\'' + ", createUser=" + createUser
               + ", modifyUser=" + modifyUser + ", status=" + status + '}';
    }
}