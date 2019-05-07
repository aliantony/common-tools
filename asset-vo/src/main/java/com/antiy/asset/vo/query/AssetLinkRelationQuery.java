package com.antiy.asset.vo.query;

import com.antiy.common.base.ObjectQuery;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Size;
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
    @Size(message = "资产编号1~30字符", max = 30)
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
    private List<String>  categoryModels;
    /**
     * 计算设备资产品类型号列表
     */
    @ApiModelProperty("计算设备资产品类型号列表，不传")
    private List<Integer> pcCategoryModels;
    /**
     * 网络设备资产品类型号列表
     */
    @ApiModelProperty("网络设备资产品类型号列表，不传")
    private List<Integer> netCategoryModels;
    /**
     * 资产综合查询
     */
    @ApiModelProperty("资产综合查询")
    @Size(message = "综合查询1~30字符", max = 30)
    private String        multipleQuery;
    /**
     * 关联资产综合查询
     */
    @ApiModelProperty("关联资产综合查询")
    @Size(message = "关联资产综合查询1~30字符", max = 30)
    private String        parentMultipleQuery;
    /**
     * 资产区域
     */
    @ApiModelProperty("资产区域,不传")
    private List<Integer> areaIds;
    /**
     * 关联资产综合查询
     */
    @ApiModelProperty("资产状态列表")
    private List<String>  statusList;
    /**
     * 端口号列表
     */
    private List<Integer> portCount;

    public List<Integer> getPortCount() {
        return portCount;
    }

    public void setPortCount(List<Integer> portCount) {
        this.portCount = portCount;
    }

    public List<String> getCategoryModels() {
        return categoryModels;
    }

    public void setCategoryModels(List<String> categoryModels) {
        this.categoryModels = categoryModels;
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

    public List<String> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<String> statusList) {
        this.statusList = statusList;
    }
}