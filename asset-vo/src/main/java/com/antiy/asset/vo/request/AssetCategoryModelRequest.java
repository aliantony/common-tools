package com.antiy.asset.vo.request;

import com.antiy.common.base.BasicRequest;
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

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * id
     */
    @ApiModelProperty("id")
    private Integer id;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String  name;

    @ApiModelProperty("资产类型:1软件，2硬件")
    private Integer assetType;
    /**
     * 父ID
     */
    @ApiModelProperty("父ID")
    private Integer parentId;

    /**
     * 是否系统默认：0系统1自定义
     */
    @ApiModelProperty("是否是系统默认")
    private Integer isDefault;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String  memo;

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