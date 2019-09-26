package com.antiy.asset.entity;

import com.antiy.asset.vo.response.CategoryType;
import com.antiy.common.base.BaseEntity;

/**
 * <p> AssetLinkRelationResponse 响应对象 </p>
 *
 * @author lvliang
 * @since 2018-12-27
 */
public class AssetLinkedCount extends BaseEntity {
    /**
     * 资产id
     */
    private String       assetId;
    /**
     * 资产名称
     */
    private String       name;
    /**
     * 资产编号
     */
    private String       number;
    /**
     * 资产品类型号
     */
    private Integer      categoryModel;
    /**
     * 资产品类型号名称
     */
    private String       categoryModelName;
    /**
     * 可绑定数量
     */
    private Integer      canBind;
    /**
     * 未绑定数量
     */
    private Integer      noBind;
    /**
     * 设备类型
     */
    private CategoryType categoryType;
    /**
     * 区域id
     */
    private String       areaId;
    /**
     * 区域名称
     */
    private String       areaName;

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public CategoryType getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(CategoryType categoryType) {
        this.categoryType = categoryType;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCategoryModel() {
        return categoryModel;
    }

    public void setCategoryModel(Integer categoryModel) {
        this.categoryModel = categoryModel;
    }

    public String getCategoryModelName() {
        return categoryModelName;
    }

    public void setCategoryModelName(String categoryModelName) {
        this.categoryModelName = categoryModelName;
    }

    public Integer getCanBind() {
        return canBind;
    }

    public void setCanBind(Integer canBind) {
        this.canBind = canBind;
    }

    public Integer getNoBind() {
        return noBind;
    }

    public void setNoBind(Integer noBind) {
        this.noBind = noBind;
    }

    @Override
    public String toString() {
        return "AssetLinkedCount{" + "assetId='" + assetId + '\'' + ", name='" + name + '\'' + ", number='" + number
               + '\'' + ", categoryModel='" + categoryModel + '\'' + ", categoryModelName='" + categoryModelName + '\''
               + ", canBind=" + canBind + ", noBind=" + noBind + ", categoryType=" + categoryType + '}';
    }
}
