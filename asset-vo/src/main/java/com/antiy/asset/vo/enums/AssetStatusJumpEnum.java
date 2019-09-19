package com.antiy.asset.vo.enums;

import com.antiy.common.exception.BusinessException;

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
    REGISTER(AssetStatusEnum.WAIT_REGISTER, AssetStatusEnum.WAIT_TEMPLATE_IMPL, AssetStatusEnum.WAIT_REGISTER),
    /**
     * (模板)待实施
     */
    TEMPLATE_IMPL(AssetStatusEnum.WAIT_TEMPLATE_IMPL, AssetStatusEnum.WAIT_VALIDATE, AssetStatusEnum.WAIT_REGISTER),
    /**
     * 待验证
     */
    VALIDATE(AssetStatusEnum.WAIT_VALIDATE, AssetStatusEnum.WAIT_NET, AssetStatusEnum.WAIT_TEMPLATE_IMPL),
    /**
     * 待入网
     */
    NET_IN(AssetStatusEnum.WAIT_NET, AssetStatusEnum.NET_IN, AssetStatusEnum.WAIT_VALIDATE),
    /**
     * 待整改
     */
    CORRECT(AssetStatusEnum.WAIT_CORRECT, AssetStatusEnum.WAIT_CHECK, AssetStatusEnum.WAIT_REGISTER),
    /**
     * 待检查
     */
    NET_IN_CHECK(AssetStatusEnum.WAIT_CHECK, AssetStatusEnum.NET_IN, AssetStatusEnum.WAIT_CORRECT),

    // CORRECT_CHECK(AssetStatusEnum.WAIT_CORRECT, AssetStatusEnum.NET_IN, AssetStatusEnum.WAIT_VALIDATE),
    /**
     * 拟退役
     */
    TO_WAIT_RETIRE(AssetStatusEnum.NET_IN, AssetStatusEnum.WAIT_RETIRE,AssetStatusEnum.WAIT_RETIRE),
    /**
     * 退役
     */
    RETIRE(AssetStatusEnum.WAIT_RETIRE, AssetStatusEnum.RETIRE, AssetStatusEnum.NET_IN),
    /**
     * 不予登记
     */
    NOT_REGISTER(AssetStatusEnum.NOT_REGISTER, AssetStatusEnum.NOT_REGISTER, AssetStatusEnum.NOT_REGISTER),

    /**
     * 变更:基准处理完成后状态为已入网
     */
    CHANGE_TO_NET_IN(AssetStatusEnum.IN_CHANGE, AssetStatusEnum.NET_IN, AssetStatusEnum.NET_IN);
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

    public AssetStatusEnum getAgreeStatus() {
        return agreeStatus;
    }

    public AssetStatusEnum getRefuseStatus() {
        return refuseStatus;
    }

    /**
     *
     * @param assetFlowEnum 资产当前流程
     * @param isAgree 当前操作是否通过。通过true，不通过false
     * @param isWaitCorrectToWaitRegister 整改到待登记 通过true，不通过false
     * @param isNetIn 已入网 是true，否false
     * @return AssetStatusEnum
     */
    public static AssetStatusEnum getNextStatus(AssetFlowEnum assetFlowEnum, boolean isAgree,
                                                boolean isWaitCorrectToWaitRegister, boolean isNetIn) {
        if (assetFlowEnum == null || assetFlowEnum.getCurrentAssetStatus() == null) {
            return null;
        }

        // 不予登记到登记,未入网->待实施,已入网的->待检查
        if (assetFlowEnum.equals(AssetFlowEnum.REGISTER)
                && assetFlowEnum.getCurrentAssetStatus().equals(AssetStatusEnum.NOT_REGISTER)) {
            return isNetIn ? AssetStatusEnum.WAIT_CHECK : AssetStatusEnum.WAIT_TEMPLATE_IMPL;
        }

        // 整改不通过有两种情况;待登记,待检查
        if (assetFlowEnum.equals(AssetFlowEnum.CORRECT) && !isAgree) {
            return isWaitCorrectToWaitRegister ? AssetStatusEnum.WAIT_REGISTER : AssetStatusEnum.WAIT_CHECK;
        }

        // 其他没有分支操作的情况
        for (AssetStatusJumpEnum statusJumpEnum : AssetStatusJumpEnum.values()) {
            if (statusJumpEnum.currentStatus.equals(assetFlowEnum.getCurrentAssetStatus())) {
                return isAgree ? statusJumpEnum.getAgreeStatus() : statusJumpEnum.getRefuseStatus();
            }
        }
        throw new BusinessException("获取资产下一个状态错误");
    }
}
