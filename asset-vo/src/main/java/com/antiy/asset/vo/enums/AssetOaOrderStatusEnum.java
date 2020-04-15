package com.antiy.asset.vo.enums;

/**
 * @author shenliang
 * @since 2020-04-07
 */
public enum AssetOaOrderStatusEnum {
    /**
     * 待处理
     */
    WAIT_HANDLE(1,"待处理"),
    /**
     * 已处理
     */
    OVER_HANDLE(2,"已处理");

    private Integer code;
    private String msg;

    AssetOaOrderStatusEnum(Integer code, String msg){
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
    public static AssetOaOrderStatusEnum getValueByCode(Integer code) {
        for (AssetOaOrderStatusEnum assetOaOrderStatusEnum : AssetOaOrderStatusEnum.values()) {
            if (assetOaOrderStatusEnum.code.equals(code)) {
                return assetOaOrderStatusEnum;
            }
        }
        return null;
    }
}
