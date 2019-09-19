package com.antiy.asset.vo.enums;

import org.apache.commons.lang.StringUtils;

/**
 * @Auther: zhangbing
 * @Date: 2019/1/3 13:27
 * @Description:
 */
public enum AssetStatusEnum {
    // WAIT_REGISTER(1, "待登记"),
    // NOT_REGISTER(2, "不予登记"),
    // WAIT_SETTING(3, "待配置"),
    // WAIT_VALIDATE(4, "待验证"),
    // WAIT_NET(5, "待入网"),
    // WAIT_CHECK(6, "待检查"),
    // NET_IN(7, "已入网"),
    // WAIT_RETIRE(8, "待退役"),
    // RETIRE(9, "已退役");

    /**
     * 待登记
     */
    WAIT_REGISTER(1, "待登记"),

    /**
     * 不予登记
     */
    NOT_REGISTER(2, "不予登记"),

    /**
     * 模板待实施
     */
    WAIT_TEMPLATE_IMPL(3, "待实施"),
    /**
     * 待验证
     */
    WAIT_VALIDATE(4, "待验证"),
    /**
     * 待入网
     */
    WAIT_NET(5, "待入网"),
    /**
     * 已入网
     */
    NET_IN(6, "已入网"),
    /**
     * 待检查
     */
    WAIT_CHECK(7, "待检查"),
    /**
     * 待整改
     */
    WAIT_CORRECT(8, "待整改"),

    /**
     * 变更中,基准验证通过后变更为已入网
     */
    IN_CHANGE(9, "变更中"),

    /**
     * 待退役
     */
    WAIT_RETIRE(10, "待退役"),

    /**
     * 已退役
     */
    RETIRE(11, "已退役");

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
