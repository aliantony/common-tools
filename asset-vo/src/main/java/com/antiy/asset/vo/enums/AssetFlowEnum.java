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
    /**
     * 登记
     */
    REGISTER(AssetStatusEnum.WAIT_REGISTER, "", "登记资产。", "登记资产信息"),
    /**
     * 实施
     */
    TEMPLATE_IMPL(AssetStatusEnum.WAIT_TEMPLATE_IMPL, "resultCheckUser", "模板实施。实施情况：", "实施资产信息"),
    /**
     * 验证
     */
    VALIDATE(AssetStatusEnum.WAIT_VALIDATE, "netImplementUser", "结果验证。验证情况：", "验证资产信息"),
    /**
     * 入网
     */
    NET_IN(AssetStatusEnum.WAIT_NET, "", "准入实施。实施情况：", "入网资产信息"),
    /**
     * 检查
     */
    CHECK(AssetStatusEnum.WAIT_CHECK, "safetyChangeUser", "安全检查。验证情况：", "检查资产信息"),
    /**
     * 整改
     */
    CORRECT(AssetStatusEnum.WAIT_CORRECT, "safetyChangeUser", "安全整改。整改情况：", "整改资产信息"),
    /**
     * 拟退役
     */
    TO_WAIT_RETIRE(AssetStatusEnum.NET_IN, "retireUserId", "待退役资产。", "拟退役资产信息"),
    /**
     * 实施退役
     */
    RETIRE(AssetStatusEnum.WAIT_RETIRE, "", "实施退役。退役情况：", "退役资产信息"),
    /**
     * 变更
     */
    CHANGE(AssetStatusEnum.IN_CHANGE, "", "变更资产信息。", "变更资产信息"),
    /**
     * 配置模块调用.
     */
    CHANGE_COMPLETE(AssetStatusEnum.IN_CHANGE, "", "变更完成。", "变更完成"),
    ;

    /**
     * 资产当前状态
     */
    private AssetStatusEnum currentAssetStatus;
    /**
     *
     * 工作流中formData中对应的下一步用户key
     */
    private String activityKey;

    /**
     * 对应流程信息,用于记录资产record表
     */
    private String  msg;

    /**
     * 对应流程信息,用于记录操作日志
     */
    private String operaLog;

    AssetFlowEnum(AssetStatusEnum currentAssetStatus, String activityKey, String msg, String operaLog) {
        this.currentAssetStatus = currentAssetStatus;
        this.activityKey = activityKey;
        this.msg = msg;
        this.operaLog = operaLog;
    }

    public String getActivityKey() {
        return activityKey;
    }

    public String getMsg() {
        return msg;
    }

    public String getOperaLog() {
        return operaLog;
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
