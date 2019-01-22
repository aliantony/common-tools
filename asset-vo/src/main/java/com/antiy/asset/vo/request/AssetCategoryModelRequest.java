package com.antiy.asset.vo.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetCategoryModelRequest 请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetCategoryModelRequest extends BasicRequest implements ObjectValidator {

    /**
     * id
     */
    @ApiModelProperty("id")
    @Encode
    private String  id;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String  name;

    @Max(value = 2, message = "资产类型只能为1或2")
    @Min(value = 1, message = "资产类型只能为1或2")
    @ApiModelProperty("资产类型:1软件，2硬件")
    private Integer assetType;
    /**
     * 父ID
     */
    @ApiModelProperty("父ID")
    @NotNull(message = "父ID不能为空")
    private Integer parentId;

    /**
     * 是否系统默认：0系统1自定义
     */
    @Max(value = 1, message = "isDefault只能为0和1")
    @Min(value = 0, message = "isDefault只能为0和1")
    @ApiModelProperty("是否是系统默认")
    private Integer isDefault;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String  memo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public void validate() throws RequestParamValidateException {

    }

}