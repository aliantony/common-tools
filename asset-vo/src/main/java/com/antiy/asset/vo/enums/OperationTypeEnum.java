package com.antiy.asset.vo.enums;

/**
 * @author liulusheng
 * @since 2020/3/19
 */
public enum OperationTypeEnum implements CodeEnum {
    ADD(1, "add"),
    DELETE(2, "delete"),
    UPDATE(3, "update");


    OperationTypeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    int code;
    String msg;


    @Override
    public Integer getCode() {
        return null;
    }

    @Override
    public String getMsg() {
        return null;
    }
}
