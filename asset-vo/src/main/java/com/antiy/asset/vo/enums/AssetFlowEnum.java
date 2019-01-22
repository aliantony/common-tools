package com.antiy.asset.vo.enums;

/**
 * 硬件流程枚举
 *
 * @Auther: zhangyajun
 * @Date: 2019/1/14 11:36
 * @Description:
 */
public enum AssetFlowEnum implements CodeEnum {
    HARDWARE_REGISTER(AssetStatusEnum.WATI_REGSIST.getCode(),"硬件资产登记"),
    HARDWARE_CONFIG_BASELINE(AssetStatusEnum.WAIT_SETTING.getCode(),"硬件配置基准"),
    HARDWARE_BASELINE_VALIDATE(AssetStatusEnum.WAIT_VALIDATE.getCode(),"硬件基准验证"),
    HARDWARE_ENTER_IMPLEMENTATION(AssetStatusEnum.WAIT_NET.getCode(),"硬件准入实施"),
    HARDWARE_EFFECT_CHECK(AssetStatusEnum.WAIT_CHECK.getCode(),"硬件效果检查"),
    TOPOLOGY_REGISTER(99,"拓扑登记"),
    WAIT_RETIRE_SCHEMA(AssetStatusEnum.NET_IN.getCode(),"制定待退役方案"),
    VALIDATE_RETIRE_RESULT(AssetStatusEnum.WAIT_RETIRE.getCode(),"实施退役");

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
