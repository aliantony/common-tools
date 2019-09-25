package com.antiy.asset.vo.query;

import java.util.List;

import javax.validation.constraints.Size;

import com.antiy.common.base.ObjectQuery;
import com.antiy.common.encoder.Encode;

import io.swagger.annotations.ApiModelProperty;

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
    @Encode
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
    @Size(message = "资产编号0~30字符", max = 30)
    private String        assetNumber;
    /**
     * 关联资产id
     */
    @ApiModelProperty("关联资产id")
    @Encode
    private String        parentAssetId;
    /**
     * 资产品类型号
     */
    @ApiModelProperty("资产品类型号")
    private Integer       categoryModel;

    /**
     * 资产综合查询
     */
    @ApiModelProperty("资产综合查询")
    @Size(message = "综合查询0~30字符", max = 30)
    private String        multipleQuery;
    /**
     * 关联资产综合查询
     */
    @ApiModelProperty("关联资产综合查询")
    @Size(message = "关联资产综合查询0~30字符", max = 30)
    private String        parentMultipleQuery;
    /**
     * 资产区域
     */
    @ApiModelProperty("资产区域")
    @Encode
    private List<String>  areaIds;
    /**
     * 资产状态列表
     */
    @ApiModelProperty("资产状态列表")
    private List<String>  statusList;

    public void setCategoryModel(Integer categoryModel) {
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

    public List<String> getAreaIds() {
        return areaIds;
    }

    public void setAreaIds(List<String> areaIds) {
        this.areaIds = areaIds;
    }

    public List<String> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<String> statusList) {
        this.statusList = statusList;
    }
}