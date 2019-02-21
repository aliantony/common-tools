package com.antiy.asset.vo.enums;

/**
 * 软件状态跃迁
 *
 * @author zhangyajun
 * @create 2019-01-19 10:29
 **/
public enum SoftwareStatusJumpEnum {
    WATI_REGSIST(SoftwareStatusEnum.WATI_REGSIST, SoftwareStatusEnum.WAIT_ANALYZE, SoftwareStatusEnum.NOT_REGSIST),
    WAIT_ANALYZE(SoftwareStatusEnum.WAIT_ANALYZE, SoftwareStatusEnum.ALLOW_INSTALL,SoftwareStatusEnum.WATI_REGSIST),
    ALLOW_INSTALL(SoftwareStatusEnum.ALLOW_INSTALL, SoftwareStatusEnum.RETIRE, SoftwareStatusEnum.ALLOW_INSTALL);

    // code
    private SoftwareStatusEnum currentStatus;

    // msg
    private SoftwareStatusEnum agreeStatus;

    private SoftwareStatusEnum refuseStatus;

    SoftwareStatusJumpEnum(SoftwareStatusEnum currentStatus, SoftwareStatusEnum agreeStatus, SoftwareStatusEnum refuseStatus) {
        this.currentStatus = currentStatus;
        this.agreeStatus = agreeStatus;
        this.refuseStatus = refuseStatus;
    }

    public static SoftwareStatusEnum getNextStatus(SoftwareStatusEnum currentStatus, Boolean isAgree) {
        for (SoftwareStatusJumpEnum statusJumpEnum : SoftwareStatusJumpEnum.values()) {
            if (statusJumpEnum.currentStatus.equals(currentStatus)) {
                return isAgree ? statusJumpEnum.agreeStatus : statusJumpEnum.refuseStatus;
            }
        }
        return null;
    }

    public SoftwareStatusEnum getCurrentStatus() {
        return currentStatus;
    }

    public SoftwareStatusEnum getAgreeStatus() {
        return agreeStatus;
    }

    public SoftwareStatusEnum getRefuseStatus() {
        return refuseStatus;
    }
}
