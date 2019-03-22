package com.antiy.asset.vo.enums;

public enum AdmittanceStatusEnum {
                                  WAIT_SETTING(1, "待设置"), ALLOW(2, "已允许"), REFUSE(3, "已拒绝");

    private Integer code;
    private String  name;

    AdmittanceStatusEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static String getAdmittanceStatusEnum(Integer code) {
        for (AdmittanceStatusEnum admittanceStatusEnum : AdmittanceStatusEnum.values()) {
            if (admittanceStatusEnum.code.equals(code)) {
                return admittanceStatusEnum.getName();
            }
        }
        return "";
    }
}
