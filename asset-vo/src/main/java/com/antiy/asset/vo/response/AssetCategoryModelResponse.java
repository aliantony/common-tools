package com.antiy.asset.vo.response;

import com.antiy.common.encoder.Encode;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetCategoryModelResponse 响应对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetCategoryModelResponse extends BaseResponse {

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String name;
    /**
     * 类型:1-品类，2-型号
     */

    /**
     * 父ID
     */
    @ApiModelProperty("父ID")
    @Encode
    private String parentId;
    /**
     * 父ID
     */
    @ApiModelProperty("是否系统默认")
    private Integer isDefault;
    /**
     * 描述
     */
    @ApiModelProperty("备注")
    private String memo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String toString() {
        return "AssetCategoryModelResponse{" + "name='" + name + '\'' + ", parentId='" + parentId + '\''
               + ", isDefault='" + isDefault + '\'' + ", memo='" + memo + '\'' + '}';
    }
}