package com.antiy.asset.manage.builder;


import com.antiy.asset.entity.AssetStatusDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * 日志动态
 */
public class Product {
	List<AssetStatusDetail> products = new ArrayList<>();

	public void setProducts(List<AssetStatusDetail> products) {
		this.products = products;
	}

	public List<AssetStatusDetail> getProducts() {
		return products;
	}
}
