package com.antiy.asset.vo.enums;

/**
 * @author shenliang
 * @since 2020-04-07
 */
public enum AssetOaOrderTypeEnum {

    INNET(1,"入网"),
    BACK(2,"退回"),
    SCRAP(3,"报废"),
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
}
