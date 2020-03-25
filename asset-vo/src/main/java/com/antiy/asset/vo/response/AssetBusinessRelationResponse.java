package com.antiy.asset.vo.response;

import com.antiy.common.base.BaseResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * AssetBusinessRelationResponse 响应对象
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */
@ApiModel("资产关联类")
public class AssetBusinessRelationResponse extends BaseResponse {

    @ApiModelProperty("资产关联业务编号")
    private String assetBusinessId;
    /**
     * 资产编号
     */
    @ApiModelProperty("资产编号")
    private String            number;
    /**
     * 资产名称
     */
    private String            name;
    /**
     *
     */
    @ApiModelProperty("资产组")
    private String            assetGroup;

    private String            ips;
    /**
     * mac
     */
    private String            macs;
    /**
     * 厂商
     */
    private String            manufacturer;
    /**
     * 资产对业务影响
     */
    private Integer businessInfluence;

    public Integer getBusinessInfluence() {
        return businessInfluence;
    }

    public void setBusinessInfluence(Integer businessInfluence) {
        this.businessInfluence = businessInfluence;
    }

    private String uniqueId;

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }
    public String getAssetBusinessId() {
        return assetBusinessId;
    }

    public void setAssetBusinessId(String assetBusinessId) {
        this.assetBusinessId = assetBusinessId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAssetGroup() {
        return assetGroup;
    }

    public void setAssetGroup(String assetGroup) {
        this.assetGroup = assetGroup;
    }

    public String getIps() {
        return ips;
    }

    public void setIps(String ips) {
        this.ips = ips;
    }

    public String getMacs() {
        return macs;
    }

    public void setMacs(String macs) {
        this.macs = macs;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

}