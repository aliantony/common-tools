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
     * 待登记->登记/不予登记
     */
    REGISTER(AssetStatusEnum.WAIT_REGISTER, "", "登记资产。", "登记资产信息","不予登记资产。","不予登记资产"),
    /**
     * 不予登记->登记
     */
    NO_REGISTER(AssetStatusEnum.NOT_REGISTER,"","登记资产。", "登记资产信息"),
    /**
     * 整改中->继续入网/不予登记
     */
    CORRECT(AssetStatusEnum.CORRECTING, "", "入网申请。", "通过安全整改","不予登记资产。", "不予登记资产"),
    /**
     * 入网待审批->审批
     */
    LEADER_CHECK(AssetStatusEnum.NET_IN_LEADER_CHECK,"","入网审批。审批情况：","领导审批"),
    /**
     * 入网审批未通过->登记/不予登记
     */
    LEADER_CHECK_DISAGREE(AssetStatusEnum.NET_IN_LEADER_DISAGREE, "", "登记资产。", "登记资产信息","不予登记资产。","不予登记资产"),
      /**
     * 待准入->允许/禁止
     */
    ADMITTANCE(AssetStatusEnum.NET_IN_CHECK, "", "准入实施。准入情况：", "允许入网","准入实施。准入情况：","禁止入网"),
    /**
     * 已入网->变更/退役申请
     */
    CHANGE(AssetStatusEnum.NET_IN, "", "变更资产信息。", "变更资产信息","退役申请。","提出退役申请"),

    /**
     * 退役申请
     * 已经入网--->退役待审批
     */
    RETIRE_APPLICATION(AssetStatusEnum.NET_IN,AssetStatusEnum.WAIT_RETIRE_CHECK,AssetStatusEnum.WAIT_RETIRE_CHECK,"","退役申请","退役申请"),
    /**
     * 退役申请
     * 退役审批不通过--->退役待审批
     */
    RETIRE_DISAGREE_APPLICATION(AssetStatusEnum.RETIRE_DISAGREE,AssetStatusEnum.WAIT_RETIRE_CHECK,AssetStatusEnum.WAIT_RETIRE_CHECK,"","继续退役申请","继续退役申请"),
    /**
     * 退役执行
     */
    RETIRE_EXECUTEE(AssetStatusEnum.WAIT_RETIRE,AssetStatusEnum.RETIRE,AssetStatusEnum.RETIRE,"","退役执行","退役执行"),
    /**
     * 报废申请
     * 已退役---->报废待审批
     */
    SCRAP_APPLICATION(AssetStatusEnum.RETIRE,AssetStatusEnum.WAIT_SCRAP_CHECK,AssetStatusEnum.WAIT_RETIRE_CHECK,"","报废申请","报废申请"),
    /**
     * 报废申请
     * 报废审批未通过---->报废待审批
     */
    SCRAP_DISAGREE_APPLICATION(AssetStatusEnum.SCRAP_DISAGREE,AssetStatusEnum.WAIT_SCRAP_CHECK,AssetStatusEnum.WAIT_SCRAP_CHECK,"","继续报废申请","继续报废申请"),
    /**
     * 入网审批
     */
    NET_IN_CHECK(AssetStatusEnum.NET_IN_LEADER_CHECK,AssetStatusEnum.NET_IN,AssetStatusEnum.NET_IN_LEADER_DISAGREE,"","入网审批","入网审批"),
    /**
     * 继续入网
     */
    CONTINUE_NET_IN(AssetStatusEnum.CORRECTING,AssetStatusEnum.NET_IN_LEADER_CHECK,AssetStatusEnum.NET_IN_LEADER_CHECK,"","继续入网","继续入网"),
    /**
     * 退役审批
     */
    RETIRE_CHECK(AssetStatusEnum.WAIT_RETIRE_CHECK,AssetStatusEnum.WAIT_RETIRE,AssetStatusEnum.RETIRE_DISAGREE,"","资产退役审批","资产退役审批"),
    /**
     * 关闭退役
     */
    CLOSE_RETIRE(AssetStatusEnum.RETIRE_DISAGREE,AssetStatusEnum.NET_IN,AssetStatusEnum.NET_IN,"","关闭退役","关闭退役"),
    /**
     * 关闭报废
     */
    CLOSE_SCRAP(AssetStatusEnum.SCRAP_DISAGREE,AssetStatusEnum.RETIRE,AssetStatusEnum.RETIRE,"","关闭报废","关闭报废"),
    /**
     * 报废审批
     */
    SCRAP_CHECK(AssetStatusEnum.WAIT_SCRAP_CHECK,AssetStatusEnum.WAIT_SCRAP,AssetStatusEnum.SCRAP_DISAGREE,"","报废审批","报废审批"),

    /**
     * 报废执行
     */
    SCRAP_EXECUTEE(AssetStatusEnum.WAIT_SCRAP,AssetStatusEnum.SCRAP,AssetStatusEnum.SCRAP,"","报废执行","报废执行"),

    /**
     * 变更中->已入网    配置模块调用.
     */
    CHANGE_COMPLETE(AssetStatusEnum.IN_CHANGE,AssetStatusEnum.NET_IN, AssetStatusEnum.NET_IN, "", "变更完成。", "变更完成"),

    /**
     * 退役审批未通过->继续退役/关闭退役
     */
    RETIRE_CHECK_DISAGREE(AssetStatusEnum.RETIRE_DISAGREE, "", "退役申请。", "重新退役","关闭退役。","关闭退役"),
    /**
     * 待退役->退役执行
     */
    RETIRE(AssetStatusEnum.WAIT_RETIRE, "", "退役执行。", "执行退役"),
    /**
     * 已退役->登记/报废申请
     */
    RETIRED(AssetStatusEnum.RETIRE, "", "登记资产。", "登记资产信息","报废申请。", "提出报废申请"),

    /**
     * 报废审批未通过->继续报废/关闭报废
     */
    SCRAP_CHECK_DISAGREE(AssetStatusEnum.SCRAP_DISAGREE, "", "报废申请。", "重新报废","关闭报废。","关闭报废"),

    /**
     * 待报废->报废执行
     */
    SCRAP(AssetStatusEnum.WAIT_SCRAP, "", "报废执行。", "执行报废");



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
     * 对应流程信息的下一步,用于记录资产record表
     */
    private String nextMsg;

    /**
     * 同意以后的状态
     */
    private AssetStatusEnum agreeStatus;

    /**
     * 拒绝以后的状态
     */
    private AssetStatusEnum refuseStatus;
    /**
     * 对应流程信息的下一步,用于记录操作日志
     */
    private String nextOperaLog;
    /**
     * 对应流程信息的上一步,用于记录资产record表
     */
    private String preMsg;

    /**
     * 对应流程信息的上一步,用于记录操作日志
     */
    private String preOperaLog;

    AssetFlowEnum(AssetStatusEnum currentAssetStatus, String activityKey, String msg, String operaLog) {
        this.currentAssetStatus = currentAssetStatus;
        this.activityKey = activityKey;
        this.nextMsg = msg;
        this.nextOperaLog = operaLog;
    }
    AssetFlowEnum(AssetStatusEnum currentAssetStatus,AssetStatusEnum agreeStatus,AssetStatusEnum refuseStatus, String activityKey, String msg, String operaLog) {
        this.currentAssetStatus = currentAssetStatus;
        this.activityKey = activityKey;
        this.nextMsg = msg;
        this.nextOperaLog = operaLog;
        this.agreeStatus=agreeStatus;
        this.refuseStatus=refuseStatus;
    }
    AssetFlowEnum(AssetStatusEnum currentAssetStatus, String activityKey, String nextMsg, String nextOperaLog, String preMsg, String preOperaLog) {
        this.currentAssetStatus = currentAssetStatus;
        this.activityKey = activityKey;
        this.nextMsg = nextMsg;
        this.nextOperaLog = nextOperaLog;
        this.preMsg = preMsg;
        this.preOperaLog = preOperaLog;
    }

    public String getActivityKey() {
        return activityKey;
    }

    public String getNextMsg() {
        return nextMsg;
    }

    public String getNextOperaLog() {
        return nextOperaLog;
    }

    public AssetStatusEnum getCurrentAssetStatus() {
        return currentAssetStatus;
    }

    public AssetStatusEnum getAgreeStatus() {
        return agreeStatus;
    }

    public AssetStatusEnum getRefuseStatus() {
        return refuseStatus;
    }

    public String getPreMsg() {
        return preMsg;
    }

    public String getPreOperaLog() {
        return preOperaLog;
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
                return value.getNextMsg();
            }
        }
        return null;
    }
}
