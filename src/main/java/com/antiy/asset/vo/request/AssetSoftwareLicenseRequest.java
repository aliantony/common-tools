package com.antiy.asset.vo.request;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * AssetSoftwareLicenseRequest 请求对象
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */
@Data
public class AssetSoftwareLicenseRequest extends BasicRequest implements ObjectValidator {

    private Integer id;

    /**
     * 软件主键
     */
    @ApiModelProperty("软件主键")
    private Integer softwareId;
    /**
     * 购买日期
     */
    @ApiModelProperty("购买日期")
    private Long busyDate;
    /**
     * 有效期限
     */
    @ApiModelProperty("有效期限")
    private Long expiryDate;
    /**
     * 许可密钥
     */
    @ApiModelProperty("许可密钥")
    private String licenseSecretKey;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String memo;

    @Override
    public void validate() throws RequestParamValidateException {
//        ParamterExceptionUtils.isNull(expiryDate, "有效期限不能为空");
//        ParamterExceptionUtils.isNull(busyDate,"购买日期不能为空");
//        ParamterExceptionUtils.isBlank(licenseSecretKey, "许可密钥不能为空");
    }
    public void setExpiryDate(Long expiryDate) {
        this.expiryDate = expiryDate;
    }


}