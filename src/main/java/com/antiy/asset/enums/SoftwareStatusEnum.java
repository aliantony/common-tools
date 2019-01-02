package com.antiy.asset.enums;

import org.apache.commons.lang.StringUtils;

/**
 * @Auther: zhangbing
 * @Date: 2018/11/22 10:17
 * @Description: 软件状态枚举
 */
public enum SoftwareStatusEnum {
    IN_ACTIVE("0", "失效"), ACTIVE("1", "生效");

    SoftwareStatusEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    // code
    private String code;

    // msg
    private String msg;

    /**
     * 通过code获取枚举
     *
     * @param code code
     * @return
     */
    public static SoftwareStatusEnum getSoftStatusByCode(String code) {
        if (StringUtils.isNotBlank(code)) {
            for (SoftwareStatusEnum softwareStatusEnum : SoftwareStatusEnum.values()) {
                if (softwareStatusEnum.name().equals(code)) {
                    return softwareStatusEnum;
                }
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
