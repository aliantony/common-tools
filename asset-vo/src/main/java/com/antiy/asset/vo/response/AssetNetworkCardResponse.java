package com.antiy.asset.vo.response;

import com.antiy.common.encoder.Encode;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetNetworkCardResponse 响应对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetNetworkCardResponse extends BaseResponse {
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
    private String  brand;
    /**
     * 型号
     */
    @ApiModelProperty("型号")
    private String  model;
    /**
     * 序列号
     */
    @ApiModelProperty("序列号")
    private String  serial;
    /**
     * IP地址
     */
    @ApiModelProperty("IP地址")
    private String  ipAddress;
    /**
     * MAC地址
     */
    @ApiModelProperty("MAC地址")
    private String  macAddress;
    /**
     * 默认网关
     */
    @ApiModelProperty("默认网关")
    private String  defaultGateway;
    /**
     * 网络地址
     */
    @ApiModelProperty("网络地址")
    private String  networkAddress;
    /**
     * 子网掩码
     */
    @ApiModelProperty("子网掩码")
    private String  subnetMask;

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getNetworkAddress() {
        return networkAddress;
    }

    public void setNetworkAddress(String networkAddress) {
        this.networkAddress = networkAddress;
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

    public String getnetworkAddress() {
        return networkAddress;
    }

    public void setnetworkAddress(String networkAddress) {
        this.networkAddress = networkAddress;
    }

    public String getSubnetMask() {
        return subnetMask;
    }

    public void setSubnetMask(String subnetMask) {
        this.subnetMask = subnetMask;
    }

}