package com.antiy.asset.entity;

import com.antiy.common.base.BaseEntity;
import com.antiy.common.base.BaseResponse;

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
    private String  assetId;
    /**
     * 资产名称
     */
    private String  name;
    /**
     * 资产编号
     */
    private String number;
    /**
     * 资产品类型号
     */
    private String  categoryModel;
    /**
     * 资产品类型号名称
     */
    private String  categoryModelName;
    /**
     * 可绑定数量
     */
    private Integer canBind;
    /**
     * 未绑定数量
     */
    private Integer noBind;

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
        return "AssetLinkedCount{" + "assetId='" + assetId + '\'' + ", name='" + name + '\'' + ", categoryModel='"
               + categoryModel + '\'' + ", categoryModelName='" + categoryModelName + '\'' + ", canBind=" + canBind
               + ", noBind=" + noBind + '}';
    }
}
