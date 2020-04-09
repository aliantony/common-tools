package com.antiy.asset.vo.query;

import com.antiy.common.base.ObjectQuery;
import com.antiy.common.encoder.Encode;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class AssetAddOfBusinessQuery extends ObjectQuery {

    /**
     * 综合查询
     */
    @ApiModelProperty("综合查询")
    private String multipleQuery;

    /**
     * 资产组
     */
    @ApiModelProperty("资产组")
    private List<String> assetGroup;
    /**
     * 厂商
     */
    @ApiModelProperty("厂商")
    private List<String> manufacturer;

    /**
     *
     * 品类型号
     */
    @ApiModelProperty("品类型号")
    private List<String> categoryModels;

    public List<String> getCategoryModels() {
        return categoryModels;
    }
    public void setCategoryModels(List<String> categoryModels) {
        this.categoryModels = categoryModels;
    }

    public String getAssetBusinessId() {
        return assetBusinessId;
    }

    public void setAssetBusinessId(String assetBusinessId) {
        this.assetBusinessId = assetBusinessId;
    }

    /**
     * 业务
     */
    @ApiModelProperty("资产关联业务编号")
    private String assetBusinessId;

    /**
     *
     */
    @ApiModelProperty("业务主键")
    private String uniqueId;

    @ApiModelProperty("业务已关联的资产id")
    /**
     * 资产id,用于排除业务已添加的业务id
     *
     */
    @Encode
    private List<String> assetIds;

    /**
     *
     *
     *
     *
     *
     *
     *
     * 区域id
     *
     */
    String [] areaIds;

    public String[] getAreaIds() {
        return areaIds;
    }

    public void setAreaIds(String[] areaIds) {
        this.areaIds = areaIds;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public List<String> getAssetIds() {
        return assetIds;
    }

    public void setAssetIds(List<String> assetIds) {
        this.assetIds = assetIds;
    }

    public String getMultipleQuery() {
        return multipleQuery;
    }

    public void setMultipleQuery(String multipleQuery) {
        this.multipleQuery = multipleQuery;
    }

    public List<String> getAssetGroup() {
        return assetGroup;
    }

    public void setAssetGroup(List<String> assetGroup) {
        this.assetGroup = assetGroup;
    }

    public List<String> getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(List<String> manufacturer) {
        this.manufacturer = manufacturer;
    }
}
