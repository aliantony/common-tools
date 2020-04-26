package com.antiy.asset.vo.request;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * <p> AssetCpuRequest 请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetImportRequest extends BasicRequest implements ObjectValidator {


    /**
     * 品类型号
     */
    @ApiModelProperty("品类型号")
    @Encode
    @NotBlank(message = "品类型号不能为空")
    private String   category;



    @Override
    public void validate() throws RequestParamValidateException {

    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}