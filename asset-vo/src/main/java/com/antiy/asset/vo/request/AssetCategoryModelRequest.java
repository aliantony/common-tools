package com.antiy.asset.vo.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.antiy.common.base.BaseRequest;
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

public class AssetCategoryModelRequest extends BaseRequest implements ObjectValidator {

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    @NotBlank(message = "名称不能为空")
    @Size(message = "名称范围应在1~30字符", max = 30, min = 1)
    private String name;

    /**
     * 父ID
     */
    @Encode(message = "父Id转换异常")
    @ApiModelProperty("父ID")
    @NotBlank(message = "父Id不能为空")
    private String parentId;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    @Size(message = "备注范围应在5~300字符", max = 300)
    private String memo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Override
    public void validate() throws RequestParamValidateException {

    }

}