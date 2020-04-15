package com.antiy.asset.vo.enums;

/**
 * @author shenliang
 * @since 2020-04-07
 */
public enum AssetOaOrderStatusEnum {
    WAIT_HANDLE(1,"待处理"),
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
}
