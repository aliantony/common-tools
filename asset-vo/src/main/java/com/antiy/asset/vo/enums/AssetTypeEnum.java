package com.antiy.asset.vo.enums;

public enum AssetTypeEnum {
                           HARDWARE(1, "硬件"), SOFTWARE(2, "软件");

    private Integer code;
    private String  name;

    AssetTypeEnum(Integer code, String name) {
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
