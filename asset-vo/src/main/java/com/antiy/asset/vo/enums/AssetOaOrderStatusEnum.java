package com.antiy.asset.vo.enums;

/**
 * @author shenliang
 * @since 2020-04-07
 */
public enum AssetOaOrderStatusEnum {
    WAIT_HANDLE("1","待处理"),
    OVER_HANDLE("2","已处理");

    private String code;
    private String msg;

    AssetOaOrderStatusEnum(String code, String msg){
        this.code=code;
        this.msg=msg;
    }
    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
