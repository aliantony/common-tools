package com.antiy.asset.vo.enums;

import org.apache.commons.lang.StringUtils;

/**
 * @Auther: zhangbing
 * @Date: 2019/1/3 13:27
 * @Description:
 */
public enum AssetStatusEnum {
    WAIT_REGISTER(1, "待登记"),
    NOT_REGISTER(2, "不予登记"),
    WAIT_SETTING(3, "待配置"),
    WAIT_VALIDATE(4, "待验证"),
    WAIT_NET(5, "待入网"),
    WAIT_CHECK(6, "待检查"),
    NET_IN(7, "已入网"),
    WAIT_RETIRE(8, "待退役"),
    RETIRE(9, "已退役");

    AssetStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    // code
    private Integer code;

    // msg
    private String  msg;

    /**
     * 通过code获取资产
     * @param code
     * @return
     */
    public static AssetStatusEnum getAssetByCode(Integer code) {
        if (code != null) {
            for (AssetStatusEnum softwareType : AssetStatusEnum.values()) {
                if (softwareType.getCode().equals(code)) {
                    return softwareType;
                }
            }
        }
        return null;
    }

    /**
     * 通过code获取枚举
     *
     * @param name name
     * @return
     */
    public static AssetStatusEnum getAssetByName(String name) {
        if (StringUtils.isNotBlank(name)) {
            for (AssetStatusEnum softwareType : AssetStatusEnum.values()) {
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

    public String getMsg() {
        return msg;
    }

}
