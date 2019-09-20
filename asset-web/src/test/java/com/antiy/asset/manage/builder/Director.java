package com.antiy.asset.manage.builder;

/**
 * 指挥者
 */
public class Director {
	private Builder builder;

	public Director(Builder builder) {
		this.builder = builder;
	}
	public Product construct()
	{
		builder.builder();
		return builder.getProducts();
	}
}
