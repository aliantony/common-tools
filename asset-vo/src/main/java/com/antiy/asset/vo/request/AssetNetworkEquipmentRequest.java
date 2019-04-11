package com.antiy.asset.vo.request;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p> AssetNetworkEquipmentRequest 请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetNetworkEquipmentRequest extends BasicRequest implements ObjectValidator {

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    @Encode
    private String  id;

    /**
     * 资产主键
     */
    @ApiModelProperty("资产主键")
    @Encode
    private String  assetId;
    /**
     * 接口数目
     */
    @ApiModelProperty("接口数目")
    private Integer interfaceSize;
    /**
     * 端口数目
     */
    @ApiModelProperty("端口数目")
    @NotNull(message = "端口数目不为空")
    private Integer portSize;
    /**
     * 是否无线:0-否,1-是
     */
    @ApiModelProperty("是否无线:0-否,1-是")
    private Integer isWireless;
    /**
     * 内网IP
     */
    @ApiModelProperty("内网IP")
    @NotBlank(message = "内网IP不能为空")
    private String  innerIp;
    /**
     * 外网IP
     */
    @ApiModelProperty("外网IP")
//    @NotBlank(message = "外网IP不能为空")
    private String  outerIp;
    /**
     * MAC地址
     */
    @ApiModelProperty("MAC地址")
    private String  macAddress;
    /**
     * 子网掩码
     */
    @ApiModelProperty("子网掩码")
    private String  subnetMask;
    /**
     * 预计带宽(M)
     */
    @ApiModelProperty("预计带宽(M)")
    private Integer expectBandwidth;
    /**
     * 配置寄存器(GB)
     */
    @ApiModelProperty("配置寄存器(GB)")
    private Integer register;
    /**
     * DRAM大小
     */
    @ApiModelProperty("DRAM大小")
    private Float   dramSize;
    /**
     * FLASH大小
     */
    @ApiModelProperty("FLASH大小")
    private Float   flashSize;
    /**
     * NCRM大小
     */
    @ApiModelProperty("NCRM大小")
    private Float   ncrmSize;
    /**
     * ios
     */
    @ApiModelProperty("ios")
    private String  ios;
    /**
     * 固件版本
     */
    @ApiModelProperty("固件版本")
    private String  firmwareVersion;
    /**
     * cpu版本
     */
    @ApiModelProperty("cpu版本")
    private String  cpuVersion;
    /**
     * cpu大小
     */
    @ApiModelProperty("cpu大小")
    private Integer cpuSize;

    public String getCpuVersion() {
        return cpuVersion;
    }

    public void setCpuVersion(String cpuVersion) {
        this.cpuVersion = cpuVersion;
    }

    public Integer getCpuSize() {
        return cpuSize;
    }

    public void setCpuSize(Integer cpuSize) {
        this.cpuSize = cpuSize;
    }

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    public String getIos() {
        return ios;
    }

    public void setIos(String ios) {
        this.ios = ios;
    }

    public Integer getPortSize() {
        return portSize;
    }

    public void setPortSize(Integer portSize) {
        this.portSize = portSize;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public Integer getInterfaceSize() {
        return interfaceSize;
    }

    public void setInterfaceSize(Integer interfaceSize) {
        this.interfaceSize = interfaceSize;
    }

    public Integer getIsWireless() {
        return isWireless;
    }

    public void setIsWireless(Integer isWireless) {
        this.isWireless = isWireless;
    }

    public String getInnerIp() {
        return innerIp;
    }

    public void setInnerIp(String innerIp) {
        this.innerIp = innerIp;
    }

    public String getOuterIp() {
        return outerIp;
    }

    public void setOuterIp(String outerIp) {
        this.outerIp = outerIp;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getSubnetMask() {
        return subnetMask;
    }

    public void setSubnetMask(String subnetMask) {
        this.subnetMask = subnetMask;
    }

    public Integer getExpectBandwidth() {
        return expectBandwidth;
    }

    public void setExpectBandwidth(Integer expectBandwidth) {
        this.expectBandwidth = expectBandwidth;
    }

    public Integer getRegister() {
        return register;
    }

    public void setRegister(Integer register) {
        this.register = register;
    }

    public Float getDramSize() {
        return dramSize;
    }

    public void setDramSize(Float dramSize) {
        this.dramSize = dramSize;
    }

    public Float getFlashSize() {
        return flashSize;
    }

    public void setFlashSize(Float flashSize) {
        this.flashSize = flashSize;
    }

    public Float getNcrmSize() {
        return ncrmSize;
    }

    public void setNcrmSize(Float ncrmSize) {
        this.ncrmSize = ncrmSize;
    }

    @Override
    public String toString() {
        return "AssetNetworkEquipmentRequest{" + "id='" + id + '\'' + ", assetId='" + assetId + '\''
               + ", interfaceSize=" + interfaceSize + ", isWireless=" + isWireless + ", innerIp='" + innerIp + '\''
               + ", outerIp='" + outerIp + '\'' + ", macAddress='" + macAddress + '\'' + ", subnetMask='" + subnetMask
               + '\'' + ", expectBandwidth=" + expectBandwidth + ", register=" + register + ", dramSize=" + dramSize
               + ", flashSize=" + flashSize + ", ncrmSize=" + ncrmSize + '}';
    }

    @Override
    public void validate() throws RequestParamValidateException {

    }

}