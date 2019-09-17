package com.antiy.asset.vo.enums;

/**
 * 资产状态跃迁
 *
 * @author zhangyajun
 * @create 2019-01-15 16:13
 **/
public enum AssetStatusJumpEnum {

    /**
     * 待登记
     */
    WAIT_REGISTER(AssetStatusEnum.WAIT_REGISTER, AssetStatusEnum.WAIT_TEMPLATE_IMPL, AssetStatusEnum.WAIT_REGISTER),
    /**
     * (模板)待实施
     */
    WAIT_TEMPLATE_IMPL(AssetStatusEnum.WAIT_TEMPLATE_IMPL, AssetStatusEnum.WAIT_VALIDATE, AssetStatusEnum.WAIT_REGISTER),
    /**
     * 待验证
     */
    WAIT_VALIDATE(AssetStatusEnum.WAIT_VALIDATE, AssetStatusEnum.WAIT_NET, AssetStatusEnum.WAIT_TEMPLATE_IMPL),
    /**
     * 待入网
     */
    WAIT_NET_IN(AssetStatusEnum.WAIT_NET, AssetStatusEnum.NET_IN, AssetStatusEnum.WAIT_VALIDATE),
    /**
     * 待整改
     */
    WAIT_CORRECT(AssetStatusEnum.WAIT_NET, AssetStatusEnum.WAIT_CHECK, AssetStatusEnum.WAIT_REGISTER),
    /**
     * 待检查
     */
    WAIT_CHECK(AssetStatusEnum.WAIT_NET, AssetStatusEnum.NET_IN, AssetStatusEnum.WAIT_VALIDATE),
    /**
     * 待退役
     */
    WAIT_RETIRE(AssetStatusEnum.WAIT_RETIRE, AssetStatusEnum.RETIRE, AssetStatusEnum.NET_IN),
    /**
     * 不予登记
     */
    NOT_REGISTER(AssetStatusEnum.NOT_REGISTER,AssetStatusEnum.NOT_REGISTER,AssetStatusEnum.NOT_REGISTER);

    /**
     * 当前状态
     */
    private AssetStatusEnum currentStatus;

    /**
     * 同意以后的状态
     */
    private AssetStatusEnum agreeStatus;

    /**
     * 拒绝以后的状态
     */
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

}
