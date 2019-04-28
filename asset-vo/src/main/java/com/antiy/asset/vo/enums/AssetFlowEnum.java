package com.antiy.asset.vo.enums;

/**
 * 硬件流程枚举
 *
 * @Auther: zhangyajun
 * @Date: 2019/1/14 11:36
 * @Description:
 */
public enum AssetFlowEnum implements CodeEnum {
                                               HARDWARE_REGISTER(AssetStatusEnum.WATI_REGSIST
                                                   .getCode(), "已登记资产"), HARDWARE_CONFIG_BASELINE(AssetStatusEnum.WAIT_SETTING.getCode(), "已配置基准"), HARDWARE_BASELINE_VALIDATE(AssetStatusEnum.WAIT_VALIDATE.getCode(), "已验证基准"), HARDWARE_ENTER_IMPLEMENTATION(AssetStatusEnum.WAIT_NET.getCode(), "已入网实施资产"), HARDWARE_EFFECT_CHECK(AssetStatusEnum.WAIT_CHECK.getCode(), "已入网检查资产"),
    TOPOLOGY_REGISTER(99,"拓扑登记"),
                                               WAIT_RETIRE_SCHEMA(AssetStatusEnum.NET_IN
                                                   .getCode(), "已待退役资产"), VALIDATE_RETIRE_RESULT(AssetStatusEnum.WAIT_RETIRE.getCode(), "已实施退役资产");

    AssetFlowEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    // code
    private Integer code;

    // msg
    private String  msg;

    @Override
    public Integer getCode() {
        return code;
    }


    @Override
    public String getMsg() {
        return msg;
    }

}
