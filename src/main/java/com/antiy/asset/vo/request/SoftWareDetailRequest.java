package com.antiy.asset.vo.request;

import static com.antiy.asset.utils.DateUtils.WHOLE_FORMAT;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotBlank;

import com.antiy.asset.encoder.Encode;
import com.antiy.asset.exception.RequestParamValidateException;
import com.antiy.asset.utils.DateUtils;
import com.antiy.asset.utils.ParamterExceptionUtils;
import com.antiy.asset.validation.ObjectValidator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Auther: zhangbing
 * @Date: 2018/11/22 10:52
 * @Description: 软件详情请求
 */
@ApiModel(value = "软件详情请求")
public class SoftWareDetailRequest extends SoftWareRequest implements ObjectValidator {

    /**
     * 购买日期
     */
    @ApiModelProperty("购买日期,格式:yyyy-MM-dd HH:mm:ss")
    @NotBlank(message = "购买日期不能为空")
    private String     busyDate;

    /**
     * 有效期限
     */
    @ApiModelProperty("有效期限,格式：yyyy-MM-dd HH:mm:ss")
    @NotBlank(message = "有效期限不能为空")
    private String     expiryDate;

    /**
     * 定购成本
     */
    @ApiModelProperty("定购成本")
    private BigDecimal orderCost;

    /**
     * 许可密钥
     */
    @ApiModelProperty("许可密钥")
    @NotBlank(message = "许可密钥不能为空")
    private String     licenseSecretKey;

    @Encode
    @ApiModelProperty(value = "软件授权Id")
    private String     licenseId;

    public String getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(String licenseId) {
        this.licenseId = licenseId;
    }

    @Override
    public void validate() throws RequestParamValidateException {
        ParamterExceptionUtils.isNull(DateUtils.getDateFormat(busyDate, WHOLE_FORMAT), "购买时间异常");
        ParamterExceptionUtils.isNull(DateUtils.getDateFormat(expiryDate, WHOLE_FORMAT), "过期时间异常");
    }

    public Date getBusyDate() {
        return DateUtils.getDateFormat(busyDate, WHOLE_FORMAT);
    }

    public void setBusyDate(String busyDate) {
        this.busyDate = busyDate;
    }

    public Date getExpiryDate() {
        return DateUtils.getDateFormat(expiryDate, WHOLE_FORMAT);
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public BigDecimal getOrderCost() {
        return orderCost;
    }

    public void setOrderCost(BigDecimal orderCost) {
        this.orderCost = orderCost;
    }

    public String getLicenseSecretKey() {
        return licenseSecretKey;
    }

    public void setLicenseSecretKey(String licenseSecretKey) {
        this.licenseSecretKey = licenseSecretKey;
    }
}
