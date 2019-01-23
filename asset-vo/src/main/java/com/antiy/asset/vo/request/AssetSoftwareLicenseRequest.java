package com.antiy.asset.vo.request;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetSoftwareLicenseRequest 请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */
public class AssetSoftwareLicenseRequest extends BasicRequest implements ObjectValidator {

    @ApiModelProperty(value = "主键")
    @Encode
    private String id;

    /**
     * 软件主键
     */
    @ApiModelProperty("软件主键")
    @Encode
    private String softwareId;
    /**
     * 购买日期
     */
    @ApiModelProperty("购买日期")
    private Long   buyDate;
    /**
     * 有效期限
     */
    @ApiModelProperty("有效期限")
    private Long   expiryDate;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSoftwareId() {
        return softwareId;
    }

    public void setSoftwareId(String softwareId) {
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

    public String getLicenseSecretKey() {
        return licenseSecretKey;
    }

    public void setLicenseSecretKey(String licenseSecretKey) {
        this.licenseSecretKey = licenseSecretKey;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String toString() {
        return "AssetSoftwareLicenseRequest{" +
                "id='" + id + '\'' +
                ", softwareId='" + softwareId + '\'' +
                ", buyDate=" + buyDate +
                ", expiryDate=" + expiryDate +
                ", licenseSecretKey='" + licenseSecretKey + '\'' +
                ", memo='" + memo + '\'' +
                '}';
    }

    @Override
    public void validate() throws RequestParamValidateException {
        // ParamterExceptionUtils.isNull(expiryDate, "有效期限不能为空");
        // ParamterExceptionUtils.isNull(buyDate,"购买日期不能为空");
        // ParamterExceptionUtils.isBlank(licenseSecretKey, "许可密钥不能为空");
    }

    public void setExpiryDate(Long expiryDate) {
        this.expiryDate = expiryDate;
    }

}