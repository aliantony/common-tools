package com.antiy.asset.vo.enums;

/**
 * @auther: zhangbing
 * @date: 2019/1/17 15:30
 * @description: 资产流程引擎类型
 */
public enum AssetActivityTypeEnum {
    HARDWARE_ADMITTANCE("hardwareAdmittance","硬件入网(手动)"),
    SOFTWARE_ADMITTANCE("softwareAdmittance","软件入网(手动)"),
    HARDWARE_CHANGE("hardwareChange","硬件变更(手动)"),
    HARDWARE_RETIRE("hardwareRetire","硬件退役"),
    SOFTWARE_CHANGE("softwareChange","软件变更"),
    SOFTWARE_RETIRE("softwareRetire","软件退役"),
    HARDWARE_ADMITTANGE_AUTO("hardwareAdmittanceAuto","硬件登记(导入)"),
    SOFTWARE_ADMITTANCE_ATUO("softwareAdmittanceAuto","软件登记(导入)")
    ;

    AssetActivityTypeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    /**
     * 通过code获取枚举信息
     * @param code
     * @return
     */
    public static AssetActivityTypeEnum getActivityByCode(String code) {
        for (AssetActivityTypeEnum assetActivityTypeEnum : AssetActivityTypeEnum.values()) {
            if (assetActivityTypeEnum.code.equals(code)) {
                return assetActivityTypeEnum;
            }
        }
        return null;
    }
}
