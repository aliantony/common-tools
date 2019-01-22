package com.antiy.asset.vo.enums;

/**
 * 资产状态跃迁
 *
 * @author zhangyajun
 * @create 2019-01-15 16:13
 **/
public enum AssetStatusJumpEnum {

    WATI_REGSIST(AssetStatusEnum.WATI_REGSIST, AssetStatusEnum.WAIT_SETTING, AssetStatusEnum.NOT_REGSIST),
    WAIT_SETTING(AssetStatusEnum.WAIT_SETTING, AssetStatusEnum.WAIT_VALIDATE, AssetStatusEnum.WATI_REGSIST),
    WAIT_VALIDATE(AssetStatusEnum.WAIT_VALIDATE, AssetStatusEnum.WAIT_NET, AssetStatusEnum.WAIT_SETTING),
    WAIT_NET(AssetStatusEnum.WAIT_NET, AssetStatusEnum.WAIT_CHECK, AssetStatusEnum.WAIT_SETTING),
    WAIT_CHECK(AssetStatusEnum.WAIT_CHECK, AssetStatusEnum.NET_IN, AssetStatusEnum.WAIT_NET),
    NET_IN(AssetStatusEnum.NET_IN, AssetStatusEnum.WAIT_RETIRE, AssetStatusEnum.WAIT_VALIDATE),
    WAIT_RETIRE(AssetStatusEnum.WAIT_RETIRE, AssetStatusEnum.RETIRE, AssetStatusEnum.WAIT_RETIRE);
    // code
    private AssetStatusEnum currentStatus;

    // msg
    private AssetStatusEnum agreeStatus;

    private AssetStatusEnum refuseStatus;

    AssetStatusJumpEnum(AssetStatusEnum currentStatus, AssetStatusEnum agreeStatus, AssetStatusEnum refuseStatus) {
        this.currentStatus = currentStatus;
        this.agreeStatus = agreeStatus;
        this.refuseStatus = refuseStatus;
    }

    public static AssetStatusEnum getNextStatus(AssetStatusEnum currentStatus, Boolean isAgree) {
        if(currentStatus == null){
            return null;
        }
        for (AssetStatusJumpEnum statusJumpEnum : AssetStatusJumpEnum.values()) {
            if (statusJumpEnum.currentStatus.equals(currentStatus)) {
                return isAgree ? statusJumpEnum.getAgreeStatus() : statusJumpEnum.getRefuseStatus();
            }
        }
        return null;
    }

    public AssetStatusEnum getCurrentStatus() {
        return currentStatus;
    }

    public AssetStatusEnum getAgreeStatus() {
        return agreeStatus;
    }

    public AssetStatusEnum getRefuseStatus() {
        return refuseStatus;
    }

    public Integer getCode() {
        return null;
    }
}
