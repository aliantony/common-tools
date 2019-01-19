package com.antiy.asset.vo.enums;

/**
 * 软件状态跃迁
 *
 * @author zhangyajun
 * @create 2019-01-19 10:29
 **/
public enum SoftwareStatusJumpEnum {
                                    WATI_REGSIST(1, 3, 2), WAIT_ANALYZE(3, 4, 1);
    // code
    private Integer currentStatus;

    // msg
    private Integer agreeStatus;

    private Integer refuseStatus;

    SoftwareStatusJumpEnum(Integer currentStatus, Integer agreeStatus, Integer refuseStatus) {
        this.currentStatus = currentStatus;
        this.agreeStatus = agreeStatus;
        this.refuseStatus = refuseStatus;
    }

    public static Integer getNextStatus(Integer currentStatus, Integer isAgree) {
        for (SoftwareStatusJumpEnum statusJumpEnum : SoftwareStatusJumpEnum.values()) {
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
}
