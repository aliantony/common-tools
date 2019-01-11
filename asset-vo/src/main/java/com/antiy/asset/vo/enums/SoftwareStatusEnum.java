package com.antiy.asset.vo.enums;

import org.apache.commons.lang.StringUtils;

/**
 * @Auther: zhangbing
 * @Date: 2019/1/3 13:27
 * @Description:
 */
public enum SoftwareStatusEnum {
    WATI_REGSIST(1, "待登记"),
    NOT_REGSIST(2, "不予登记"),
    WAIT_SETTING(3, "待配置"),
    WAIT_VALIDATE(4, "待验证"),
    WAIT_NET(5, "待入网"),
    NET_IN(6, "已入网"),
    WAIT_RETIRE(7, "待退役"),
    RETIRE(8, "已退役");

    SoftwareStatusEnum(Integer code, String msg) {
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
    public static SoftwareStatusEnum getAssetByName(String name) {
        if (StringUtils.isNotBlank(name)) {
            for (SoftwareStatusEnum softwareType : SoftwareStatusEnum.values()) {
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
