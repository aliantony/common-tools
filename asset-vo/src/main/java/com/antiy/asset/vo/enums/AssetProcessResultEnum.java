package com.antiy.asset.vo.enums;

/**
 * @author zhouye
 * 资产操作结果
 */
public enum  AssetProcessResultEnum implements ValuedEnum{

	;
	/**
	 * 处理描述
	 */
	private String name;
	/**
	 * 当前操作对应的目标状态
	 */
	private int value;

	@Override
	public int getValue() {
		return value;
	}}
