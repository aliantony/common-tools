package com.antiy.asset.vo.enums;

/**
 * @author zhouye
 * 资产操作结果
 */
public enum  AssetProcessResultEnum implements ValuedEnum{
	/**
	 * 通过
	 */
	PROCESS_ADOPT("通过。",1),
	/**
	 * 不通过
	 */
	PROCESS_NOT_PASS("不通过。",0),
	/**
	 * 无此操作
	 */
	PROCESS_NULL("",null)
	;

	AssetProcessResultEnum(String name, Integer value) {
		this.name = name;
		this.value = value;
	}

	/**
	 * 处理描述
	 */
	private String name;
	/**
	 * 当前操作对应的目标状态
	 */
	private Integer value;

	public String getName() {
		return name;
	}

	@Override
	public Integer getValue() {
		return value;
	}}
