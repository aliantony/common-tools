package com.antiy.asset.vo.enums;

/**
 * 资产状态跃迁
 *
 * @author zhangyajun
 * @create 2019-01-15 16:13
 **/
public enum AssetStatusJumpEnum {
                                 WATI_REGSIST(1, 3,
                                              2), WAIT_SETTING(3, 4,
                                                               3), WAIT_VALIDATE(4, 5,
                                                                                 3), WAIT_NET(5, 6,
                                                                                              3), WAIT_CHECK(6, 7,
                                                                                                             5), NET_IN(7,
                                                                                                                        8,
                                                                                                                        7), WAIT_RETIRE(8,
                                                                                                                                        9,
                                                                                                                                        8);
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
