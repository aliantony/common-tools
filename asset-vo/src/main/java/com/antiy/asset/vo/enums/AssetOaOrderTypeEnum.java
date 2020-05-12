package com.antiy.asset.vo.enums;

/**
 * @author shenliang
 * @since 2020-04-07
 */
public enum AssetOaOrderTypeEnum {

    /**
     * 入网
     */
    INNET(1,"入网"),
    /**
     * 退回
     */
    BACK(2,"退回"),
    /**
     * 报废
     */
    SCRAP(3,"报废"),
    /**
     * 出借
     */
    LEND(4,"出借");

    private Integer code;
    private String msg;

    AssetOaOrderTypeEnum(Integer code, String msg){
        this.code=code;
        this.msg=msg;
    }
    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    /**
     * 通过code获取枚举信息
     *
     * @param code
     * @return
     */
    public static AssetOaOrderTypeEnum getValueByCode(Integer code) {
        for (AssetOaOrderTypeEnum assetOaOrderTypeEnum : AssetOaOrderTypeEnum.values()) {
            if (assetOaOrderTypeEnum.code.equals(code)) {
                return assetOaOrderTypeEnum;
            }
        }
        return null;
    }
}
