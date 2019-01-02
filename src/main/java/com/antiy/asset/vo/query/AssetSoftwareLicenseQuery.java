package com.antiy.asset.vo.query;

import com.antiy.common.base.ObjectQuery;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * AssetSoftwareLicense 查询条件
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */
@Data
public class AssetSoftwareLicenseQuery extends ObjectQuery implements ObjectValidator {

    /**
     * 许可名称
     */
    @ApiModelProperty("许可名称")
    private String name;
    /**
     * 厂商名称
     */
    @ApiModelProperty("厂商名称")
    private String manufacturer;

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

    @Override
    public void validate() throws RequestParamValidateException {

    }
}