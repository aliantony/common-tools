package com.antiy.asset.templet;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetHardSoftLibResponse 响应对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */
@ApiModel
public class AssetHardSoftLibTemplate {

    /**
     * 供应商
     */
    @ApiModelProperty("厂商")
    private String supplier;
    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String productName;
    /**
     * 版本号
     */
    @ApiModelProperty("版本号")
    private String version;
    /**
     * 更新信息
     */
    @ApiModelProperty("更新信息")
    private String upgradeMsg;
    /**
     * 系统版本
     */
    @ApiModelProperty("系统版本")
    private String sysVersion;
    /**
     * 语言
     */
    @ApiModelProperty("语言")
    private String language;
    /**
     * 软件版本
     */
    @ApiModelProperty("软件版本")
    private String softVersion;
    /**
     * 软件平台
     */
    @ApiModelProperty("软件平台")
    private String softPlatform;

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUpgradeMsg() {
        return upgradeMsg;
    }

    public void setUpgradeMsg(String upgradeMsg) {
        this.upgradeMsg = upgradeMsg;
    }

    public String getSysVersion() {
        return sysVersion;
    }

    public void setSysVersion(String sysVersion) {
        this.sysVersion = sysVersion;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSoftVersion() {
        return softVersion;
    }

    public void setSoftVersion(String softVersion) {
        this.softVersion = softVersion;
    }

    public String getSoftPlatform() {
        return softPlatform;
    }

    public void setSoftPlatform(String softPlatform) {
        this.softPlatform = softPlatform;
    }
}