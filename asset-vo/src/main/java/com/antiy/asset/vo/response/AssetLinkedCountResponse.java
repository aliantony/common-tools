package com.antiy.asset.vo.response;

import com.antiy.common.base.BaseResponse;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetLinkRelationResponse 响应对象 </p>
 *
 * @author lvliang
 * @since 2018-12-27
 */
public class AssetLinkedCountResponse extends BaseResponse {
    /**
     * 资产id
     */
    @ApiModelProperty("资产id")
    private String  assetId;
    /**
     * 资产名称
     */
    @ApiModelProperty("资产名称")
    private String  name;
    /**
     * 资产编号
     */
    @ApiModelProperty("资产编号")
    private String  number;
    /**
     * 资产品类型号
     */
    @ApiModelProperty("资产品类型号")
    private String  categoryModel;
    /**
     * 资产品类型号名称
     */
    @ApiModelProperty("资产品类型号名称")
    private String  categoryModelName;
    /**
     * 可绑定数量
     */
    @ApiModelProperty("可绑定数量")
    private Integer canBind;
    /**
     * 未绑定数量
     */
    @ApiModelProperty("未绑定数量")
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
        return "AssetLinkedCountResponse{" + "assetId='" + assetId + '\'' + ", name='" + name + '\'' + ", number='"
               + number + '\'' + ", categoryModel='" + categoryModel + '\'' + ", categoryModelName='"
               + categoryModelName + '\'' + ", canBind=" + canBind + ", noBind=" + noBind + '}';
    }
}
