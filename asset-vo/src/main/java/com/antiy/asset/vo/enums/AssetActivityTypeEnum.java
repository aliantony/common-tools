package com.antiy.asset.vo.enums;

/**
 * @auther: zhangbing
 * @date: 2019/1/17 15:30
 * @description: 资产流程引擎类型
 */
public enum AssetActivityTypeEnum {
                                   HARDWARE_ADMITTANCE("hardwareAdmittance",
                                                       "硬件入网(手动)"), SOFTWARE_ADMITTANCE("softwareAdmittance",
                                                                                        "软件入网(手动)"), HARDWARE_CHANGE("hardwareChange",
                                                                                                                     "硬件变更(手动)"),;

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
