package com.antiy.asset.vo.enums;

public enum ImportTypeEnum {
                            HARDWARE(1, "asset:hard:dj"), SOFTWARE(2, "asset:soft:dj");

    private Integer code;
    private String  name;

    ImportTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

}
