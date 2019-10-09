package com.antiy.asset.vo.request;

import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.asset.vo.enums.ValuedEnum;

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
	OPERATION_NOT_REGISTER("不予登记资产。",AssetStatusEnum.NOT_REGISTER.getCode()),
	/**
	 * 模板实施-3
	 */
	WAIT_TEMPLATE_IMPL("模板实施，实施情况：",AssetStatusEnum.WAIT_TEMPLATE_IMPL.getCode()),
	/**
	 * 待验证-4
	 */
	OPERATION_WAIT_VALIDATE("结果验证，验证情况：",AssetStatusEnum.WAIT_VALIDATE.getCode()),
	/**
	 * 准入实施-5
	 */
	OPERATION_WAIT_NET("准入实施。实施情况：",AssetStatusEnum.WAIT_NET.getCode()),
	/**
	 * 安全检查-7
	 */
	OPERATION_WAIT_CHECK("安全检查。检查情况：",AssetStatusEnum.WAIT_CHECK.getCode()),
	/**
	 * 入网-6
	 */
	OPERATION_NET_IN("已入网资产。",AssetStatusEnum.NET_IN.getCode()),
	/**
	 * 整改-8
	 */
	WAIT_CORRECT("整改，整改情况：",AssetStatusEnum.WAIT_CORRECT.getCode()),
	/**
	 * 变更中-9
	 */
	IN_CHANGE("资产变更完成。",AssetStatusEnum.IN_CHANGE.getCode()),
	/**
	 * 待退役-10
	 */
	OPERATION_WAIT_RETIRE("待退役资产。",AssetStatusEnum.WAIT_RETIRE.getCode()),
	/**
	 * 已退役-11
	 */
	OPERATION_RETIRE("实施退役。退役情况：",AssetStatusEnum.RETIRE.getCode()),
	/**
	 * 无状态-0
	 */
	OPERATION_NO("无上一步状态", Integer.valueOf(0)) ;
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
	public String describe(String processResult){
		String isPass ="1";
		String pr ="";
		if (processResult==null || "".equals(processResult)){
			return getName();
		}else if (isPass.equals(processResult)){
			pr = "通过。";
		}else if("0".equals(processResult)){
			pr ="不通过。";
		}
		pr = filter(pr);
		return getName() + pr;

	}

	/**
	 * 过滤没有操作结果的描述
	 * 如果操作状态为：登记-不予登记-已入网-待退役，没有操作结果描述
	 * @return 操作结果
	 */
	private String filter(String pr){
		String res ="";
		switch (this) {
			case OPERATION_HARDWARE_REGISTER:
				break;
			case OPERATION_NOT_REGISTER:
				break;
			case OPERATION_NET_IN:
				break;
			case OPERATION_WAIT_RETIRE:
				break;
			default:
				res = pr;
		}
		return res;
	}
	@Override
	public int getValue() {
		return value;
	}

}
