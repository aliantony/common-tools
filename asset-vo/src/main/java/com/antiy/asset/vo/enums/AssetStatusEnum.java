package com.antiy.asset.vo.enums;

/**
 * @Auther: zhangbing
 * @Date: 2019/1/3 13:27
 * @Description:
 */
public enum AssetStatusEnum implements CodeEnum {
    /**
     * 待登记
     */
    WAIT_REGISTER(1, "待登记"),

    /**
     * 不予登记
     */
    NOT_REGISTER(2, "不予登记"),

    /**
     * 整改中
     */
    CORRECTING(3,"整改中"),
    /**
     * 待准入
     */
    NET_IN_CHECK(4,"待准入"),
    /**
     * 已入网
     */
    NET_IN(5, "已入网"),
    /**
     * 变更中
     */
    IN_CHANGE(6, "变更中"),
    /**
     * 待退回
     */
    WAIT_RETIRE(7, "待退回"),

    /**
     * 已退回
     */
    RETIRE(8, "已退回"),
    /**
     * 待报废
     */
    WAIT_SCRAP(9,"待报废"),
    /**
     * 已报废
     */
                             SCRAP(10, "已报废"),
                             /**
                              * 已报废
                              */
                             NULL(0, "无状态");

    AssetStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    // code
    private Integer code;

    // msg
    private String  msg;

    /**
     * 通过code获取资产状态枚举
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

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

}
