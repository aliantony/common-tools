package com.antiy.asset.entity.vo.query;

import com.antiy.common.base.ObjectQuery;
import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
import com.antiy.common.validation.ObjectValidator;
import com.antiy.common.exception.RequestParamValidateException;
/**
 * <p>
 * AssetNetworkEquipment 查询条件
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetNetworkEquipmentQuery extends ObjectQuery implements ObjectValidator{
    /**
     *  资产主键
     */
    @ApiModelProperty("资产主键")
    private Integer assetId;
    /**
     *  接口数目
     */
    @ApiModelProperty("接口数目")
    private Integer interfaceSize;
    /**
     *  是否无线:0-否,1-是
     */
    @ApiModelProperty("是否无线:0-否,1-是")
    private Boolean isWireless;
    /**
     *  内网IP
     */
    @ApiModelProperty("内网IP")
    private String innerIp;
    /**
     *  外网IP
     */
    @ApiModelProperty("外网IP")
    private String outerIp;
    /**
     *  MAC地址
     */
    @ApiModelProperty("MAC地址")
    private String macAddress;
    /**
     *  子网掩码
     */
    @ApiModelProperty("子网掩码")
    private String subnetMask;

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }

    public Integer getInterfaceSize() {
        return interfaceSize;
    }

    public void setInterfaceSize(Integer interfaceSize) {
        this.interfaceSize = interfaceSize;
    }

    public Boolean getWireless() {
        return isWireless;
    }

    public void setWireless(Boolean wireless) {
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

    @Override
    public void validate() throws RequestParamValidateException {

    }
}