package com.antiy.asset.vo.query;

import com.antiy.common.base.ObjectQuery;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * AssetSoftwareLicense 查询条件
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */
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
    private Long buyDate;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Integer getSoftwareId() {
        return softwareId;
    }

    public void setSoftwareId(Integer softwareId) {
        this.softwareId = softwareId;
    }

    public Long getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Long buyDate) {
        this.buyDate = buyDate;
    }

    public Long getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Long expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getLicenseSecretKey() {
        return licenseSecretKey;
    }

    public void setLicenseSecretKey(String licenseSecretKey) {
        this.licenseSecretKey = licenseSecretKey;
    }

    @Override
    public void validate() throws RequestParamValidateException {

    }
}