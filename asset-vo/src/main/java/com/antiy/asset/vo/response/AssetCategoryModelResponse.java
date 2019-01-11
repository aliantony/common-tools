package com.antiy.asset.vo.response;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetCategoryModelResponse 响应对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetCategoryModelResponse {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty("id")
    private Integer           id;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String            name;
    /**
     * 类型:1-品类，2-型号
     */
    @ApiModelProperty("类型:1-品类，2-型号")
    private Integer           type;
    /**
     * 资产类型:1软件，2硬件
     */
    @ApiModelProperty("资产类型:1软件，2硬件")
    private Integer           assetType;
    /**
     * 父ID
     */
    @ApiModelProperty("父ID")
    private Integer           parentId;
    /**
     * 描述
     */
    @ApiModelProperty("描述")
    private String            description;
    // /**
    // * 是否系统默认：0系统1自定义
    // */
    // private Integer isDefault;

    @ApiModelProperty("状态,1未删除,0已删除")
    private Integer           status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getAssetType() {
        return assetType;
    }

    public void setAssetType(Integer assetType) {
        this.assetType = assetType;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // public Integer getIsDefault() {
    // return isDefault;
    // }
    //
    // public void setIsDefault(Integer isDefault) {
    // this.isDefault = isDefault;
    // }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}