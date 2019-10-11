package com.antiy.asset.vo.enums;

/**
 * 硬件操作流程枚举<br>
 * <code>currentAssetStatus</code>表示当前状态下才可进行操作
 * <p>msg作为操作日志记录event</p>
 *
 * @Auther: zhangyajun
 * @Date: 2019/1/14 11:36
 * @Description:
 */
public enum AssetFlowEnum {
    // HARDWARE_REGISTER(AssetStatusEnum.WAIT_REGISTER.getCode(), "登记资产"),
    // HARDWARE_CONFIG_BASELINE(AssetStatusEnum.WAIT_SETTING.getCode(), "配置基准"),
    // HARDWARE_BASELINE_VALIDATE(AssetStatusEnum.WAIT_VALIDATE.getCode(), "验证基准"),
    // HARDWARE_ENTER_IMPLEMENTATION(AssetStatusEnum.WAIT_NET.getCode(), "准入实施"),
    // HARDWARE_EFFECT_CHECK(AssetStatusEnum.WAIT_CHECK.getCode(), "效果检查"),
    // TOPOLOGY_REGISTER(99,"拓扑登记"),
    // WAIT_RETIRE_SCHEMA(AssetStatusEnum.NET_IN .getCode(), "待退役资产"),
    // VALIDATE_RETIRE_RESULT(AssetStatusEnum.WAIT_RETIRE.getCode(), "实施退役"),
    // HARDWARE_CHANGE(21, "变更资产"),

    // 新增
    REGISTER(AssetStatusEnum.WAIT_REGISTER, "登记资产信息"),
    TEMPLATE_IMPL(AssetStatusEnum.WAIT_TEMPLATE_IMPL, "resultCheckUser", "实施资产信息"),
    VALIDATE(AssetStatusEnum.WAIT_VALIDATE, "netImplementUser", "验证资产信息"),
    NET_IN(AssetStatusEnum.WAIT_NET, "入网资产信息"),
    CHECK(AssetStatusEnum.WAIT_CHECK, "safetyChangeUser", "检查资产信息"),
    CORRECT(AssetStatusEnum.WAIT_CORRECT, "safetyChangeUser",  "整改资产信息"),
    TO_WAIT_RETIRE(AssetStatusEnum.NET_IN, "implementUserId", "拟退役资产信息"),
    RETIRE(AssetStatusEnum.WAIT_RETIRE, "退役资产信息"),
    CHANGE(AssetStatusEnum.IN_CHANGE, "变更资产信息"),
    CHANGE_COMPLETE(AssetStatusEnum.IN_CHANGE, "变更完成"),
            ;

    /**
     * 资产当前状态
     */
    private AssetStatusEnum currentAssetStatus;
    /**
     *
     * 工作流中formData中对应的key
     */
    private String activityKey;

    /**
     * 对应流程信息,用于记录操作日志
     */
    private String  msg;

    AssetFlowEnum(AssetStatusEnum currentAssetStatus, String activityKey, String msg) {
        this.currentAssetStatus = currentAssetStatus;
        this.activityKey = activityKey;
        this.msg = msg;
    }

    AssetFlowEnum(AssetStatusEnum currentAssetStatus, String msg) {
        this.currentAssetStatus = currentAssetStatus;
        this.msg = msg;
    }

    public String getActivityKey() {
        return activityKey;
    }

    public String getMsg() {
        return msg;
    }

    public AssetStatusEnum getCurrentAssetStatus() {
        return currentAssetStatus;
    }

    /**
     * 通过资产状态获取当前流程描述msg
     *
     * @param assetStatusEnum
     * @return string
     */
    public static String  getMsgByAssetStatus(AssetStatusEnum assetStatusEnum) {
        for (AssetFlowEnum value : AssetFlowEnum.values()) {
            if (value.getCurrentAssetStatus().equals(assetStatusEnum)) {
                return value.getMsg();
            }
        }
        return null;
    }
}
