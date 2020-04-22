package com.antiy.asset.vo.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.utils.DataTypeUtils;
import com.antiy.common.validation.ObjectValidator;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.StringUtils;

/**
 * <p> AssetCategoryModelRequest 请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetCategoryModelRequest implements ObjectValidator {


    /**
     * 名称
     */
    @ApiModelProperty("名称")
    @NotBlank(message = "名称不能为空")
    @Size(message = "名称范围应在1~90字符", max = 90, min = 1)
    private String name;

    /**
     * 父ID
     */
//    @Encode(message = "父Id转换异常")
    @ApiModelProperty("父ID")
    @NotBlank(message = "父Id不能为空")
    private String parentId;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    @Size(message = "备注范围应在1~300字符", max = 300)
    private String memo;
    @ApiModelProperty("资产类型id")
    private String stringId;

    public String getStringId() {
        return stringId;
    }

    public void setStringId(String stringId) {
        this.stringId = stringId;
    }

    /**
     * 数据拷贝会根据类型和方法拷贝，类型不正确则无法拷贝
     *
     * @return
     */
    public Integer getId() {
        return StringUtils.isNotBlank(stringId) ? DataTypeUtils.stringToInteger(stringId) : null;
    }

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