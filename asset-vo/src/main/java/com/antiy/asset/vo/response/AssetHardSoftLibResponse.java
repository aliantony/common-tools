package com.antiy.asset.vo.response;

import com.antiy.common.base.BaseResponse;

/**
 * <p> AssetHardSoftLibResponse 响应对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetHardSoftLibResponse extends BaseResponse {
    /**
     * 业务主键
     */
    private String  businessId;
    /**
     * 编号
     */
    private Integer number;
    /**
     * 类型：a-应用软件 h-硬件 o-操作系统
     */
    private String  type;
    /**
     * 供应商
     */
    private String  supplier;
    /**
     * 产品名
     */
    private String  productName;
    /**
     * 版本号
     */
    private String  version;
    /**
     * 更新信息
     */
    private String  upgradeMsg;
    /**
     * 系统版本
     */
    private String  sysVersion;
    /**
     * 语言
     */
    private String  language;
    /**
     * 软件版本
     */
    private String  softVersion;
    /**
     * 软件平台
     */
    private String  softPlatform;
    /**
     * 硬件平台
     */
    private String  hardPlatform;
    /**
     * 其他
     */
    private String  other;
    /**
     * 数据来源：1-CPE，2-MANUAL
     */
    private Integer dataSource;
    /**
     * 是否入库：1-已入库、2-未入库
     */
    private Integer isStorage;
    /**
     * cpe路径
     */
    private String  cpeUri;

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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

    public String getHardPlatform() {
        return hardPlatform;
    }

    public void setHardPlatform(String hardPlatform) {
        this.hardPlatform = hardPlatform;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public Integer getDataSource() {
        return dataSource;
    }

    public void setDataSource(Integer dataSource) {
        this.dataSource = dataSource;
    }

    public Integer getIsStorage() {
        return isStorage;
    }

    public void setIsStorage(Integer isStorage) {
        this.isStorage = isStorage;
    }

    public String getCpeUri() {
        return cpeUri;
    }

    public void setCpeUri(String cpeUri) {
        this.cpeUri = cpeUri;
    }
}