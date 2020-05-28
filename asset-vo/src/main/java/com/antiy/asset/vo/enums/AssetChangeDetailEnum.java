package com.antiy.asset.vo.enums;


/**
 * @author zhouye
 * 资产动态信息记录
 */
public enum AssetChangeDetailEnum implements ValuedEnum {

	/**
	 * 登记资产-1
	 */
	OPERATION_HARDWARE_REGISTER("登记资产。", AssetStatusEnum.WAIT_REGISTER.getCode()),
	/**
	 * 不予登记-2
	 */
	OPERATION_NOT_REGISTER("不予登记资产。", AssetStatusEnum.NOT_REGISTER.getCode()),
	/**
	 * 入网审批-4
	 */
	OPERATION_WAIT_LEADER_CHECK("入网审批。审批情况：",AssetStatusEnum.NET_IN_CHECK.getCode()),
	/**
	 * 整改中-3
	 */
     OPERATION_CORRECTING("整改资产：",AssetStatusEnum.CORRECTING.getCode()),
	/**
	 * 准入实施-6
	 */
	OPERATION_WAIT_NET("准入实施。准入情况：",AssetStatusEnum.NET_IN_CHECK.getCode()),

	/**
	 * 入网-7
	 */
	OPERATION_NET_IN("已入网资产。",AssetStatusEnum.NET_IN.getCode()),

	/**
	 * 变更中-8
	 */
	IN_CHANGE("资产变更完成。",AssetStatusEnum.IN_CHANGE.getCode()),
	/**
	 * 退役申请-9
	 */
	OPERATION_APPLY_RETIRE("退役申请。",AssetStatusEnum.WAIT_RETIRE.getCode()),
	/**
	 * 待退役-11
	 */
	OPERATION_WAIT_RETIRE("待退役资产。",AssetStatusEnum.WAIT_RETIRE.getCode()),
	/**
	 * 已退役-12
	 */
	OPERATION_RETIRE("实施退役。退役情况：",AssetStatusEnum.RETIRE.getCode()),	/**
	/**
	 * 报废申请-13
	 */
	OPERATION_APPLY_SCRAP("报废申请。",AssetStatusEnum.WAIT_SCRAP.getCode()),	/**
	 * 报废申请-13
	 */
	OPERATION_EXECUTE_SCRAP("报废执行。",AssetStatusEnum.SCRAP.getCode()),
	/**
	 * 无状态-0
	 */
	OPERATION_NO("登记资产。", 0) ;
	AssetChangeDetailEnum(String name, int value) {
		this.name = name;
		this.value = value;
	}

	/**
	 * 操作描述
	 */
	private String name;
	/**
	 * 当前操作对应的目标状态
	 */
	private int value;

	public String getName() {
		return ","+ name;
	}
	public String
	describe(AssetProcessResultEnum processResult){
		if (processResult==null) {
			return "";
		}
		switch (this) {
			//准入实施
			case OPERATION_WAIT_NET:
				return processResult.getValue().equals(1) ? "允许" : "禁止";
			//入网审批
			case OPERATION_WAIT_LEADER_CHECK:
				return processResult.getValue().equals(1) ? "通过" : "未通过";
			default:
				return "";
		}
	}

	/**
	 * 过滤没有操作结果的描述
	 * 如果操作状态为：登记-不予登记-已入网-待退役，没有操作结果描述
	 * @return 操作结果
	 */
	private String filter(String pr){
		String res ="";
		//有些前置状态，下一步操作没有通不-通过结果：
		switch (this) {
			//未登记
			case OPERATION_HARDWARE_REGISTER:
				break;
				//不予登记
			case OPERATION_NOT_REGISTER:
				break;
				//已入网
			case OPERATION_NET_IN:
				break;
				//已退役
			case OPERATION_RETIRE:
				break;
				//未登记
			case OPERATION_NO:
				break;
			default:
				res = pr;
		}
		return res;
	}
	@Override
	public Integer getValue() {
		return value;
	}

}
