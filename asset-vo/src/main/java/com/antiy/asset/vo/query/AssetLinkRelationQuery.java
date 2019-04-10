package com.antiy.asset.vo.query;

import com.antiy.common.base.ObjectQuery;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

/**
 * <p> AssetLinkRelation 查询条件 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetLinkRelationQuery extends ObjectQuery {
    /**
     * 资产id
     */
    @ApiModelProperty("资产id")
    private String        assetId;
    /**
     * 资产名称
     */
    @ApiModelProperty("资产名称")
    private String        assetName;
    /**
     * 资产编号
     */
    @ApiModelProperty("资产编号")
    private String        assetNumber;
    /**
     * 关联资产id
     */
    @ApiModelProperty("关联资产id")
    private String        parentAssetId;
    /**
     * 资产品类型号
     */
    @ApiModelProperty("资产品类型号")
    private String        categoryModel;
    /**
     * 计算设备资产品类型号列表
     */
    @ApiModelProperty("计算设备资产品类型号列表")
    private List<Integer> pcCategoryModels;
    /**
     * 网络设备资产品类型号列表
     */
    @ApiModelProperty("网络设备资产品类型号列表")
    private List<Integer> netCategoryModels;
    /**
     * 资产综合查询
     */
    @ApiModelProperty("资产综合查询")
    private String        multipleQuery;
    /**
     * 关联资产综合查询
     */
    @ApiModelProperty("关联资产综合查询")
    private String        parentMultipleQuery;
    /**
     * 资产区域
     */
    @ApiModelProperty("资产区域")
    private List<Integer> areaIds;
    /**
     * 网络设备根节点品类型号
     */
    @ApiModelProperty("网络设备根节点品类型号")
    private Integer pcRootCategoryModel;
    /**
     * 计算设备根节点品类型号
     */
    @ApiModelProperty("计算设备根节点品类型号")
    private Integer netRootCategoryModel;

    public Integer getPcRootCategoryModel() {
        return pcRootCategoryModel;
    }

    public void setPcRootCategoryModel(Integer pcRootCategoryModel) {
        this.pcRootCategoryModel = pcRootCategoryModel;
    }

    public Integer getNetRootCategoryModel() {
        return netRootCategoryModel;
    }

    public void setNetRootCategoryModel(Integer netRootCategoryModel) {
        this.netRootCategoryModel = netRootCategoryModel;
    }

    public String getCategoryModel() {
        return categoryModel;
    }

    public void setCategoryModel(String categoryModel) {
        this.categoryModel = categoryModel;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getAssetNumber() {
        return assetNumber;
    }

    public void setAssetNumber(String assetNumber) {
        this.assetNumber = assetNumber;
    }

    public List<Integer> getPcCategoryModels() {
        return pcCategoryModels;
    }

    public void setPcCategoryModels(List<Integer> pcCategoryModels) {
        this.pcCategoryModels = pcCategoryModels;
    }

    public List<Integer> getNetCategoryModels() {
        return netCategoryModels;
    }

    public void setNetCategoryModels(List<Integer> netCategoryModels) {
        this.netCategoryModels = netCategoryModels;
    }

    public String getMultipleQuery() {
        return multipleQuery;
    }

    public void setMultipleQuery(String multipleQuery) {
        this.multipleQuery = multipleQuery;
    }

    public String getParentMultipleQuery() {
        return parentMultipleQuery;
    }

    public void setParentMultipleQuery(String parentMultipleQuery) {
        this.parentMultipleQuery = parentMultipleQuery;
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

    public List<Integer> getAreaIds() {
        return areaIds;
    }

    public void setAreaIds(List<Integer> areaIds) {
        this.areaIds = areaIds;
    }
}