package com.antiy.asset.vo.response;

import com.antiy.asset.vo.enums.AssetCategoryEnum;
import com.antiy.common.base.BaseResponse;
import com.antiy.common.encoder.Encode;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetLinkRelationResponse 响应对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetLinkRelationResponse extends BaseResponse {
    /**
     * 资产主键
     */
    @ApiModelProperty("资产主键")
    @Encode
    private String       assetId;
    /**
     * 资产名称
     */
    @ApiModelProperty("资产名称")
    private String       assetName;
    /**
     * 资产IP
     */
    @ApiModelProperty("资产IP")
    private String       assetIp;
    /**
     * 资产网口
     */
    @ApiModelProperty("资产网口")
    private String       assetPort;
    /**
     * 资产品类型号
     */
    @ApiModelProperty("资产品类型号")
    private Integer      categoryModel;
    /**
     * 资产品类型号名称
     */
    @ApiModelProperty("资产品类型号名称")
    private String       categoryModelName;
    /**
     * 父级设备主键
     */
    @ApiModelProperty("父级设备主键")
    @Encode
    private String       parentAssetId;
    /**
     * 关联资产名称
     */
    @ApiModelProperty("关联资产名称")
    private String       parentAssetName;
    /**
     * 父级设备IP
     */
    @ApiModelProperty("父级设备IP")
    private String       parentAssetIp;
    /**
     * 父级设备网口
     */
    @ApiModelProperty("父级设备网口")
    private String       parentAssetPort;
    /**
     * 关联资产品类型号
     */
    @ApiModelProperty("关联资产品类型号")
    private Integer      parentCategoryModel;
    /**
     * 关联资产品类型号名称
     */
    @ApiModelProperty("关联资产品类型号名称")
    private String       parentCategoryModelName;
    /**
     * 设备类型
     */
    @ApiModelProperty("设备类型")
    private CategoryType categoryType;

    public CategoryType getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(CategoryType categoryType) {
        this.categoryType = categoryType;
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

    public String getCategoryModelName() {
        return categoryModelName;
    }

    public void setCategoryModelName(String categoryModelName) {
        this.categoryModelName = AssetCategoryEnum.getNameByCode(categoryModel);
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

    public String getParentCategoryModelName() {
        return parentCategoryModelName;
    }

    public void setParentCategoryModelName(String parentCategoryModelName) {
        this.parentCategoryModelName = AssetCategoryEnum.getNameByCode(parentCategoryModel);
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
        return "AssetLinkRelationResponse{" + "assetId=" + assetId + ", assetName='" + assetName + '\'' + ", assetIp='"
               + assetIp + '\'' + ", assetPort='" + assetPort + '\'' + ", categoryModel='" + categoryModel + '\''
               + ", categoryModelName='" + categoryModelName + '\'' + ", parentAssetId=" + parentAssetId
               + ", parentAssetName='" + parentAssetName + '\'' + ", parentAssetIp='" + parentAssetIp + '\''
               + ", parentAssetPort='" + parentAssetPort + '\'' + ", parentCategoryModel='" + parentCategoryModel + '\''
               + ", parentCategoryModelName='" + parentCategoryModelName + '\'' + ", categoryType=" + categoryType
               + '}';
    }
}