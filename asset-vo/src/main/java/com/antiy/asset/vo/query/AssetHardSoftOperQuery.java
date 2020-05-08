package com.antiy.asset.vo.query;

import com.antiy.common.base.ObjectQuery;
import io.swagger.annotations.ApiModelProperty;

/**
 * cpe信息查询-应急补丁登记厂商列表接口
 * @author zhouye
 */
public class AssetHardSoftOperQuery extends ObjectQuery {
	/**
	 * 供应商
	 */
	private String            supplier;
	/**
	 * 产品名
	 */
	private String            productName;

	@ApiModelProperty("版本")
	private String version;

	/**
	 * 系统版本
	 */
	@ApiModelProperty("系统版本")
	private String        sysVersion;
	/**
	 * 软件版本
	 */
	@ApiModelProperty("软件版本")
	private String        softVersion;
	/**
	 * 软件平台
	 */
	@ApiModelProperty("软件平台")
	private String        softPlatform;
	/**
	 * 硬件平台
	 */
	@ApiModelProperty("硬件平台")
	private String        hardPlatform;

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

	public String getSysVersion() {
		return sysVersion;
	}

	public void setSysVersion(String sysVersion) {
		this.sysVersion = sysVersion;
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

    @Override
    public String toString() {
        return "AssetHardSoftOperQuery{" +
                "supplier='" + supplier + '\'' +
                ", productName='" + productName + '\'' +
                ", version='" + version + '\'' +
                ", sysVersion='" + sysVersion + '\'' +
                ", softVersion='" + softVersion + '\'' +
                ", softPlatform='" + softPlatform + '\'' +
                ", hardPlatform='" + hardPlatform + '\'' +
                '}';
    }
}
