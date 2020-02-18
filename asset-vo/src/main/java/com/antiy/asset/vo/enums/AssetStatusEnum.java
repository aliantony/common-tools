package com.antiy.asset.vo.enums;

/**
 * @Auther: zhangbing
 * @Date: 2019/1/3 13:27
 * @Description:
 */
public enum AssetStatusEnum {
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
     * 入网待审批
     */
    NET_IN_LEADER_CHECK(4,"入网待审批"),
    /**
     * 入网审批未通过
     */
    NET_IN_LEADER_DISAGREE(5,"入网审批未通过"),
    /**
     * 待准入
     */
    NET_IN_CHECK(6,"待准入"),
    /**
     * 已入网
     */
    NET_IN(7, "已入网"),
    /**
     * 变更中
     */
    IN_CHANGE(8, "变更中"),

    /**
     * 退役待审批
     */
    WAIT_RETIRE_CHECK(9, "退役待审批"),
    /**
     * 退役审批未通过
    */
    RETIRE_DISAGREE(10, "退役审批未通过"),
    /**
     * 待退役
     */
    WAIT_RETIRE(11, "待退役"),
    /**
     * 已退役
     */
    RETIRE(12, "已退役"),
    /**
     * 报废待审批
     */
    WAIT_SCRAP_CHECK(13,"报废待审批"),
    /**
     * 报废审批未通过
     */
    SCRAP_DISAGREE(14,"报废审批未通过"),
    /**
     * 待报废
     */
    WAIT_SCRAP(15,"待报废"),
    /**
     * 已报废
     */
    SCRAP(16,"已报废");

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

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
