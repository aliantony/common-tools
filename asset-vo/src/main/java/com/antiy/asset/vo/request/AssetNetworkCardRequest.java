package com.antiy.asset.vo.request;

import java.io.Serializable;
import java.util.regex.Matcher;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.utils.ParamterExceptionUtils;
import com.antiy.common.validation.ObjectValidator;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetNetworkCardRequest 请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetNetworkCardRequest extends BasicRequest implements ObjectValidator, Serializable {
    /**
     * 主键
     */
    @ApiModelProperty("主键")
    @Encode
    private String id;

    /**
     * 资产主键
     */
    @ApiModelProperty("资产主键")
    @Encode
    private String assetId;
    /**
     * 品牌
     */
    @ApiModelProperty("品牌")
    // @NotBlank(message = "网卡品牌不能为空")
    @Size(message = "网卡品牌长度不能超过30位", max = 30)
    private String brand;
    /**
     * 型号
     */
    @ApiModelProperty("型号")
    @Size(message = "网卡型号长度不能超过30位", max = 30)
    private String model;
    /**
     * 序列号
     */
    @ApiModelProperty("序列号")
    @Size(message = "网卡序列号长度不能超过30位", max = 30)
    private String serial;
    /**
     * IP地址
     */
    @ApiModelProperty("IP地址")
    @NotBlank(message = "IP地址不能为空")
    @Size(message = "IP地址长度应该在8-15位", min = 8, max = 15)
    @Pattern(regexp = "^(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])$", message = "ip地址错误")
    private String ipAddress;
    /**
     * MAC地址
     */
    @ApiModelProperty("MAC地址")
    @NotBlank(message = "MAC地址不能为空")
    @Size(message = "MAC地址长度应该为17位", max = 17, min = 17)
    @Pattern(regexp = "(([a-f0-9A-F]{2}:)|([a-f0-9A-F]{2}-)){5}[a-f0-9A-F]{2}", message = "mac地址错误")
    private String macAddress;
    /**
     * 默认网关
     */
    @ApiModelProperty("默认网关")
    @Size(message = "默认网关长度不能超过30位", max = 30)
    private String defaultGateway;
    /**
     * 网络地址
     */
    @ApiModelProperty("网络地址")
    private String networkAddress;
    /**
     * 子网掩码
     */
    @ApiModelProperty("子网掩码")
    @Size(message = "子网掩码长度不能超过30位", max = 30)
    private String subnetMask;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNetworkAddress() {
        return networkAddress;
    }

    public void setNetworkAddress(String networkAddress) {
        this.networkAddress = networkAddress;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getDefaultGateway() {
        return defaultGateway;
    }

    public void setDefaultGateway(String defaultGateway) {
        this.defaultGateway = defaultGateway;
    }

    public String getSubnetMask() {
        return subnetMask;
    }

    public void setSubnetMask(String subnetMask) {
        this.subnetMask = subnetMask;
    }

    @Override
    public void validate() throws RequestParamValidateException {
        if (StringUtils.isNotBlank(subnetMask)) {
            java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(
                "^(254|252|248|240|224|192|128|0)\\.0\\.0\\.0|255\\.(254|252|248|240|224|192|128|0)\\.0\\.0|255\\.255\\.(254|252|248|240|224|192|128|0)\\.0|255\\.255\\.255\\.(254|252|248|240|224|192|128|0)$");
            Matcher matcher = pattern.matcher(subnetMask);
            ParamterExceptionUtils.isTrue(matcher.matches(), "子网掩码错误");
        }
        if (StringUtils.isNotBlank(defaultGateway)) {
            java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(
                "^(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])$");
            Matcher matcher = pattern.matcher(defaultGateway);
            ParamterExceptionUtils.isTrue(matcher.matches(), "默认网关错误");
        }

    }

    @Override
    public String toString() {
        return "AssetNetworkCardRequest{" + "id='" + id + '\'' + ", assetId='" + assetId + '\'' + ", brand='" + brand
               + '\'' + ", model='" + model + '\'' + ", serial='" + serial + '\'' + ", ipAddress='" + ipAddress + '\''
               + ", macAddress='" + macAddress + '\'' + ", defaultGateway='" + defaultGateway + '\''
               + ", networkAddress='" + networkAddress + '\'' + ", subnetMask='" + subnetMask + '\'' + '}';
    }
}