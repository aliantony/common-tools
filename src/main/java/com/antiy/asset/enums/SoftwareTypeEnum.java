package com.antiy.asset.enums;

import org.apache.commons.lang.StringUtils;

/**
 * @Auther: zhangbing
 * @Date: 2018/11/22 10:08
 * @Description:
 */
public enum SoftwareTypeEnum {
    UN_IDENTIFIED(0, "未识别"),
    EXCLUDED(1, "排除"),
    FREE_WARE(2, "免费"),
    MANAGED(3, "管理级"),
    PROHIBITED(4, "禁止"),
    SHARE_WARE(5, "共享");

    SoftwareTypeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    // code
    private Integer code;

    // msg
    private String msg;

    /**
     * 通过code获取枚举
     *
     * @param name name
     * @return
     */
    public static SoftwareTypeEnum getSoftwateByName(String name) {
        if (StringUtils.isNotBlank(name)) {
            for (SoftwareTypeEnum softwareType : SoftwareTypeEnum.values()) {
                if (softwareType.name().equals(name)) {
                    return softwareType;
                }
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
