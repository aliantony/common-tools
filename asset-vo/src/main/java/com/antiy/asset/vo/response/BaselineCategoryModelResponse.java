package com.antiy.asset.vo.response;

import com.antiy.asset.vo.response.BaseResponse;
import com.antiy.common.encoder.Encode;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetCategoryModelResponse 响应对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class BaselineCategoryModelResponse extends BaseResponse {

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String name;

    /**
     * 父ID
     */
    @ApiModelProperty("父ID")
    @Encode
    private String parentId;
    /**
     * 描述
     */
    @ApiModelProperty("备注")
    private String memo;

    /**
     * 是否系统默认：0 系统 1 自定义
     */
    @ApiModelProperty("是否系统默认")
    private Integer isDefault;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }
}