package com.antiy.asset.vo.enums;

/**
 * 资产变更状态
 *
 * @author zhangyajun
 * @create 2019-01-15 16:13
 **/
public enum AssetStatusChangeEnum {

    NET_IN(AssetStatusEnum.NET_IN, AssetStatusEnum.WAIT_SETTING, AssetStatusEnum.NET_IN),
    WAIT_SETTING(AssetStatusEnum.WAIT_RETIRE, AssetStatusEnum.WAIT_SETTING, AssetStatusEnum.NET_IN);
    // code
    private AssetStatusEnum currentStatus;

    // msg
    private AssetStatusEnum agreeStatus;

    private AssetStatusEnum refuseStatus;

    AssetStatusChangeEnum(AssetStatusEnum currentStatus, AssetStatusEnum agreeStatus, AssetStatusEnum refuseStatus) {
        this.currentStatus = currentStatus;
        this.agreeStatus = agreeStatus;
        this.refuseStatus = refuseStatus;
    }

    public static AssetStatusEnum getNextStatus(AssetStatusEnum currentStatus, Boolean isAgree) {
        if(currentStatus == null){
            return null;
        }
        for (AssetStatusChangeEnum statusJumpEnum : AssetStatusChangeEnum.values()) {
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
