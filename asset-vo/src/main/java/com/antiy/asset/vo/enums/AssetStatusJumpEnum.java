package com.antiy.asset.vo.enums;

/**
 * 资产状态跃迁
 *
 * @author zhangyajun
 * @create 2019-01-15 16:13
 **/
public enum AssetStatusJumpEnum {

    WATI_REGSIST(AssetStatusEnum.WATI_REGSIST.getCode(), AssetStatusEnum.WAIT_SETTING.getCode(), AssetStatusEnum.NOT_REGSIST.getCode()),
    WAIT_SETTING(AssetStatusEnum.WAIT_SETTING.getCode(), AssetStatusEnum.WAIT_VALIDATE.getCode(), AssetStatusEnum.WATI_REGSIST.getCode()),
    WAIT_VALIDATE(AssetStatusEnum.WAIT_VALIDATE.getCode(), AssetStatusEnum.WAIT_NET.getCode(), AssetStatusEnum.WAIT_SETTING.getCode()),
    WAIT_NET(AssetStatusEnum.WAIT_NET.getCode(), AssetStatusEnum.WAIT_CHECK.getCode(), AssetStatusEnum.WAIT_VALIDATE.getCode()),
    WAIT_CHECK(AssetStatusEnum.WAIT_CHECK.getCode(), AssetStatusEnum.NET_IN.getCode(), AssetStatusEnum.WAIT_NET.getCode()),
    NET_IN(AssetStatusEnum.NET_IN.getCode(), AssetStatusEnum.WAIT_RETIRE.getCode(), AssetStatusEnum.WAIT_VALIDATE.getCode()),
    WAIT_RETIRE(AssetStatusEnum.WAIT_RETIRE.getCode(), AssetStatusEnum.RETIRE.getCode(), AssetStatusEnum.WAIT_RETIRE.getCode());
    // code
    private Integer currentStatus;

    // msg
    private Integer agreeStatus;

    private Integer refuseStatus;

    AssetStatusJumpEnum(Integer currentStatus, Integer agreeStatus, Integer refuseStatus) {
        this.currentStatus = currentStatus;
        this.agreeStatus = agreeStatus;
        this.refuseStatus = refuseStatus;
    }

    public static Integer getNextStatus(Integer currentStatus, Integer isAgree) {
        for (AssetStatusJumpEnum statusJumpEnum : AssetStatusJumpEnum.values()) {
            if (statusJumpEnum.currentStatus.equals(currentStatus)) {
                if (isAgree.equals(ProcessTypeEnum.YES.getCode())) {
                    return statusJumpEnum.getAgreeStatus();
                } else {
                    return statusJumpEnum.getRefuseStatus();
                }
            }
        }
        return -1;
    }

    public Integer getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(Integer currentStatus) {
        this.currentStatus = currentStatus;
    }

    public Integer getAgreeStatus() {
        return agreeStatus;
    }

    public void setAgreeStatus(Integer agreeStatus) {
        this.agreeStatus = agreeStatus;
    }

    public Integer getRefuseStatus() {
        return refuseStatus;
    }

    public void setRefuseStatus(Integer refuseStatus) {
        this.refuseStatus = refuseStatus;
    }

    public Integer getCode() {
        return null;
    }
}
