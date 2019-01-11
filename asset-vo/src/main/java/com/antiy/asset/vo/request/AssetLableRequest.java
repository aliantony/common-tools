package com.antiy.asset.vo.request;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetLableRequest 请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetLableRequest extends BasicRequest implements ObjectValidator {

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
     * 标签类型:1东区２西区
     */
    @ApiModelProperty("标签类型:1东区２西区")
    private Integer           labelType;
    /**
     * 描述
     */
    @ApiModelProperty("描述")
    private String            description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLabelType() {
        return labelType;
    }

    public void setLabelType(Integer labelType) {
        this.labelType = labelType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void validate() throws RequestParamValidateException {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}