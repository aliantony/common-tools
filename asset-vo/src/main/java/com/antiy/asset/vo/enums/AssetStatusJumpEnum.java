package com.antiy.asset.vo.enums;

import com.antiy.common.exception.BusinessException;

/**
 * 资产状态跃迁
 * <p>获取下一个状态</p>
 *
 * @author zhangyajun
 * @create 2019-01-15 16:13
 **/
public enum AssetStatusJumpEnum {

    /**
     * 待登记
     */
                                 REGISTER(AssetStatusEnum.WAIT_REGISTER, AssetStatusEnum.CORRECTING,
                                          AssetStatusEnum.WAIT_REGISTER),
    /**
     * (模板)待实施
     */
                                 TEMPLATE_IMPL(AssetStatusEnum.NULL, AssetStatusEnum.NULL, AssetStatusEnum.NULL),
    /**
     * 待验证
     */
                                 VALIDATE(AssetStatusEnum.NULL, AssetStatusEnum.NULL, AssetStatusEnum.NULL),
                                 /**
                                  * 已入网
                                  */
                                 NET_IN(AssetStatusEnum.NULL, AssetStatusEnum.NULL, AssetStatusEnum.NULL),
                                 /**
                                  * 整改中
                                  */
                                 CORRECT(AssetStatusEnum.CORRECTING, AssetStatusEnum.NET_IN_LEADER_CHECK,
                                         AssetStatusEnum.NET_IN_LEADER_DISAGREE),
                                 /**
                                  * 待准入
                                  */
                                 NET_IN_CHECK(AssetStatusEnum.NET_IN_CHECK, AssetStatusEnum.NET_IN,
                                              AssetStatusEnum.NET_IN_LEADER_CHECK),

    // CORRECT_CHECK(AssetStatusEnum.WAIT_CORRECT, AssetStatusEnum.NET_IN, AssetStatusEnum.WAIT_VALIDATE),
    /**
     * 退役申请
     */
    RETIRE_APPLICATION(AssetStatusEnum.NET_IN, AssetStatusEnum.WAIT_RETIRE_CHECK,AssetStatusEnum.WAIT_RETIRE_CHECK),
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
        if (currentStatus == null) {
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
     * @param assetFlowEnum               资产当前流程
     * @param isAgree                     当前操作是否通过。通过true，不通过false
     * @param isWaitCorrectToWaitRegister 整改到待登记 通过true，不通过false
     * @param isNetIn                     已入网 是true，否false
     * @return AssetStatusEnum
     */
    public static AssetStatusEnum getNextStatus(AssetFlowEnum assetFlowEnum, Boolean isAgree) {
        if (assetFlowEnum == null || assetFlowEnum.getCurrentAssetStatus() == null) {
            return null;
        }

        // 不予登记到登记,未入网->待实施,已入网的->待检查
        // if (assetFlowEnum.equals(AssetFlowEnum.REGISTER)
        // && assetFlowEnum.getCurrentAssetStatus().equals(AssetStatusEnum.NOT_REGISTER)) {
        // return isNetIn ? AssetStatusEnum.WAIT_CHECK : AssetStatusEnum.WAIT_TEMPLATE_IMPL;
        // }

        // 整改不通过有两种情况;待登记,待检查

        // 其他没有分支操作的情况
        for (AssetStatusJumpEnum statusJumpEnum : AssetStatusJumpEnum.values()) {
            if (statusJumpEnum.currentStatus.equals(assetFlowEnum.getCurrentAssetStatus())) {
                return isAgree ? statusJumpEnum.getAgreeStatus() : statusJumpEnum.getRefuseStatus();
            }
        }
        throw new BusinessException("获取资产下一个状态错误");
    }
}
