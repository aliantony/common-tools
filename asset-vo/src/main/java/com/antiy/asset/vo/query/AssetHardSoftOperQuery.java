package com.antiy.asset.vo.query;

import com.antiy.common.base.ObjectQuery;

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

	@Override
	public String toString() {
		return "AssetHardSoftOperQuery{" +
				"supplier='" + supplier + '\'' +
				", productName='" + productName + '\'' +
				'}';
	}
}
