package com.antiy.asset.vo.response;

import com.antiy.common.encoder.Encode;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetNetworkEquipmentResponse 响应对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetNetworkEquipmentResponse extends BaseResponse {

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
     * 是否无线:0-否,1-是
     */
    @ApiModelProperty("是否无线:0-否,1-是")
    private Integer isWireless;
    /**
     * 内网IP
     */
    @ApiModelProperty("内网IP")
    private String  innerIp;
    /**
     * 外网IP
     */
    @ApiModelProperty("外网IP")
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
     * 状态,1未删除,0已删除
     */
    @ApiModelProperty("状态,1未删除,0已删除")
    private Integer status;
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
    /**
     * ios
     */
    @ApiModelProperty("ios")
    private String  ios;
    /**
     * 端口数目
     */
    @ApiModelProperty("端口数目")
    private Integer portSize;

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

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
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

    public Integer getWireless() {
        return isWireless;
    }

    public void setWireless(Integer wireless) {
        isWireless = wireless;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AssetNetworkEquipmentResponse{" +
                "assetId='" + assetId + '\'' +
                ", interfaceSize=" + interfaceSize +
                ", isWireless=" + isWireless +
                ", innerIp='" + innerIp + '\'' +
                ", outerIp='" + outerIp + '\'' +
                ", macAddress='" + macAddress + '\'' +
                ", subnetMask='" + subnetMask + '\'' +
                ", expectBandwidth=" + expectBandwidth +
                ", register=" + register +
                ", dramSize=" + dramSize +
                ", flashSize=" + flashSize +
                ", ncrmSize=" + ncrmSize +
                ", status=" + status +
                ", firmwareVersion='" + firmwareVersion + '\'' +
                ", cpuVersion='" + cpuVersion + '\'' +
                ", cpuSize=" + cpuSize +
                ", ios='" + ios + '\'' +
                ", portSize=" + portSize +
                '}';
    }
}