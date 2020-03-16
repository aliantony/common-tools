package com.antiy.asset.vo.response;

import com.antiy.common.base.BaseResponse;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * AssetBusinessResponse 响应对象
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetBusinessResponse extends BaseResponse {
    /**
     * 业务id
     */
    @ApiModelProperty("业务id")
    private Integer id;
    /**
     *  业务名称（中文字符，去重）
     */
    @ApiModelProperty("业务名称")
    private String name;
    /**
     *  业务重要性：1-高，2-中，3-低
     */
    @ApiModelProperty("业务重要性")
    private Integer importance;
    @ApiModelProperty("业务重要性")
    private String importanceDesc;
    /**
     *  描述
     */
    @ApiModelProperty("描述")
    private String description;

    /**
     *  创建时间
     */
    @ApiModelProperty("创建时间")
    private Long gmtCreate;
    /**
     *  更新时间
     */
    @ApiModelProperty("更新时间")
    private Long gmtModified;
    /**
     * 关联资产数量
     */
    @ApiModelProperty("关联资产数量")
    private Integer assetCount;
    /**
     * 唯一键
     */
    @ApiModelProperty("唯一键")
    private String uniqueId;

    /**
     * 排序字段
     * 1 资产数量  2 重要行  3  更新时间
     *
     */
    @ApiModelProperty("排序字段 1 资产数量  2 重要行  3  更新时间")
    private Integer orderBy;
    /**
     * 顺序
     * 1 从大到小
     * 2 从小到大
     */
    @ApiModelProperty("顺序")
    private Integer sortOrder;

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImportanceDesc() {
        if(importance==1)
            return "高";
        if(importance==2)
            return "中";
        if(importance==3)
            return "低";
        return null;
    }



    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getImportance() {
        return importance;
    }

    public void setImportance(Integer importance) {
        this.importance = importance;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Integer getAssetCount() {
        return assetCount;
    }

    public void setAssetCount(Integer assetCount) {
        this.assetCount = assetCount;
    }
}