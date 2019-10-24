package com.antiy.asset.vo.request;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.utils.ParamterExceptionUtils;
import com.antiy.common.validation.ObjectValidator;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.StringUtils;

import javax.validation.constraints.*;
import java.util.regex.Matcher;

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
    @Max(value = 127, message = "接口数目大小不超过127")
    private Integer interfaceSize;
    /**
     * 端口数目
     */
    @ApiModelProperty("网口数目")
    @NotNull(message = "网口数目不为空")
    @Max(value = 100, message = "网口数目大小不超过100")
    private Integer portSize;
    /**
     *
     */
    @ApiModelProperty("是否无线：0-否,1-是")
    @Max(value = 1, message = "是否无线不能大于1")
    @Min(value = 0, message = "是否无线不能小于0")
    private Integer isWireless;
    /**
     * 外网IP
     */
    @ApiModelProperty("外网IP")
    private String  outerIp;

    /**
     * 子网掩码
     */
    @ApiModelProperty("子网掩码")
    @Size(message = "子网掩码不能大于30个字符", max = 30)
    private String  subnetMask;
    /**
     * 预计带宽(M)
     */
    @ApiModelProperty("预计带宽(M)")
    @Max(value = 9999999, message = "预计带宽大小不超过9999999")
    private Integer expectBandwidth;
    /**
     * 配置寄存器(GB)
     */
    @ApiModelProperty("配置寄存器(GB)")
    @Max(value = 9999999, message = "配置寄存器大小不超过9999999")
    private Integer register;
    /**
     * DRAM大小
     */
    @ApiModelProperty("DRAM大小")
    @Max(value = 9999999, message = "DRAM大小不超过9999999")
    private Float   dramSize;
    /**
     * FLASH大小
     */
    @ApiModelProperty("FLASH大小")
    @Max(value = 9999999, message = "FLASH大小不超过9999999")
    private Float   flashSize;
    /**
     * NCRM大小
     */
    @ApiModelProperty("NCRM大小")
    @Max(value = 9999999, message = "NCRM大小不超过9999999")
    private Float   ncrmSize;
    /**
     * ios
     */
    @ApiModelProperty("ios")
    @Size(message = "ios长度不能超过30位", max = 30)
    private String  ios;
    /**
     * 固件版本
     */
    @ApiModelProperty("固件版本")
    @Size(message = "固件版本长度不能超过30位", max = 30)
    private String  firmwareVersion;
    /**
     * cpu版本
     */
    @ApiModelProperty("cpu版本")
    @Size(message = "cpu版本版本长度不能超过30位", max = 30)
    private String  cpuVersion;
    /**
     * cpu大小
     */
    @ApiModelProperty("cpu大小")
    @Max(value = 9999999, message = "cpu大小不超过9999999")
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

    public String getOuterIp() {
        return outerIp;
    }

    public void setOuterIp(String outerIp) {
        this.outerIp = outerIp;
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
    public void validate() throws RequestParamValidateException {

        if (StringUtils.isNotBlank(outerIp)) {
            if (!(outerIp.length() > 6 && outerIp.length() < 16)) {
                ParamterExceptionUtils.isTrue(false, "外网IP 7-15位");
            }
            String reg = "^(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])$";
            ParamterExceptionUtils.isTrue(outerIp.matches(reg), "ip地址错误");
        }

        if (StringUtils.isNotBlank(subnetMask)) {
            java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(
                "^(254|252|248|240|224|192|128|0)\\.0\\.0\\.0|255\\.(254|252|248|240|224|192|128|0)\\.0\\.0|255\\.255\\.(254|252|248|240|224|192|128|0)\\.0|255\\.255\\.255\\.(254|252|248|240|224|192|128|0)$");
            Matcher matcher = pattern.matcher(subnetMask);
            ParamterExceptionUtils.isTrue(matcher.matches(), "子网掩码错误");
        }

    }

}